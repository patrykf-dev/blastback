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
public class SimulationDataContainer
{

    private final PlayerStateInfo[] _array;
    private final float _remainingTime;

    public float getRemainingTime()
    {
        return _remainingTime;
    }

    public SimulationDataContainer(PlayerStateInfo[] array, float remainingTime)
    {
        _array = array;
        _remainingTime = remainingTime;
    }

    public PlayerStateInfo[] getArray()
    {
        return _array;
    }

}
