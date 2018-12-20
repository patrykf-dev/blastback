package com.blastback.shared.networking.data;

import com.jme3.math.Vector3f;


public class PlayerState
{
    private final int _hp;
    private final Vector3f _localTranslation;
    private final Vector3f _localRotation;

    public PlayerState(int hp, Vector3f localTranslation,Vector3f localRotation)
    {
        this._hp = hp;
        this._localTranslation = localTranslation;
        this._localRotation = localRotation;
        
    }

    
    public int getHp()
    {
        return _hp;
    }

    public Vector3f getLocalTranslation()
    {
        return _localTranslation;
    }

    public Vector3f getLocalRotation()
    {
        return _localRotation;
    }




    
    
    
    
}
