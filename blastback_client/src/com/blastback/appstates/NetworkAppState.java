/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.GameInterfaceControl;
import com.blastback.listeners.ClientListener;
import com.blastback.shared.messages.BaseBlastbackMessage;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.messages.MatchEndedMessage;
import com.blastback.shared.messages.MatchStartedMessage;
import com.blastback.shared.messages.PlayerDeathMessage;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.SimulationDataMessage;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import com.jme3.network.Message;
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
    private GameClient _app;
    private PlayerAppState _playerAppState;
    private GameInterfaceControl _interfaceControl;

    private Client _clientInstance;
    private final int _port;
    private final String _ip;
    private final String _username;
    private final List<MessageListener> _messageListeners;
    
    private ClientListener _connectionListener;
    private final float _timeThreshold = 5f;
    private boolean _isDisconnected = false;
    
    private final MutableFloat _timeSinceLastMessage = new MutableFloat(0f);
    
    public NetworkAppState()
    {
        initListener();
        _ip = "localhost";
        _port = 7777;
        _messageListeners = new ArrayList<>();
        _messageListeners.add(_connectionListener);
        _username= "Clyde";
    }

    public NetworkAppState(String ip, int port, String username)
    {
        initListener();
        _ip = ip;
        _port = port;
        _messageListeners = new ArrayList<>();
        _messageListeners.add(_connectionListener);
        _username = username;
    }

    public Client getClientInstance()
    {
        return _clientInstance;
    }

    public String getUsername()
    {
        return _username;
    }
                
    @Override
    protected void initialize(Application app)
    {
        registerMessages();
        _app = (GameClient)app;
        _playerAppState = _app.getStateManager().getState(PlayerAppState.class);
        _interfaceControl = _playerAppState.getGameInterfaceControl();
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
            if(_clientInstance.isConnected())
            {
                _clientInstance.close();
            }
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

    @Override
    public void update(float tpf)
    {
        if(_isDisconnected == false)
        {
            synchronized(_timeSinceLastMessage)
            {
                float val = _timeSinceLastMessage.getValue();
                if(val > _timeThreshold)
                {
                    _interfaceControl.displayConnectionLostNotification(true);
                    _isDisconnected = true;
                }
                else
                {
                    _timeSinceLastMessage.setValue(val + tpf);
                }
            }
        }
    }
    
    
    
    private void initListener()
    {
        _connectionListener = new ClientListener()
        {
            @Override
            public void messageReceived(Client source, Message message)
            {
                synchronized(_timeSinceLastMessage)
                {
                    _timeSinceLastMessage.setValue(0f);
                }
            }  
        };
    }

    private void registerMessages()
    {
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(BaseBlastbackMessage.class);
        Serializer.registerClass(PlayerMovedMessage.class);
        Serializer.registerClass(PlayerShotMessage.class);
        Serializer.registerClass(SimulationDataMessage.class);
        Serializer.registerClass(PlayerHitMessage.class);
        Serializer.registerClass(PlayerDeathMessage.class);
        Serializer.registerClass(MatchStartedMessage.class);
        Serializer.registerClass(MatchEndedMessage.class);
    }
    
    private class MutableFloat
    {
        private float _value;
        
        public MutableFloat(float value)
        {
            _value = value;
        }
        
        public float getValue()
        {
            return _value;
        }

        public void setValue(float _value)
        {
            this._value = _value;
        }
    }
}
