/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.listeners;

import com.blastback.events.ShootEvent.ShootEventArgs;


/**
 *
 * @author Eryk
 */
public interface ShootEventListener
{    
    public void invoke(ShootEventArgs e);
}
