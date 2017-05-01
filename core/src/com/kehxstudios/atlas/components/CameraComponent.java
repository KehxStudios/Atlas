package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-04-08.
 */

public class CameraComponent extends Component {

  private OrthographicCamera camera;

  public OrthographicCamera getCamera() { return camera; }
  public void setCamera(OrthographicCamera camera) { this.camera = camera; }
}
