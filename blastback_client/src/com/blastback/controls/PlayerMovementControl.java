/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;


public class PlayerMovementControl extends AbstractControl {
    
    
    float _speed = 0.1f;
    Vector3f _direction = new Vector3f();
    boolean _left = false, _right = false, _up = false, _down = false;
    
    
    
    

    
    
    
    
    

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {


    }
    

    @Override
    protected void controlUpdate(float tpf) {
        _direction = Vector3f.ZERO;
        
        if(_right)
        {
            _direction.addLocal(1f, 0f, 0f);
        }
        if(_left)
        {
            _direction.addLocal(-1f, 0f, 0f);
        }
        if (_up) 
        {
            _direction.addLocal(0f, 0f, -1f);
        }        
        if (_down) 
        {
            _direction.addLocal(0f, 0f, 1f);
        }
        
        _direction.normalizeLocal().multLocal(_speed);

    }
    
    
    
    public float getMoveSpeed() {
        return _speed;
    }

    public Vector3f getMoveDirection() {
        return _direction;
    }

    public void setMoveDirection(Vector3f moveDirection) {
        this._direction = moveDirection;
    }

    public void setLeft(boolean _left) {
        this._left = _left;
    }

    public void setRight(boolean _right) {
        this._right = _right;
    }

    public void setUp(boolean _up) {
        this._up = _up;
    }

    public void setDown(boolean _down) {
        this._down = _down;
    }
    
    
    
    
    
}
