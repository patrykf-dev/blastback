package com.blastback;

import com.blastback.appstates.BackgroundAudioAppState;
import com.blastback.appstates.GUIAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;

public class GameClient extends SimpleApplication
{

    public GameClient()
    {
        super(new StatsAppState(),
                new AudioListenerState(),
                new DebugKeysAppState(),
                new GUIAppState(),
                new BackgroundAudioAppState());
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

        setDisplayFps(false);
        setDisplayStatView(false);
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
