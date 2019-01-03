/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

/**
 *
 * @author Marcin
 */
public class WeaponInfo
    {
        private int _damage;
        private float _speed;
        private float _ammoCapacity;
        private float _bulletCooldown; //in ms
        private float _reloadTime; // in miliseconds       
        
        public int getDamage()
        {
            return _damage;
        }
        
        public float getSpeed()
        {
            return _speed;
        }
        
        public float getAmmoCapacity()
        {
            return _ammoCapacity;
        }
        
        public float getReloadTime()
        {
            return _reloadTime;
        }
        
        public float getBulletCooldown()
        {
            return _bulletCooldown;
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
        
        public WeaponInfo(int dmg,float speed,float ammoCap,float reloadTime)
        {
            _damage = dmg;
            _speed = speed;
            _ammoCapacity = ammoCap;
            _reloadTime = reloadTime;
        }        
        
    }
