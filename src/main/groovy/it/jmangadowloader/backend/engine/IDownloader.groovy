package it.jmangadowloader.backend.engine

import it.jmangadowloader.backend.bean.ChapterInfoContainer

/**
 *
 */
interface IDownloader {
    void setMainUrl(String url)
    void setFolder(String folder)
    //void setChaptersDownloader(IChapter chaptersDownloader)


    List<ChapterInfoContainer> getAllChapters(String path)
    void setConfiguration(Map<String, String> parameters)
    void downloadSelectedChapters(int[] selected)

}