package com.badlogic.soulknight.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.soulknight.SoulKnight;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SoulKnight(), config);
		config.title = "Soul Knight";
		config.width = 1600;
		config.height = 900;
		config.forceExit = false;
	}
}
