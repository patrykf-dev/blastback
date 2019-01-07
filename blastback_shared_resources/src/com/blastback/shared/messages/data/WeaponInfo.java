/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Marcin
 */
public class WeaponInfo
{

    private int _damage;
    private float _speed;
    
    private int _ammoCapacity;
    private int _inMagazine;
    
    private int _burst = 1;
    private int _burstCounter;
    
    private float _bulletCooldown; //in ms
    private float _reloadTime; // in miliseconds       
    
    private Timer _reloading;
    
    private String _sound;
    
    public int getDamage()
    {
        return _damage;
    }

    public float getSpeed()
    {
        return _speed;
    }

    public int getAmmoCapacity()
    {
        return _ammoCapacity;
    }   

    public int getCurrentAmmo()
    {
        return _inMagazine;
    }
    
    public float getReloadTime()
    {
        return _reloadTime;
    }

    public float getBulletCooldown()
    {
        return _bulletCooldown;
    }
    
    public String getSound()
    {
        return _sound;
    }

    public boolean Shoot(boolean keyPressed)
    {
        //reset bursts when the mouse key is lifted
        if(!keyPressed){
            
            _burstCounter = _burst;
            return false;
        }

        //if eligible to shoot
        if(_reloading == null && _burstCounter > 0)
        {
            
            if(_inMagazine>0)
            {
                _burstCounter--;
                _inMagazine--;
                return true;
            }
            else
            {               
                Reload();
                return false;
            }

        }
        return false;
    }
    
    public void Reload()
    {
        if(_reloading == null)
            {
                    _reloading = new Timer();
                     TimerTask tt = new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            ResetAmmo();
                        }
                    };
                    _reloading.schedule(tt, (long)_reloadTime);
            }
    }
    
    private void ResetAmmo()
    {
        _inMagazine = _ammoCapacity;
        _burstCounter = _burst;
        _reloading.cancel();
        _reloading = null;
    }
    public WeaponInfo()
    {
        _damage = 20;
        _speed = 20;
    }

    public WeaponInfo(int dmg, float speed, float bulletFrequency)
    {
        _damage = dmg;
        _speed = speed;
        _bulletCooldown = bulletFrequency;
    }

    public WeaponInfo(int dmg, float speed, float bulletFrequency, int ammoCap, float reloadTime, int burst, String sound)
    {
        _damage = dmg;
        _speed = speed;
        _ammoCapacity = ammoCap;
        _bulletCooldown = bulletFrequency;
        _inMagazine = ammoCap;                
        _reloadTime = reloadTime;
        _burst = burst;
        _burstCounter = burst;
        _sound = sound;
    }

}
