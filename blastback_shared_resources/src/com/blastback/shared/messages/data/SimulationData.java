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
public class SimulationData {
    List<PlayerStateInfo> data;
    
    public SimulationData(){
        data = new ArrayList<>();
    }
    
    public void addPlayer(PlayerStateInfo p)
    {
        data.add(p);
    }
    
    public void removePlayer(PlayerStateInfo p)
    {
        data.remove(p);
    }
    
    public PlayerStateInfo find(int id){
        for(PlayerStateInfo p : data){
            if(p.getIdentityData().getId() == id)
                return p;
        }
        return null;
    }
    
    public List<PlayerStateInfo> getdata()
    {
        return data;
    }
    
    public void resetStats()
    {
        data.forEach((info) ->
        {
            info.getMatchStats().resetStats();
        });
    }

}
