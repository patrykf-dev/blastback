package com.blastback.shared.networking;

import com.blastback.shared.networking.data.GameMode;
import com.blastback.shared.networking.data.Scoreboard;


public class BlastbackRoom
{
    private static int COUNTER = 0;
    
    private int _id;
    private String _name;
    private RoomState _state;
    private GameMode _mode;
    private Scoreboard _scoreboard;
    
}
