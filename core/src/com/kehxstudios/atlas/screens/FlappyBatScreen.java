package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.ai.pfa.Graph;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.data.Templates;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;

/**
 * Created by ReidC on 2017-04-26.
 */

public class FlappyBatScreen extends AScreen {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 2;
    private static final int TUBE_FLUCTUATION = 130;
    private static final int TUBE_WIDTH = 52;
    private static final int TUBE_HEIGHT = 320;
    private static final int TUBE_GAP = 100;
    private static final int TUBE_LOWEST_OPENING = 120;
    private static final int GROUND_Y_OFFSET = 25;

    private float GROUND_WIDTH;

    private Entity batEntity;
    private Entity ground1Entity, ground2Entity;
    private Entity tube1Entity, tube2Entity;

    private PhysicsComponent batPhysics;

    private float batStartX, batCurrentX;
    private int lowHighScore, highestScore;

    public FlappyBatScreen() {
        super();
        type = ScreenType.FLAPPY_BAT;
        lowHighScore =  highScores.getLowScore();
        highestScore = highScores.getHighScore();
    }

    public void finalize() {
        batEntity = Factory.createEntity(Templates.createEntityData(width/4, height/2));
        ComponentData batGraphicsData = Templates.createGraphicsComponentData(0,0,2, TextureType.FLAPPYBIRD_BIRD);
        GraphicsComponent batGraphics = (GraphicsComponent)Factory.createComponent(batEntity, batGraphicsData);
        ComponentData batPhysicsData = Templates.createPhysicsComponentData(100, 300, 100, 300, batGraphics.getWidth(),
                batGraphics.getHeight(), true);
        batPhysics = (PhysicsComponent)Factory.createComponent(batEntity, batPhysicsData);
        ActionData batPhysicsAction = Templates.createPhysicsActionData(0, 300);
        ComponentData batClickable = Templates.createClickableComponentData(width, height, false, batPhysicsAction);


        ComponentData groundGraphicsData = Templates.createGraphicsComponentData(0, 0, 1, TextureType.FLAPPYBIRD_GROUND);

        ground1Entity = Factory.createEntity(Templates.createEntityData(0,GROUND_Y_OFFSET));
        GraphicsComponent groundGraphics = (GraphicsComponent)Factory.createComponent(ground1Entity, groundGraphicsData);
        ComponentData groundPhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0, groundGraphics.getWidth(),
                groundGraphics.getHeight(), true);
        Factory.createComponent(ground1Entity, groundPhysicsData);
        GROUND_WIDTH = groundGraphics.getWidth();

        ground2Entity = Factory.createEntity(Templates.createEntityData(GROUND_WIDTH, GROUND_Y_OFFSET));
        Factory.createComponent(ground2Entity, groundGraphicsData);
        Factory.createComponent(ground2Entity, groundPhysicsData);

        ComponentData tubeGraphicsData = Templates.createGraphicsComponentData(0, 0, 2, TextureType.FLAPPYBIRD_TOPTUBE);

        tube1Entity = Factory.createEntity(Templates.createEntityData(0,0));
        GraphicsComponent tubeGraphics = (GraphicsComponent)Factory.createComponent(tube1Entity, tubeGraphicsData);
        ComponentData tubePhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0, tubeGraphics.getWidth(),
                tubeGraphics.getHeight(), true);
        Factory.createComponent(tube1Entity, tubePhysicsData);

        tube2Entity = Factory.createEntity(Templates.createEntityData(TUBE_SPACING, 0));
        Factory.createComponent(tube2Entity, tubeGraphicsData);
        Factory.createComponent(tube2Entity, tubePhysicsData);

        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
        score = 0;
    }

    private void resetScreen() {
        if (score > lowHighScore) {
            highScores.addToHighScores("Test",score);
            lowHighScore =  highScores.getLowScore();
            highestScore = highScores.getHighScore();
        }

        screenEntity.setPosition(width/2, height/2);

        batEntity.setPosition(width/4, height/2);
        ground1Entity.setPosition(0, GROUND_Y_OFFSET);
        ground2Entity.setPosition(GROUND_WIDTH, GROUND_Y_OFFSET);

        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
        score = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (batPhysics.hasCollided()) {
            resetScreen();
        }

        updateGround();

        GameManager.getInstance().getCamera().position.x = batEntity.getPosition().x + 80;
        GameManager.getInstance().getCamera().update();

        screenEntity.movePosition(GameManager.getInstance().getCamera().position.x - (
                GameManager.getInstance().getCamera().viewportWidth/2) + width/2, 0);


        batCurrentX = batEntity.getPosition().x;
        score = (int)(batCurrentX - batStartX);
    }

    private void updateGround() {
        if(GameManager.getInstance().getCamera().position.x - (
                GameManager.getInstance().getCamera().viewportWidth / 2) > ground1Entity.getPosition().x + GROUND_WIDTH/2)
            ground1Entity.movePosition(GROUND_WIDTH * 2,0);
        if(GameManager.getInstance().getCamera().position.x - (
                GameManager.getInstance().getCamera().viewportWidth / 2) > ground2Entity.getPosition().x + GROUND_WIDTH/2)
            ground2Entity.movePosition(GROUND_WIDTH * 2,0);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
