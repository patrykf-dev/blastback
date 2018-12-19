package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.shared.networking.data.PlayerState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import java.util.List;

public class SimulationAppState extends BaseAppState
{
    private GameClient _app;
    private int _clientId;
    private List<PlayerState> _states;
    
    public void SetClientsInfo(String msg)
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
