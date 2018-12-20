package com.blastback.shared.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * This type of message is made mainly for testing purposes.
 * @author Patryk
 */
@Serializable
public class HelloMessage extends BaseBlastbackMessage<String>
{
    public HelloMessage()
    {
        super();
    }
    
    public HelloMessage(String param)
    {
        super(param);
        _content = param;
    }

    @Override
    public String deserialize()
    {
        return _content;
    }
}
