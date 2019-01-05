package com.blastback.controls;

import com.blastback.appstates.GUIAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;


public class GameInterfaceControl extends AbstractControl
{
    private GUIAppState _guiAppState;

    public GameInterfaceControl(GUIAppState guiAppState)
    {
        this._guiAppState = guiAppState;
    }

    public GUIAppState getGui()
    {
        return _guiAppState;
    }
    
    
    
    @Override
    protected void controlUpdate(float tpf)
    {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }
    
}
