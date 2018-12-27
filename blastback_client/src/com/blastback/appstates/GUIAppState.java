package com.blastback.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIAppState extends BaseAppState implements ScreenController
{
    private NiftyJmeDisplay _niftyDisplay;
    private Nifty _niftyScreen;

    /**
     * Parameterless constructor is needed for xml to create the object. All
     * initialization should be done in initialize though
     */
    public GUIAppState()
    {
    }

    @Override
    protected void initialize(Application app)
    {
        _niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        _niftyScreen = _niftyDisplay.getNifty();
        _niftyScreen.fromXml("Interface/Screens/screens.xml", "start-screen", this);
        app.getGuiViewPort().addProcessor(_niftyDisplay);
    }

    public void joinClick()
    {
        Log("JOIN BUTTON CLICKED");
        Log(_niftyScreen.getAllScreensName().toString());
        _niftyScreen.gotoScreen("hud");
    }

    public void exitClick()
    {
        Log("EXIT BUTTON CLICKED");
    }

    /**
     * Update ammo label in hud screen
     *
     * @param currentAmmo amount of currently owned ammo
     * @param maxAmmo max amount of ammo
     */
    public void updateAmmo(int currentAmmo, int maxAmmo)
    {
        Element ammoLabel = _niftyScreen.getCurrentScreen().findElementById("text_ammo");
        if (ammoLabel != null)
        {
            ammoLabel.getRenderer(TextRenderer.class).setText("AMMO " + currentAmmo + " / " + maxAmmo);
        }
    }

    /**
     * Update timer label in hud screen
     *
     * @param timeLeft time left in seconds
     */
    public void updateTimer(long timeLeft)
    {
        Element timerLabel = _niftyScreen.getCurrentScreen().findElementById("text_timer");
        if (timerLabel != null)
        {
            int minutes = (int) (timeLeft / 60);
            int seconds = (int) (timeLeft - 60 * minutes);
            timerLabel.getRenderer(TextRenderer.class).setText("TIMER " + minutes + ":" + seconds);
        }
    }

    @Override
    protected void cleanup(Application app)
    {
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }

    @Override
    public void update(float tpf)
    {
    }

    @Override
    public void bind(Nifty nifty, Screen screen)
    {
    }

    @Override
    public void onStartScreen()
    {
    }

    @Override
    public void onEndScreen()
    {
    }

    private void Log(String msg)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }
}
