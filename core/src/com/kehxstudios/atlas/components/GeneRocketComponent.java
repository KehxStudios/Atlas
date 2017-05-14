package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-05-14.
 */

public class GeneRocketComponent extends Component {

    private ArrayList<Vector2> genes;
    private float fitness;

    public GeneRocketComponent(int numOfGenes) {
        super();
        createGenes(numOfGenes);
    }

    public void createGenes(int numOfGenes) {
        genes = new ArrayList<Vector2>(numOfGenes);
    }

    public void setGenes(ArrayList<Vector2> genes) {
        this.genes = genes;
    }

    public Vector2 getGene(int location) {
        if (location < 0)
            location = 0;
        if (location > genes.size())
            location = genes.size();
        return genes.get(location);
    }

    public void setGene(int location, Vector2 gene) {
        if (location < 0 || location > genes.size())
            return;
        genes.set(location, gene);
    }

    public void setFitness(float value) { fitness = value; }
    public float getFitness() { return fitness; }
}
