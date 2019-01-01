package com.blastback.controls;

import com.blastback.appstates.NetworkAppState;
import com.blastback.shared.messages.HelloMessage;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.data.ClientCoordinates;
import com.blastback.shared.messages.data.HitEventArgs;
import com.blastback.shared.messages.data.ShootEventArgs;
import com.blastback.shared.observer.BlastbackEventArgs;
import com.blastback.shared.observer.BlastbackEventListener;
import com.google.gson.Gson;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Quaternion;
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
    private PlayerShootingControl _shootingControl;
    
    private Timer _messageTimer;
    private final int _timerTick = 50;
    
    private BlastbackEventListener<ShootEventArgs> _shotListener;
    private BlastbackEventListener<HitEventArgs> _hitListener;
    private BlastbackEventListener<BulletControl> _bulletListener;
    
    public PlayerNetworkPresenceControl(NetworkAppState networkAppState)
    {
        _network = networkAppState;
        initListeners();
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        if(enabled)
        {
            if(_messageTimer == null)
            {
                initTimer();
            }
            _shootingControl.onShootEvent.addListener(_shotListener);
            _shootingControl.onBulletCreated.addListener(_bulletListener);
        }
        else
        {
            if(_messageTimer != null)
            {
            _messageTimer.cancel();
            }
            _shootingControl.onShootEvent.removeListener(_shotListener);
            _shootingControl.onBulletCreated.removeListener(_bulletListener);
        }
    }

    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        _charControl = spatial.getControl(CharacterControl.class);
        _shootingControl = spatial.getControl(PlayerShootingControl.class);
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

    private void initListeners() 
    {
        _shotListener = new BlastbackEventListener<ShootEventArgs>() {
            @Override
            public void invoke(ShootEventArgs e) 
            {
                sendShotMessage(e);
            }
        };
        
        _hitListener = new BlastbackEventListener<HitEventArgs>()
        {
            @Override
            public void invoke(HitEventArgs e)
            {
                sendHitMessage(e);
            }
        };

        _bulletListener = new BlastbackEventListener<BulletControl>()
        {
            @Override
            public void invoke(BulletControl e)
            {
                e.onPlayerHitEvent.addListener(_hitListener);
            }
        };
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

            client.send(m);
        }
    }
    
    private void sendShotMessage(ShootEventArgs e)
    {
        Client client = _network.getClientInstance();
        if (client != null && client.isConnected())
        {
            Message m = new PlayerShotMessage(e);
            client.send(m);
        }
    }
    
    private void sendHitMessage(HitEventArgs e)
    {
        Client client = _network.getClientInstance();
        if (client != null && client.isConnected())
        {
            Message m = new PlayerHitMessage(e);
            client.send(m);
        }
    }
    
}