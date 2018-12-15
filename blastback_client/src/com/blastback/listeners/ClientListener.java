/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.listeners;

import com.blastback.sharedresources.messages.HelloMessage;
import com.jme3.network.Client;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author Marcin
 */
public class ClientListener implements MessageListener<Client> 
{
  public void messageReceived(Client source, Message message) 
  {
    if (message instanceof HelloMessage) 
    {
      // do something with the message
      HelloMessage helloMessage = (HelloMessage) message;
      System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
    } // else...
    
    
  }
}
