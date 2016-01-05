package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.jonathangeoffroy.skit.controller.observer.TextActorListener;

/**
 * @author Jonathan Geoffroy
 */
public class CharacterActor extends Actor implements TextActorListener {
    private static final int FRAME_SIZE = 128;
    private static final int NB_FRAMES = 8;
    private static final float TIME_PER_FRAME = 0.16f;

    private Animation animation;
    private float animationStateTime;
    private boolean speaking;

    public CharacterActor(Texture texture) {
        TextureRegion[][] splitted = TextureRegion.split(texture, FRAME_SIZE, FRAME_SIZE);
        TextureRegion[] frames = new TextureRegion[NB_FRAMES];
        System.arraycopy(splitted[0], 0, frames, 0, NB_FRAMES);
        animation = new Animation(TIME_PER_FRAME, frames);
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

    /**
     * Stop the animation as soon as the text is entirely displayed
     */
    @Override
    public void onTextDisplayed() {
        speaking = false;
    }

    public boolean isSpeaking() {
        return speaking;
    }

    public void setSpeaking(boolean speaking) {
        this.speaking = speaking;
    }
}
