package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsManager {

    private static GraphicsManager instance;
    public static GraphicsManager getInstance() {
        if (instance == null) {
            instance = new GraphicsManager();
        }
        return instance;
    }

    private int MAX_LAYERS = 5;

    private ArrayList<ArrayList<GraphicsComponent>> graphicComponents;

    public void tick(float delta) {

    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.begin();
        for (ArrayList<GraphicsComponent> layerList : graphicComponents) {
            for (GraphicsComponent graphics : layerList) {
                if (graphics.getTexture() == null) {
                    continue;
                }
                batch.draw(graphics.getTexture(),
                        graphics.getX() - graphics.getWidth() / 2,
                        graphics.getY() - graphics.getHeight() / 2,
                        graphics.getWidth(), graphics.getHeight());
            }
        }
        batch.end();
    }

    public void dispose() {

    }

    private boolean contained(GraphicsComponent graphics) {
        if (graphicComponents.get(graphics.getLayer()).contains(graphics))
            return true;
        else
            return false;
    }

    public void add(GraphicsComponent graphics) {
        if (!contained(graphics)) {
            graphicComponents.get(graphics.getLayer()).add(graphics);
        } else {
            DebugTool.log("Failed to add graphic to graphicComponents");
        }
    }

    public void remove(GraphicsComponent graphics) {
        if (contained(graphics)) {
            graphicComponents.get(graphics.getLayer()).remove(graphics);
        } else {
            DebugTool.log("Failed to find graphic in graphicComponents");
        }
    }

    private GraphicsManager() {
        graphicComponents = new ArrayList<ArrayList<GraphicsComponent>>();
        for (int i = 0; i < MAX_LAYERS; i++) {
            graphicComponents.add(new ArrayList<GraphicsComponent>());
        }
    }
}
