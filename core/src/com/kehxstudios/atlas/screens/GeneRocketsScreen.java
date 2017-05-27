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
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Factory;
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
    private Entity targetEntity;

    private int numOfGenes;
    private int activeGeneNumber;
    private ArrayList<GeneRocketComponent> newRocketGenes;
    private ArrayList<Integer> geneMatingPool;

    private Random random = new Random();

    public GeneRocketsScreen() {
        super(ScreenType.GENE_ROCKETS);

        numOfGenes = (int)Math.ceil(timePerGeneration);

        generationNumber = 0;
        currentGenerationTime = 0f;
        activeGeneNumber = 0;

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

        geneMatingPool = new ArrayList<Integer>();

        float targetWidth = TextureType.GENE_ROCKETS_TARGET.getWidth();
        float targetHeight = TextureType.GENE_ROCKETS_TARGET.getHeight();

        targetEntity = Factory.createEntity(width/2, height/5*4);
        Factory.createGraphicsComponent(targetEntity, 2, TextureType.GENE_ROCKETS_TARGET);
        Factory.createCollisionComponent(targetEntity, targetWidth, targetHeight, true, false, null);

        generateRandomRockets();
    }
    
    private void generateRandomRockets() {
        rocketPopulation = new HashMap<Integer, Entity>(rocketPopulationSize);
        geneRocketComponents = new HashMap<Integer, GeneRocketComponent>();
        physicsComponents = new HashMap<Integer, PhysicsComponent>();

        float rocketWidth = TextureType.GENE_ROCKETS_ROCKET.getWidth();
        float rocketHeight = TextureType.GENE_ROCKETS_ROCKET.getHeight();

        for (int i = 0; i < rocketPopulationSize; i++) {
            Entity rocketEntity = Factory.createEntity(width/2, height/5);
            rocketPopulation.put(rocketEntity.id, rocketEntity);
            Factory.createGraphicsComponent(rocketEntity, 1, TextureType.GENE_ROCKETS_ROCKET);
            physicsComponents.put(rocketEntity.id, Factory.createPhysicsComponent(rocketEntity, new Vector2(5,5), new Vector2(5,5)));
            Factory.createCollisionComponent(rocketEntity, rocketWidth, rocketHeight, false, false, null);
            geneRocketComponents.put(rocketEntity.id, Factory.createGeneRocketComponent(rocketEntity, randomGenes()));
        }
    }

    private ArrayList<Vector2> randomGenes() {
        ArrayList<Vector2> genes = new ArrayList<Vector2>();
        for (int i = 0; i < numOfGenes; i++) {
            genes.add(new Vector2(random.nextFloat()*10-5, random.nextFloat()*10-5));
        }
        return genes;
    }

    private void destroyGeneRocketComponents() {
        for (GeneRocketComponent geneRocketComponent : geneRocketComponents.values()) {
            EntityManager.getInstance().markComponentForRemoval(geneRocketComponent.id);
        }
        EntityManager.getInstance().tick(0);
        geneRocketComponents.clear();
    }

    private void populateMatingPool() {
        for (int i = 0; i < geneRocketComponents.size(); i++) {
            int matingScore = (int)(geneRocketComponents.get(i).fitness / 100);
            for (int j = 0; j < matingScore; j++) {
                geneMatingPool.add(i);
            }
        }
    }

    private void populateNewRockets() {
        newRocketGenes.clear();
        for (int i = 0; i < rocketPopulationSize; i++) {
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
            newRocketGenes.add(Factory.createGeneRocketComponent(rocketPopulation.get(i), genes));
        }
    }
    
    private void nextGeneration() {
        for (Entity rocket : rocketPopulation.values()) {
            rocket.position.set(width/2, height/5);
        }

        measureRocketsFitness();
        populateMatingPool();
        destroyGeneRocketComponents();
        populateNewRockets();

        generationNumber++;
        currentGenerationTime = 0f;
        activeGeneNumber = 0;
    }

    private void measureRocketsFitness() {
        for (int i = 0; i < rocketPopulationSize; i++) {
            float distance = (float)(Math.pow((rocketPopulation.get(i).position.x-targetEntity.position.x), 2)
                    + Math.pow((rocketPopulation.get(i).position.y-targetEntity.position.y), 2));
            float fitness = 500f - distance;
            CollisionComponent rocketCollision = (CollisionComponent)rocketPopulation.get(i).getComponentOfType(ComponentType.COLLISION);
            if (rocketCollision.collided && distance != 0f) {
                fitness -= 100f;
                rocketCollision.collided = false;
            }
            geneRocketComponents.get(i).fitness = fitness;
        }
    }
    
    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);

        currentGenerationTime += delta;

        if (currentGenerationTime >= timePerGeneration) {
            nextGeneration();
            for (int i = 0; i < rocketPopulationSize; i++) {
                physicsComponents.get(i).velocity.set(geneRocketComponents.get(i).genes.get(activeGeneNumber));
            }
        }

        if (Math.floor(timePerGeneration) > activeGeneNumber) {
            activeGeneNumber++;
            for (int i = 0; i < rocketPopulationSize; i++) {
                physicsComponents.get(i).velocity.set(geneRocketComponents.get(i).genes.get(activeGeneNumber));
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
