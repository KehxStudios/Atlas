package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.ButtonComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

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

    private int GROUND_WIDTH;

    private Entity bird;
    private PhysicsComponent birdPhysics;
    private Entity ground1, ground2;
    private ArrayList<Entity> tubes;

    Random random = new Random();

    private float birdStartX, birdCurrentX;
    private int score = 0, lowScore, highScore;

    private BitmapFont font;
    private GlyphLayout scoreLayout;
    private String scoreText = "Score: ";

    private GlyphLayout lowScoreLayout;
    private String lowScoreText = "Low-Score: ";
    private GlyphLayout highScoreLayout;
    private String highScoreText = "High-Score: ";


    @Override
    public void init() {
        screenEntity.setY(HEIGHT/5*3);

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
        GraphicsComponent graphics = new GraphicsComponent(bird);
        graphics.setTexture(new Texture("screens/flappyBird/bird.png"));
        birdPhysics = new PhysicsComponent(bird, graphics.getTexture().getWidth(), graphics.getTexture().getHeight(), 100, -15, true);
        PhysicsManager.getInstance().setPlayer(birdPhysics);
        ButtonComponent button = new ButtonComponent(bird, birdPhysics, Input.Keys.SPACE);
        ClickableComponent clickable = new ClickableComponent(bird, birdPhysics, WIDTH,HEIGHT);
        clickable.setUniqueLocation(WIDTH/2,HEIGHT/2);
        birdStartX = bird.getX();

        gm.getCamera().position.x = bird.getX() + 80;
        gm.getCamera().update();

        ground1 = new Entity((int)(gm.getCamera().position.x - gm.getCamera().viewportWidth/2),GROUND_Y_OFFSET);
        GraphicsComponent graphics1 = new GraphicsComponent(ground1);
        graphics1.setTexture(new Texture("screens/flappyBird/ground.png"));
        GROUND_WIDTH = (int)graphics1.getWidth();

        ground2 = new Entity((int)(ground1.getX() + GROUND_WIDTH), GROUND_Y_OFFSET);
        GraphicsComponent graphics2 = new GraphicsComponent(ground2);
        graphics2.setTexture(new Texture("screens/flappyBird/ground.png"));

        tubes = new ArrayList<Entity>();

        for(int i = 1; i <= TUBE_COUNT; i++) {
            Entity topTube = new Entity(i * (TUBE_SPACING + TUBE_WIDTH) + 50, random.nextInt(TUBE_FLUCTUATION) + TUBE_LOWEST_OPENING + TUBE_GAP + TUBE_HEIGHT/2);
            tubes.add(topTube);

            GraphicsComponent topGraphic = new GraphicsComponent(topTube);
            topGraphic.setTexture(new Texture("screens/flappyBird/toptube.png"));
            PhysicsComponent tubeTop = new PhysicsComponent(topTube, topGraphic.getWidth(), topGraphic.getHeight(), 0, 0, true);


            Entity bottomTube = new Entity(topTube.getX(), topTube.getY() - TUBE_GAP - TUBE_HEIGHT);
            tubes.add(bottomTube);

            GraphicsComponent bottomGraphic = new GraphicsComponent(bottomTube);
            bottomGraphic.setTexture(new Texture("screens/flappyBird/bottomtube.png"));
            PhysicsComponent tubeBottom = new PhysicsComponent(bottomTube, bottomGraphic.getWidth(), bottomGraphic.getHeight(), 0, 0, true);

            DebugTool.log("t_x:"+topGraphic.getX(),"y:"+topGraphic.getY());
            DebugTool.log("b_x:"+bottomGraphic.getX(),"y:"+bottomGraphic.getY());
        }
    }

    public void create(ScreenSnapShot snapShot, ScreenType type, GameManager gm) {
        super.create(snapShot, type, gm);
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

    public ScreenSnapShot getSaveSnapShot() {
        ScreenSnapShot snapShot = super.getSaveSnapShot();
        return snapShot;
    }


    @Override
    public void render(float delta) {
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
    public void resetScreen() {
        if (score > lowScore) {
            highScores.addToHighScores("Test",score);
            highScore = highScores.getHighScore();
            lowScore = highScores.getLowScore();
            DebugTool.log("Score added to HighScores!");
            lowScoreLayout.setText(font, lowScoreText+lowScore, Color.BLACK,WIDTH/2, Align.left, true);
            highScoreLayout.setText(font, highScoreText+highScore, Color.BLACK,WIDTH/2, Align.left, true);
        }
        DebugTool.log("Destructing...");
        bird.destroy();
        ground1.destroy();
        ground2.destroy();
        for (Entity entity : tubes) {
            entity.destroy();
        }
        DebugTool.log("Destruction Completed. Reconstructing...");
        init();
        DebugTool.log("Reconstruction Completed.");
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
}
