package it.jmangadowloader.gui

import it.jmangadowloader.backend.engine.IDownloader

import javax.swing.JFrame
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * Created by pablo on 04/02/15.
 */
class MainWindow extends JFrame {

    MainWindow() {
        println "STUB FOR GUI - NOW ONLY TEST"

        ApplicationContext context = new ClassPathXmlApplicationContext('mangadownloader.xml')
        IDownloader downloader = context.getBean("downloader", IDownloader.class)

        println "DOWNLOADER: ${downloader}"

        def lista = downloader.getAllChapters('/manga/angel-heart')

        println "\n\nAFTER\n\n"
        lista.each {
            println it
        }

        downloader.downloadSelectedChapters([1] as int[])

    }

}
