package com.blastback.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenAppState extends BaseAppState implements ScreenController
{

    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");
    private final ColorRGBA backgroundColor = ColorRGBA.Gray;

    public ScreenAppState()
    {
    }

    
    @Override
    protected void initialize(Application app)
    {
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        ((SimpleApplication) app).getRootNode().attachChild(localRootNode);
        ((SimpleApplication) app).getGuiNode().attachChild(localGuiNode);
        ((SimpleApplication) app).getViewPort().setBackgroundColor(backgroundColor);

        /**
         * init the screen
         */
    }

    public void joinClick()
    {
        Log("JOIN BUTTON CLICKED");
        //nifty.gotoScreen(nextScreen);  // switch to another screen
        // start the game and do some more stuff...
        
    }

    public void exitClick()
    {
        Log("EXIT BUTTON CLICKED");
        getApplication().stop();
    }
    
    
    private void Log(String msg)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "\t[LOG] {0}", msg);
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

}
