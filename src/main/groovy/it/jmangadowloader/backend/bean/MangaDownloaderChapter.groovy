package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import groovyx.gpars.GParsPool
import groovyx.net.http.HTTPBuilder
import it.jmangadowloader.backend.engine.IChapter

@ToString
public class MangaDownloaderChapter implements IChapter {


	@Override
	void downloadAllPagesInformation(String mainUrl, List<ChapterInfoContainer> chapters) {

		GParsPool.withPool(4) {
			chapters.eachParallel { chapter ->
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

        /*
		println "\n\nAFTER\n\n"
		chapters.each {println it}
		*/
	}

    @Override
    void downloadSelectedChapters(int[] selected, List<ChapterInfoContainer> allChapters) {
        if(selected && allChapters) {
            selected.each {
                println "Want to download ${it}"
                ChapterInfoContainer toDownload = allChapters.get(it)
                println "ChapterInfo -> ${toDownload}"

                String chapterBaseUrl = toDownload.imgLink[0..-6]
                println "Base URL -> ${chapterBaseUrl}"
                toDownload.imgNumber.each { img ->
                    println "DOWNLOAD: ${chapterBaseUrl}/${it}.jpg"
                }
            }
        }
    }
}