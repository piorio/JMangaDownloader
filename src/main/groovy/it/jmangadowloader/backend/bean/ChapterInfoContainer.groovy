package it.jmangadowloader.backend.bean

import groovy.transform.ToString

@ToString
class ChapterInfoContainer{

    String url
    String name
    String title

    ChapterInfoContainer(String url, String name, String title) {
        this.url = url
        this.name = name
        this.title = title
    }

}
