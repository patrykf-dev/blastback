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
public class PlayerStateInfoContainer
{
    private final PlayerStateInfo[] _array;
    
    public PlayerStateInfoContainer(PlayerStateInfo[] array)
    {
        _array= array;
    }

    public PlayerStateInfo[] getArray()
    {
        return _array;
    }
    
    
}
