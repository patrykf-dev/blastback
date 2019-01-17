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

    private boolean _firing;
    private float _counter;

    private CharacterControl _charControl;
    private final WeaponControl _weaponControl;
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

        if (_firing)
        {

            if (_counter > _weaponControl.getCurrentWeapon().getBulletCooldown() && _weaponControl.getCurrentWeapon().shoot(true))
            {
                onShoot();
                _counter %= _weaponControl.getCurrentWeapon().getBulletCooldown();

            }
            _counter += tpf * 1000;

        }
        if (!_firing)
        {
            _weaponControl.getCurrentWeapon().shoot(false);
            if (_counter < _weaponControl.getCurrentWeapon().getBulletCooldown())
            {
                _counter += tpf * 1000;
            }

            // _counter = 0;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }

    public void shoot(boolean keyPressed)
    {
        _firing = keyPressed;
    }

    private void onShoot()
    {
        Vector3f shotPosition = spatial.getLocalTranslation().add(spatial.getLocalRotation().mult(_barrelOffset));
        ShootEventArgs eventArgs = new ShootEventArgs(shotPosition, spatial.getLocalRotation(), _weaponControl.getCurrentWeapon());
        onShootEvent.notify(eventArgs);

        BulletControl bullet = BulletFactoryAppState.createBullet(
                spatial.getParent(),
                _charControl.getPhysicsSpace(),
                eventArgs,
                false);

        onBulletCreated.notify(bullet);
    }

}
