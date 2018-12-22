package com.blastback.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerShootingControl extends AbstractControl
{

    @Override
    protected void controlUpdate(float tpf)
    {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }
    
    public void shoot(boolean keyPressed)
    {
        if(keyPressed)
        {
            onShoot();
        }
    }
    
    private void onShoot()
    {
        System.out.println("Player shoots!"); //delet dis
    }
    
}
