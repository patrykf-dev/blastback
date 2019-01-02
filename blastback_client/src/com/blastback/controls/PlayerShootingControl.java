package com.blastback.controls;


import com.blastback.appstates.BulletFactoryAppState;
import com.blastback.shared.messages.data.ShootEventArgs;
import com.blastback.shared.observer.BlastbackEvent;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class PlayerShootingControl extends AbstractControl
{
    public final BlastbackEvent<ShootEventArgs> onShootEvent = new BlastbackEvent<>();
    public final BlastbackEvent<BulletControl> onBulletCreated = new BlastbackEvent<>();
    
    private CharacterControl _charControl;
    private WeaponControl _weaponControl;
    private final Vector3f _barrelOffset;
    
   
    public PlayerShootingControl(Vector3f barrelOffset)
    {
        _barrelOffset = barrelOffset;
        _weaponControl = new WeaponControl();
    }
 
    public WeaponControl getWeaponControl()
    {
        return _weaponControl;
    }
    
    
    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        _charControl = spatial.getControl(CharacterControl.class);
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
        Vector3f shotPosition = spatial.getLocalTranslation().add(spatial.getLocalRotation().mult(_barrelOffset));
        ShootEventArgs eventArgs = new ShootEventArgs(shotPosition, spatial.getLocalRotation(),_weaponControl.getCurrentWeapon());
        onShootEvent.notify(eventArgs);
        
        BulletControl bullet = BulletFactoryAppState.createBullet(
                spatial.getParent(), 
                _charControl.getPhysicsSpace(),
                eventArgs, 
                (int) _weaponControl.getCurrentWeapon().getDamage(),
                _weaponControl.getCurrentWeapon().getSpeed(), 
                false);
        
        onBulletCreated.notify(bullet);
    }
    
}
