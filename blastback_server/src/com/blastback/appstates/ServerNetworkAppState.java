/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.listeners.ServerListener;
import com.blastback.shared.messages.BaseBlastbackMessage;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.messages.MatchEndedMessage;
import com.blastback.shared.messages.MatchStartedMessage;
import com.blastback.shared.messages.PlayerDeathMessage;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.SimulationDataMessage;
import com.blastback.shared.networking.BlastbackClient;
import com.blastback.shared.networking.data.PlayerState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin
 */
public class ServerNetworkAppState extends BaseAppState
{
    private List<MessageListener> _messageListeners;
    private Server _serverInstance;
    private List<BlastbackClient> _clients;
    private final int _port;

    public ServerNetworkAppState()
    {
        _port = 7777;
        _clients = new ArrayList<>();
        _messageListeners = new ArrayList<>();
    }

    public ServerNetworkAppState(int port)
    {
        _port = port;
        _clients = new ArrayList<>();
        _messageListeners = new ArrayList<>();
    }

    @Override
    protected void initialize(Application app)
    {
        registerMessages();
    }

    @Override
    protected void cleanup(Application app)
    {

    }

    @Override
    protected void onEnable()
    {
        initConnection();
        for (MessageListener listener : _messageListeners)
        {
            registerListener(listener);
        }
    }

    @Override
    protected void onDisable()
    {
        for (MessageListener listener : _messageListeners)
        {
            unregisterListener(listener);
        }
        Log("Server destroyed");
        _serverInstance.close();
    }

    @Override
    public void update(float tpf)
    {
        Collection<HostedConnection> connections = _serverInstance.getConnections();
        //Log(Integer.toString(connections.size()));
        String msg = createBroadcastMessage();
        
        Message message = new HelloMessage(msg);
       // _serverInstance.broadcast(message);
    }

    public Server getServer()
    {
        return _serverInstance;
    }
    
    private String createBroadcastMessage()
    {
        String rc = "";
        
        for (BlastbackClient c : _clients)
        {
            Vector3f tr = c.getState().getLocalTranslation();
            rc += String.format("%d;%f;%f;%f\n", c.getId() , tr.x, tr.y, tr.z);
        }
        
        return rc;
    }
    
    private void registerMessages()
    {
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(BaseBlastbackMessage.class);
        Serializer.registerClass(PlayerMovedMessage.class);
        Serializer.registerClass(PlayerHitMessage.class);
        Serializer.registerClass(PlayerShotMessage.class);
        Serializer.registerClass(SimulationDataMessage.class);
        Serializer.registerClass(PlayerDeathMessage.class);
        Serializer.registerClass(MatchStartedMessage.class);
        Serializer.registerClass(MatchEndedMessage.class);
    }

    private void initConnection()
    {
        try
        {
            Log("Creating server on port " + _port);
            _serverInstance = Network.createServer(_port);
            //TODO: add more message types to be handled by the listener
            //this.addListener(new ServerListener());
            //_serverInstance.addMessageListener(new ServerListener(), PlayerMovedMessage.class);
            _serverInstance.start();
            Log("Server created succesfully");
        } catch (IOException ex)
        {
            Log(ex.getMessage());
        }
    }

    private void Log(String msg)
    {
        Logger.getLogger(GameServer.class.getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }

    public void addListener(MessageListener listener)
    {
        _messageListeners.add(listener);
        if (isEnabled())
        {
            registerListener(listener);
        }
    }

    public void removeListener(MessageListener listener)
    {
        _messageListeners.remove(listener);
        if (isEnabled())
        {
            unregisterListener(listener);
        }
    }

    private void registerListener(MessageListener listener)
    {
        _serverInstance.addMessageListener(listener);
    }

    private void unregisterListener(MessageListener listener)
    {
        _serverInstance.removeMessageListener(listener);
    }

    
}
