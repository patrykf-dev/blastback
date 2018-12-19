package com.blastback.shared.networking;

import com.blastback.shared.networking.data.PlayerState;
import com.jme3.network.Client;


public class BlastbackClient
{
    private static int COUNTER = 0;
    
    private Client _client;
    private int _id;
    private String _name;
    private int _roomId;
    private PlayerState _state;

    public static int getCOUNTER()
    {
        return COUNTER;
    }

    public Client getClient()
    {
        return _client;
    }

    public int getId()
    {
        return _id;
    }

    public String getName()
    {
        return _name;
    }

    public int getRoomId()
    {
        return _roomId;
    }

    public PlayerState getState()
    {
        return _state;
    }
    
    
    
    
}
