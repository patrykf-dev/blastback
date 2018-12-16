/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.PlayerInputControl;
import com.blastback.controls.PlayerMovementControl;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Eryk
 */
public class PlayerAppState extends BaseAppState
{
    private GameClient _app;
    private InputManagerAppState _inputAppState;
    private BulletAppState _bulletAppState;
    
    private Spatial _player;
    private PlayerInputControl _inputControl;
    private CharacterControl _charControl;

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient)app;
        _inputAppState = _app.getStateManager().getState(InputManagerAppState.class);
        _bulletAppState = _app.getStateManager().getState(BulletAppState.class);
        
        initPlayer();
    }

    @Override
    protected void onEnable()
    {
        _inputAppState.addListener(_inputControl.getKeyboardListener());
        _inputAppState.addListener(_inputControl.getMouseListener());
        
        _app.getRootNode().attachChild(_player);
        
        _bulletAppState.getPhysicsSpace().add(_charControl);

        _charControl.setPhysicsLocation(new Vector3f(0f, 2.2f, 0f));
        _charControl.setViewDirection(new Vector3f(1f, 0f, 0f));
    }

    @Override
    protected void onDisable()
    {
        _inputAppState.removeListener(_inputControl.getKeyboardListener());
        _inputAppState.removeListener(_inputControl.getMouseListener());

        _app.getRootNode().detachChild(_player);
        
        _bulletAppState.getPhysicsSpace().remove(_charControl);
    }

    @Override
    protected void cleanup(Application app)
    {
        
    }
    
    private void initPlayer()
    {
        _player = _app.getAssetManager().loadModel("Models/Player.j3o");

        CollisionShape shape = new CapsuleCollisionShape(0.5f, 1f, 1);
        _charControl = new CharacterControl(shape, 0.1f);
        _charControl.setGravity(new Vector3f(0f, -1f, 0f));

        // Add controls to spatials
        _player.addControl(_charControl);
        _player.addControl(new PlayerMovementControl());
        _inputControl = new PlayerInputControl();
        _player.addControl(_inputControl);
    }
    
}
