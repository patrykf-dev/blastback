package com.blastback;

import com.blastback.appstates.GameMatchAppState;
import com.blastback.appstates.ServerNetworkAppState;
import com.blastback.appstates.SimulationDataAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
public class GameServer extends SimpleApplication
{

    public GameServer()
    {
        super(new ServerNetworkAppState(),
                new GameMatchAppState(60),
                new SimulationDataAppState());
    }

    public static void main(String[] args)
    {
        AppSettings setting= new AppSettings(true);
        setting.setTitle("BlastBack Server");
        GameServer app = new GameServer();
        app.setSettings(setting);
        app.start(JmeContext.Type.Display);
    }

    @Override
    public void simpleInitApp()
    {
        
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        // reacting to messages...

    }

    @Override
    public void destroy()
    {
        // disposing...
        super.destroy();
    }

}
