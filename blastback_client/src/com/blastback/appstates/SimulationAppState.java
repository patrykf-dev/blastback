package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.CharacterManagerControl;
import com.blastback.shared.messages.data.PlayerStateInfo;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import java.util.List;

public class SimulationAppState extends BaseAppState
{
    private GameClient _app;
    private int _clientId;
    private List<PlayerStateInfo> _clientsInfo;
    
    private List<CharacterManagerControl> _characters;
    
    public void setClientsInfo(String msg)
    {
        
    }
    
    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient)app;
    }

    @Override
    protected void cleanup(Application app)
    {
        
    }

    @Override
    protected void onEnable()
    {
        
    }
    

    @Override
    protected void onDisable()
    {
        
    }
    
}
