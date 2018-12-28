package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIAppState extends BaseAppState implements ScreenController
{

    private Nifty _niftyInstance;
    private List<BaseAppState> _gameStates;
    private GameClient _application;

    /**
     * Parameterless constructor is needed for xml to create its controller
     * (using reflection). All initialization should be done in the initialize
     * method though.
     */
    public GUIAppState()
    {
    }

    @Override
    protected void initialize(Application app)
    {
        _application = (GameClient) app;
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        _niftyInstance = niftyDisplay.getNifty();
        _niftyInstance.fromXml("Interface/Screens/screens.xml", "start-screen", this);

        app.getGuiViewPort().addProcessor(niftyDisplay);

        _gameStates = new ArrayList<>();
        _gameStates.add(new InputManagerAppState());
        _gameStates.add(new MapAppState());
        _gameStates.add(new PlayerAppState());
        _gameStates.add(new TopDownCameraAppState());
        _gameStates.add(new NetworkAppState());

    }

    /**
     * Handle join button onClick (only in hud-screen).
     */
    public void joinButtonClicked()
    {
        // This should show the "connecting..." message, but it's shown after the attachGameStates call.
        _niftyInstance.getCurrentScreen().findElementById("wait_layer").setVisible(true);

        boolean canConnect = true;
        if (canConnect)
        {
            attachGameStates();
            _niftyInstance.gotoScreen("hud-screen");

        } else
        {

        }
    }

    /**
     * Handle exit button onClick (only in hud-screen).
     */
    public void exitButtonClicked()
    {
        Log("EXIT BUTTON CLICKED");
    }


    /**
     * Update ammo label (only in hud-screen).
     *
     * @param currentAmmo amount of currently owned ammo
     * @param maxAmmo max amount of ammo
     */
    public void updateAmmo(int currentAmmo, int maxAmmo)
    {
        Element ammoLabel = _niftyInstance.getCurrentScreen().findElementById("text_ammo");
        if (ammoLabel != null)
        {
            ammoLabel.getRenderer(TextRenderer.class).setText("AMMO " + currentAmmo + " / " + maxAmmo);
        }
    }

    /**
     * Update timer label (only in hud-screen).
     *
     * @param timeLeft time left in seconds
     */
    public void updateTimer(long timeLeft)
    {
        Element timerLabel = _niftyInstance.getCurrentScreen().findElementById("text_timer");
        if (timerLabel != null)
        {
            int minutes = (int) (timeLeft / 60);
            int seconds = (int) (timeLeft - 60 * minutes);
            timerLabel.getRenderer(TextRenderer.class).setText("TIMER " + minutes + ":" + seconds);
        }
    }

    private void attachGameStates()
    {
        for (BaseAppState state : _gameStates)
        {
            _application.getStateManager().attach(state);
        }
    }

    private void detachGameStates()
    {
        for (BaseAppState state : _gameStates)
        {
            _application.getStateManager().detach(state);
        }
    }

    @Override
    protected void cleanup(Application app)
    {
        detachGameStates();
    }

    @Override
    public void update(float tpf)
    {
    }

    @Override
    public void bind(Nifty nifty, Screen screen)
    {
        // This should be empty, because screen already gets its controller in our initialize method.
    }

    @Override
    public void onStartScreen()
    {
        // Happens every time a screen loads.
    }

    @Override
    public void onEndScreen()
    {
        // Happens every time a screen hides.
    }

    private void Log(String msg)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }
}
