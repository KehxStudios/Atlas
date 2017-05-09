package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;

import java.util.Random;


public class PongScreen extends AScreen {

    private final int paddleWidth;
    private final int paddleHeight;
    private final float paddleSpeed;
    
    private final int ballWidth;
    private final int ballHeight;
    private final float ballSpeed;

    Entity player1Entity, player2Entity, ballEntity;
    PhysicsComponent player1Physics, player2Physics, ballPhysics;

    private int player2Score;
    
    private Random random = new Random();

    public PongScreen() {
        super(ScreenType.PONG);

        screenGraphics.setTextureType(TextureType.PONG_BACKGROUND);
        screenGraphics.setEnabled(true);
        
        paddleWidth = TextureType.PONG_PADDLE.getWidth();
        paddleHeight = TextureType.PONG_PADDLE.getHeight();
        
        ballWidth = TextureType.PONG_BALL.getWidth();
        ballHeight = TextureType.PONG_BALL.getHeight();
        
        setup();
    }
    
    private void setup() {
        player2Score = 0;
        paddleSpeed = 5f;
        ballSpeed = 6f;
        
        ComponentData paddleGraphicsData = Templates.createGraphicsComponentData(0, 0, 3, TextureType.PONG_PADDLE);
        ComponentData paddlePhysicsData = Templates.createPhysicsComponentData(0, paddleSpeed, 0, 
                paddleSpeed, TextureType.PONG_PADDLE.getWidth(), TextureType.PONG_PADDLE.getHeight(), true);
        
        player1Entity = Factory.createEntity(Templates.createEntityData(paddleWidth, height/2));
        GraphicsComponent player1Graphics = (GraphicsComponent)Factory.createComponent(player1, paddleGraphicsData);
        player1Physics = (PhysicsComponent)Factory.createComponent(player1, paddlePhysicsData);
        
        player2 = Factory.createEntity(Templates.createEntityData(width - paddleWidth, height/2));
        GraphicsComponent player2Graphics = (GraphicsComponent)Factory.createComponent(player2, paddleGraphicsData);
        player2Physics = (PhysicsComponent)Factory.createComponent(player2, paddlePhysicsData);
        
        
        ComponentData ballGraphicsData = Templates.createGraphicsComponentData(0, 0, 3, TextureType.PONG_BALL);
        ComponentData ballPhysicsData = Templates.createPhysicsComponentData(ballSpeed, ballSpeed, ballSpeed, 
                ballSpeed, TextureType.PONG_BALL.getWidth(), TextureType.PONG_BALL.getHeight(), true);
        
        ballEntity = Factory.createEntity(Templates.createEntityData(width/2, height/2));
        GraphicsComponent ballGraphics = (GraphicsComponent)Factory.createComponent(ball, ballGraphicsData);
        ballPhysics = (PhysicsComponent)Factory.createComponent(ball, ballPhysicsData);
        PhysicsManager.getInstance().setPlayer(ballPhysics);
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
    
        if (ballEntity.getPosition().x + ballWidth/2 > width) {
            score++;
            roundReset();
        } else if (ballEntity.getPosition().x - ballWidth/2 < 0) {
            player2Score++;
            roundReset();
        }
        
        if (ballEntity.getPosition().y + ballHeight/2 > height || ballEntity.getPosition().y - ballHeight/2 < 0) {
            ballPhysics.setYVelocity(-ballPhysics.getYVelocity);
        }
        
        if (player1Entity.getPosition().y + paddleHeight/2 > height) {
            player1Entity.setY(height - paddleHeight/2);
        } else if (player1Entity.getPosition().y - paddleHeight/2 < 0) {
            player1Entity.setY(paddleHeight/2);
        }
        
        if (player2Entity.getPosition().y + paddleHeight/2 > height) {
            player2Entity.setY(height - paddleHeight/2);
        } else if (player2Entity.getPosition().y - paddleHeight/2 < 0) {
            player2Entity.setY(paddleHeight/2);
        }
    }
    
    private void roundReset() {
        ballEntity.setPosition(width/2, height/2);
    }
    
    private void gameReset() {
        roundReset();
        screenTime = 0f;
        score = 0;
        player2Score = 0;
        player1Entity.setPosition(paddleWidth, height/2);
        player1Physics.setVelocity(0,0);
        player2Entity.setPosition(width - paddleWidth, height/2);
        player2Physics.setVelocity(0,0);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
