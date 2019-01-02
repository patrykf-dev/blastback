package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.listeners.ClientListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
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
    }

    /**
     * Handle join button onClick (only in hud-screen).
     */
    public void joinButtonClicked() throws InterruptedException
    {
        boolean canConnect = initFakeConnection();

        if (canConnect)
        {
            attachGameStates();
            _niftyInstance.gotoScreen("hud-screen");
        } else
        {

        }
    }

    private boolean initFakeConnection()
    {
        try
        {
            Client _clientInstance = Network.connectToServer(getTextIp(), getTextPort());
            _clientInstance.start();
            _clientInstance.close();
        } catch (IOException ex)
        {
            return false;
        }
        return true;
    }

    private void attachGameStates() throws InterruptedException
    {
        _gameStates.add(new NetworkAppState(getTextIp(), getTextPort()));
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


    private String getTextName()
    {
        TextField inputName = (TextField) _niftyInstance.getCurrentScreen().findNiftyControl("input_name", TextField.class);
        if (inputName != null)
        {
            return inputName.getRealText();
        } else
        {
            return "";
        }
    }

    private String getTextIp()
    {
        TextField inputIp = (TextField) _niftyInstance.getCurrentScreen().findNiftyControl("input_ip", TextField.class);
        if (inputIp != null)
        {
            return inputIp.getRealText();
        } else
        {
            return "";
        }
    }

    private int getTextPort()
    {
        TextField inputPort = (TextField) _niftyInstance.getCurrentScreen().findNiftyControl("input_port", TextField.class);
        if (inputPort != null)
        {
            try
            {
                int rc = Integer.parseInt(inputPort.getRealText());
                return rc;
            } catch (Exception ex)
            {
                return - 1;
            }
        } else
        {
            return -1;
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
