package fr.jonathangeoffroy.skit.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.jonathangeoffroy.skit.model.Person;

/**
 * @author Jonathan Geoffroy
 */
public class CharacterActor extends Actor {
    private static final int FRAME_SIZE = 128;
    private static final int NB_FRAMES = 8;
    private static final float TIME_PER_FRAME = 0.16f;

    private TextureRegion[] frames;
    private Animation animation;
    private float animationStateTime;

    public CharacterActor(Person person, Texture texture) {
        TextureRegion[][] splitted = TextureRegion.split(texture, FRAME_SIZE, FRAME_SIZE);
        frames = new TextureRegion[NB_FRAMES];
        System.arraycopy(splitted[0], 0, frames, 0, NB_FRAMES);
        animation = new Animation(TIME_PER_FRAME, frames);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // TODO: stop the animation as soon as the text is entirely displayed
        // We should create a listener from TextActor to know when the text is entirely displayed
        // So this Actor should listen it.

        // TODO: declare if the character is actually speaking or not.
        // If it is, we should play this animation; otherwise, we should only display the first frame.
        animationStateTime += Gdx.graphics.getDeltaTime();
        TextureRegion keyFrame = animation.getKeyFrame(animationStateTime, true);
        batch.draw(keyFrame, getX(), getY(), getWidth(), getHeight());
    }


}
