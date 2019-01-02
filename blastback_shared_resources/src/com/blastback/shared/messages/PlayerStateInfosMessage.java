package com.blastback.shared.messages;

import com.blastback.shared.messages.data.PlayerStateInfo;
import com.blastback.shared.messages.data.PlayerStateInfoContainer;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This type of message is supposed to be sent by SERVER.
 *
 * @author Patryk
 */
@Serializable
public class PlayerStateInfosMessage extends BaseBlastbackMessage<PlayerStateInfoContainer>
{
    
    public PlayerStateInfosMessage()
    {
        super();
    }
    
    public PlayerStateInfosMessage(PlayerStateInfoContainer param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public PlayerStateInfoContainer deserialize()
    {
        return _gsonInstance.fromJson(_content, PlayerStateInfoContainer.class);
    }

}
