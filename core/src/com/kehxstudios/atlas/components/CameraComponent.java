package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-04-08.
 */

public class CameraComponent extends Component {

  private OrthographicCamera camera;

  public OrthographicCamera getCamera() { return camera; }
  public void setCamera(OrthographicCamera camera) { this.camera = camera; }
  
  public void setWidth(float width) { camera.setWidth(width); }
  public void setHeight(float height) { camera.setHeight(height); }
  public void setFlipped(boolean flipped) { camera.setFlipped(flipped); }
  
  public void updateCamera() {
    camera.position.set(entity.getPosition);
    camera.update(); 
  }
}
