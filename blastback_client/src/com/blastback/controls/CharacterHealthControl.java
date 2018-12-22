package com.blastback.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.Observable;

public class CharacterHealthControl extends AbstractControl
{
    private final int _maxHealth;
    private int _currentHealth;
    
    public CharacterHealthControl()
    {
        _maxHealth = 100;
        _currentHealth = _maxHealth;
    }
    
    public CharacterHealthControl(int maxHealth)
    {
        _maxHealth = maxHealth;
        _currentHealth = _maxHealth;
    }

    @Override
    protected void controlUpdate(float tpf)
    {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
        
    }
    
    public void takeDamage(int amount)
    {
        _currentHealth -= amount;
        if(_currentHealth<=0)
        {
            _currentHealth = 0;
            onDeath();
        }
    }
    
    public void heal(int amount)
    {
        _currentHealth = Math.max(_currentHealth + amount, _maxHealth);
    }
    
    public void resetHealth()
    {
        _currentHealth = _maxHealth;
    }
    
    private void onDeath()
    {
        
    }
}
