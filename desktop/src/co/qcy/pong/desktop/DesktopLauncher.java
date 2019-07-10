package co.qcy.pong.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import co.qcy.pong.Pong;

public class DesktopLauncher
{
	
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Pong(), config);
		
		config.title = "uwu";
		config.width = 480;
		config.height = 800;
		//config.fullscreen = true;
		config.addIcon("icon_128.png", FileType.Internal);
		config.addIcon("icon_32.png", FileType.Internal);
		config.addIcon("icon_16.png", FileType.Internal);
	}
	
}