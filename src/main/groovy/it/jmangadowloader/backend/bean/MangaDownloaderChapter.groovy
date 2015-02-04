package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import it.jmangadowloader.backend.engine.IChapter

@ToString
public class MangaDownloaderChapter implements IChapter {

	String url
	String title
	String name

	public void setUrl(String url) {
		this.url = url
	}

	public String getUrl() {
		return url
	}

	@Override
	void downloadAllPages(String folder) {

	}

	@Override
	void setTitle(String title) {
		this.title = title
	}

	@Override
	void setName(String name) {
		this.name = name
	}
}