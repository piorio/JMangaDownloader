package it.jmangadowloader.backend.engine

import it.jmangadowloader.backend.bean.ChapterInfoContainer

public interface IChapter {
	void downloadAllPagesInformation(String mainUrl, List<ChapterInfoContainer> chapters)
    void downloadSelectedChapters(int[] selected, List<ChapterInfoContainer> allChapters)
}