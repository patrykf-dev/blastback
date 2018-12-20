package com.blastback.controls;

import com.blastback.appstates.NetworkAppState;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.data.ClientCoordinates;
import com.google.gson.Gson;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerNetworkPresenceControl extends AbstractControl
{
    private final NetworkAppState _network;
    private CharacterControl _charControl;
    
    private Timer _messageTimer;
    private final int _timerTick = 50;
    
    public PlayerNetworkPresenceControl(NetworkAppState networkAppState)
    {
        _network = networkAppState;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        if(enabled && _messageTimer == null)
        {
            initTimer();
        }
        else if (_messageTimer != null)
        {
            _messageTimer.cancel();
        }
    }

    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        _charControl = spatial.getControl(CharacterControl.class);
        initTimer();
    }
    
    
    @Override
    protected void controlUpdate(float tpf)
    {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }
    
    private void initTimer()
    {
        _messageTimer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                sendPlayerInfo();
            }
        };

        _messageTimer.scheduleAtFixedRate(tt, 0, _timerTick);
    }
    
    private void sendPlayerInfo()
    {
        Client client = _network.getClientInstance();
        if (client != null && client.isConnected())
        {
            Vector3f location = _charControl.getPhysicsLocation();
            Vector3f viewDirection = _charControl.getViewDirection();

            
            
            ClientCoordinates DataForJson = new ClientCoordinates(location, viewDirection);
            Message m = new PlayerMovedMessage(DataForJson);
            
           
            
            
           
            
            
            //client.send(data);
            client.send(m);
        }
    }
    
}
