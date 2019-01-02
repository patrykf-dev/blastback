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
    private HashMap<Integer,WeaponInfo> _weapons;
    
    public WeaponControl()
    {
        InitWeapons();
        _currentWeapon = _weapons.get(2);
    }    

    public WeaponInfo getCurrentWeapon()
    {
        return _currentWeapon;
    }   
    
    public void ChangeWeapon(int weaponNumber)
    {
        _currentWeapon = _weapons.get(2);
        //WeaponInfo tmp = _currentWeapon;
        //_currentWeapon = _weapons.get(weaponNumber);
        //if(_currentWeapon == null) _currentWeapon = tmp;
    }
    
    private void InitWeapons()
    {
        _weapons = new HashMap<>();
        _weapons.put(1, new WeaponInfo(20, 20));
        _weapons.put(2, new WeaponInfo(100000, 0.1f));
        _weapons.put(3, new WeaponInfo(10,1000));
        
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
