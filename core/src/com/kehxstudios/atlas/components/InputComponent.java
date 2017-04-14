package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-06.
 */

public class InputComponent extends Component {


    public InputComponent(Entity entity) {
        super(entity);
        type = ComponentType.INPUT;
    }
}
