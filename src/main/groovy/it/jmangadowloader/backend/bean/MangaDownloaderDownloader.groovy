package it.jmangadowloader.backend.bean
import groovy.transform.ToString
import groovyx.gpars.GParsPool
import groovyx.net.http.HTTPBuilder
import it.jmangadowloader.backend.engine.IDownloader
import it.jmangadowloader.exception.InvalidObjectStatus

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

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

                    String absolutePath = "${folder}/${toDownload.title}"
                    createFolder(absolutePath)

                    String chapterBaseUrl = toDownload.imgLink[0..-6]
                    println "Base URL -> ${chapterBaseUrl}"
                    (1..toDownload.imgNumber).each { imgindex ->
                        println "DOWNLOAD: ${chapterBaseUrl}${imgindex}.jpg"
                        def out = new BufferedOutputStream(new FileOutputStream("${absolutePath}/${imgindex}.jpg"))
                        out << new URL("${chapterBaseUrl}${imgindex}.jpg").openStream()
                        out.close()
                    }

                    createCbr("${absolutePath}/${toDownload.title}", absolutePath)
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

    private void createFolder(String absolutePath) {
        println "\nCREATE FOLDER -> ${absolutePath}\n"
        new File(absolutePath).mkdirs()
    }

    private void createCbr2(String filename, String absolutePath) {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(filename))
        new File(absolutePath).eachFile() { file ->
            zipFile.putNextEntry(new ZipEntry(file.getName()))
            def buffer = new byte[file.getTotalSpace()]
            file.withInputStream { i ->
                def l = i.read(buffer)
                // check wether the file is empty
                if (l > 0) {
                    zipFile.write(buffer, 0, l)
                }
            }
            zipFile.closeEntry()
        }
        zipFile.close()
    }

    private void createCbr(String filename, String absolutePath) {
        def files = []
        new File(absolutePath).eachFile() { file ->
            files.add(file.absolutePath)
            println "ADDED: ${file.absolutePath}"
        }

        println "TEST--"
        files.each {println it}

        def ant = new AntBuilder()
        def zip = new File("${absolutePath}.zip")
        ant.zip(destFile: zip.getAbsolutePath(), basedir: absolutePath, includes: files.join(' '))
    }

    String getFolder() {
        return folder
    }

    void setFolder(String folder) {
        this.folder = folder
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
