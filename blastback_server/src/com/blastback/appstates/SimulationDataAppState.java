/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameServer;
import com.blastback.listeners.ServerListener;
import com.blastback.shared.messages.PlayerDeathMessage;
import com.blastback.shared.messages.PlayerHitMessage;
import com.blastback.shared.messages.PlayerMovedMessage;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.SimulationDataMessage;
import com.blastback.shared.messages.data.ClientCoordinates;
import com.blastback.shared.messages.data.HitEventArgs;
import com.blastback.shared.messages.data.MatchSettings;
import com.blastback.shared.messages.data.PlayerStateInfo;
import com.blastback.shared.messages.data.SimulationDataContainer;
import com.blastback.shared.messages.data.SimulationData;
import com.blastback.shared.networking.data.IdentityData;
import com.blastback.shared.networking.data.PlayerState;
import com.blastback.shared.observer.BlastbackEventListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Server;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class SimulationDataAppState extends BaseAppState
{

    private GameServer _app;
    private ServerNetworkAppState _serverNetworkAppState;
    private GameMatchAppState _gameMatchAppState;

    private final SimulationData _simData;
    private ServerListener _listener;

    private Timer _messageTimer;
    private final int _timerTick = 50;

    private BlastbackEventListener<MatchSettings> _roundStartedListener;

    public SimulationDataAppState()
    {
        initListeners();
        _simData = new SimulationData();
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameServer) app;
        _serverNetworkAppState = _app.getStateManager().getState(ServerNetworkAppState.class);
        _gameMatchAppState = _app.getStateManager().getState(GameMatchAppState.class);
    }

    @Override
    protected void cleanup(Application app)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onEnable()
    {
        _serverNetworkAppState.addListener(_listener);
        _gameMatchAppState.onRoundStarted.addListener(_roundStartedListener);
        if (_messageTimer == null)
        {
            initTimer();
        } else if (_messageTimer != null)
        {
            _messageTimer.cancel();
        }
    }

    @Override
    protected void onDisable()
    {
        _serverNetworkAppState.removeListener(_listener);
        _gameMatchAppState.onRoundStarted.removeListener(_roundStartedListener);
        if (_messageTimer != null)
        {
            _messageTimer.cancel();
        }
    }

    private void initTimer()
    {
        _messageTimer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                deleteInactivePlayers();
                sendSimulationData();
            }
        };
        _messageTimer.scheduleAtFixedRate(tt, 0, _timerTick);
    }

    private void deleteInactivePlayers()
    {

        Server server = _serverNetworkAppState.getServer();

        Iterator<PlayerStateInfo> it = _simData.getdata().iterator();

        while (it.hasNext())
        {
            PlayerStateInfo psi = it.next();
            HostedConnection connection = server.getConnection(psi.getIdentityData().getId());
            if (connection == null)
            {
                it.remove();
            }
        }

        //TODO: Send Disconnect message
    }

    private void sendSimulationData()
    {
        Server server = _serverNetworkAppState.getServer();
        PlayerStateInfo[] arr = new PlayerStateInfo[_simData.getdata().size()];

        for (int i = 0; i < _simData.getdata().size(); i++)
        {
            arr[i] = (PlayerStateInfo) _simData.getdata().get(i);
        }

        SimulationDataMessage message = new SimulationDataMessage(new SimulationDataContainer(arr, _gameMatchAppState.getRemainingTime()));
        server.broadcast(message);
    }

    private void initListeners()
    {
        _roundStartedListener = new BlastbackEventListener<MatchSettings>()
        {
            @Override
            public void invoke(MatchSettings e)
            {
                _simData.resetStats();
            }
        };

        _listener = new ServerListener()
        {
            @Override
            public void messageReceived(HostedConnection source, Message message)
            {
                Server server = _serverNetworkAppState.getServer();

                if (message instanceof PlayerMovedMessage)
                {
                    PlayerStateInfo playerUpdate = _simData.find(source.getId());

                    PlayerMovedMessage PMovedMessage = (PlayerMovedMessage) message;
                    ClientCoordinates coordinates = PMovedMessage.deserialize();

                    if (playerUpdate != null)
                    {
                        playerUpdate.getPlayerState().setLocalTranslation(coordinates.geTranslationVector());
                        playerUpdate.getPlayerState().setLocalRotation(coordinates.getRotationVector());
                    } else
                    {
                        PlayerState temp = new PlayerState(100, coordinates.geTranslationVector(), coordinates.getRotationVector());
                        //mozna przerzucic username do hellomessage i wtedy trzeba po porstu zrobic dodawanie player state w innym miejscu
                        IdentityData userData = new IdentityData(source.getId(), coordinates.getUsername());
                        playerUpdate = new PlayerStateInfo(temp, userData);
                        _simData.addPlayer(playerUpdate);
                    }

                    //System.out.println("Server received '" + coordinates.getCoordinates() + "' from client #" + source.getId());
                } else if (message instanceof PlayerShotMessage)
                {
                    server.broadcast(Filters.notEqualTo(source), message);
                } else if (message instanceof PlayerHitMessage)
                {
                    PlayerHitMessage msg = (PlayerHitMessage) message;
                    HitEventArgs data = msg.deserialize();
                    data.getShooterData().setId(source.getId());
                    msg = new PlayerHitMessage(data);
                    HostedConnection conn = server.getConnection(data.getTargetData().getId());
                    if (conn != null)
                    {
                        conn.send(msg);
                    }
                } else if (message instanceof PlayerDeathMessage)
                {
                    PlayerDeathMessage msg = (PlayerDeathMessage) message;
                    HitEventArgs data = msg.deserialize();
                    _simData.find(data.getShooterData().getId()).getMatchStats().addScore(1);
                    _simData.find(data.getTargetData().getId()).getMatchStats().addDeath();
                    Log("Player " + data.getShooterData().getUsername() + " killed player " + data.getTargetData().getUsername() + "!");
                    server.broadcast(message);
                }
            }

        };
    }

    private void Log(String msg)
    {
        Logger.getLogger(GameServer.class.getName()).log(Level.INFO, "\t[LOG] {0}", msg);
    }
}
