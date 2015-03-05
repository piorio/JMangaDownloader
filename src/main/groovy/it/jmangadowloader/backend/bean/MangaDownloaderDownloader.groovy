package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import groovyx.gpars.GParsPool
import it.jmangadowloader.backend.engine.IDownloader
import it.jmangadowloader.exception.InvalidObjectStatus
import groovyx.net.http.HTTPBuilder

@ToString
class MangaDownloaderDownloader implements IDownloader{

    String mainUrl
    String folder
    List<ChapterInfoContainer> chaptersInfoContainer

    @Override
    List<ChapterInfoContainer> getAllChapters(String path) {

        if(!mainUrl || !folder) {
            throw new InvalidObjectStatus('MainUrl o folder is invalid')
        }

        def http = new HTTPBuilder( mainUrl )
        def html = http.get(path: path)

        html."**".findAll {it.@class.toString().contains("list")}.each {
            it.children()[1].UL.children().each {
                def link = it.SPAN.A[0].@href.text()
                def spanText = it.SPAN.text()
                def title = null
                if(spanText.contains(':')) {
                    title = spanText.split(':')[1].trim()
                } else {
                    title = 'none'
                }

                def name = link?.split('/')[3]
                def chapter = new ChapterInfoContainer(link, name,  title)
                chaptersInfoContainer.add(chapter)
            }
        }
        println "CHAPTERS -> ${chaptersInfoContainer.size()}"
        //chaptersDownloader.downloadAllPagesInformation(this.mainUrl, chaptersInfoContainer)
        getChaptersInfo()

        println "DONE!!!"

        return chaptersInfoContainer
    }

    @Override
    void downloadSelectedChapters(int[] selected) {
        if(selected) {
            GParsPool.withPool(4) {
                selected.eachParallel {
                    println "Want to download ${it}"
                    ChapterInfoContainer toDownload = chaptersInfoContainer.get(it)
                    println "ChapterInfo -> ${toDownload}"

                    String chapterBaseUrl = toDownload.imgLink[0..-6]
                    println "Base URL -> ${chapterBaseUrl}"
                    (0..toDownload.imgNumber).each { imgindex ->
                        println "DOWNLOAD: ${chapterBaseUrl}${imgindex}.jpg"
                    }
                }
            }
        }
    }

    @Override
    public void setConfiguration(Map<String, String> parameters) {
        if(parameters) {
            mainUrl = parameters.get("URL")
            folder = parameters.get("FOLDER")
        } else {
            println "ERROR for parameters invalid"
        }
    }

    private void getChaptersInfo() {
        GParsPool.withPool(4) {
            chaptersInfoContainer.eachParallel { chapter ->
                //println "-->${it}"
                def http = new HTTPBuilder( mainUrl )
                def internalLink = chapter.link
                def html = http.get(path: internalLink)

                html."**".findAll {it.@class.toString().contains("reload")}.each {
                    def imgSpan = it.parent()
                    def imgNumber = Integer.parseInt(imgSpan.EM.children()[0].toString()?.split('of ')[1])
                    def imgTarget = imgSpan.EM.children()[0].@href.text()

                    chapter.imgLink = imgTarget
                    chapter.imgNumber = imgNumber
                }
            }
        }
    }

    @Override
    public String toString() {
        return "MangaDownloaderDownloader{" +
                "mainUrl='" + mainUrl + '\'' +
                ", folder='" + folder + '\'' +
                ", chaptersInfoContainer=" + chaptersInfoContainer +
                '}';
    }
}
