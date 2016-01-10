package fr.jonathangeoffroy.skit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.jonathangeoffroy.skit.view.screens.SkitLoaderScreen;

public class SkitGame extends Game {
    private SpriteBatch batch;
    private final static AssetManager assetManager = new AssetManager();

    @Override
	public void create () {
		batch = new SpriteBatch();
        this.setScreen(new SkitLoaderScreen(this, "tests/test1.json"));
    }

	@Override
	public void render () {
        super.render();
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
}
