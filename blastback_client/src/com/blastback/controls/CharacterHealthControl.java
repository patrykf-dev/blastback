package com.blastback.controls;

import com.blastback.shared.messages.data.HitEventArgs;
import com.blastback.shared.observer.BlastbackEvent;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class CharacterHealthControl extends AbstractControl
{

    public BlastbackEvent<HitEventArgs> onDeathEvent = new BlastbackEvent<>();
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

    /**
     * Method to be used on simulated characters (update their state
     * periodically)
     *
     * @param health
     */
    public void setHealth(int health)
    {
        _currentHealth = Math.max(health, 0);
        if (_currentHealth <= 0)
        {
            _currentHealth = 0;
            onDeathSimulated();
        }
    }

    /**
     * Method to be used on local player (as a response to PlayerHit message)
     *
     * @param args
     */
    public void takeDamage(HitEventArgs args)
    {
        _currentHealth -= args.getDamage();
        if (_currentHealth <= 0)
        {
            _currentHealth = 0;
            onDeath(args);
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

    private void onDeath(HitEventArgs args)
    {
        onDeathEvent.notify(args);
    }

    private void onDeathSimulated()
    {

    }

    /**
     * Get current health in <0, 1>
     *
     * @return current health (fraction)
     */
    public float getCurrentHealth()
    {
        float rc = (float) _currentHealth / (float) _maxHealth;
        return rc;
    }
}
