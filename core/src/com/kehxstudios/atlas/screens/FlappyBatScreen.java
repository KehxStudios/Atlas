package com.kehxstudios.atlas.screens;

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
    public static final int TUBE_WIDTH = 52;
    public static final int TUBE_HEIGHT = 320;
    private static final int TUBE_FLUCTUATION = 150;
    private static final int TUBE_GAP = 100;
    private static final int TUBE_LOWEST_OPENING = 175;
    
    private static final int GROUND_Y_OFFSET = 25;
    private static final int GROUND_WIDTH = 366;
    
    private static final float BAT_X_MOVEMENT = 100;
    private static final float BAT_Y_JUMP = 300;

    private static final float GRAVITY = -15;

    private Entity batEntity;
    private Entity ground1Entity, ground2Entity;
    private ArrayList<Entity> tubes;

    private PhysicsComponent batPhysics;
    private float batStartX, batCurrentX;

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

        ground1Entity = Factory.createEntity(Templates.createEntityData(0,GROUND_Y_OFFSET));

        GraphicsComponent groundGraphics = (GraphicsComponent)Factory.createComponent(ground1Entity, groundGraphicsData);
        ComponentData groundPhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0,  TextureType.FLAPPY_BAT_GROUND.getWidth(),
                TextureType.FLAPPY_BAT_GROUND.getHeight(), true);
        Factory.createComponent(ground1Entity, groundPhysicsData);

        ground2Entity = Factory.createEntity(Templates.createEntityData(GROUND_WIDTH, GROUND_Y_OFFSET));

        Factory.createComponent(ground2Entity, groundGraphicsData);
        Factory.createComponent(ground2Entity, groundPhysicsData);

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
        score = 0;
    }

    public float tubeRandomY() {
        return random.nextFloat() * TUBE_FLUCTUATION + TUBE_LOWEST_OPENING;
    }

    public boolean isOutsideOfMap() {
        if (batEntity.getPosition().x < screenEntity.getPosition().x - width /2 ||
                batEntity.getPosition().x > screenEntity.getPosition().x + width/2 ||
                batEntity.getPosition().y < GROUND_Y_OFFSET * 2 ||
                batEntity.getPosition().y > height) {
            return true;
        }
        return false;
    }


    @Override
    public void render(float delta) {
        if (batPhysics.hasCollided() || isOutsideOfMap()) {
            resetScreen();
        }

        screenEntity.setPosition(batEntity.getPosition().x + 80, height/2);
        batPhysics.setXVelocity(BAT_X_MOVEMENT);
        batPhysics.addYVelocity(GRAVITY);

        updateGround();
        checkTubes();

        batCurrentX = batEntity.getPosition().x;
        score = (int)(batCurrentX - batStartX);


        super.render(delta);
    }

    private void resetScreen() {
        highScores.addToHighScores("Test",score);
        
        screenEntity.setPosition(width/2, height/2);

        batEntity.setPosition(width/4, height/2);
        batPhysics.setVelocity(0,0);
        batPhysics.setCollided(false);
        ground1Entity.setPosition(0, GROUND_Y_OFFSET);
        ground2Entity.setPosition(GROUND_WIDTH, GROUND_Y_OFFSET);

        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.get(i).setPosition(screenEntity.getPosition().x + 100 + i * TUBE_SPACING, tubeRandomY());
        }

        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
        score = 0;
    }

    private void checkTubes() {
        for (Entity tube : tubes) {
            if (screenEntity.getPosition().x - width / 2 > tube.getPosition().x + TUBE_WIDTH/2) {
                DebugTool.log("Moving Tube");
                tube.setPosition(tube.getPosition().x + TUBE_SPACING * TUBE_COUNT, tubeRandomY());
            }
        }
    }

    private void updateGround() {
        if(screenEntity.getPosition().x - (
                width / 2) > ground1Entity.getPosition().x + GROUND_WIDTH/2)
            ground1Entity.movePosition(GROUND_WIDTH * 2,0);
        if(screenEntity.getPosition().x - (
                width / 2) > ground2Entity.getPosition().x + GROUND_WIDTH/2)
            ground2Entity.movePosition(GROUND_WIDTH * 2,0);
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

        /*
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
        */
         /*
        for(int i = 0; i < TUBE_COUNT; i++) {
            tubes.get(i).setLocation(i * (TUBE_SPACING + TUBE_WIDTH) + 250, random.nextInt(TUBE_FLUCTUATION) + TUBE_LOWEST_OPENING + TUBE_GAP + TUBE_HEIGHT/2);
        }
        */
         /*
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
        */
         /*
        for (Entity tube : tubes) {
            if(gm.getCamera().position.x - (gm.getCamera().viewportWidth / 2) > tube.getX() + TUBE_WIDTH)
                repositionTube(tube, tube.getX() + ((TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
        }
        */

}
