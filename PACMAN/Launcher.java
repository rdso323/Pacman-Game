package PACMAN;

import javafx.application.Application;

/**
 * Entry point for packaged builds. jpackage should use this class instead of Main.
 */
public class Launcher {
	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}
}
