package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-05-13.
 */

public class ResetScreenAction extends Action {

    @Override
    public void trigger() {
        ScreenManager.getInstance().requestScreenReset();
    }
}
