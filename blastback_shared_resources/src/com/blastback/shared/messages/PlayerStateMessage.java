package com.blastback.shared.messages;

import com.blastback.shared.networking.data.PlayerState;


/**
 * This type of message is supposed to be sent by SERVER. 
 * @author Patryk
 */
public class PlayerStateMessage extends BaseBlastbackMessage<PlayerState>
{
    public PlayerStateMessage(PlayerState param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public PlayerState deserialize()
    {
        return _gsonInstance.fromJson(_content, PlayerState.class);
    }
}
