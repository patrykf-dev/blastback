package com.blastback.shared.networking;

import com.jme3.network.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlastbackServer
{

    private Server _server;
    private String _ip;
    private int _port;
    private BlastbackRoom _idleRoom;
    private List<BlastbackRoom> _rooms;
    private List<BlastbackClient> _clients;

    public BlastbackServer(Server _server, String _ip, int _port)
    {
        // _server should be created here instead of passing reference
        this._server = _server;
        this._ip = _ip;
        this._port = _port;
        this._idleRoom = new BlastbackRoom(Constants.IDLE_ROOM_NAME);
        this._rooms = new ArrayList<>();
        this._clients = new ArrayList<>();
    }

    /**
     * Add room to server rooms collection. Can fail if there are already too
     * many rooms.
     *
     * @param r room to add
     */
    public void addRoom(BlastbackRoom r)
    {
        if (_rooms.size() >= Constants.MAX_ROOMS_COUNT)
        {
            throw new IllegalArgumentException("Too many rooms already!");
        } else
        {
            _rooms.add(r);
        }
    }

    /**
     * Try to remove room with given id.
     *
     * @param roomId id of room to remove
     */
    public void removeRoom(int roomId)
    {
        BlastbackRoom room = _rooms.stream()
                .filter(r -> r.getId() == roomId)
                .findFirst()
                .orElse(null);

        if (room == null)
        {
            throw new NullPointerException("Cannot find room of id " + roomId);
        } else
        {
            if (room.getState() == RoomState.GameInProgress)
            {
                throw new IllegalArgumentException("Cannot remove room " + roomId + ", game is in progress");
            } else
            {
                _rooms.remove(room);
            }
        }
    }

    /**
     * Add client to server clients collection and to its idle room. Can fail if
     * there are already too many clients.
     *
     * @param c
     */
    public void addClient(BlastbackClient c)
    {
        if (_clients.size() >= Constants.MAX_CLIENTS_COUNT)
        {
            throw new IllegalArgumentException("Too many clients already!");
        } else
        {
            _clients.add(c);
            moveClientToRoom(c.getId(), findRoom(Constants.IDLE_ROOM_NAME).getId());
        }
    }

    /**
     * Try to remove client with given id.
     *
     * @param clientId
     */
    public void removeClient(int clientId)
    {
        BlastbackClient client = _clients.stream()
                .filter(r -> r.getId() == clientId)
                .findFirst()
                .orElse(null);

        if (client == null)
        {
            throw new NullPointerException("Cannot find client of id " + clientId);
        } else
        {
            _rooms.remove(client);
        }
    }

    /**
     * Enumerate all clients from given room.
     *
     * @param roomId room's id of which you want to enumerate clients
     * @return
     */
    public List<BlastbackClient> enumerateRoomClients(int roomId)
    {
        List<BlastbackClient> toret = _clients.stream()
                .filter(c -> c.getRoomId() == roomId)
                .collect(Collectors.toList());

        return toret;
    }

    /**
     * Try to move client of given id to a given room id.
     *
     * @param clientId id of client to move
     * @param roomId id of room to move the player to
     */
    public void moveClientToRoom(int clientId, int roomId)
    {
        if (enumerateRoomClients(roomId).size() >= Constants.MAX_CLIENTS_PER_ROOM_COUNT)
        {
            throw new IllegalArgumentException("There are already too many clients in room of id " + roomId);
        } else
        {
            BlastbackClient client = findClient(clientId);
            client.setRoomId(roomId);
        }
    }

    //--------------------------------------------------------------------------
    //FINDERS-------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * Find client based on its name.
     *
     * @param clientName name of client
     * @return client
     */
    public BlastbackClient findClient(String clientName)
    {
        BlastbackClient client = _clients.stream()
                .filter(c -> c.getName().equals(clientName))
                .findFirst()
                .orElse(null);

        if (client != null)
        {
            return client;
        } else
        {
            throw new IllegalArgumentException("Cannot find client called " + clientName);
        }
    }

    /**
     * Find room based on its id.
     *
     * @param clientId id of room
     * @return room
     */
    public BlastbackClient findClient(int clientId)
    {
        BlastbackClient client = _clients.stream()
                .filter(c -> c.getId() == clientId)
                .findFirst()
                .orElse(null);

        if (client != null)
        {
            return client;
        } else
        {
            throw new IllegalArgumentException("Cannot find client of id " + clientId);
        }
    }

    /**
     * Find room based on its name.
     *
     * @param roomName name of room
     * @return room
     */
    public BlastbackRoom findRoom(String roomName)
    {
        BlastbackRoom room = _rooms.stream()
                .filter(r -> r.getName().equals(roomName))
                .findFirst()
                .orElse(null);

        if (room != null)
        {
            return room;
        } else
        {
            throw new IllegalArgumentException("Cannot find room called " + roomName);
        }
    }

    /**
     * Find room based on its id.
     *
     * @param roomId id of room
     * @return room
     */
    public BlastbackRoom findRoom(int roomId)
    {
        BlastbackRoom room = _rooms.stream()
                .filter(r -> r.getId() == roomId)
                .findFirst()
                .orElse(null);

        if (room != null)
        {
            return room;
        } else
        {
            throw new IllegalArgumentException("Cannot find room of id " + roomId);
        }
    }

}
