package it.jmangadowloader.backend.bean

import groovy.transform.ToString

@ToString
class ChapterInfoContainer{

    String name
    String title
    String link

    String imgLink
    int imgNumber

    ChapterInfoContainer(String link, String name, String title) {
        this.name = name
        this.title = title
        this.link = link
    }


    @Override
    public String toString() {
        return "ChapterInfoContainer{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", imgNumber=" + imgNumber +
                '}';
    }
}
