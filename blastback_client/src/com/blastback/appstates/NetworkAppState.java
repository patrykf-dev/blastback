/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.listeners.ClientListener;
import com.blastback.shared.messages.BaseBlastbackMessage;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.PlayerStateInfosMessage;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkAppState extends BaseAppState
{

    private Client _clientInstance;
    private final int _port;
    private final String _ip;

    private final List<MessageListener> _messageListeners;

    public NetworkAppState()
    {
        _ip = "localhost";
        _port = 7777;
        _messageListeners = new ArrayList<>();
    }

    public NetworkAppState(String ip, int port)
    {
        _ip = ip;
        _port = port;
        _messageListeners = new ArrayList<>();
    }

    public Client getClientInstance()
    {
        return _clientInstance;
    }

    @Override
    protected void initialize(Application app)
    {
        registerMessages();
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
        if (_clientInstance != null)
        {
            _clientInstance.close();
        }
        
    }

    @Override
    protected void cleanup(Application app)
    {
    }

    private void initConnection()
    {
        try
        {
            Log("Creating Client on port " + _port);

            _clientInstance = Network.connectToServer(_ip, _port);
            this.addListener(new ClientListener());
            _clientInstance.start();

            Log("Server created succesfully");
        } catch (IOException ex)
        {
            Log(ex.getMessage());
        }
    }

    private void Log(String msg)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "\t[LOG] {0}", msg);
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
        if(_clientInstance != null)
        {
            _clientInstance.addMessageListener(listener);
        }
    }

    private void unregisterListener(MessageListener listener)
    {
        if(_clientInstance != null)
        {
            _clientInstance.removeMessageListener(listener);
        }
    }

    private void registerMessages()
    {
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(BaseBlastbackMessage.class);
        Serializer.registerClass(PlayerMovedMessage.class);
        Serializer.registerClass(PlayerShotMessage.class);
        Serializer.registerClass(PlayerStateInfosMessage.class);
        Serializer.registerClass(PlayerHitMessage.class);
    }
}
