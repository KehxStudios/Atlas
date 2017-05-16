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

package com.kehxstudios.atlas.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Used to hold the active OrthographicCamera
 */

public class CameraComponent extends Component {

  private OrthographicCamera camera;
  private boolean flipped;

  public CameraComponent() {
    camera = new OrthographicCamera();
  }

  public OrthographicCamera getCamera() { return camera; }
  public void setCamera(OrthographicCamera camera) { this.camera = camera; }

  public float getWidth() { return camera.viewportWidth; }
  public void setWidth(float width) { camera.viewportWidth = width; }

  public float getHeight() { return camera.viewportHeight; }
  public void setHeight(float height) { camera.viewportHeight = height; }

  public boolean isFlipped() { return flipped; }
  public void setFlipped(boolean flipped) { flipped = true; }
  
  public void update() {
    camera.position.set(new Vector3(entity.getPosition().x,
            entity.getPosition().y, 0));
    camera.update(); 
  }
}
