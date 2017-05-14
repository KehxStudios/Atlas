package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.util.ArrayList;

public class GeneRocketsScreen extends AScreen {

    private int generationNumber;
    private float timePerGeneration;
    private int rockPopulationSize;
    
    private ArrayList<Entity> rocketPopulation;
    private Entity targetEntity;

    public GeneRocketsScreen() {
        super(ScreenType.GENE_ROCKETS);

        generationNumber = 0;
        timePerGeneration = 400f;
        rockPopulationSize = 50;
    }

    public void finalizeSetup() {
        super.finalizeSetup();
        ComponentData targetGraphicsData = Templates.graphicsComponentData(0,0,1, TextureType.GENE_ROCKETS_TARGET);
        ComponentData targetPhysicsData = Templates.physicsComponentData(0, 0, 0, 0);
        
        targetEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*4));
        Factory.createComponent(targetEntity, targetGraphicsData);
        PhysicsComponent targetPhysics = (PhysicsComponent)Factory.createComponent(targetEntity, targetPhysicsData);
        
        generateRockets();
    }
    
    private void generateRockets() {
        ComponentData rocketGraphicsData = Templates.graphicsComponentData(0,0,2, TextureType.GENE_ROCKETS_ROCKET);
        ComponentData rocketPhysicsData = Templates.physicsComponentData(10, 10, 10, 10);

        rocketPopulation = new ArrayList<Entity>();
        for (int i = 0; i < rockPopulationSize; i++) {
            Entity rocket = Factory.createEntity(Templates.createEntityData(width/2, height/5));
            Factory.createComponent(rocket, rocketGraphicsData);
            Factory.createComponent(rocket, rocketPhysicsData);
            rocketPopulation.add(rocket);
        }
    }
    
    private void newGeneration() {
        for (Entity rocket : rocketPopulation) {
            rocket.setPosition(width/2, height/5);   
        }
        generationNumber++;
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
