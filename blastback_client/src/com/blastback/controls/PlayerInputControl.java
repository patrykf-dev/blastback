package com.blastback.controls;

import com.blastback.appstates.InputManagerAppState;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class PlayerInputControl extends AbstractControl
{
    private ActionListener _keyboardListener;
    private AnalogListener _mouseListener;
    private PlayerMovementControl _movementControl;
    private PlayerShootingControl _shootingControl;
    private GameInterfaceControl _gameInterfaceControl;


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
            _shootingControl = spatial.getControl(PlayerShootingControl.class);
            _gameInterfaceControl = spatial.getControl(GameInterfaceControl.class);
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
                 if (name.equals("Tab"))
                {
                    _gameInterfaceControl.displayScoreboard(keyPressed);
                }
                
                if (name.equals("Shoot"))
                {
                    _shootingControl.shoot(keyPressed);
                }
                if (name.equals("1") && keyPressed)
                {
                    _shootingControl.getWeaponControl().ChangeWeapon(1);
                }
                if (name.equals("2") && keyPressed)
                {
                    _shootingControl.getWeaponControl().ChangeWeapon(2);
                }
                if (name.equals("3") && keyPressed)
                {
                    _shootingControl.getWeaponControl().ChangeWeapon(3);
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
                    Vector3f cursor3d = InputManagerAppState.cursorDirection.clone();
                    cursor3d.setY(0.0f);
                    _movementControl.setRotation(cursor3d);
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
