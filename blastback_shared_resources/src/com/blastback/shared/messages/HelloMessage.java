/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Marcin
 */

@Serializable
public class HelloMessage extends AbstractMessage 
{

    private String hello;       // custom message data
    public HelloMessage() {}    // empty constructor
    public HelloMessage(String s) { hello = s; } // custom constructor
    
    public String getSomething() {
        return hello;
    }
}