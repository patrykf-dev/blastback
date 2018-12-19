package com.blastback.shared.networking.data;

import com.jme3.math.Vector3f;


public class PlayerState
{
    private int _hp;
    private Vector3f _localTranslation;
    private boolean _rightDirection;
    private boolean _leftDirection;
    private boolean _upDirection;
    private boolean _downDirection;

    public PlayerState(int _hp, Vector3f _localTranslation)
    {
        this._hp = _hp;
        this._localTranslation = _localTranslation;
        this._rightDirection = false;
        this._leftDirection = false;
        this._upDirection = false;
        this._downDirection = false;
    }

    
    
    
    public int getHp()
    {
        return _hp;
    }

    public Vector3f getLocalTranslation()
    {
        return _localTranslation;
    }

    public boolean isRightDirection()
    {
        return _rightDirection;
    }

    public boolean isLeftDirection()
    {
        return _leftDirection;
    }

    public boolean isUpDirection()
    {
        return _upDirection;
    }

    public boolean isDownDirection()
    {
        return _downDirection;
    }



    
    
    
    
}
