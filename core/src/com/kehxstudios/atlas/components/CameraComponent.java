package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by ReidC on 2017-04-08.
 */

public class CameraComponent extends Component {

  private OrthographicCamera camera;

  public CameraComponent() {
    camera = new OrthographicCamera();
  }

  public OrthographicCamera getCamera() { return camera; }
  public void setCamera(OrthographicCamera camera) { this.camera = camera; }
  
  public void setWidth(float width) { camera.viewportWidth = width; }
  public void setHeight(float height) { camera.viewportHeight = height; }
  public void setFlipped(boolean flipped) { camera.setToOrtho(flipped); }
  
  public void update() {
    camera.position.set(new Vector3(entity.getPosition().x,
            entity.getPosition().y, 0));
    camera.update(); 
  }
}
