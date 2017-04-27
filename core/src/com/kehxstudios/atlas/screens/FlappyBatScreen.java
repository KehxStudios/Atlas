package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.data.Templates;
import com.kehxstudios.atlas.entities.Entity;

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

    private float batStartX, batCurrentX;

    public FlappyBatScreen() {
        super();
    }

    public void finalize() {
        batEntity = Factory.createEntity(Templates.createEntityData(width/4, height/2));
        // Factory.createComponent()

        ground1Entity = Factory.createEntity(Templates.createEntityData(0,GROUND_Y_OFFSET));

        // SET GROUND_WIDTH

        ground2Entity = Factory.createEntity(Templates.createEntityData(GROUND_WIDTH, GROUND_Y_OFFSET));


        tube1Entity = Factory.createEntity(Templates.createEntityData(0,0));

        tube2Entity = Factory.createEntity(Templates.createEntityData(TUBE_SPACING, 0));



        batStartX = batEntity.getPosition().x;
        batCurrentX = batStartX;
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
