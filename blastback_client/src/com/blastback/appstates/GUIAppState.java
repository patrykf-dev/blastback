package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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

        BatchRenderConfiguration batchConfig = new BatchRenderConfiguration();
        batchConfig.atlasHeight = 2048;
        batchConfig.atlasWidth = 2048;
        batchConfig.fillRemovedImagesInAtlas = false;
        batchConfig.disposeImagesBetweenScreens = false;
        batchConfig.useHighQualityTextures = true;

        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort(), batchConfig);
        _niftyInstance = niftyDisplay.getNifty();
        _niftyInstance.fromXml("Interface/Screens/screens.xml", "start-screen", this);
        app.getGuiViewPort().addProcessor(niftyDisplay);

        _gameStates = new ArrayList<>();
        _gameStates.add(new InputManagerAppState());
        _gameStates.add(new MapAppState());
        _gameStates.add(new PlayerAppState());
        _gameStates.add(new BulletFactoryAppState());
        _gameStates.add(new TopDownCameraAppState());
    }

    /**
     * Handle join button onClick (only in hud-screen).
     */
    public void joinButtonClicked()
    {
        boolean fieldsFilled = verifyStartScreenInput();
        if (fieldsFilled)
        {
            boolean hostAvailable = hostAvailabilityCheck();
            if (hostAvailable)
            {
                attachGameStates();
                // When clients connects, we don't want nifty to eat any input events
                _niftyInstance.setIgnoreKeyboardEvents(true);
                _niftyInstance.setIgnoreMouseEvents(true);
                _niftyInstance.gotoScreen("hud-screen");
            } else
            {
                showMessage("Cannot connect to host!");
            }
        } else
        {
            showMessage("Incorrect input!");
        }
    }

    private void showMessage(String msg)
    {
        Element txtMessage = _niftyInstance.getCurrentScreen().findElementById("text_message");

        if (txtMessage != null)
        {
            txtMessage.getRenderer(TextRenderer.class).setText(msg);
        }
    }

    /**
     * Handle exit button onClick (only in hud-screen).
     */
    public void exitButtonClicked()
    {
        detachGameStates();
        _application.stop();
    }

    /**
     * It is a way of verifying user's ip/port combination. I wasn´t able to
     * check that while attaching the state. This is the only workaround I have
     * found and I am not proud of it.
     *
     * @return value indicating whether it is possible to connect to given port
     */
    public boolean hostAvailabilityCheck()
    {
        boolean available = true;
        Socket s;
        try
        {
            s = new Socket(getTextIp(), getTextPort());
            if (s.isConnected())
            {
                s.close();
            }
        } catch (UnknownHostException e)
        {
            // unknown host 
            available = false;
        } catch (IOException e)
        {
            // io exception, service probably not running 
            available = false;
        } catch (NullPointerException e)
        {
            available = false;
        } finally
        {
            s = null;
        }

        return available;
    }

    private boolean verifyStartScreenInput()
    {
        int maxNameLength = 6;
        return (!getTextIp().equals("") && getTextName().length() > 0 && 
                getTextName().length() <= maxNameLength && getTextPort() != -1);
    }

    private void attachGameStates()
    {
        _gameStates.add(new NetworkAppState(getTextIp(), getTextPort(), getTextName()));
        _gameStates.add(new SimulationAppState());
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

    public Nifty getNifty()
    {
        return _niftyInstance;
    }
}
