package it.jmangadownloader.backend.backend

import groovy.transform.ToString

@ToString
public class Page {
	String url

	public void setUrl(String url) {
		this.url = url
	}

	public String getUrl() {
		return url
	}
}