package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.UtilityTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-08.
 */

public class FlappyBirdScreen extends AScreen {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 2;
    private static final int TUBE_FLUCTUATION = 130;
    private static final int TUBE_WIDTH = 52;
    private static final int TUBE_HEIGHT = 320;
    private static final int TUBE_GAP = 100;
    private static final int TUBE_LOWEST_OPENING = 120;
    private static final int GROUND_Y_OFFSET = 25;

    private float GROUND_WIDTH;

    private Entity bird;
    private PhysicsComponent birdPhysics;
    private Entity ground1, ground2;
    private ArrayList<Entity> tubes;

    Random random = new Random();

    private float birdStartX, birdCurrentX;
    private int lowScore, highScore;

    private BitmapFont font;
    private GlyphLayout scoreLayout;
    private String scoreText = "Score: ";

    private GlyphLayout lowScoreLayout;
    private String lowScoreText = "Low-Score: ";
    private GlyphLayout highScoreLayout;
    private String highScoreText = "High-Score: ";

    public FlappyBirdScreen() {
        super();
        screenData = UtilityTool.load(ScreenType.FLAPPY_BIRD);
        init();
    }


    @Override
    public void init() {
        super.init();

        lowScore =  highScores.getLowScore();
        highScore = highScores.getHighScore();

        font = new BitmapFont();
        scoreLayout = new GlyphLayout(font, scoreText+score);
        scoreLayout.setText(font, scoreText+score, Color.BLACK,WIDTH/2, Align.center, true);
        lowScoreLayout = new GlyphLayout(font, lowScoreText+lowScore);
        lowScoreLayout.setText(font, lowScoreText+lowScore, Color.BLACK,WIDTH/2, Align.center, true);
        highScoreLayout = new GlyphLayout(font, highScoreText+highScore);
        highScoreLayout.setText(font, highScoreText+highScore, Color.BLACK,WIDTH/2, Align.center, true);

        bird = new Entity(WIDTH/4,HEIGHT/2);
        GraphicsComponent graphics = new GraphicsComponent(bird, TextureType.FLAPPYBIRD_BIRD, 3);
        birdPhysics = new PhysicsComponent(bird, graphics.getWidth(), graphics.getHeight(), 100, -15, true);
        PhysicsManager.getInstance().setPlayer(birdPhysics);
        ClickableComponent clickable = new ClickableComponent(screenEntity, WIDTH, HEIGHT,
                false, new PhysicsAction(birdPhysics, new Vector2(0,250)));
        birdStartX = bird.getX();

        gm.getCamera().position.x = bird.getX() + 80;
        gm.getCamera().update();

        ground1 = new Entity((int)(gm.getCamera().position.x - gm.getCamera().viewportWidth/2),GROUND_Y_OFFSET);
        GraphicsComponent ground1Graphics = new GraphicsComponent(ground1, TextureType.FLAPPYBIRD_GROUND, 2);
        GROUND_WIDTH = ground1Graphics.getWidth();

        ground2 = new Entity((int)(ground1.getX() + GROUND_WIDTH), GROUND_Y_OFFSET);
        GraphicsComponent ground2Graphics = new GraphicsComponent(ground2, TextureType.FLAPPYBIRD_GROUND, 2);

        tubes = new ArrayList<Entity>();

        for(int i = 0; i < TUBE_COUNT; i++) {
            Entity tube = new Entity(i * (TUBE_SPACING + TUBE_WIDTH) + 250, random.nextInt(TUBE_FLUCTUATION) + TUBE_LOWEST_OPENING + TUBE_GAP + TUBE_HEIGHT/2);

            GraphicsComponent topGraphic = new GraphicsComponent(tube, TextureType.FLAPPYBIRD_TOPTUBE, 1);
            PhysicsComponent topPhysics = new PhysicsComponent(tube, topGraphic.getWidth(), topGraphic.getHeight(), 0, 0, true);

            GraphicsComponent bottomGraphic = new GraphicsComponent(tube, TextureType.FLAPPYBIRD_BOTTOMTUBE, 1);
            bottomGraphic.setUsePositionAsOffset(true);
            bottomGraphic.setLocation(0, -TUBE_GAP - TUBE_HEIGHT);
            PhysicsComponent bottomPhysics = new PhysicsComponent(tube, bottomGraphic.getWidth(), bottomGraphic.getHeight(), 0, 0, true);
            bottomPhysics.setUsePositionAsOffset(true);
            bottomPhysics.setLocation(0, -TUBE_GAP - TUBE_HEIGHT);

            tubes.add(tube);
        }
    }

