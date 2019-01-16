package com.blastback.shared.messages;

import com.blastback.shared.messages.data.PlayerStateInfo;
import com.blastback.shared.messages.data.SimulationDataContainer;
import com.jme3.network.serializing.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This type of message is supposed to be sent by SERVER.
 *
 * @author Patryk
 */
@Serializable
public class SimulationDataMessage extends BaseBlastbackMessage<SimulationDataContainer>
{
    
    public SimulationDataMessage()
    {
        super();
    }
    
    public SimulationDataMessage(SimulationDataContainer param)
    {
        super(param);
        _content = _gsonInstance.toJson(param);
    }

    @Override
    public SimulationDataContainer deserialize()
    {
        return _gsonInstance.fromJson(_content, SimulationDataContainer.class);
    }

}
