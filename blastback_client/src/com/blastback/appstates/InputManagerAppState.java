/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eryk
 */
public class InputManagerAppState extends BaseAppState
{

    private GameClient _app;
    private InputManager _inputManager;
    private Camera _cam;

    public static Vector2f cursorPosition = new Vector2f(0.0f, 0.0f);
    public static Vector3f cursorDirection = new Vector3f(0.0f, 0.0f, 0.0f);

    private final List<InputListener> _listeners;
    private final List<RawInputListener> _rawListeners;

    public InputManagerAppState()
    {
        _listeners = new ArrayList<>();
        _rawListeners = new ArrayList<>();
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient) app;
        _inputManager = _app.getInputManager();
        _cam = _app.getCamera();
        initKeys();
    }

    @Override
    protected void onEnable()
    {
        for (InputListener listener : _listeners)
        {
            registerListener(listener);
        }
        for (RawInputListener listener : _rawListeners)
        {
            registerListener(listener);
        }
    }

    @Override
    protected void onDisable()
    {
        for (InputListener listener : _listeners)
        {
            unregisterListener(listener);
        }
        for (RawInputListener listener : _rawListeners)
        {
            unregisterListener(listener);
        }
    }

    @Override
    public void update(float tpf)
    {
        cursorPosition = _inputManager.getCursorPosition().clone();
        cursorDirection = _cam.getWorldCoordinates(cursorPosition, 0f);
        cursorDirection.subtractLocal(_cam.getWorldCoordinates(cursorPosition, 1f));
        cursorDirection.normalizeLocal();
    }

    @Override
    protected void cleanup(Application app)
    {

    }

    public void addListener(RawInputListener listener)
    {
        _rawListeners.add(listener);
        if (isEnabled())
        {
            registerListener(listener);
        }
    }

    public void addListener(InputListener listener)
    {
        _listeners.add(listener);
        if (isEnabled())
        {
            registerListener(listener);
        }
    }

    public void removeListener(RawInputListener listener)
    {
        _rawListeners.remove(listener);
        if (isEnabled())
        {
            unregisterListener(listener);
        }
    }

    public void removeListener(InputListener listener)
    {
        _listeners.remove(listener);
        if (isEnabled())
        {
            unregisterListener(listener);
        }
    }

    private void initKeys()
    {
        _inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        _inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        _inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        _inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));

        _inputManager.addMapping("MouseMoved", new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, true),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));
    }

    private void registerListener(RawInputListener listener)
    {
        _inputManager.addRawInputListener(listener);
    }

    private void registerListener(InputListener listener)
    {
        if (listener instanceof AnalogListener)
        {
            _inputManager.addListener(listener, "MouseMoved");
        } else if (listener instanceof ActionListener)
        {
            _inputManager.addListener(listener, "Right", "Left", "Up", "Down");
        }
    }

    private void unregisterListener(RawInputListener listener)
    {
        _inputManager.removeRawInputListener(listener);
    }

    private void unregisterListener(InputListener listener)
    {
        _inputManager.removeListener(listener);
    }

}