/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import com.blastback.shared.networking.data.PlayerState;

/**
 *
 * @author Marcin
 */
public class PlayerStateInfo {

    private final int _cId; // client ID
    private final PlayerState _ps; // corresponding playerstate

    public PlayerStateInfo(int Id, PlayerState ps) {
        _cId = Id;
        _ps = ps;
    }

    public int getClientId() {
        return _cId;
    }

    public PlayerState getPlayerState() {
        return _ps;
    }

}
