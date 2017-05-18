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

import com.kehxstudios.atlas.components.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the position of an Entity across all linked Components
 */

public class PositionManager extends Manager {

    private static PositionManager instance;
    public static PositionManager getInstance() {
        if (instance == null) {
            instance = new PositionManager();
        }
        return instance;
    }

    private HashMap<Integer,ArrayList<Component>> positionHashMap;

    private PositionManager() {
        super();
        init();
    }

    @Override
    protected void init() {
        positionHashMap = new HashMap<Integer, ArrayList<Component>>();
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void add(Component component) {
        if (positionHashMap.containsKey(component.entityId)) {
            ArrayList<Component> newEntityList = new ArrayList<Component>();
            newEntityList.add(component);
            positionHashMap.put(component.entityId, newEntityList);
        } else {
            if (positionHashMap.get(component.entityId).contains(component)) {
                positionHashMap.get(component.entityId).add(component);
            }
        }
    }

    @Override
    public void remove(Component component) {
        if (positionHashMap.containsKey(component.entityId)) {
            if (positionHashMap.get(component.entityId).contains(component)) {
                positionHashMap.get(component.entityId).remove(component);
            }
        }
    }

    @Override
    protected void loadSettings() {

    }

    @Override
    protected void removeSettings() {

    }
}
