package co.qcy.pong;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Pong extends ApplicationAdapter
{
	
	private Texture raindropImage;
	private Texture bucketImage;
	private Sound waterdropSound;
	private Music rainMusic;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Rectangle bucket;
	
	private Vector3 touchPos = new Vector3();
	
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	
	@Override
	public void create ()
	{
		raindropImage = new Texture(Gdx.files.internal("raindrop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		waterdropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		bucket = new Rectangle();
		bucket.x = 800 /2 - 64 /2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
		
		rainMusic.setLooping(true);
		rainMusic.play();
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		bucket.x = touchPos.x - 64 / 2;
		
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();
		
		for(Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();)
		{
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.overlaps(bucket))
			{
				waterdropSound.play();
				iter.remove();
			}
			else if(raindrop.y + 64 < 0)
				iter.remove();
		}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop : raindrops)
			batch.draw(raindropImage, raindrop.x, raindrop.y);
		batch.end();
	}
	
	@Override
	public void dispose ()
	{
		raindropImage.dispose();
		bucketImage.dispose();
		waterdropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
	
	private void spawnRaindrop()
	{
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	
}