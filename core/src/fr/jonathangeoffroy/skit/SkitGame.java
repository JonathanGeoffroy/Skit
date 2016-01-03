package fr.jonathangeoffroy.skit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.jonathangeoffroy.skit.view.actors.screens.SkitLoaderScreen;

public class SkitGame extends Game {
    SpriteBatch batch;
    private AssetManager assetManager;

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
        this.setScreen(new SkitLoaderScreen(this, "tests/test1.json"));
    }

	@Override
	public void render () {
        super.render();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
}
