/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import com.blastback.shared.networking.data.MatchStats;
import com.blastback.shared.networking.data.IdentityData;
import com.blastback.shared.networking.data.PlayerState;

/**
 *
 * @author Marcin
 */
public class PlayerStateInfo
{
//
//    private final int _cId; // client ID
//    private String _username;
//    

    private IdentityData _identityData;
    private final MatchStats _stats;
    private final PlayerState _ps; // corresponding playerstate

    public PlayerStateInfo(PlayerState ps, IdentityData identityData)
    {

        _identityData = identityData;
        _ps = ps;
        _stats = new MatchStats();
    }

    public IdentityData getIdentityData()
    {
        return _identityData;
    }

    public PlayerState getPlayerState()
    {
        return _ps;
    }

    public MatchStats getMatchStats()
    {
        return _stats;
    }

}
