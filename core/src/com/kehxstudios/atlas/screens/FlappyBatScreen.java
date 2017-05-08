package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ScreenType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ReidC on 2017-04-26.
 */

public class FlappyBatScreen extends AScreen {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 3;
    private static final int TUBE_WIDTH = 52;
    private static final int TUBE_HEIGHT = 320;
    private static final int TUBE_FLUCTUATION = 150;
    private static final int TUBE_GAP = 100;
    private static final int TUBE_LOWEST_OPENING = 175;

    private static final int GROUND_COUNT = 2;
    private static final int GROUND_Y_OFFSET = 25;
    private static final int GROUND_WIDTH = 366;
    
    private static final float BAT_X_MOVEMENT = 100;
    private static final float BAT_Y_JUMP = 300;

    private static final float GRAVITY = -15;

    private Entity batEntity;
    private ArrayList<Entity> grounds;
    private ArrayList<Entity> tubes;

    private PhysicsComponent batPhysics;
    private float batStartX, batCurrentX;

    private int lowScore, highScore;
    private FloatingTextComponent scoreText, lowScoreText, highScoreText;

    private Random random = new Random();

    public FlappyBatScreen() {
        super(ScreenType.FLAPPY_BAT);

        screenGraphics.setTextureType(TextureType.FLAPPY_BAT_BACKGROUND);
        screenGraphics.setEnabled(true);
        
        batEntity = Factory.createEntity(Templates.createEntityData(width/4, height/2));
        ComponentData batGraphicsData = Templates.createGraphicsComponentData(0,0,2, TextureType.FLAPPY_BAT_BAT);
        GraphicsComponent batGraphics = (GraphicsComponent)Factory.createComponent(batEntity, batGraphicsData);
        ComponentData batPhysicsData = Templates.createPhysicsComponentData(100, 300, 100, 300, TextureType.FLAPPY_BAT_BAT.getWidth(),
                TextureType.FLAPPY_BAT_BAT.getHeight(), true);
        batPhysics = (PhysicsComponent)Factory.createComponent(batEntity, batPhysicsData);
        PhysicsManager.getInstance().setPlayer(batPhysics);
        ActionData batPhysicsAction = Templates.createPhysicsActionData(0, BAT_Y_JUMP);
        ComponentData batClickableData = Templates.createClickableComponentData(width, height, false, batPhysicsAction);
        ClickableComponent batClickable = (ClickableComponent)Factory.createComponent(screenEntity, batClickableData);
        ((PhysicsAction)batClickable.getAction()).setPhysicsComponent(batPhysics);

        ComponentData groundGraphicsData = Templates.createGraphicsComponentData(0, 0, 2, TextureType.FLAPPY_BAT_GROUND);
        ComponentData groundPhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0,  TextureType.FLAPPY_BAT_GROUND.getWidth(),
                TextureType.FLAPPY_BAT_GROUND.getHeight(), true);

        grounds = new ArrayList<Entity>();

        for (int i = 0; i < GROUND_COUNT; i++) {
            Entity ground = Factory.createEntity(Templates.createEntityData(0,GROUND_Y_OFFSET));
            Factory.createComponent(ground, groundGraphicsData);
            Factory.createComponent(ground, groundPhysicsData);
            grounds.add(ground);
        }

