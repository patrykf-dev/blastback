/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.events;

import com.blastback.listeners.ShootEventListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Eryk
 */
public class ShootEvent
{
    
    private final List<ShootEventListener> _listeners;
    
    public ShootEvent()
    {
        _listeners = new ArrayList<>();
    }
    
    public void notify(Vector3f shotPosition, Quaternion shotRotation)
    {
        ShootEventArgs shootEventArgs = new ShootEventArgs(shotPosition, shotRotation);
        for(ShootEventListener listener : _listeners)
        {
            listener.invoke(shootEventArgs);
        }
    }
    
    public void addListener(ShootEventListener listener)
    {
        if(_listeners.contains(listener) == false)
        {
            _listeners.add(listener);
        }
    }
    
    public void removeListener(ShootEventListener listener)
    {
        _listeners.remove(listener);
    }
    
    public class ShootEventArgs
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
}
