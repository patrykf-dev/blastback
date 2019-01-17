/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages;

import com.blastback.shared.messages.data.MatchSettings;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Eryk
 */
@Serializable
public class MatchStartedMessage extends BaseBlastbackMessage<MatchSettings>
{

    public MatchStartedMessage()
    {
        super();
    }

    public MatchStartedMessage(MatchSettings param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public MatchSettings deserialize()
    {
        return _gsonInstance.fromJson(_content, MatchSettings.class);
    }
}
