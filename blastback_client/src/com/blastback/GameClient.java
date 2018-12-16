package com.blastback;

import com.blastback.appstates.InputManagerAppState;
import com.blastback.controls.PlayerInputControl;
import com.blastback.controls.PlayerMovementControl;
import com.blastback.listeners.ClientListener;
import com.blastback.sharedresources.messages.HelloMessage;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.istack.internal.logging.Logger;
import java.util.logging.Level;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class GameClient extends SimpleApplication {

    BulletAppState bulletAppState;
    InputManagerAppState inputAppState;

    //Player fields
    Spatial player;
    private Client clientInstance;
    
    
    Timer messageTimer;
    //napisane tu zeby latwo bylo znalezc
    int timerTick = 50;
    
//    public GameClient()
//    {
//        super(new BulletAppState(), new InputManagerAppState(), new StatsAppState(), new AudioListenerState(), new DebugKeysAppState());
//    }
    
    public static void main(String[] args) {
        GameClient app = new GameClient();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        inputAppState = new InputManagerAppState();
        stateManager.attach(inputAppState);
        inputAppState.setEnabled(true);
        
        cam.setLocation(new Vector3f(0f, 20f, 0.1f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

        
        initScene();
        initPlayer();
        registerMessages();
        initConnection();
    }

    private void registerMessages()
    {
        Serializer.registerClass(HelloMessage.class);
    }
    
    private void initScene() {
        Spatial scene = assetManager.loadModel("Scenes/Main.j3o");
        CollisionShape col = CollisionShapeFactory.createMeshShape(scene);
        RigidBodyControl map_rb = new RigidBodyControl(0.0f);
        scene.addControl(map_rb);

        rootNode.attachChild(scene);
        bulletAppState.getPhysicsSpace().add(map_rb);
    }
    
    @Override
    public void destroy()
    {
        super.destroy();
        if(clientInstance != null)
        {
            messageTimer.cancel();
            clientInstance.close();
        }
    }
    
    private void initPlayer() {
        player = assetManager.loadModel("Models/Player.j3o");
        
        CollisionShape shape = new CapsuleCollisionShape(0.5f, 1f, 1);
        CharacterControl player_cc = new CharacterControl(shape, 0.1f);
        player_cc.setGravity(new Vector3f(0f, -1f, 0f));
        
        // Add controls to spatials
        player.addControl(player_cc);
        player.addControl(new PlayerMovementControl(bulletAppState));
        PlayerInputControl inputControl = new PlayerInputControl();
        player.addControl(inputControl);
        inputAppState.addListener(inputControl.getKeyboardListener());
        inputAppState.addListener(inputControl.getMouseListener());

        // Attach spatials
        rootNode.attachChild(player);
        
        Logger.getLogger(GameClient.class).log(Level.SEVERE, "InitPlayer Invoked");
    }

    private void cameraUpdate(float tpf) 
    {
        //cam.setLocation(player_rb.getPhysicsLocation().add(0f, 20f, 0f));
    }

    @Override
    public void simpleUpdate(float tpf) 
    {
        cameraUpdate(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private void initConnection() {
        
        int port = 6143;
        try 
        {
            //Log("Creating Client on port " + port);
            
            clientInstance = Network.connectToServer("localhost", port);
            clientInstance.addMessageListener(new ClientListener(), HelloMessage.class);
            clientInstance.start();
            
            Message helloMessage = new HelloMessage("TESTING. TESTING.");
            clientInstance.send(helloMessage);
            
            initTimer();
            
            //Log("Server created succesfully");
            
        } 
        catch (IOException ex) 
        {
            //Log(ex.getMessage());
        }
    }

    private void Log(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    private void initTimer() 
    {
        messageTimer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run() 
            {
                sendCoordinates();
            }
        };
            
        messageTimer.scheduleAtFixedRate(tt, 0, timerTick);
    }
    
    private void sendCoordinates()
    {   
        if (clientInstance.isConnected()) 
        {
            Vector3f coordsV3f = player.getLocalTranslation();
            String coordString = String.format("%f;%f;%f", coordsV3f.x,coordsV3f.y,coordsV3f.z);
            Message coordinates = new HelloMessage(coordString);
            clientInstance.send(coordinates);
        }
    }
}
