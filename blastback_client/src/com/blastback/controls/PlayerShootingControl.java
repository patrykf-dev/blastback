package com.blastback.controls;

import com.blastback.events.ShootEvent;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerShootingControl extends AbstractControl
{
    public final ShootEvent onShootEvent = new ShootEvent();
    
    private final Vector3f _barrelOffset;
    
    public PlayerShootingControl(Vector3f barrelOffset)
    {
        _barrelOffset = barrelOffset;
    }

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
        if(spatial != null)
        {
            Vector3f shotPosition = spatial.getLocalTranslation().add(spatial.getLocalRotation().mult(_barrelOffset));
            onShootEvent.notify(shotPosition, spatial.getLocalRotation());
        }
    }
    
}
