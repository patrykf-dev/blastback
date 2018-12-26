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

    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");

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
        //getApplication().stop(); NullPointerException here
    }

    /**
     * Update ammo label
     * 
     * @param currentAmmo amount of currently owned ammo
     * @param maxAmmo max amount of ammo
     */
    public void updateAmmo(int currentAmmo, int maxAmmo)
    {
        Element niftyElement = _niftyScreen.getCurrentScreen().findElementById("text_ammo");
        niftyElement.getRenderer(TextRenderer.class).setText("AMMO " + currentAmmo + "/" + maxAmmo);
    }

    /**
     * Update timer label
     *
     * @param timeLeft time left in seconds
     */
    public void updateTimer(long timeLeft)
    {
        int minutes = (int) (timeLeft / 60);
        int seconds = (int) (timeLeft - 60 * minutes);
        Element niftyElement = _niftyScreen.getCurrentScreen().findElementById("text_timer");
        niftyElement.getRenderer(TextRenderer.class).setText("TIMER " + minutes + ":" + seconds);
    }

    @Override
    protected void cleanup(Application app)
    {
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        ((SimpleApplication) app).getRootNode().detachChild(localRootNode);
        ((SimpleApplication) app).getGuiNode().detachChild(localGuiNode);
    }

    @Override
    protected void onEnable()
    {
        //Called when the state is fully enabled, ie: is attached and
        //isEnabled() is true or when the setEnabled() status changes after the
        //state is attached.
    }

    @Override
    protected void onDisable()
    {
        //Called when the state was previously enabled but is now disabled
        //either because setEnabled(false) was called or the state is being
        //cleaned up.
    }

    @Override
    public void update(float tpf)
    {
        //TODO: implement behavior during runtime
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
