package com.blastback.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.nifty.tools.SizeValueType;

public class GameInterfaceControl extends AbstractControl
{

    private Nifty _niftyInstance;
    Element _scoreboardElement;

    public GameInterfaceControl(Nifty niftyInstance)
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
            String ammoCount = currentAmmo + " / " + maxAmmo;
            ammoLabel.getRenderer(TextRenderer.class).setText(ammoCount);
        }
    }

    /**
     * Update hp label and red/green panels (only in hud-screen).
     *
     * @param currentHp hp in <0,1>
     */
    public void updateHealthBar(float currentHp)
    {
        Element hpLabel = _niftyInstance.getCurrentScreen().findElementById("text_hp_percentage");
        Element greenPanel = _niftyInstance.getCurrentScreen().findElementById("panel_health_green");
        Element containerPanel = _niftyInstance.getCurrentScreen().findElementById("panel_health_bar");

        if (hpLabel != null && greenPanel != null && containerPanel != null)
        {
            int containerWidth = containerPanel.getWidth();
            int greenWidth = (int) (containerWidth * currentHp);            
            int hpValue = (int) (currentHp * 100);
            String hpCaption = hpValue + "%";
            
            hpLabel.getRenderer(TextRenderer.class).setText(hpCaption);
            greenPanel.setWidth(greenWidth);
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

    /**
     * Update waepon label and thumbnail (only in hud-screen).
     *
     * @param weapon weapon id from WeaponControl.weapons
     */
    public void updateWeapon(int weapon)
    {
        Element weaponLabel = _niftyInstance.getCurrentScreen().findElementById("text_weapon_type");
        Element weaponImage = _niftyInstance.getCurrentScreen().findElementById("image_weapon");

        if (weaponImage == null || weaponLabel == null)
        {
            return;
        }

        String weaponCaption = "";
        String imagePath = "Interface/Images/";
        switch (weapon)
        {
            case 1:
                weaponCaption = "Pistol";
                imagePath += "pistol_thumb.png";
                break;
            case 2:
                weaponCaption = "Shotgun";
                imagePath += "shotgun_thumb.png";
                break;
            case 3:
                weaponCaption = "Uzi";
                imagePath += "uzi_thumb.png";
                break;
        }

        NiftyImage image = _niftyInstance.getRenderEngine().createImage(_niftyInstance.getCurrentScreen(), imagePath, true);
        weaponImage.getRenderer(ImageRenderer.class).setImage(image);
        weaponLabel.getRenderer(TextRenderer.class).setText(weaponCaption);
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
