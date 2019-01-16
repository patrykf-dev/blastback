/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.shared.messages.data.MatchSettings;
import com.blastback.shared.observer.BlastbackEvent;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

/**
 *
 * @author Eryk
 */
public class GameMatchAppState extends BaseAppState
{
    public final BlastbackEvent<MatchSettings> onRoundStarted = new BlastbackEvent<>();
    public final BlastbackEvent<MatchSettings> onRoundEnded = new BlastbackEvent<>();
    
    private GameServer _app;
    
    private final float _gameLength;
    private float _remainingTime;
    private final float _startDelay = 5.0f;
    private float _timeToNextRound;
    
    /**
     * AppState that governs match cycle.
     * @param roundLength in seconds must be greater than 0
     */
    public GameMatchAppState(int roundLength)
    {
        _gameLength = roundLength;
        _remainingTime = 0;
        _timeToNextRound = _startDelay;
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameServer)app;
    }

    @Override
    public void update(float tpf)
    {
        if(_remainingTime > 0)
        {
            _remainingTime -= tpf;
            if(_remainingTime <= 0);
            {
                _remainingTime = 0;
                onRoundEnded.notify(new MatchSettings(_gameLength));
                _timeToNextRound = _startDelay;
            }
        }
        else
        {
            _timeToNextRound -= tpf;
            if(_timeToNextRound <= 0)
            {
                onRoundStarted.notify(new MatchSettings(_gameLength));
                _remainingTime = _gameLength;
            }
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
    
    public float getGameLength()
    {
        return _gameLength;
    }
    
}
