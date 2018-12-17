package com.blastback.shared.networking;

import com.jme3.network.Server;
import java.util.List;


public class BlastbackServer
{
    private Server _server;
    private String _ip;
    private int _port;
    private BlastbackRoom _idleRoom;
    private List<BlastbackRoom> _rooms;
    private List<BlastbackClient> _clients;
}
