package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;

import java.util.ArrayList;

public class GeneRocketsScreen extends AScreen {

    private int generation;
    private float timePerGeneration;
    private int rocketsPerGeneration;
    
    private ArrayList<Entities> rockets;
    private Entity targetEntity;

    public GeneRocketsScreen() {
        super(ScreenType.GENE_ROCKETS);
        
        generation = 0;
        timePerGeneration = 400f;
        rocketsPerGeneration = 50;
        
        setup();
    }
    
    private void setup() {
        ComponentData targetGraphicsData = Templates.createGraphicsComponentData(0,0,1, TextureType.GENE_ROCKETS_TARGET);
        ComponentData targetPhysicsData = Templates.createPhysicsComponentData(0, 0, 0, 0, 
                TextureType.GENE_ROCKETS_TARGET.getWidth(), TextureType.GENE_ROCKETS_TARGET.getHeight(), true);
        
        targetEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*4));
        Factory.createComponent(targetEntity, targetGraphicsData);
        PhysicsComponent targetPhysics = (PhysicsComponent)Factory.createComponent(targetEntity, targetPhysicsData);
        PhysicsManager.getInstance().setPlayer(targetPhysics);
        
        generateRockets();
    }
    
    private void generateRockets() {
        ComponentData rocketGraphicsData = Templates.createGraphicsComponentData(0,0,2, TextureType.GENE_ROCKETS_ROCKET);
        ComponentData rocketPhysicsData = Templates.createPhysicsComponentData(10, 10, 10, 10, 
                TextureType.GENE_ROCKETS_ROCKET.getWidth(), TextureType.GENE_ROCKETS_ROCKET.getHeight(), true);
        
        rockets = new ArrayList<Entity>();
        for (int i = 0; i < rocketsPerGeneration; i++) {
            Entity rocket = Factory.createEntity(Templates.createEntityData(width/2, height/5));
            Factory.createComponent(rocket, rocketGraphicsData);
            Factory.createComponent(rocket, rocketPhysicsData);
            rockets.add(rocket);
        }
    }
    
    private void newGeneration() {
        for (Entity rocket : rockets) {
            rocket.setPosition(width/2, height/5);   
        }
        generation++;
        screenTime = 0f;
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (screenTime >= timePerGeneration) {
            newGeneration();
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
