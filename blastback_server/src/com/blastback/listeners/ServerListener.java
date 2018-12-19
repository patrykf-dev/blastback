/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.listeners;

import com.blastback.gsonclassServer.Person;
import com.blastback.shared.messages.HelloMessage;
import com.google.gson.Gson;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

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
            // do something with the message
            HelloMessage helloMessage = (HelloMessage) message;
            String data = helloMessage.getContent();
            
            // We need to know class name
            Gson gson = new Gson();
            Person Mydata = gson.fromJson(data, Person.class);
            
            System.out.println("Server received '" + Mydata.getinfo() + "' from client #" + source.getId());System.out.println("Server received '" + helloMessage.getContent() + "' from client #" + source.getId());
            
//            String msgReceived = helloMessage.getContent();
//            Message response = new HelloMessage("RESPONDED");
//            source.send(response);
        }
    }
}
