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
    
}
