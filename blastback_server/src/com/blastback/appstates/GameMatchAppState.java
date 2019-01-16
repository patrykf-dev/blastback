/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.shared.observer.BlastbackEvent;
import com.blastback.shared.observer.BlastbackEventArgs;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

/**
 *
 * @author Eryk
 */
public class GameMatchAppState extends BaseAppState
{
    public final BlastbackEvent<BlastbackEventArgs> onRoundEnded = new BlastbackEvent<>();
    
    private GameServer _app;
    
    private float _remainingTime;
    
    public GameMatchAppState(int roundLength)
    {
        _remainingTime = roundLength;
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameServer)app;
    }

    @Override
    public void update(float tpf)
    {
        _remainingTime -= tpf;
        if(_remainingTime < 0);
        {
            onRoundEnded.notify(BlastbackEventArgs.VOID);
        }
    }
    
    

    @Override
    protected void cleanup(Application app)
    {
    }

    @Override
    protected void onEnable()
    {
    }

    @Override
    protected void onDisable()
    {
    }
    
    
}
