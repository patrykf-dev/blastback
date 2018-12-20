/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages;

import com.blastback.shared.messages.data.PlayerStateInfo;
import com.jme3.network.serializing.Serializable;

/**
 * This type of message is supposed to be sent by CLIENT.
 * @author Marcin
 */
@Serializable
public class PlayerHitMessage extends BaseBlastbackMessage<PlayerStateInfo>
{
    
    public PlayerHitMessage()
    {
        super();
    }
    
    public PlayerHitMessage(PlayerStateInfo param)
    {
        super(param);
         _content = _gsonInstance.toJson(param);
    }
   
    @Override
    public PlayerStateInfo deserialize()
    {
        return _gsonInstance.fromJson(_content, PlayerStateInfo.class);
    }
}
