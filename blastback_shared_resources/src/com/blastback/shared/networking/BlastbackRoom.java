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

    public BlastbackRoom(String _name)
    {
        this._name = _name;
        this._mode = _mode;
        this._id = COUNTER++;
        this._mode = GameMode.Deathmatch;
        this._state = RoomState.Idle;
        this._scoreboard = new Scoreboard();
    }

    public String getName()
    {
        return _name;
    }
    
    public int getId()
    {
        return _id;
    }

    public RoomState getState()
    {
        return _state;
    }

    public GameMode getMode()
    {
        return _mode;
    }
}
