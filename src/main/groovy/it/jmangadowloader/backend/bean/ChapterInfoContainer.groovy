package it.jmangadowloader.backend.bean

import groovy.transform.ToString
import it.jmangadowloader.backend.engine.IChapterInfoContainer

@ToString
class ChapterInfoContainer implements IChapterInfoContainer{

    String url
    String name
    String title

    ChapterInfoContainer(String url, String name, String title) {
        this.url = url
        this.name = name
        this.title = title
    }

    @Override
    void setTitle(String title) {
        this.title = title
    }

    @Override
    void setName(String name) {
        this.name = name
    }

    @Override
    void setUrl(String url) {
        this.url = url
    }
}
