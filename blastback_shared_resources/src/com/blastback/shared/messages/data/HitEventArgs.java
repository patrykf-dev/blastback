package com.blastback.shared.messages.data;

import com.blastback.shared.networking.data.IdentityData;
import com.blastback.shared.observer.BlastbackEventArgs;

public class HitEventArgs extends BlastbackEventArgs
{
    private IdentityData _shooterData;
    private final IdentityData _targetData;
    private final int _damage;

    public HitEventArgs(IdentityData targetData, int _damage)
    {
        _shooterData = new IdentityData("Unknown");
        this._targetData = targetData;
        this._damage = _damage;
    }

    public IdentityData getTargetData()
    {
        return _targetData;
    }

    public int getDamage()
    {
        return _damage;
    }

    public IdentityData getShooterData()
    {
        return _shooterData;
    }

    public void setShooterData(IdentityData shooterData)
    {
        this._shooterData = shooterData;
    }
    
}
