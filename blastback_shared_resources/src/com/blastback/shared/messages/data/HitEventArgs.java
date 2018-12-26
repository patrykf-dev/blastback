package com.blastback.shared.messages.data;

import com.blastback.shared.observer.BlastbackEventArgs;

public class HitEventArgs extends BlastbackEventArgs
{
    private final int _targetId;
    private final int _damage;

    public HitEventArgs(int _targetId, int _damage)
    {
        this._targetId = _targetId;
        this._damage = _damage;
    }

    public int getTargetId()
    {
        return _targetId;
    }

    public int getDamage()
    {
        return _damage;
    }
    
    
}
