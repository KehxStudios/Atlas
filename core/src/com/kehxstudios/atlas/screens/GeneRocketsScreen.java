package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.util.ArrayList;
import java.util.Random;

public class GeneRocketsScreen extends AScreen {

    private int generationNumber;
    private float timePerGeneration = 400f;
    private float currentGenerationTime;
    private int rocketPopulationSize = 50;
    
    private ArrayList<Entity> rocketPopulation;
    private ArrayList<PhysicsComponent> physicsComponents;
    private ArrayList<GeneRocketComponent> geneRocketComponents;
    private Entity targetEntity;

    private int numOfGenes;
    private int activeGeneNumber;
    private ArrayList<GeneRocketComponent> geneMatingPool;
    private ArrayList<Integer> rocketMatingPool;

    private Random random = new Random();

    public GeneRocketsScreen() {
        super(ScreenType.GENE_ROCKETS);

        numOfGenes = (int)Math.ceil(timePerGeneration);

        generationNumber = 0;
        currentGenerationTime = 0f;
        activeGeneNumber = 0;
    }

    public void reset() {
        super.reset();

        generationNumber = 0;
        currentGenerationTime = 0f;
        activeGeneNumber = 0;
        destroyGeneRocketComponents();
        generateRockets();
    }

    public void finalizeSetup() {
        super.finalizeSetup();

        rocketMatingPool = new ArrayList<Integer>();

        ComponentData targetGraphicsData = Templates.graphicsComponentData(0,0,1, TextureType.GENE_ROCKETS_TARGET);
        ComponentData targetCollisionData = Templates.collisionComponentData(TextureType.GENE_ROCKETS_TARGET.getWidth(),
                TextureType.GENE_ROCKETS_TARGET.getHeight(), true, false, false, Templates.scoreActionData(10));

        targetEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*4));
        Factory.createComponent(targetEntity, targetGraphicsData);
        Factory.createComponent(targetEntity, targetCollisionData);
        
        generateRockets();
    }
    
    private void generateRockets() {
        ComponentData rocketGraphicsData = Templates.graphicsComponentData(0,0,2, TextureType.GENE_ROCKETS_ROCKET);
        ComponentData rocketPhysicsData = Templates.physicsComponentData(10, 10, 10, 10);
        ComponentData rocketCollisionData = Templates.collisionComponentData(TextureType.GENE_ROCKETS_ROCKET.getWidth(),
                TextureType.GENE_ROCKETS_ROCKET.getHeight(), false, false, false, Templates.scoreActionData(10));
        ComponentData rocketGeneData = Templates.geneRocketComponentData();

        rocketPopulation = new ArrayList<Entity>(rocketPopulationSize);
        physicsComponents = new ArrayList<PhysicsComponent>(rocketPopulationSize);
        for (int i = 0; i < rocketPopulationSize; i++) {
            Entity rocketEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5));
            Factory.createComponent(rocketEntity, rocketGraphicsData);
            physicsComponents.add(i, (PhysicsComponent)Factory.createComponent(rocketEntity, rocketPhysicsData));
            Factory.createComponent(rocketEntity, rocketCollisionData);
            GeneRocketComponent rocketGenes = (GeneRocketComponent)Factory.createComponent(rocketEntity, rocketGeneData);
            rocketGenes.setGenes(randomGenes());
            geneRocketComponents.add(i, rocketGenes);
            rocketPopulation.add(i, rocketEntity);
        }
    }

    private ArrayList<Vector2> randomGenes() {
        ArrayList<Vector2> genes = new ArrayList<Vector2>(numOfGenes);

        for (int i = 0; i < numOfGenes; i++) {
            genes.set(i, new Vector2(random.nextFloat()*6-3, random.nextFloat()*6-3));
        }

        return genes;
    }

    private void destroyGeneRocketComponents() {
        for (GeneRocketComponent geneRocketComponent : geneRocketComponents) {
            EntityManager.getInstance().markComponentForRemoval(geneRocketComponent);
        }
        EntityManager.getInstance().tick(0);
        geneRocketComponents = null;
    }

    private void populateMatingPool() {
        for (int i = 0; i < geneRocketComponents.size(); i++) {
            int matingScore = (int)(geneRocketComponents.get(i).getFitness() / 100);
            for (int j = 0; j < matingScore; j++) {
                rocketMatingPool.add(i);
            }
        }
        geneMatingPool = new ArrayList<GeneRocketComponent>(geneRocketComponents);
    }

    private void populateNewRockets() {
        geneRocketComponents = new ArrayList<GeneRocketComponent>(rocketPopulationSize);

        ComponentData rocketGeneData = Templates.geneRocketComponentData();

        for (int i = 0; i < rocketPopulationSize; i++) {
            int parentA = rocketMatingPool.get(random.nextInt(rocketMatingPool.size()));
            int parentB = rocketMatingPool.get(random.nextInt(rocketMatingPool.size()));
            while (parentA == parentB) {
                parentB = rocketMatingPool.get(random.nextInt(rocketMatingPool.size()));
            }

            ArrayList<Vector2> genes = new ArrayList<Vector2>(numOfGenes);
            for (int j = 0; j < numOfGenes; j++) {
                if (random.nextBoolean()) {
                    genes.set(j, geneMatingPool.get(parentA).getGene(j));
                } else {
                    genes.set(j, geneMatingPool.get(parentB).getGene(j));
                }
            }
            GeneRocketComponent geneRocket = (GeneRocketComponent)Factory.createComponent(rocketPopulation.get(i), rocketGeneData);
            geneRocket.setGenes(genes);
            geneRocketComponents.set(i, geneRocket);
        }


    }
    
    private void nextGeneration() {
        for (Entity rocket : rocketPopulation) {
            rocket.setPosition(width/2, height/5);   
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
        for (Entity rocketEntity : rocketPopulation) {
            float distance = Math.pow((rocketEntity.getPosition.x-targetEntity.getPosition.x), 2)
                            + Math.pow((rocketEntity.getPosition.y-targetEntity.getPosition.y), 2));
            float fitness = 500f - distance;
            CollisionComponent rocketCollision = (CollisionComponent)rocketEntity.getComponentOfType(ComponentType.COLLISION);
            if (rocketCollision.isTriggered && distance != 0f) {
                fitness -= 100f;
                rocketCollision.setTriggered(false);
            }
            rocketEntity.setFitness(fitness);
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
                physicsComponents.get(i).setVelocity(geneRocketComponents.get(i).getGene(activeGeneNumber));
            }
        }

        if (Math.floor(timePerGeneration) > activeGeneNumber) {
            activeGeneNumber++;
            for (int i = 0; i < rocketPopulationSize; i++) {
                physicsComponents.get(i).setVelocity(geneRocketComponents.get(i).getGene(activeGeneNumber));
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
