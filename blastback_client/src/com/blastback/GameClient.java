package com.blastback;

import com.blastback.appstates.InputManagerAppState;
import com.blastback.appstates.MapAppState;
import com.blastback.appstates.NetworkAppState;
import com.blastback.appstates.PlayerAppState;
import com.blastback.appstates.TopDownCameraAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;

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
        
        // Disable the black fps box
        setDisplayFps(false);
        setDisplayStatView(false);
        
        // Link nifty
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Screens/screens.xml", "start-screen");
        guiViewPort.addProcessor(niftyDisplay);
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
