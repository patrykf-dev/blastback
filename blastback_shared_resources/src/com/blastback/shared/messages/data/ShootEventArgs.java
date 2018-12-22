/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import com.blastback.shared.observer.BlastbackEventArgs;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Eryk
 */
public class ShootEventArgs extends BlastbackEventArgs
{

    private final Vector3f _shotPosition;
    private final Quaternion _shotRotation;

    public ShootEventArgs(Vector3f shotPosition, Quaternion shotRotation)
    {
        _shotPosition = shotPosition.clone();
        _shotRotation = shotRotation.clone();
    }

    public Vector3f getShotPosition()
    {
        return _shotPosition;
    }

    public Quaternion getShotRotation()
    {
        return _shotRotation;
    }
}
