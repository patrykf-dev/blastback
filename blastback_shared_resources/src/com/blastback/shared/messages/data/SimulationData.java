/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arturhaczek
 */
public class SimulationData
{

    private List<PlayerStateInfo> _data;

    public SimulationData()
    {
        _data = new ArrayList<>();
    }

    public void addPlayer(PlayerStateInfo p)
    {
        _data.add(p);
    }

    public void removePlayer(PlayerStateInfo p)
    {
        _data.remove(p);
    }

    public PlayerStateInfo find(int id)
    {
        for (PlayerStateInfo p : _data)
        {
            if (p.getIdentityData().getId() == id)
            {
                return p;
            }
        }
        return null;
    }

    public List<PlayerStateInfo> getdata()
    {
        return _data;
    }

    public void resetStats()
    {
        _data.forEach((info) ->
        {
            info.getMatchStats().resetStats();
        });
    }

}
