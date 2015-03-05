package it.jmangadowloader.backend.engine

/**
 * Created by pablo on 04/02/15.
 */
interface IDownloader {
    void setMainUrl(String url)
    void setFolder(String folder)
    void extractAllChapters(String path)
    void setChaptersDownloader(IChapter chaptersDownloader)
    void downloadSomeChapters(int[] selected)
}