package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.Gdx;

/**
 * Created by ReidC on 2017-05-28.
 */

public class LaunchWebsiteAction extends Action {

    public String site;

    public void trigger() {
        Gdx.net.openURI(site);
    }
}
