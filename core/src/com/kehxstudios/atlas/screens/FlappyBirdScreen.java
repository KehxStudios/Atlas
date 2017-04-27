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

    Random random = new Random();

    public FlappyBirdScreen() {
        super();
    }

    public void init() {
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
    }

    protected void reset() {
        for(int i = 0; i < TUBE_COUNT; i++) {
            tubes.get(i).setLocation(i * (TUBE_SPACING + TUBE_WIDTH) + 250, random.nextInt(TUBE_FLUCTUATION) + TUBE_LOWEST_OPENING + TUBE_GAP + TUBE_HEIGHT/2);
        }
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
        for (Entity tube : tubes) {
            if(gm.getCamera().position.x - (gm.getCamera().viewportWidth / 2) > tube.getX() + TUBE_WIDTH)
                repositionTube(tube, tube.getX() + ((TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    public void dispose() {]
        super.dispose();
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        DebugTool.log("RESUMED");
    }

    @Override
    public void hide() {

    }
}
