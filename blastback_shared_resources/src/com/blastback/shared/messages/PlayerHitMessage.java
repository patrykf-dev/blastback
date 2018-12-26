/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages;

import com.blastback.shared.messages.data.HitEventArgs;
import com.jme3.network.serializing.Serializable;

/**
 * This type of message is supposed to be sent by CLIENT.
 * @author Marcin
 */
@Serializable
public class PlayerHitMessage extends BaseBlastbackMessage<HitEventArgs>
{
    
    public PlayerHitMessage()
    {
        super();
    }
    
    public PlayerHitMessage(HitEventArgs param)
    {
        super(param);
         _content = _gsonInstance.toJson(param);
    }
   
    @Override
    public HitEventArgs deserialize()
    {
        return _gsonInstance.fromJson(_content, HitEventArgs.class);
    }
}
