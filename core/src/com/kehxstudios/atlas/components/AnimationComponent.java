package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;

/**
 * Created by ReidC on 2017-04-08.
 */

public class AnimationComponent extends Component {


    public AnimationComponent(Entity entity) {
        super(entity);
        type = ComponentType.ANIMATION;
        id = type.getId() + GraphicsManager.getInstance().getUniqueId();
    }


}
