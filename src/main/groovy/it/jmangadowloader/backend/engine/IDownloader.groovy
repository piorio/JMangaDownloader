package it.jmangadowloader.backend.engine

/**
 * Created by pablo on 04/02/15.
 */
interface IDownloader {
    void setMainUrl(String url)
    void setFOlder(String folder)
    void extractAllChapters()
}