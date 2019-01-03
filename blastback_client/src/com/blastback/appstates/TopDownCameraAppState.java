/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Eryk
 */
public class TopDownCameraAppState extends BaseAppState
{

    private GameClient _app;
    private Camera _cam;
    private PlayerAppState _playerAppState;
    private Vector3f _offset = new Vector3f(0f, 20f, 0.1f);
    private final float _directionMult = 2.0f;

    public TopDownCameraAppState()
    {
    }

    public TopDownCameraAppState(Vector3f offset)
    {
        _offset = offset;
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient) app;
        _cam = _app.getCamera();
        _playerAppState = _app.getStateManager().getState(PlayerAppState.class);

        _cam.setLocation(_offset);
        _cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }

    @Override
    public void update(float tpf)
    {
        Vector3f cursorPos = new Vector3f(InputManagerAppState.cursorPosition.x, 0.0f, -InputManagerAppState.cursorPosition.y).multLocal(_directionMult);
        _cam.setLocation(_playerAppState.getPlayerPosition().add(_offset).addLocal(cursorPos));
    }

    @Override
    protected void cleanup(Application app)
    {
    }
}
