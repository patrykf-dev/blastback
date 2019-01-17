/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.shared.messages.MatchEndedMessage;
import com.blastback.shared.messages.MatchStartedMessage;
import com.blastback.shared.messages.data.MatchSettings;
import com.blastback.shared.observer.BlastbackEvent;
import com.blastback.shared.observer.BlastbackEventListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Server;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eryk
 */
public class GameMatchAppState extends BaseAppState
{

    public final BlastbackEvent<MatchSettings> onRoundStarted = new BlastbackEvent<>();
    public final BlastbackEvent<MatchSettings> onRoundEnded = new BlastbackEvent<>();

    private GameServer _app;
    private ServerNetworkAppState _networkAppState;

    private BlastbackEventListener<MatchSettings> _startedListener;
    private BlastbackEventListener<MatchSettings> _endedListener;

    private final float _gameLength;
    private float _remainingTime;
    private final float _startDelay = 5.0f;
    private float _timeToNextRound;

    /**
     * AppState that governs match cycle.
     *
     * @param roundLength in seconds must be greater than 0
     */
    public GameMatchAppState(int roundLength)
    {
        _gameLength = roundLength;
        _remainingTime = 0;
        _timeToNextRound = _startDelay;
        initListeners();
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameServer) app;
        _networkAppState = _app.getStateManager().getState(ServerNetworkAppState.class);
    }

    @Override
    public void update(float tpf)
    {
        if (_remainingTime > 0)
        {
            _remainingTime -= tpf;
            if (_remainingTime <= 0)
            {
                _remainingTime = 0;
                _timeToNextRound = _startDelay;
                onRoundEnded.notify(getMatchSettings());
                Log("Round Ended");
            }
        } else
        {
            _timeToNextRound -= tpf;
            if (_timeToNextRound <= 0)
            {
                _remainingTime = _gameLength;
                onRoundStarted.notify(getMatchSettings());
                Log("Round Started");
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
        onRoundEnded.addListener(_endedListener);
        onRoundStarted.addListener(_startedListener);
    }

    @Override
    protected void onDisable()
    {
        onRoundEnded.removeListener(_endedListener);
        onRoundStarted.removeListener(_startedListener);
    }

    public float getRemainingTime()
    {
        return _remainingTime;
    }

    public MatchSettings getMatchSettings()
    {
        return new MatchSettings(_gameLength);
    }

    private void initListeners()
    {
        _startedListener = new BlastbackEventListener<MatchSettings>()
        {
            @Override
            public void invoke(MatchSettings e)
            {
                sendMatchStartedMessage(e);
            }
        };
        _endedListener = new BlastbackEventListener<MatchSettings>()
        {
            @Override
            public void invoke(MatchSettings e)
            {
                sendMatchEndedMessage(e);
            }
        };
    }

    private void sendMatchStartedMessage(MatchSettings settings)
    {
        Server server = _networkAppState.getServer();
        MatchStartedMessage message = new MatchStartedMessage(settings);
        server.broadcast(message);

    }

    private void sendMatchEndedMessage(MatchSettings settings)
    {
        Server server = _networkAppState.getServer();
        MatchEndedMessage message = new MatchEndedMessage(settings);
        server.broadcast(message);

    }

    private void Log(String msg)
    {
        Logger.getLogger(GameServer.class.getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }
}
