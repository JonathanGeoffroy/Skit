package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.jonathangeoffroy.skit.SkitGame;
import fr.jonathangeoffroy.skit.model.Character;
import fr.jonathangeoffroy.skit.view.screens.SkitScreen;

/**
 * @author Jonathan Geoffroy
 */
public class CharacterActor extends Actor {
    private static final int FRAME_SIZE = 128;
    private static final int NB_FRAMES = 8;
    private static final float TIME_PER_FRAME = 0.16f;

    private Character character;
    private Animation animation;
    private float animationStateTime;
    private boolean speaking;

    public CharacterActor(Character character) {
        this.character = character;
        changeAnimation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // If the character is actually speaking, we display the right frame of the animation,
        // Otherwise, we display th first frame of the animation.
        TextureRegion keyFrame;
        if (speaking) {
            animationStateTime += Gdx.graphics.getDeltaTime();
            keyFrame = animation.getKeyFrame(animationStateTime, true);
        } else {
            keyFrame = animation.getKeyFrames()[0];
        }
        batch.draw(keyFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void changeAnimation() {
        Texture texture = computeTexture();
        TextureRegion[][] splitted = TextureRegion.split(texture, FRAME_SIZE, FRAME_SIZE);
        TextureRegion[] frames = new TextureRegion[NB_FRAMES];
        System.arraycopy(splitted[0], 0, frames, 0, NB_FRAMES);
        animation = new Animation(TIME_PER_FRAME, frames);
    }

    private Texture computeTexture() {
        return SkitGame.getAssetManager().get(SkitScreen.findTexturePath(character), Texture.class);
    }

    public boolean isSpeaking() {
        return speaking;
    }

    public void setSpeaking(boolean speaking) {
        this.speaking = speaking;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
