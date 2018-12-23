/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.BulletControl;
import com.blastback.shared.messages.data.ShootEventArgs;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Eryk
 */
public class BulletFactoryAppState extends BaseAppState
{
    private GameClient _app;
    private static AssetManager _assetManager;
    

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient)app;
        _assetManager = _app.getAssetManager();
    }

    @Override
    protected void cleanup(Application app)
    {
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }
    
    public static BulletControl createBullet(Node root, PhysicsSpace space, ShootEventArgs eventArgs, int damage, float speed)
    {
        Spatial bullet = _assetManager.loadModel("Models/Bullet.j3o");
        BulletControl bulletControl = new BulletControl(damage, speed, 1f);
        bullet.addControl(bulletControl);
        bulletControl.setPhysicsLocation(eventArgs.getShotPosition());
        bulletControl.setPhysicsRotation(eventArgs.getShotRotation());
        
        root.attachChild(bullet);
        space.add(bulletControl);
        space.addCollisionListener(bulletControl);
        bulletControl.setGravity(Vector3f.ZERO);
        bulletControl.setCcdMotionThreshold(0.2f);
        bulletControl.setCcdSweptSphereRadius(0.12f);
        
        return bulletControl;
    }
    
}
