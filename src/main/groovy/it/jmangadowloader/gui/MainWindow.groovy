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
        downloader.setFolder('/tmp')

        println "BEAN: ${downloader}"

        downloader.extractAllChapters()
    }

}
