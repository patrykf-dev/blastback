package com.blastback.shared.messages;

import com.blastback.shared.messages.data.PlayerStateInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * This type of message is supposed to be sent by SERVER.
 *
 * @author Patryk
 */
public class PlayerStateInfosMessage extends BaseBlastbackMessage<List<PlayerStateInfo>>
{
    private static final List<PlayerStateInfo> LIST_TOKEN = new ArrayList<>();

    public PlayerStateInfosMessage(List<PlayerStateInfo> param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public List<PlayerStateInfo> deserialize()
    {
        return _gsonInstance.fromJson(_content, LIST_TOKEN.getClass());
    }

}
