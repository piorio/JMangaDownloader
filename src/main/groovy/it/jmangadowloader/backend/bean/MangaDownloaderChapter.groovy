package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import groovyx.gpars.GParsPool
import it.jmangadowloader.backend.engine.IChapter

@ToString
public class MangaDownloaderChapter implements IChapter {


	@Override
	void downloadAllPages(List<ChapterInfoContainer> chapters) {

		def one = (new Date()).toInstant()
		GParsPool.withPool(4) {
			chapters.eachParallel {
				println "-->${it}"
				def a = 2 * 2
			}
		}
		def two = (new Date()).toInstant()
		chapters.each {
			println "-->${it}"
			def a = 2 * 2
		}
		def three = (new Date()).toInstant()

		println "${one} - ${two} - ${three}"
	}
}