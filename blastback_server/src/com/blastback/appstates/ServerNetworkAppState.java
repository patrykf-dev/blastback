/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.listeners.ServerListener;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.networking.BlastbackClient;
import com.blastback.shared.networking.data.PlayerState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
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

    private Server _serverInstance;
    private List<BlastbackClient> _clients;
    private final int _port;

    public ServerNetworkAppState()
    {
        _port = 7777;
        _clients = new ArrayList<>();
    }

    public ServerNetworkAppState(int port)
    {
        _port = port;
        _clients = new ArrayList<>();
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
    }

    @Override
    protected void onDisable()
    {
        Log("Server destroyed");
        _serverInstance.close();
    }

    @Override
    public void update(float tpf)
    {
        Collection<HostedConnection> connections = _serverInstance.getConnections();
        Log(Integer.toString(connections.size()));
        String msg = createBroadcastMessage();
        
        
        
        Message message = new HelloMessage(msg);
        _serverInstance.broadcast(message);
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
    }

    private void initConnection()
    {
        try
        {
            Log("Creating server on port " + _port);
            _serverInstance = Network.createServer(_port);
            _serverInstance.addMessageListener(new ServerListener(), HelloMessage.class);
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

}
