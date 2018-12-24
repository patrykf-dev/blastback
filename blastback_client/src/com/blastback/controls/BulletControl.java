/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Eryk
 */
public class BulletControl extends RigidBodyControl implements PhysicsCollisionListener
{
    private float _ttl = 0.75f;
    private final int _damage;
    private final float _speed;
    private boolean _isDestroyed = false;
    private final boolean _isDummy;
    
    public BulletControl(int damage, float speed, float mass, boolean dummy)
    {
        _damage = damage;
        _speed = speed;
        this.mass = mass;
        _isDummy = dummy;
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space)
    {
        super.setPhysicsSpace(space);
        fire();
    }
    
    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
    }

    @Override
    public void collision(PhysicsCollisionEvent event)
    {
        if(_isDestroyed) return;
        if(event.getNodeA() == spatial || event.getNodeB() == spatial)
        {
            destroyBullet();
            if(_isDummy == false)
            {
                System.out.println("Collided!");
            }
        }
    }

    @Override
    public void update(float tpf)
    {
        super.update(tpf);
        updateTTL(tpf);
    }

    private void updateTTL(float tpf)
    {
        _ttl -= tpf;
        if (_ttl < 0)
        {
            destroyBullet();
        }
    }

    private void fire()
    {
        setLinearVelocity(getPhysicsRotation().mult(Vector3f.UNIT_Z.mult((-1f) * _speed)));
    }
    
    private void destroyBullet()
    {
        if (spatial.getParent() != null)
        {
            spatial.getParent().detachChild(spatial);
        }
        if (this.getPhysicsSpace() != null)
        {
            this.getPhysicsSpace().remove(this);
        }
        _isDestroyed = true;
    }
    
}
