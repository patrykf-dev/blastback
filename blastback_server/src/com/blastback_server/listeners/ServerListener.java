/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback_server.listeners;

import com.blastback.sharedresources.messages.HelloMessage;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author Marcin
 */
public class ServerListener implements MessageListener<HostedConnection> 
{
  public void messageReceived(HostedConnection source, Message message) 
  {
    if (message instanceof HelloMessage) 
    {
      // do something with the message
      HelloMessage helloMessage = (HelloMessage) message;
      System.out.println("Server received '" +helloMessage.getSomething() +"' from client #"+source.getId() );
      Message response = new HelloMessage("RESPONDED");
      source.send(response);
      
      
      
    } // else....
  }
}