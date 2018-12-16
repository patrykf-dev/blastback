/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Eryk
 */
public class PlayerInputControl extends AbstractControl
{
    private ActionListener _keyboardListener;
    private AnalogListener _mouseListener;
    private PlayerMovementControl _movementControl;

    public PlayerInputControl()
    {
        initListeners();
    }

    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        if (spatial != null)
        {
            _movementControl = spatial.getControl(PlayerMovementControl.class);
        }
    }

    @Override
    protected void controlUpdate(float tpf)
    {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {

    }

    private void initListeners()
    {
        _keyboardListener = new ActionListener()
        {
            @Override
            public void onAction(String name, boolean keyPressed, float tpf)
            {
                if (name.equals("Left"))
                {
                    _movementControl.setLeft(keyPressed);
                }
                if (name.equals("Right"))
                {

                    _movementControl.setRight(keyPressed);
                }
                if (name.equals("Up"))
                {
                    _movementControl.setUp(keyPressed);
                }
                if (name.equals("Down"))
                {
                    _movementControl.setDown(keyPressed);
                }

            }
        };
        
        _mouseListener = new AnalogListener()
        {
            @Override
            public void onAnalog(String name, float value, float tpf)
            {
                if (name.equals("MouseMoved"))
                {
                    //_movementControl.setRight(true); // Dude is moving to the right if mouse was moved
                }
            }
        };
    }

    public ActionListener getKeyboardListener()
    {
        return _keyboardListener;
    }

    public AnalogListener getMouseListener()
    {
        return _mouseListener;
    }
    
}
