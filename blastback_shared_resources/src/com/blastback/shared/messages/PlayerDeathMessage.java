/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages;

import com.blastback.shared.messages.data.HitEventArgs;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Eryk
 */
@Serializable
public class PlayerDeathMessage extends BaseBlastbackMessage<HitEventArgs>
{

    public PlayerDeathMessage()
    {
        super();
    }

    public PlayerDeathMessage(HitEventArgs param)
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
