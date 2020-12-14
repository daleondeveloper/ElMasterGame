package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGameObject extends Sprite {

    protected Set<AbstractGameObject> fixOnContact;

    public AbstractGameObject(){
        fixOnContact = new HashSet<AbstractGameObject>();
    }
    public void renderDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(getBoundingRectangle().x, getBoundingRectangle().y, getBoundingRectangle().width, getBoundingRectangle().height);
    }

    public void addFixToFixOnContact(AbstractGameObject fixture){
        fixOnContact.add(fixture);
    }
    public Set<AbstractGameObject> removeFixOnContact(AbstractGameObject mainFix, AbstractGameObject contactFix){
        fixOnContact.remove(contactFix);
        if(mainFix != null){
            contactFix.removeFixOnContact(null,mainFix);
        }
        return fixOnContact;
    }
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract boolean isDisposable();
}