    protected void reset() {
        if (score > lowScore) {
            highScores.addToHighScores("Test",score);
        }
        super.reset();

        lowScore =  highScores.getLowScore();
        highScore = highScores.getHighScore();

        scoreLayout.setText(font, scoreText+score, Color.BLACK,WIDTH/2, Align.center, true);
        lowScoreLayout.setText(font, lowScoreText+lowScore, Color.BLACK,WIDTH/2, Align.center, true);
        highScoreLayout.setText(font, highScoreText+highScore, Color.BLACK,WIDTH/2, Align.center, true);

        bird.setLocation(WIDTH/4,HEIGHT/2);
        birdStartX = bird.getX();

        gm.getCamera().position.x = bird.getX() + 80;
        gm.getCamera().update();

        ground1.setLocation((gm.getCamera().position.x - gm.getCamera().viewportWidth/2),GROUND_Y_OFFSET);
        ground2.setLocation((ground1.getX() + GROUND_WIDTH),GROUND_Y_OFFSET);

        for(int i = 0; i < TUBE_COUNT; i++) {
            tubes.get(i).setLocation(i * (TUBE_SPACING + TUBE_WIDTH) + 250, random.nextInt(TUBE_FLUCTUATION) + TUBE_LOWEST_OPENING + TUBE_GAP + TUBE_HEIGHT/2);
        }
    }

    public void birdJump() {
        birdPhysics.velocity.y = 250;
    }

    private void repositionTube(Entity entity, float x) {
        for(Entity tube : tubes) {
            if (tube.getX() == entity.getX() && entity != tube) {
                entity.setY(random.nextInt(TUBE_FLUCTUATION) + TUBE_GAP + TUBE_LOWEST_OPENING + TUBE_HEIGHT/2);
                entity.setX(x);
                ((PhysicsComponent)entity.getComponentByType(ComponentType.PHYSICS)).updateBounds();

                tube.setY(entity.getY() - TUBE_GAP - TUBE_HEIGHT);
                tube.setX(x);
                ((PhysicsComponent)tube.getComponentByType(ComponentType.PHYSICS)).updateBounds();
                return;
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (birdPhysics.hasCollided) {
            reset();
        }
        super.render(delta);
        updateGround();
        gm.getCamera().position.x = bird.getX() + 80;
        gm.getCamera().update();
        screenEntity.setX(gm.getCamera().position.x - (gm.getCamera().viewportWidth/2) + WIDTH/2);

        for (Entity tube : tubes) {
            if(gm.getCamera().position.x - (gm.getCamera().viewportWidth / 2) > tube.getX() + TUBE_WIDTH)
                repositionTube(tube, tube.getX() + ((TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
        }

        birdCurrentX = bird.getX();
        score = (int)(birdCurrentX - birdStartX);
        scoreLayout.setText(font, scoreText+score, Color.BLACK,0, Align.center, true);

        gm.getBatch().begin();
        font.draw(gm.getBatch(), scoreLayout, gm.getCamera().position.x, 55);
        font.draw(gm.getBatch(), lowScoreLayout, gm.getCamera().position.x - lowScoreLayout.width/2, 40);
        font.draw(gm.getBatch(), highScoreLayout, gm.getCamera().position.x - highScoreLayout.width/2, 25);
        gm.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void dispose() {
        font.dispose();
        bird.destroy();
        ground1.destroy();
        ground2.destroy();
        for (Entity entity : tubes) {
            entity.destroy();
        }
        super.dispose();
    }

    private void updateGround() {
        if(gm.getCamera().position.x - (gm.getCamera().viewportWidth / 2) > ground1.getX() + GROUND_WIDTH/2)
            ground1.move(GROUND_WIDTH * 2,0);
        if(gm.getCamera().position.x - (gm.getCamera().viewportWidth / 2) > ground2.getX() + GROUND_WIDTH/2)
            ground2.move(GROUND_WIDTH * 2,0);
    }


    @Override
    public void pause() {
        DebugTool.log("PAUSED");
        dispose();
    }

    @Override
    public void resume() {
        DebugTool.log("RESUMED");
    }

    @Override
    public void hide() {

    }
}
