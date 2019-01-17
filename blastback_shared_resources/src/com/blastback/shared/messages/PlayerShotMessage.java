package com.blastback.shared.messages;
import com.blastback.shared.messages.data.ShootEventArgs;
import com.jme3.network.serializing.Serializable;

/**
 * This type of message is supposed to be sent by CLIENT. 
 * @author Patryk
 */
@Serializable
public class PlayerShotMessage extends BaseBlastbackMessage<ShootEventArgs>
{
    
    
    public PlayerShotMessage()
    {
        super();
    }

    public PlayerShotMessage(ShootEventArgs param)
    {
        super(param);
        this.setReliable(false);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public ShootEventArgs deserialize()
    {
        return _gsonInstance.fromJson(_content, ShootEventArgs.class);
    }
}
