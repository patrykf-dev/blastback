/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.controls;

import com.blastback.shared.messages.data.PlayerStateInfo;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
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
    private RigidBodyControl _rbControl;
    
    //TODO: INTERPOLATION DATA
    private Vector3f _targetPosition;
    private Vector3f _targetRotation;
    
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
        Vector3f nextPos = FastMath.interpolateLinear(0.05f, _charControl.getPhysicsLocation(), _targetPosition);
        Vector3f nextRot = FastMath.interpolateLinear(0.05f, _charControl.getViewDirection(), _targetRotation);
        setPosition(nextPos);
        setRotation(nextRot);
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
    
    /**
     * Method sets new position to which affected character should move making the character move smoothly towards desired position.
     * @param position 
     */
    public void setTargetPosition(Vector3f position)
    {
        _targetPosition = position;
    }
    
    /**
     * Method sets new rotation for the affected character, making it interpolate its local rotation towards the desired rotation smoothly.
     * @param rotation 
     */
    public void setTargetRotation(Vector3f rotation)
    {
        _targetRotation = rotation;
    }

    public void enableCharacter(Node root, PhysicsSpace space)
    {
        root.attachChild(spatial);
        space.add(spatial);
    }
    
    public void disableCharacter(Node root, PhysicsSpace space)
    {
        root.detachChild(spatial);
        space.remove(spatial);
    }
    
    private void setPosition(Vector3f position)
    {
        _charControl.setPhysicsLocation(position);
    }

    private void setRotation(Vector3f rotation)
    {
        _charControl.setViewDirection(rotation);
    }
}
