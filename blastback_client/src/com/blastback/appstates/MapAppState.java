/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

/**
 *
 * @author Eryk
 */
public class MapAppState extends BaseAppState
{

    private GameClient _app;
    private BulletAppState _bulletAppState;

    private Spatial _map;
    private RigidBodyControl _mapRigidBody;

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient) app;
        _bulletAppState = _app.getStateManager().getState(BulletAppState.class);
        initScene();
    }

    @Override
    protected void onEnable()
    {
        _app.getRootNode().attachChild(_map);
        _bulletAppState.getPhysicsSpace().add(_mapRigidBody);
    }

    @Override
    protected void onDisable()
    {
        _app.getRootNode().detachChild(_map);
        _bulletAppState.getPhysicsSpace().remove(_mapRigidBody);
    }

    @Override
    protected void cleanup(Application app)
    {
    }

    private void initScene()
    {
        _map = _app.getAssetManager().loadModel("Scenes/Main.j3o");
        CollisionShape col = CollisionShapeFactory.createMeshShape(_map);
        _mapRigidBody = new RigidBodyControl(col, 0.0f);
        _map.addControl(_mapRigidBody);
    }
}
