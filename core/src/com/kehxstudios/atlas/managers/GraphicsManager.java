package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.type.TextureType;
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

    private ArrayList<AnimationComponent> animationComponents;
    private ArrayList<ArrayList<GraphicsComponent>> graphicComponents;
    private ArrayList<FloatingTextComponent> floatingTextComponents;

    private TextureAtlas textureAtlas;
    
    private CameraComponent cameraComponent;

    public void tick(float delta) {
        if (animationComponents.size() > 0) {
            for (AnimationComponent animation : animationComponents) {
                if (animation.isEnabled()) {

                }
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (componentCamera == null) {
            return;
        }
        batch.begin();
        batch.setProjectionMatrix(cameraComponent.getCamera().combined);
        for (ArrayList<GraphicsComponent> layerList : graphicComponents) {
            if (layerList.size() == 0) {
                continue;
            }
            for (GraphicsComponent graphics : layerList) {
                if (graphics.isEnabled() && graphics.getTextureType() != TextureType.VOID) {

                    batch.draw(textureAtlas.findRegion(graphics.getTextureType().getFileName()),
                        graphics.getPosition().x - graphics.getWidth() / 2,
                        graphics.getPosition().y - graphics.getHeight() / 2,
                        graphics.getWidth(), graphics.getHeight());
                }

            }
        }
        for (FloatingTextComponent floatingText : floatingTextComponents) {
            if (floatingText.isEnabled()) {
                floatingText.getFont().draw(gm.getBatch(), floatingText.getLayout(),
                        floatingText.getPosition().x - floatingText.getLayout().width / 2,
                        floatingText.getPosition().y - floatingText.getLayout().height / 2);
            }
        }
        batch.end();
    }

    public void dispose() {
        removeScreenTypeSettings();
    }

    private boolean contained(GraphicsComponent graphics) {
        if (graphicComponents.get(graphics.getLayer()).contains(graphics))
            return true;
        else
            return false;
    }
    
    public void add(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (!animationComponents.contains(animation)) {
                animationComponents.add(animation);
        } else if (component.getType() == ComponentType.CAMERA) {
            CameraComponent camera = (CameraComponent)component;
            if (cameraComponent != null) {
                EntityManager.remove(cameraComponent);
            }
            cameraComponent = camera;
        } else if (component.getType() == ComponentType.GRAPHICS) {
            GraphicsComponent graphics = (GraphicsComponent)component;
            if (!contained(graphics)) {
                graphicComponents.get(graphics.getLayer()).add(graphics);
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
            FloatingTextComponent floatingText = (FloatingTextComponent)component;
            if (!floatingTextComponents.contains(floatingText)) {
                floatingTextComponents.add(floatingText);
        }
    }
                
    public void remove(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (animationComponents.contains(animation)) {
                animationComponents.remove(animation);
        } else if (component.getType() == ComponentType.CAMERA) {
            CameraComponent camera = (CameraComponent)component;
            if (cameraComponent == camera) {
                EntityManager.remove(cameraComponent);
                cameraComponent = null;
            }
        } else if (component.getType() == ComponentType.GRAPHICS) {
            GraphicsComponent graphics = (GraphicsComponent)component;
            if (contained(graphics)) {
                graphicComponents.get(graphics.getLayer()).remove(graphics);
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
            FloatingTextComponent floatingText = (FloatingTextComponent)component;
            if (floatingTextComponents.contains(floatingText))
                floatingTextComponents.remove(floatingText);
        }
    }
 
    public OrthographicCamera getCamera() {
        if (cameraComponent
            return cameraComponent.getCamera();
        }
        return null;
    }

    private GraphicsManager() {
        super();
        textureAtlas = new TextureAtlas();
        
        setup();
    }
            
    private void setup() {
        animationComponents = new ArrayList<AnimationComponent>();
        graphicComponents = new ArrayList<ArrayList<GraphicsComponent>>();
        for (int i = 0; i < MAX_LAYERS; i++) {
            graphicComponents.add(new ArrayList<GraphicsComponent>());
        }
        floatingTextComponents = new ArrayList<FloatingTextComponent>();
        cameraComponent = null;
    }

    @Override
    protected void loadScreenTypeSettings() {
        gm.getAssetManager().load(screenType.getPath(), TextureAtlas.class);
        gm.getAssetManager().finishLoading();
        textureAtlas = gm.getAssetManager().get(screenType.getPath());
        
        setup();
    }

    @Override
    protected void removeScreenTypeSettings() {
        textureAtlas.dispose();
        for (Texture texture : textureAtlas.getTextures()) {
            texture.dispose();
            DebugTool.log("Removing Texture from TextureAtlas");
        }
    }

    public Texture getTexture(TextureType textureType) {
        return textureAtlas.findRegion(textureType.getFileName()).getTexture();
    }

    public void setAtlas(TextureAtlas atlas) {
        textureAtlas = atlas;
    }
}
