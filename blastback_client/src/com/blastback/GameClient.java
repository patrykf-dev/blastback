package com.blastback;

import com.blastback.appstates.InputManagerAppState;
import com.blastback.appstates.MapAppState;
import com.blastback.appstates.NetworkAppState;
import com.blastback.appstates.PlayerAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
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
                new NetworkAppState());
    }

    public static void main(String[] args)
    {
        GameClient app = new GameClient();
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
        //BulletAppState gives UnsatisfiedLinkError when attached in constructor
        stateManager.attach(new BulletAppState());

        cam.setLocation(new Vector3f(0f, 20f, 0.1f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
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
    public void simpleRender(RenderManager rm)
    {
    }
}
