/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.CharacterHealthControl;
import com.blastback.controls.GameInterfaceControl;
import com.blastback.controls.PlayerInputControl;
import com.blastback.controls.PlayerMovementControl;
import com.blastback.controls.PlayerNetworkPresenceControl;
import com.blastback.controls.PlayerShootingControl;
import com.blastback.listeners.ClientListener;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.data.HitEventArgs;
import com.blastback.shared.observer.BlastbackEventArgs;
import com.blastback.shared.observer.BlastbackEventListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.scene.Spatial;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eryk
 */
public class PlayerAppState extends BaseAppState
{
    private GameClient _app;
    private InputManagerAppState _inputAppState;
    private BulletAppState _bulletAppState;
    private NetworkAppState _networkAppState;
    private GUIAppState _guiAppState;

    private Spatial _player;
    private PlayerInputControl _inputControl;
    private CharacterControl _charControl;
    private CharacterHealthControl _healthControl;
    private PlayerNetworkPresenceControl _networkPresenceControl;
    private GameInterfaceControl _gameInterfaceControl;
    
    private ClientListener _listener;
    private BlastbackEventListener<HitEventArgs> _deathListener;

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient) app;
        _inputAppState = _app.getStateManager().getState(InputManagerAppState.class);
        _bulletAppState = _app.getStateManager().getState(BulletAppState.class);
        _networkAppState = _app.getStateManager().getState(NetworkAppState.class);
        _guiAppState = _app.getStateManager().getState(GUIAppState.class);
        
        initListeners();
        initPlayer();
    }

    @Override
    protected void onEnable()
    {
        _inputAppState.addListener(_inputControl.getKeyboardListener());
        _inputAppState.addListener(_inputControl.getMouseListener());

        _app.getRootNode().attachChild(_player);

        _bulletAppState.getPhysicsSpace().add(_charControl);

        _charControl.setPhysicsLocation(new Vector3f(0f, 2.2f, 0f));
        _charControl.setViewDirection(new Vector3f(1f, 0f, 0f));
        
        if(_networkPresenceControl.isEnabled() == false)
        {
            _networkPresenceControl.setEnabled(true);
        }
        
        _networkAppState.addListener(_listener);
    }

    @Override
    protected void onDisable()
    {
        _inputAppState.removeListener(_inputControl.getKeyboardListener());
        _inputAppState.removeListener(_inputControl.getMouseListener());

        _app.getRootNode().detachChild(_player);

        _bulletAppState.getPhysicsSpace().remove(_charControl);
        
        if(_networkPresenceControl.isEnabled() == true)
        {
            _networkPresenceControl.setEnabled(false);
        }
        
        _networkAppState.removeListener(_listener);
    }

    @Override
    protected void cleanup(Application app)
    {

    }

    private void initPlayer()
    {
        _player = _app.getAssetManager().loadModel("Models/Player.j3o");

        CollisionShape shape = new CapsuleCollisionShape(0.5f, 5f, 1);
        _charControl = new CharacterControl(shape, 0.1f);
        _charControl.setGravity(new Vector3f(0f, -10f, 0f));

        // Add controls to spatials
        _player.addControl(_charControl);
        _player.addControl(new PlayerMovementControl());
        _gameInterfaceControl = new GameInterfaceControl(_guiAppState.getNifty());
        _player.addControl(_gameInterfaceControl);
        _player.addControl(new PlayerShootingControl(new Vector3f(0f,0.0f, -1.85f))); //to adjust
        _healthControl = new CharacterHealthControl();
        _player.addControl(_healthControl);
        _healthControl.onDeathEvent.addListener(_deathListener);
        
        _networkPresenceControl = new PlayerNetworkPresenceControl(_networkAppState);
        _player.addControl(_networkPresenceControl);
        _networkPresenceControl.setEnabled(true);
        
        _inputControl = new PlayerInputControl();
        _player.addControl(_inputControl);
    }
    
    private void initListeners()
    {
        _listener = new ClientListener()
        {
            @Override
            public void messageReceived(Client source, Message message)
            {
                if(message instanceof PlayerHitMessage)
                {
                    PlayerHitMessage msg = (PlayerHitMessage)message;
                    HitEventArgs data = msg.deserialize();
                    _healthControl.takeDamage(data);
                    _gameInterfaceControl.updateHealthBar(_healthControl.getCurrentHealth());
                }
            }
            
        };
        
        _deathListener = new BlastbackEventListener<HitEventArgs>()
        {
            @Override
            public void invoke(HitEventArgs e)
            {
                respawnPlayer();
            }
        };
    }

    public Vector3f getPlayerPosition()
    {
        return _charControl.getPhysicsLocation().clone();
    }
    
    private void respawnPlayer()
    {
        _healthControl.resetHealth();
        
        Random generator = new Random(); 
        int i = generator.nextInt(4);
        Vector3f playerPosition = _charControl.getPhysicsLocation();
        Vector3f temp = new Vector3f(0, 2.2f, 0);
        
        switch (i) {
            case 0:
                temp = new Vector3f(10f, 2.2f, 20f);
                if(temp.distance(playerPosition) > 4f)
                    break; 
            case 1:
                temp = new Vector3f(8f, 2.2f, -20f);
                if(temp.distance(playerPosition) > 4f)
                    break; 
            case 2:
                temp = new Vector3f(-8f, 2.2f, -20f);
                if(temp.distance(playerPosition) > 4f)
                    break; 
            case 3:
                temp = new Vector3f(-8f, 2.2f, 20f);
                if(temp.distance(playerPosition) > 4f)
                    break; 
            default:
                break;
        }
        _charControl.setPhysicsLocation(temp);
       
        _charControl.setViewDirection(new Vector3f(1f, 0f, 0f));
    }
    private void Log(String msg)
    {
        Logger.getLogger(GameClient.class.getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }
}
