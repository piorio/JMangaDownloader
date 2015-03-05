package it.jmangadowloader.backend.engine

import it.jmangadowloader.backend.bean.ChapterInfoContainer

/**
 *
 */
interface IDownloader {
    List<ChapterInfoContainer> getAllChapters(String path)
    void setConfiguration(Map<String, String> parameters)
    void downloadSelectedChapters(int[] selected)

}