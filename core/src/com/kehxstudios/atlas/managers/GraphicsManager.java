package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.SpriteType;
import com.kehxstudios.atlas.tools.DataTool;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsManager extends Manager {

    private static GraphicsManager instance;
    public static GraphicsManager getInstance() {
        if (instance == null) {
            instance = new GraphicsManager();
        }
        return instance;
    }

    private int MAX_LAYERS = 5;

    private ArrayList<ArrayList<GraphicsComponent>> graphicComponents;
    private ArrayList<AnimationComponent> animationComponents;

    private TextureAtlas textureAtlas;
    private SpriteType spriteTypes;

    public void tick(float delta) {
        if (animationComponents.size() == 0) {
            return;
        }
        for (AnimationComponent animation : animationComponents) {
            if (animation.isEnabled()) {
                animation.update(delta);
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.begin();
        for (ArrayList<GraphicsComponent> layerList : graphicComponents) {
            if (layerList.size() == 0) {
                continue;
            }
            for (GraphicsComponent graphics : layerList) {
                if (graphics.getSprite() == null || !graphics.isEnabled()) {
                    continue;
                }
                batch.draw(graphics.getSprite().getTexture(),
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

    public void add(AnimationComponent animation) {
        if (!animationComponents.contains(animation)) {
            animationComponents.add(animation);
        } else {
            DebugTool.log("Failed to add animation to animationComponents");
        }
    }

    public void remove(AnimationComponent animation) {
        if (animationComponents.contains(animation)) {
            animationComponents.remove(animation);
        } else {
            DebugTool.log("Failed to find animation in animationComponents");
        }
    }

    private GraphicsManager() {
        graphicComponents = new ArrayList<ArrayList<GraphicsComponent>>();
        for (int i = 0; i < MAX_LAYERS; i++) {
            graphicComponents.add(new ArrayList<GraphicsComponent>());
        }
        animationComponents = new ArrayList<AnimationComponent>();
        textureAtlas = new TextureAtlas();
        spriteTypes = DataTool.getTexturePaths();
    }

    @Override
    protected void loadScreenTypeSettings() {

    }

    @Override
    protected void removeScreenTypeSettings() {
        textureAtlas.dispose();
    }

    public Sprite getSprite(String spriteName) {
        Sprite sprite = textureAtlas.createSprite(spriteName);
        if (sprite == null) {
            SpriteType type = SpriteType.getTypeByString(spriteName);
            textureAtlas.addRegion(spriteName, new TextureRegion(new Texture(SpriteType.getPath(spriteName))));
            sprite = textureAtlas.createSprite(spriteName);
        }
        return sprite;
    }
}
