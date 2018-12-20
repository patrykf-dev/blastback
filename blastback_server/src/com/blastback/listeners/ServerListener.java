/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.listeners;

import com.blastback.shared.networking.data.Person;
import com.blastback.shared.messages.HelloMessage;
import com.google.gson.Gson;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin
 */
public class ServerListener implements MessageListener<HostedConnection>
{

    @Override
    public void messageReceived(HostedConnection source, Message message)
    {
        if (message instanceof HelloMessage)
        {
            System.out.println("\t\tASDASDASDAS");
            
            
            // do something with the message
            HelloMessage helloMessage = (HelloMessage) message;
            String data = helloMessage.getContent();
            
            System.out.println("Server received raw: " + data + "' from client #" + source.getId());
            
            
            // We need to know class name
            Gson gson = new Gson();
            Person personInstance = gson.fromJson(data, Person.class);
            
            System.out.println("Server received '" + personInstance.getinfo() + "' from client #" + source.getId());

        }
    }
    
}
