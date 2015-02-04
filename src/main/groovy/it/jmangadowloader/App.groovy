package it.jmangadownloader

import it.jmangadowloader.gui.MainWindow

import javax.swing.SwingUtilities

public class App {

	public static final String VERSION = "0.0.1"
	public static final String NAME = "JMangaDownloader"

	public static void main(String[] args) {
		
		if(args.size() == 1) {
			if(args[0] == '-h' || args[0] == '--help') {
				App.showHelp
			} else {
				println "Invalid command parameter. Accepted:"
				println "\t-h|--help to show help"
			}
		} else {
			println "GUI"
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				void run() {
					MainWindow frame = new MainWindow()

				}
			})
		}

	}

	public static void showHelp() {
		println "${NAME} - ${VERSION}"
	}
}