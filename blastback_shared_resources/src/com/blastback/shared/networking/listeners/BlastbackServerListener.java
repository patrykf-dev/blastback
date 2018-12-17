package com.blastback.shared.networking.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;


public class BlastbackServerListener implements MessageListener<HostedConnection> 
{
    @Override
    public void messageReceived(HostedConnection source, Message m)
    {
    }
}
