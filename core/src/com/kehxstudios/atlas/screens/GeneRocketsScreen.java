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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.managers.PositionManager;
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
    private float timePerGeneration;
    private float currentGenerationTime;
    private int rocketPopulationSize;
    
    private HashMap<Integer, Entity> rocketPopulation;
    private HashMap<Integer, GeneRocketComponent> geneRocketComponents;
    private HashMap<Integer, PhysicsComponent> physicsComponents;
    private HashMap<Integer, CollisionComponent> collisionComponents;
    private Entity targetEntity;

    private int numOfGenes;
    private int activeGeneNumber;
    private float maxGeneValue;
    private float geneMutationRate;
    private ArrayList<GeneRocketComponent> newRocketGenes;
    private ArrayList<Integer> geneMatingPool;

    private FloatingTextComponent currentGenerationFloatingText;
    private FloatingTextComponent currentGenerationTimeFloatingText;

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
        geneMutationRate = 0.04f;
    }

    protected void init() {
        super.init();

        geneMatingPool = new ArrayList<Integer>();

        float targetWidth = TextureType.GENE_ROCKETS_TARGET.getWidth();
        float targetHeight = TextureType.GENE_ROCKETS_TARGET.getHeight();

        targetEntity = buildManager.createEntity(width/2, height/5*4);
        buildManager.createGraphicsComponent(targetEntity, 2, TextureType.GENE_ROCKETS_TARGET);
        buildManager.createCollisionComponent(targetEntity, targetWidth, targetHeight, true, false, false, new Action());

        newRocketGenes = new ArrayList<GeneRocketComponent>();
        rocketPopulationSize = 30;
        timePerGeneration = 10f;
        maxGeneValue = 100f;
        numOfGenes = (int)(timePerGeneration * 4);
        currentGenerationTime = 0f;
        activeGeneNumber = -1;
        generationNumber = 0;

        Entity floatingTextEntity = buildManager.createEntity(width/2, height - 50);
        currentGenerationFloatingText = buildManager.createFloatingTextComponent(floatingTextEntity,
                true, true, "Current Generation: ", generationNumber+"", graphicsManager.COLOR_BLUE);

        Entity floatingTextTimeEntity = buildManager.createEntity(width/2, height-100);
        currentGenerationTimeFloatingText = buildManager.createFloatingTextComponent(floatingTextTimeEntity,
                true, true, "Generation Time: ", String.format(java.util.Locale.US,"%.1f",
                        currentGenerationTime) + " / " + timePerGeneration,
                graphicsManager.COLOR_BLUE);

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
            buildManager.createGraphicsComponent(rocketEntity, 1, TextureType.GENE_ROCKETS_ROCKET).rotate = true;
            physicsComponents.put(rocketEntity.id, buildManager.createPhysicsComponent(rocketEntity,
                    new Vector2(100,100), new Vector2(100,100)));
            collisionComponents.put(rocketEntity.id, buildManager.createCollisionComponent(rocketEntity,
                    rocketWidth, rocketHeight, false, false, false, new Action()));
            geneRocketComponents.put(rocketEntity.id, buildManager.createGeneRocketComponent(
                    rocketEntity, randomGenes()));
        }
    }

    private ArrayList<Vector2> randomGenes() {
        ArrayList<Vector2> genes = new ArrayList<Vector2>();
        Vector2 gene = null;
        for (int i = 0; i < numOfGenes; i++) {
            if (gene == null)
                gene = newGene();
            else
                gene = getRelativeGene(gene);
            genes.add(i, gene);
        }
        return genes;
    }

    private Vector2 newGene() {
        return new Vector2(random.nextFloat()*maxGeneValue*2-maxGeneValue,
                random.nextFloat()*maxGeneValue*2-maxGeneValue);
    }

    private Vector2 getRelativeGene(Vector2 relative) {
        Vector2 gene = new Vector2(random.nextFloat()*maxGeneValue/2+relative.x-maxGeneValue/4,
                random.nextFloat()*maxGeneValue/2+relative.y-maxGeneValue/4);
        gene.clamp(-maxGeneValue, maxGeneValue);
        return gene;
    }

    private void populateMatingPool() {
        DebugTool.log("Populate Mating Pool");
        geneMatingPool.clear();
        for (GeneRocketComponent geneRocket : geneRocketComponents.values()) {
            int matingScore = (int)(geneRocket.fitness);
            for (int j = 0; j < matingScore; j++) {
                geneMatingPool.add(geneRocket.entityId);
            }
        }
    }

    private void populateNewRockets() {
        DebugTool.log("Populate New Rockets");
        DebugTool.log("geneMatingPool size: "+geneMatingPool.size());
        for (Entity geneRocket : rocketPopulation.values()) {
            int parentA = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            int parentB = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            while (parentA == parentB) {
                parentB = geneMatingPool.get(random.nextInt(geneMatingPool.size()));
            }
            ArrayList<Vector2> genes = new ArrayList<Vector2>();
            for (int j = 0; j < numOfGenes; j++) {
                if (random.nextFloat() < geneMutationRate) {
                    genes.add(j, newGene());
                } else if (random.nextBoolean()) {
                    genes.add(j, geneRocketComponents.get(parentA).genes.get(j));
                } else {
                    genes.add(j, geneRocketComponents.get(parentB).genes.get(j));
                }
            }
            newRocketGenes.add(buildManager.createGeneRocketComponent(geneRocket, genes));
            physicsComponents.get(geneRocket.id).velocity = new Vector2(0,0);
            positionManager.setPosition(geneRocket.id, new Vector2(width/2, height/5));
            geneRocket.position.set(width/2, height/5);
        }
        geneRocketComponents.clear();
        for (GeneRocketComponent geneRocket : newRocketGenes) {
            geneRocketComponents.put(geneRocket.entityId, geneRocket);
        }
        newRocketGenes.clear();
    }
    
    private void nextGeneration() {
        DebugTool.log("Starting next generation");
        measureRocketsFitness();
        populateMatingPool();
        populateNewRockets();

        generationNumber++;
        currentGenerationTime = 0f;
        activeGeneNumber = -1;

        currentGenerationFloatingText.text = generationNumber+"";

        currentGenerationFloatingText.layout.setText(currentGenerationFloatingText.font,
                currentGenerationFloatingText.label + currentGenerationFloatingText.text,
                currentGenerationFloatingText.color, 0, Align.left, true);
    }

    private void measureRocketsFitness() {
        DebugTool.log("Measuring Rocket Fitness");
        for (Entity geneRocket : rocketPopulation.values()) {
            float distance = geneRocket.position.dst(targetEntity.position);
            distance /= 10;
            float fitness = (200 - distance)/10;
            CollisionComponent rocketCollision = collisionComponents.get(geneRocket.id);
            if (rocketCollision.collided && distance != 0f) {
                fitness /= 2;
                rocketCollision.collided = false;
            }
            if (fitness < 1)
                fitness = 1;
            DebugTool.log("fitness: " + fitness +" , distance: " + distance);
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

        if (currentGenerationTime*4 >= activeGeneNumber) {
            activeGeneNumber++;
            if (activeGeneNumber >= numOfGenes)
                return;
            for (PhysicsComponent physics : physicsComponents.values()) {
                physics.velocity.set(geneRocketComponents.get(physics.entityId).genes.get(activeGeneNumber));
            }
        }

        currentGenerationTimeFloatingText.text = String.format(java.util.Locale.US,"%.1f",
                currentGenerationTime) + " / " + timePerGeneration;

        currentGenerationTimeFloatingText.layout.setText(currentGenerationTimeFloatingText.font,
                currentGenerationTimeFloatingText.label + currentGenerationTimeFloatingText.text,
                currentGenerationTimeFloatingText.color, 0, Align.left, true);
    }

    public void dispose() {
        if (!disposed) {
            super.dispose();
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
