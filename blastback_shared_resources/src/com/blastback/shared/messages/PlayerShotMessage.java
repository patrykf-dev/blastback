package com.blastback.shared.messages;

import com.jme3.math.Vector3f;

/**
 * This type of message is supposed to be sent by CLIENT. 
 * @author Patryk
 */
public class PlayerShotMessage extends BaseBlastbackMessage<Vector3f>
{

    public PlayerShotMessage(Vector3f param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public Vector3f deserialize()
    {
        return _gsonInstance.fromJson(_content, Vector3f.class);
    }
}
