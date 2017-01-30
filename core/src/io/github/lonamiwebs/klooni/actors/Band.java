package io.github.lonamiwebs.klooni.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import io.github.lonamiwebs.klooni.game.GameLayout;
import io.github.lonamiwebs.klooni.game.Scorer;

// Score and pause menu band actually
public class Band extends Actor {

    private final Scorer scorer;
    private final Texture bandTexture;

    public final Rectangle scoreBounds;
    public final Rectangle infoBounds;

    public final Label infoLabel;
    public final Label scoreLabel;

    public Band(final GameLayout layout, final Scorer aScorer, final Color bandColor) {
        scorer = aScorer;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(bandColor);
        pixmap.fill();
        bandTexture = new Texture(pixmap);
        pixmap.dispose();

        Label.LabelStyle scoreStyle = new Label.LabelStyle();
        scoreStyle.font = new BitmapFont(Gdx.files.internal("font/geosans-light.fnt"));

        scoreLabel = new Label("", scoreStyle);
        scoreLabel.setAlignment(Align.center);
        infoLabel = new Label("pause menu", scoreStyle);
        infoLabel.setAlignment(Align.center);

        scoreBounds = new Rectangle();
        infoBounds = new Rectangle();
        layout.update(this);
    }

    public void setGameOver() {
        infoLabel.setText("no moves left");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO This is not the best way to apply the transformation, but, oh well
        float x = getParent().getX();
        float y = getParent().getY();

        // TODO For some strange reason, the texture coordinates and label coordinates are different
        Vector2 pos = localToStageCoordinates(new Vector2(x, y));
        batch.draw(bandTexture, pos.x, pos.y, getWidth(), getHeight());

        scoreLabel.setBounds(x + scoreBounds.x, y + scoreBounds.y, scoreBounds.width, scoreBounds.height);
        scoreLabel.setText(Integer.toString(scorer.getCurrentScore()));
        scoreLabel.draw(batch, parentAlpha);

        infoLabel.setBounds(x + infoBounds.x, y + infoBounds.y, infoBounds.width, infoBounds.height);
        infoLabel.draw(batch, parentAlpha);
    }
}
