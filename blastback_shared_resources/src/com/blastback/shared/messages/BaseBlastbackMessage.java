package com.blastback.shared.messages;

import com.google.gson.Gson;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Base class for all bastblack messages - containing a string we can easily
 * serialize and deserialize
 *
 * @author Patryk
 * @param <T> is the type that given message stores
 */
@Serializable
public abstract class BaseBlastbackMessage<T> extends AbstractMessage
{
    protected String _content;
    protected Gson _gsonInstance;
    
    public String getContent()
    {
        return _content;
    }
    
    public BaseBlastbackMessage()
    {
        super(false);
        _gsonInstance = new Gson();
        
    }

    /**
     * Contructor here is also responsible for SERIALIZING received param, which is to be implemented in inheriting classes.
     * @param param
     */
    public BaseBlastbackMessage(T param)
    {
        super(false);
        _gsonInstance = new Gson();
    }

    /**
     * Deserialize message to receive object it contains.
     * @return
     */
    public abstract T deserialize();
}
