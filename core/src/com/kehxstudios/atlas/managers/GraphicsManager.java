/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

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
 * Used to control anything graphic related, including Animations, Textures & Text
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

    private HashMap<int,AnimationComponent> animationComponents;
    private ArrayList<HashMap<int,GraphicsComponent> graphicsComponents;
    private HashMap<int,FloatingTextComponent> floatingTextComponents;
    
    // TextureAtlas to load textures to and from
    private TextureAtlas textureAtlas;
    
    // Camera for rendering
    private CameraComponent cameraComponent;

    // Constructor
    private GraphicsManager() {
        super();
        init();
    }
    
    // Setup ArrayLists and other variables
    @Override
    protected void init() {
        textureAtlas = new TextureAtlas();
        animationComponents = new HashMap<int,AnimationComponent>();
        graphicComponents = new ArrayList<HashMap<int,GraphicsComponent>>();
        for (int i = 0; i < MAX_LAYERS; i++) {
            graphicComponents.add(new HashMap<int,GraphicsComponent>());
        }
        floatingTextComponents = new HashMap<int,FloatingTextComponent>();
        cameraComponent = null;
        DebugTool.log("GraphicsManager_init: Complete");
    }
    
    // Called to update @animationComponents
    @Override
    public void tick(float delta) {
        if (animationComponents.size() > 0) {
            for (AnimationComponent animation : animationComponents.values()) {
                if (animation.isEnabled()) {

                }
            }
        }
    }
    
    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        loadTextureAtlas();
        DebugTool.log("GraphicsManager_loadSettings: Complete");
    }

    // Called when unloading the current scree
    @Override
    protected void removeSettings() {
        textureAtlas.dispose();
        textureAtlas = new TextureAtlas();
        DebugTool.log("GraphicsManager_removeSettings: Complete");
    }

    // Called to render all graphics and floatingText
    public void render(SpriteBatch batch) {
        if (cameraComponent == null) {
            return;
        }
        batch.begin();
        batch.setProjectionMatrix(cameraComponent.getCamera().combined);
        for (HashMap<int,GraphicsComponent> hashMap : graphicComponents) {
            if (hashMap.size() == 0) {
                continue;
            }
            for (GraphicsComponent graphics : hashMap.values()) {
                if (graphics.isEnabled()) {
                    batch.draw(graphics.texture, graphics.getPosition().x - graphics.getWidth() / 2,
                        graphics.getPosition().y - graphics.getHeight() / 2,
                        graphics.getWidth(), graphics.getHeight());
                }
            }
        }
        for (FloatingTextComponent floatingText : floatingTextComponents.values()) {
            if (floatingText.isEnabled()) {
                floatingText.font.draw(gm.getBatch(), floatingText.layout(),
                        floatingText.getPosition().x - floatingText.layout().width / 2,
                        floatingText.getPosition().y - floatingText.layout().height / 2);
            }
        }
        batch.end();
    }

    // Called to load textures into the TextureAtlas
    private void loadTextureAtlas() {
        textureAtlas = gm.getAssetManager().get(screen.getType().getAtlasPath());
        DebugTool.log("GraphicsManager_loadTextureAtlas: Complete");
    }
    
    // Called to dispose of any resources
    public void dispose() {
        removeSettings();
    }

    // Called to check if graphics is already contained in @graphicsComponents
    private boolean contained(GraphicsComponent graphics) {
        return graphicComponents.get(graphics.getLayer()).contains(graphics));
    }
    
    // Called to add component to corresponding ArrayList
    public void add(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (!animationComponents.contains(animation)) {
                animationComponents.put(animation.id, animation);
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
                graphicComponents.get(graphics.getLayer()).put(graphics.id, graphics);
            }
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
            FloatingTextComponent floatingText = (FloatingTextComponent)component;
            if (!floatingTextComponents.contains(floatingText)) {
                floatingTextComponents.put(floatingText.id, floatingText);
            }
        }
    }
             
    // Called to remove component from corresponding ArrayList   
    public void remove(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            AnimationComponent animation = (AnimationComponent)component;
            if (animationComponents.contains(animation)) {
                animationComponents.values().remove(animation);
        } else if (component.getType() == ComponentType.CAMERA) {
                CameraComponent camera = (CameraComponent)component;
                if (cameraComponent == camera) {
                    cameraComponent = null;
                }
        } else if (component.getType() == ComponentType.GRAPHICS) {
                GraphicsComponent graphics = (GraphicsComponent)component;
                if (contained(graphics)) {
                    graphicComponents.get(graphics.getLayer()).values().remove(graphics);
                }
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                FloatingTextComponent floatingText = (FloatingTextComponent) component;
                if (floatingTextComponents.contains(floatingText)) {
                    floatingTextComponents.values().remove(floatingText);
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
