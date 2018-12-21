package com.blastback;

import com.blastback.appstates.InputManagerAppState;
import com.blastback.appstates.MapAppState;
import com.blastback.appstates.NetworkAppState;
import com.blastback.appstates.PlayerAppState;
import com.blastback.appstates.SimulationAppState;
import com.blastback.appstates.TopDownCameraAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class GameClient extends SimpleApplication
{

    public GameClient()
    {
        super(new StatsAppState(),
                new AudioListenerState(),
                new DebugKeysAppState(),
                new InputManagerAppState(),
                new MapAppState(),
                new PlayerAppState(),
                new TopDownCameraAppState(),
                new NetworkAppState(),
                new SimulationAppState());
    }

    public static void main(String[] args)
    {
        GameClient app = new GameClient();
        app.setPauseOnLostFocus(false);
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
        //BulletAppState gives UnsatisfiedLinkError when attached in constructor
        stateManager.attach(new BulletAppState());
    }

    @Override
    public void simpleUpdate(float tpf)
    {
    }

    @Override
    public void simpleRender(RenderManager rm)
    {
    }
}
