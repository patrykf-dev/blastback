package com.blastback.shared.messages;

import com.blastback.shared.messages.data.ClientCoordinates;
import com.jme3.network.serializing.Serializable;

/**
 * This type of message is supposed to be sent by CLIENT.
 *
 * @author Patryk
 */
@Serializable
public class PlayerMovedMessage extends BaseBlastbackMessage<ClientCoordinates>
{

    public PlayerMovedMessage()
    {
        super();
    }

    public PlayerMovedMessage(ClientCoordinates param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public ClientCoordinates deserialize()
    {
        return _gsonInstance.fromJson(_content, ClientCoordinates.class);
    }
}
