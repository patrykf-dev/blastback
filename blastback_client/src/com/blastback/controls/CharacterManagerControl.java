/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.blastback.shared.messages.data.PlayerStateInfo;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Eryk
 */
public class CharacterManagerControl extends AbstractControl
{
    private int _id;
    private CharacterControl _charControl;
    //TODO: INTERPOLATION DATA
    
    public CharacterManagerControl(int id)
    {
        _id = id;
    }
    
    public CharacterManagerControl()
    {
        _id = -1;
    }

    @Override
    public void setSpatial(Spatial spatial)
    {
        super.setSpatial(spatial);
        _charControl = spatial.getControl(CharacterControl.class);
    }

    @Override
    protected void controlUpdate(float tpf)
    {
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
        
    }
    
    public int getId()
    {
        return _id;
    }

    public void setId(int _id)
    {
        this._id = _id;
    }
    
    public void setFromState(PlayerStateInfo state)
    {
        _id = state.getClientId();
        setPosition(state.getPlayerState().getLocalTranslation());
        setRotation(state.getPlayerState().getLocalRotation());
    }
    
    public void setPosition(Vector3f position)
    {
        _charControl.setPhysicsLocation(position);
    }
    
    public void setRotation(Vector3f rotation)
    {
        _charControl.setViewDirection(rotation);
    }

    public void enableCharacter(Node root, PhysicsSpace space)
    {
        root.detachChild(spatial);
        space.remove(spatial);
    }
    
    public void disableCharacter(Node root, PhysicsSpace space)
    {
        root.attachChild(spatial);
        space.add(spatial);
    }

    
    
    
}
