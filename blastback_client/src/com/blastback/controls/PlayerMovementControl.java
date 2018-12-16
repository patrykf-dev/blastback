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

public class PlayerMovementControl extends AbstractControl
{

    private final BulletAppState _appState;
    private CharacterControl _charControl;
    private final float _speed = 0.1f;
    private Vector3f _direction = new Vector3f();
    private boolean _left = false, _right = false, _up = false, _down = false;

    public PlayerMovementControl(BulletAppState appState)
    {
        _appState = appState;
    }

    /**
     * This method is called when the control is added to the spatial, and when
     * the control is removed from the spatial (setting a null value). It can be
     * used for both initialization and cleanup.
     *
     * @param spatial
     */
    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        if (spatial != null)
        {
            _charControl = this.getSpatial().getControl(CharacterControl.class);
            _appState.getPhysicsSpace().add(_charControl);

            // TODO: move it from here
            _charControl.setPhysicsLocation(new Vector3f(0f, 2.2f, 0f));
            _charControl.setViewDirection(new Vector3f(1f, 0f, 0f));
        }
    }

    @Override
    protected void controlUpdate(float tpf)
    {
        _direction.set(Vector3f.ZERO);

        if (_right)
        {
            _direction.addLocal(1f, 0f, 0f);
        }
        if (_left)
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
        _charControl.setWalkDirection(_direction);
    }

    public float getMoveSpeed()
    {
        return _speed;
    }

    public Vector3f getMoveDirection()
    {
        return _direction;
    }

    public void setMoveDirection(Vector3f moveDirection)
    {
        this._direction = moveDirection;
    }

    public void setLeft(boolean left)
    {
        this._left = left;
    }

    public void setRight(boolean right)
    {
        this._right = right;
    }

    public void setUp(boolean up)
    {
        this._up = up;
    }

    public void setDown(boolean down)
    {
        this._down = down;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
        // No need to render anything here
    }
}