        ComponentData tubeGraphicsData = Templates.createGraphicsComponentData(0, 0, 1, TextureType.FLAPPY_BAT_WALL);
        ComponentData tubePhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0, TextureType.FLAPPY_BAT_WALL.getWidth(),
                TextureType.FLAPPY_BAT_WALL.getHeight(), true);

        tubes = new ArrayList<Entity>();

        for (int i = 0; i < TUBE_COUNT; i++) {
            Entity tube = Factory.createEntity(Templates.createEntityData(
                    screenEntity.getPosition().x + 100 + i * TUBE_SPACING, tubeRandomY()));
            GraphicsComponent tubeTopGraphics = (GraphicsComponent)Factory.createComponent(tube, tubeGraphicsData);
            tubeTopGraphics.setUsePositionAsOffset(true);
            tubeTopGraphics.setPosition(0, TUBE_GAP/2 + TUBE_HEIGHT/2);

            GraphicsComponent tubeBottomGraphics = (GraphicsComponent)Factory.createComponent(tube, tubeGraphicsData);
            tubeBottomGraphics.setUsePositionAsOffset(true);
            tubeBottomGraphics.setPosition(0, -(TUBE_GAP/2 + TUBE_HEIGHT/2));

            PhysicsComponent tubeTopPhysics = (PhysicsComponent)Factory.createComponent(tube, tubePhysicsData);
            tubeTopPhysics.setUsePositionAsOffset(true);
            tubeTopPhysics.setPosition(0, TUBE_GAP/2 + TUBE_HEIGHT/2);
            PhysicsComponent tubeBottomPhysics = (PhysicsComponent)Factory.createComponent(tube, tubePhysicsData);
            tubeBottomPhysics.setUsePositionAsOffset(true);
            tubeBottomPhysics.setPosition(0, -(TUBE_GAP/2 + TUBE_HEIGHT/2));

            tubes.add(tube);
        }

        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
        lowScore = highScores.getLowScore();
        highScore = highScores.getHighScore();

        scoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.createFloatingTextComponentData("Score", score+"", 1));
        scoreText.setUsePositionAsOffset(true);
        scoreText.setPosition(0, -height/2 + 60);


        lowScoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.createFloatingTextComponentData("Low-Score", lowScore+"", 1));
        lowScoreText.setUsePositionAsOffset(true);
        lowScoreText.setPosition(0, -height/2 + 40);


        highScoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.createFloatingTextComponentData("High-Score", highScore+"", 1));
        highScoreText.setUsePositionAsOffset(true);
        highScoreText.setPosition(0, -height/2 + 20);
    }

    public float tubeRandomY() {
        return random.nextFloat() * TUBE_FLUCTUATION + TUBE_LOWEST_OPENING;
    }

    @Override
    public void render(float delta) {
        if (batPhysics.hasCollided() || batEntity.getPosition().y > height) {
            resetScreen();
        }

        screenEntity.setPosition(batEntity.getPosition().x + 80, height/2);
        batPhysics.setXVelocity(BAT_X_MOVEMENT);
        batPhysics.addYVelocity(GRAVITY);

        updateGround();
        updateTubes();

        batCurrentX = batEntity.getPosition().x;
        score = (int)(batCurrentX - batStartX);
        scoreText.setText(score+"");
        scoreText.getLayout().setText(scoreText.getFont(), scoreText.getLabel() + scoreText.getText(),
            Color.BLACK, 0, Align.left, true);
        DebugTool.log("score: " + score);

        super.render(delta);
    }

    private void resetScreen() {
        if (score > lowScore) {
            highScores.addToHighScores("Test",score);
            lowScore = highScores.getLowScore();
            lowScoreText.setText(lowScore+"");
            lowScoreText.getLayout().setText(lowScoreText.getFont(), lowScoreText.getLabel() + lowScoreText.getText(),
            Color.BLACK, 0, Align.left, true);
            highScore = highScores.getHighScore();
            highScoreText.setText(highScore+"");
            highScoreText.getLayout().setText(highScoreText.getFont(), highScoreText.getLabel() + highScoreText.getText(),
            Color.BLACK, 0, Align.left, true);
        }
        
        screenEntity.setPosition(width/2, height/2);

        batEntity.setPosition(width/4, height/2);
        batPhysics.setVelocity(0,0);
        batPhysics.setCollided(false);
        for (int i = 0; i < GROUND_COUNT; i++) {
            grounds.get(i).setPosition(i * GROUND_WIDTH, GROUND_Y_OFFSET);
        }
        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.get(i).setPosition(screenEntity.getPosition().x + 100 + i * TUBE_SPACING, tubeRandomY());
        }

        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
        score = 0;
    }

    private void updateTubes() {
        for (Entity tube : tubes) {
            if (screenEntity.getPosition().x - width / 2 > tube.getPosition().x + TUBE_WIDTH/2) {
                DebugTool.log("Moving Tube");
                tube.setPosition(tube.getPosition().x + TUBE_SPACING * TUBE_COUNT, tubeRandomY());
            }
        }
    }

    private void updateGround() {
        for (Entity ground : grounds) {
            if(screenEntity.getPosition().x - (width / 2) >
                    ground.getPosition().x + GROUND_WIDTH/2) {
                ground.movePosition(GROUND_WIDTH * GROUND_COUNT, 0);
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

}
