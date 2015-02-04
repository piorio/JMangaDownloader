package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import it.jmangadowloader.backend.engine.IDownloader

@ToString
class MangaDownloaderDownloader implements IDownloader{

    String mainUrl
    String folder

    @Override
    void setMainUrl(String url) {
        this.mainUrl = url
    }

    @Override
    void setFOlder(String folder) {
        this.folder = folder
    }

    @Override
    void extractAllChapters() {

    }
}
