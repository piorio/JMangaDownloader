package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import it.jmangadowloader.backend.engine.IChapter
import it.jmangadowloader.backend.engine.IDownloader
import it.jmangadowloader.exception.InvalidObjectStatus
import groovyx.net.http.HTTPBuilder

@ToString
class MangaDownloaderDownloader implements IDownloader{

    String mainUrl
    String folder
    IChapter chaptersDownloader

    @Override
    void setMainUrl(String url) {
        this.mainUrl = url
    }

    @Override
    void setFolder(String folder) {
        this.folder = folder
    }

    @Override
    void extractAllChapters() {

        if(!mainUrl || !folder || !chaptersDownloader) {
            throw new InvalidObjectStatus('MainUrl o folder is invalid')
        }

        def http = new HTTPBuilder( mainUrl )
        def html = http.get(path: '/manga/angel-heart')
        def chapterInfoContainer = []

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
                def chapter = new ChapterInfoContainer("${this.mainUrl}${link}", name,  title)
                chapterInfoContainer.add(chapter)
            }
        }

        chaptersDownloader.downloadAllPages(chapterInfoContainer)
    }

    @Override
    void setChaptersDownloader(IChapter chaptersDownloader) {
        this.chaptersDownloader = chaptersDownloader
    }
}
