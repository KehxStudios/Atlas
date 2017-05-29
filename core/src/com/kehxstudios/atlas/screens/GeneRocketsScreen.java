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

package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Project of Gene Rockets
 */

public class GeneRocketsScreen extends AScreen {

    private int generationNumber;
    private float timePerGeneration = 400f;
    private float currentGenerationTime;
    private int rocketPopulationSize = 50;
    
    private HashMap<Integer, Entity> rocketPopulation;
    private HashMap<Integer, GeneRocketComponent> geneRocketComponents;
    private HashMap<Integer, PhysicsComponent> physicsComponents;
    private HashMap<Integer, CollisionComponent> collisionComponents;
    private Entity targetEntity;

    private int numOfGenes;
    private int activeGeneNumber;
    private ArrayList<GeneRocketComponent> newRocketGenes;
    private ArrayList<Integer> geneMatingPool;

    private Random random = new Random();

    public GeneRocketsScreen() {
        super(ScreenType.GENE_ROCKETS);
        init();
    }

    public void reset() {
        super.reset();

        generationNumber = 0;
        currentGenerationTime = 0f;
        activeGeneNumber = 0;
        destroyGeneRocketComponents();
    }

    protected void init() {
        super.init();

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));

        geneMatingPool = new ArrayList<Integer>();

        float targetWidth = TextureType.GENE_ROCKETS_TARGET.getWidth();
        float targetHeight = TextureType.GENE_ROCKETS_TARGET.getHeight();

        targetEntity = buildManager.createEntity(width/2, height/5*4);
        buildManager.createGraphicsComponent(targetEntity, 2, TextureType.GENE_ROCKETS_TARGET);
        buildManager.createCollisionComponent(targetEntity, targetWidth, targetHeight, true, false, new Action());

        newRocketGenes = new ArrayList<GeneRocketComponent>();

        numOfGenes = (int)Math.ceil(timePerGeneration);
        currentGenerationTime = 0f;
        activeGeneNumber = -1;
        generationNumber = -1;


        generateRandomRockets();
    }
    
    private void generateRandomRockets() {
        rocketPopulation = new HashMap<Integer, Entity>(rocketPopulationSize);
        geneRocketComponents = new HashMap<Integer, GeneRocketComponent>();
        physicsComponents = new HashMap<Integer, PhysicsComponent>();
        collisionComponents = new HashMap<Integer, CollisionComponent>();

        float rocketWidth = TextureType.GENE_ROCKETS_ROCKET.getWidth();
        float rocketHeight = TextureType.GENE_ROCKETS_ROCKET.getHeight();

        for (int i = 0; i < rocketPopulationSize; i++) {
            Entity rocketEntity = buildManager.createEntity(width/2, height/5);
            rocketPopulation.put(rocketEntity.id, rocketEntity);
            buildManager.createGraphicsComponent(rocketEntity, 1, TextureType.GENE_ROCKETS_ROCKET);
            physicsComponents.put(rocketEntity.id, buildManager.createPhysicsComponent(rocketEntity,
                    new Vector2(100,100), new Vector2(100,100)));
            //collisionComponents.put(rocketEntity.id, buildManager.createCollisionComponent(rocketEntity,
                    //rocketWidth, rocketHeight, false, false, new Action()));
            geneRocketComponents.put(rocketEntity.id, buildManager.createGeneRocketComponent(
                    rocketEntity, randomGenes()));
        }
        generationNumber++;
    }

    private ArrayList<Vector2> randomGenes() {
        ArrayList<Vector2> genes = new ArrayList<Vector2>();
        for (int i = 0; i < numOfGenes; i++) {
            genes.add(new Vector2(random.nextFloat()*200-100, random.nextFloat()*200-100));
        }
        return genes;
    }

    private void destroyGeneRocketComponents() {
        for (GeneRocketComponent geneRocketComponent : geneRocketComponents.values()) {
            entityManager.markComponentForRemoval(geneRocketComponent.id);
        }
        entityManager.tick(0);
        geneRocketComponents.clear();
    }

    private void populateMatingPool() {
        for (GeneRocketComponent geneRocket : geneRocketComponents.values()) {
            int matingScore = (int)(geneRocket.fitness / 100);
            for (int j = 0; j < matingScore; j++) {
                geneMatingPool.add(geneRocket.entityId);
            }
        }
    }

    private void populateNewRockets() {
        for (Entity geneRocket : rocketPopulation.values()) {
            int parentA = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            int parentB = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            while (parentA == parentB) {
                parentB = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            }
            ArrayList<Vector2> genes = new ArrayList<Vector2>(numOfGenes);
            for (int j = 0; j < numOfGenes; j++) {
                if (random.nextBoolean()) {
                    genes.set(j, geneRocketComponents.get(parentA).genes.get(j));
                } else {
                    genes.set(j, geneRocketComponents.get(parentB).genes.get(j));
                }
            }
            newRocketGenes.add(buildManager.createGeneRocketComponent(geneRocket, genes));
            physicsComponents.get(geneRocket.id).velocity = new Vector2(0,0);
            geneRocket.position.set(width/2, height/5);
        }
        geneRocketComponents.clear();
        for (GeneRocketComponent geneRocket : newRocketGenes) {
            geneRocketComponents.put(geneRocket.entityId, geneRocket);
        }
        newRocketGenes.clear();
    }
    
    private void nextGeneration() {
        measureRocketsFitness();
        populateMatingPool();
        destroyGeneRocketComponents();
        populateNewRockets();

        generationNumber++;
        currentGenerationTime = 0f;
        activeGeneNumber = -1;
    }

    private void measureRocketsFitness() {
        for (Entity geneRocket : rocketPopulation.values()) {
            float distance = (float)(Math.pow((geneRocket.position.x-targetEntity.position.x), 2)
                    + Math.pow((geneRocket.position.y-targetEntity.position.y), 2));
            float fitness = 500f - distance;
            CollisionComponent rocketCollision = collisionComponents.get(geneRocket.id);
            if (rocketCollision.collided && distance != 0f) {
                fitness -= 100f;
                rocketCollision.collided = false;
            }
            geneRocketComponents.get(geneRocket.id).fitness = fitness;
        }
    }
    
    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);

        currentGenerationTime += delta;

        if (currentGenerationTime >= timePerGeneration) {
            nextGeneration();
        }

        if (Math.floor(timePerGeneration) > activeGeneNumber) {
            activeGeneNumber++;
            if (activeGeneNumber >= numOfGenes)
                return;
            for (PhysicsComponent physics : physicsComponents.values()) {
                physics.velocity.set(geneRocketComponents.get(physics.entityId).genes.get(activeGeneNumber));
            }
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
