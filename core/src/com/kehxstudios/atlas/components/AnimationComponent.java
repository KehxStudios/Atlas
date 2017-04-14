package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-08.
 */

public class AnimationComponent extends Component {


    public AnimationComponent(Entity entity) {
        super(entity);
        type = ComponentType.ANIMATION;
    }


}
