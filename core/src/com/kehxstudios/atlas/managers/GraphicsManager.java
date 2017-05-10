package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class GraphicsManager extends Manager {

    // Holds instance of class, create new if not set
    private static GraphicsManager instance;
    public static GraphicsManager getInstance() {
        if (instance == null) {
            instance = new GraphicsManager();
        }
        return instance;
    }

    // Maximum number of layers for @graphicsComponents
    private int MAX_LAYERS = 5;

    // ArrayLists for all used components
    private ArrayList<AnimationComponent> animationComponents;
    private ArrayList<ArrayList<GraphicsComponent>> graphicComponents;
    private ArrayList<FloatingTextComponent> floatingTextComponents;

    // TextureAtlas to load textures to and from
    private TextureAtlas textureAtlas;
    
    // Camera for rendering
    private CameraComponent cameraComponent;

    // Constructor
    private GraphicsManager() {
        super();
        setup();
    }
    
    // Setup ArrayLists and other variables
    @Override
    protected void setup() {
        textureAtlas = new TextureAtlas();
        animationComponents = new ArrayList<AnimationComponent>();
        graphicComponents = new ArrayList<ArrayList<GraphicsComponent>>();
        for (int i = 0; i < MAX_LAYERS; i++) {
            graphicComponents.add(new ArrayList<GraphicsComponent>());
        }
        floatingTextComponents = new ArrayList<FloatingTextComponent>();
        cameraComponent = null;
        DebugTool.log("GraphicsManager_setup: Complete");
    }
    
    // Called to update @animationComponents
    @Override
    public void tick(float delta) {
        if (animationComponents.size() > 0) {
            for (AnimationComponent animation : animationComponents) {
                if (animation.isEnabled()) {

                }
            }
        }
    }
    
    // Called when loading a new screen
    @Override
    protected void loadScreenSettings() {
        setup();
        loadTextureAtlas();
        DebugTool.log("GraphicsManager_loadScreenSettings: Complete");
    }

    // Called when unloading the current scree
    @Override
    protected void removeScreenSettings() {
        textureAtlas.dispose();
        textureAtlas = new TextureAtlas();
        DebugTool.log("GraphicsManager_removeScreenSettings: Complete");
    }

    // Called to render all graphics and floatingText
    public void render(SpriteBatch batch) {
        if (cameraComponent == null) {
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

    // Called to load textures into the TextureAtlas
    private void loadTextureAtlas() {
        ScreenType type = screen.getType();
        gm.getAssetManager().load(type.getAtlasPath(), TextureAtlas.class);
        gm.getAssetManager().finishLoading();
        textureAtlas = gm.getAssetManager().get(type.getAtlasPath());
        DebugTool.log("GraphicsManager_loadTextureAtlas: Complete");
    }
    
    // Called to dispose of any resources
    public void dispose() {
        removeScreenSettings();
    }

    // Called to check if graphics is already contained in @graphicsComponents
    private boolean contained(GraphicsComponent graphics) {
        if (graphicComponents.get(graphics.getLayer()).contains(graphics))
            return true;
        else
            return false;
    }
    
    // Called to add component to corresponding ArrayList
    public void add(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (!animationComponents.contains(animation)) {
                animationComponents.add(animation);
            }
        } else if (component.getType() == ComponentType.CAMERA) {
            CameraComponent camera = (CameraComponent)component;
            if (cameraComponent != null) {
                EntityManager.getInstance().markComponentForRemoval(cameraComponent);
            }
            cameraComponent = camera;
        } else if (component.getType() == ComponentType.GRAPHICS) {
            GraphicsComponent graphics = (GraphicsComponent)component;
            if (!contained(graphics)) {
                graphicComponents.get(graphics.getLayer()).add(graphics);
            }
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
            FloatingTextComponent floatingText = (FloatingTextComponent)component;
            if (!floatingTextComponents.contains(floatingText)) {
                floatingTextComponents.add(floatingText);
            }
        }
    }
             
    // Called to remove component from corresponding ArrayList   
    public void remove(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (animationComponents.contains(animation)) {
                animationComponents.remove(animation);
        } else if (component.getType() == ComponentType.CAMERA) {
                CameraComponent camera = (CameraComponent)component;
                if (cameraComponent == camera) {
                    cameraComponent = null;
                }
        } else if (component.getType() == ComponentType.GRAPHICS) {
                GraphicsComponent graphics = (GraphicsComponent)component;
                if (contained(graphics)) {
                    graphicComponents.get(graphics.getLayer()).remove(graphics);
                }
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                FloatingTextComponent floatingText = (FloatingTextComponent) component;
                if (floatingTextComponents.contains(floatingText)) {
                    floatingTextComponents.remove(floatingText);
                }
            }
        }
    }
 
    // Called to get the current camera
    public OrthographicCamera getCamera() {
        if (cameraComponent != null) {
            return cameraComponent.getCamera();
        }
        return null;
    }

    // Called to get the texture from the @textureAtlas
    public Texture getTexture(TextureType textureType) {
        return textureAtlas.findRegion(textureType.getFileName()).getTexture();
    }
}
