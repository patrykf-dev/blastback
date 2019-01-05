package com.blastback.controls;

import com.blastback.appstates.GUIAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;


public class GameInterfaceControl extends AbstractControl
{
    private Nifty _niftyInstance;
    Element _scoreboardElement;

    public GameInterfaceControl( Nifty niftyInstance)
    {
        this._niftyInstance = niftyInstance;
    }
    
    public void displayScoreboard(boolean value)
    {
        if (_scoreboardElement == null)
        {
            _scoreboardElement = _niftyInstance.createPopup("popup_scoreboard");
        }

        if (value)
        {
            _niftyInstance.showPopup(_niftyInstance.getCurrentScreen(), _scoreboardElement.getId(), null);
        } else
        {
            _niftyInstance.closePopup(_scoreboardElement.getId());

        }
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
    protected void controlUpdate(float tpf)
    {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }
    
}
