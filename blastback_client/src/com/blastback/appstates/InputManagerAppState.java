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
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
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
    
    private final List<InputListener> _listeners;
    
    public InputManagerAppState()
    {
        _listeners = new ArrayList<>();
    }

    @Override
    protected void initialize(Application app) 
    {
        _app = (GameClient)app;
        _inputManager = _app.getInputManager();
        initKeys();
    }

    @Override
    protected void onEnable() 
    {
        
        for(InputListener listener : _listeners)
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
    }
    
    @Override
    protected void cleanup(Application app)
    {
        
    }
    
    public void addListener(InputListener listener)
    {
        _listeners.add(listener);
        if(isEnabled())
        {
            registerListener(listener);
        }
    }
    
    public void removeListener(InputListener listener)
    {
        _listeners.remove(listener);
        if(isEnabled())
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
    
    private void registerListener(InputListener listener)
    {
        if (listener instanceof AnalogListener)
        {
            _inputManager.addListener(listener, "MouseMoved");
        } 
        else if (listener instanceof ActionListener)
        {
            _inputManager.addListener(listener, "Right", "Left", "Up", "Down");
        }
    }
    
    private void unregisterListener(InputListener listener)
    {
        _inputManager.removeListener(listener);
    }
    
}
