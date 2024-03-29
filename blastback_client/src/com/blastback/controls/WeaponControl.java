/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.blastback.shared.messages.data.WeaponInfo;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.HashMap;

/**
 *
 * @author Marcin
 */
public class WeaponControl extends AbstractControl
{

    private WeaponInfo _currentWeapon;
    private HashMap<Integer, WeaponInfo> _weapons;

    public WeaponControl()
    {
        InitWeapons();
        _currentWeapon = _weapons.get(1);
    }

    public WeaponInfo getCurrentWeapon()
    {
        return _currentWeapon;
    }

    public void ChangeWeapon(int weaponNumber)
    {

        _currentWeapon = _weapons.get(weaponNumber);

    }

    private void InitWeapons()
    {
        _weapons = new HashMap<>();
        _weapons.put(1, new WeaponInfo(35, 20, 200, 35, 1000, 1, "Sound/Gun.wav", "Sound/Reload.wav", "Models/Bullet_1.j3o"));
        _weapons.put(2, new WeaponInfo(1000, 15f, 1000, 1, 3000, 1, "Sound/Shotgun.wav", "Sound/RocketReload.wav", "Models/Missile.j3o"));
        _weapons.put(3, new WeaponInfo(10, 20, 100, 10, 1500, 11, "Sound/Uzi.wav", "Sound/Reload.wav", "Models/Bullet.j3o"));

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
