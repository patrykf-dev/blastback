/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.listeners.ServerListener;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerStateInfosMessage;
import com.blastback.shared.messages.data.ClientCoordinates;
import com.blastback.shared.messages.data.PlayerStateInfo;
import com.blastback.shared.messages.data.PlayerStateInfoContainer;
import com.blastback.shared.messages.data.SimulationData;
import com.blastback.shared.networking.data.PlayerState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Server;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Artur
 */
public class SimulationDataAppState extends BaseAppState{

    private GameServer _app;
    private SimulationDataAppState _SimulationDataAppState;
    private ServerNetworkAppState _ServerNetworkAppState;
    
    private SimulationData simData;
    private ServerListener _listener;
    
    private Timer _messageTimer;
    private final int _timerTick = 50;
    
    
    public SimulationDataAppState(){
        initListener();
        simData = new SimulationData();
    }
    
    @Override
    protected void initialize(Application app) {
       _app = (GameServer) app;
       _SimulationDataAppState = _app.getStateManager().getState(SimulationDataAppState.class);
       _ServerNetworkAppState = _app.getStateManager().getState(ServerNetworkAppState.class);
    }

    @Override
    protected void cleanup(Application app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onEnable() {
        _ServerNetworkAppState.addListener(_listener);
        if(_messageTimer == null)
        {
            initTimer();
        }
        else if (_messageTimer != null)
        {
            _messageTimer.cancel();
        }
    }

    @Override
    protected void onDisable() {
        _messageTimer.cancel();
    }
    
    private void initTimer()
    {
        _messageTimer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                sendSimulationData();
            }
        };
        _messageTimer.scheduleAtFixedRate(tt, 0, _timerTick);
    }
    
    private void sendSimulationData()
    {
        Server server = _ServerNetworkAppState.getServer();
        PlayerStateInfo[] arr = (PlayerStateInfo[])simData.getdata().toArray();
        PlayerStateInfosMessage message = new PlayerStateInfosMessage(new PlayerStateInfoContainer(arr));
        server.broadcast(message);
    }
    
    private void initListener()
    {
        _listener = new ServerListener()
        {
            @Override
            public void messageReceived(HostedConnection source, Message message) 
            {
                if(message instanceof PlayerMovedMessage)
                {
                    PlayerStateInfo playerUpdate = simData.find(source.getId());
                    
                    PlayerMovedMessage PMovedMessage = (PlayerMovedMessage) message;
                    ClientCoordinates coordinates = PMovedMessage.deserialize();
                    
                    if(playerUpdate != null)
                    {
                        playerUpdate.getPlayerState().setLocalTranslation(coordinates.geTranslationVector());
                        playerUpdate.getPlayerState().setLocalRotation(coordinates.getRotationVector());
                    }
                    else
                    {
                        PlayerState temp = new PlayerState(100,coordinates.geTranslationVector(),coordinates.getRotationVector());
                        playerUpdate = new PlayerStateInfo(source.getId(),temp);
                        simData.addPlayer(playerUpdate);
                    }
                    
                    //System.out.println("Server received '" + coordinates.getCoordinates() + "' from client #" + source.getId());
                }
            }
            
        };
    }
}
