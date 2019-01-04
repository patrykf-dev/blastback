/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eryk
 * @param <T>
 */
public class BlastbackEvent<T>
{
    
    private final List<BlastbackEventListener<T>> _listeners;

    public BlastbackEvent()
    {
        _listeners = new ArrayList<>();
    }

    public void notify(T e)
    {
        _listeners.forEach((listener) ->
        {
            listener.invoke(e);
        });
    }

    public void addListener(BlastbackEventListener<T> listener)
    {
        if (_listeners.contains(listener) == false)
        {
            _listeners.add(listener);
        }
    }

    public void removeListener(BlastbackEventListener<T> listener)
    {
        _listeners.remove(listener);
    }
    
    public void clearListeners()
    {
        _listeners.clear();
    }
}
