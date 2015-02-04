package it.jmangadowloader.backend.engine

import it.jmangadowloader.backend.bean.ChapterInfoContainer

public interface IChapter {
	void downloadAllPages(List<ChapterInfoContainer> chapters)
}