package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.CharacterManagerControl;
import com.blastback.controls.GameInterfaceControl;
import com.blastback.listeners.ClientListener;
import com.blastback.shared.messages.PlayerShotMessage;
import com.blastback.shared.messages.SimulationDataMessage;
import com.blastback.shared.messages.data.PlayerStateInfo;
import com.blastback.shared.messages.data.ShootEventArgs;
import com.blastback.shared.messages.data.WeaponInfo;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulationAppState extends BaseAppState
{

    private GameClient _app;
    private BulletAppState _bulletAppState;
    private NetworkAppState _network;
    private GameInterfaceControl _gameInterfaceControl;

    private final ArrayList<CharacterManagerControl> _characters;

    private final List<PlayerStateInfo> _pendingInfos = new ArrayList<>();
    private final List<ShootEventArgs> _pendingBullets = new ArrayList<>();

    private ClientListener _listener;

    public SimulationAppState()
    {
        initListener();
        _characters = new ArrayList<>();
    }

    @Override
    public void update(float tpf)
    {
        Node root = _app.getRootNode();
        PhysicsSpace space = _bulletAppState.getPhysicsSpace();
        synchronized (_pendingInfos)
        {
            if (_pendingInfos.isEmpty() == false)
            {
                updateSimulation(_pendingInfos);
                _pendingInfos.clear();
            }
        }
        synchronized (_pendingBullets)
        {
            for (ShootEventArgs e : _pendingBullets)
            {
                WeaponInfo wi = e.getWeaponInfo();
                BulletFactoryAppState.createBullet(root, space, e, true);
            }
            _pendingBullets.clear();
        }
    }

    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient) app;
        _bulletAppState = _app.getStateManager().getState(BulletAppState.class);
        _network = _app.getStateManager().getState(NetworkAppState.class);
        _gameInterfaceControl = _app.getStateManager().getState(PlayerAppState.class).getGameInterfaceControl();
    }

    @Override
    protected void cleanup(Application app)
    {

    }

    @Override
    protected void onEnable()
    {
        _network.addListener(_listener);
        Node root = _app.getRootNode();
        PhysicsSpace space = _bulletAppState.getPhysicsSpace();
        for (CharacterManagerControl character : _characters)
        {
            character.enableCharacter(root, space);
        }
    }

    @Override
    protected void onDisable()
    {
        _network.removeListener(_listener);
        Node root = _app.getRootNode();
        PhysicsSpace space = _bulletAppState.getPhysicsSpace();
        for (CharacterManagerControl character : _characters)
        {
            character.disableCharacter(root, space);
        }
    }

    private void initListener()
    {
        _listener = new ClientListener()
        {
            @Override
            public void messageReceived(Client source, Message message)
            {
                Node root = _app.getRootNode();
                PhysicsSpace space = _bulletAppState.getPhysicsSpace();
                if (message instanceof SimulationDataMessage)
                {
                    SimulationDataMessage msg = (SimulationDataMessage) message;
                    PlayerStateInfo[] arr = msg.deserialize().getArray();
                    List<PlayerStateInfo> infos = new ArrayList<>(Arrays.asList(arr));
                    synchronized (_pendingInfos)
                    {
                        _pendingInfos.clear();
                        for (PlayerStateInfo state : infos)
                        {
                            _pendingInfos.add(state);
                        }
                    }
                } else if (message instanceof PlayerShotMessage)
                {
                    PlayerShotMessage msg = (PlayerShotMessage) message;
                    ShootEventArgs e = msg.deserialize();
                    synchronized (_pendingBullets)
                    {
                        _pendingBullets.add(e);
                    }
                }
            }
        };
    }

    /**
     * Method interprets received PlayerStateInfosMessage and updates simulation
     * accordingly. (Wrapper function).
     *
     * @param playerStates
     */
    public void updateSimulation(List<PlayerStateInfo> playerStates)
    {
        _gameInterfaceControl.updateScoreboard(playerStates);

        removeLocalPlayerState(playerStates);

        if (playerStates.size() != _characters.size())
        {
            resetCharacters(playerStates);
        }

        int processed = updateCharacters(playerStates);

        if (processed != playerStates.size())
        {
            resetCharacters(playerStates);
        }
    }

    /**
     * Method updates number of simulated characters based on received list of
     * player states. Then all characters are reset (id, position and rotation)
     * to match given state.
     *
     * @param playerStates List of characters' states that need to be simulated
     * (must not contain local player state!).
     */
    private void resetCharacters(List<PlayerStateInfo> playerStates)
    {
        disableCharacters();

        setCharacterListSize(playerStates.size());

        for (int i = 0; i < playerStates.size(); i++)
        {
            _characters.get(i).setFromState(playerStates.get(i));
        }

        enableCharacters();
    }

    /**
     * Removes a state that has an id equal to this client id.
     *
     * @param playerStates Received list of player states from the server.
     */
    private void removeLocalPlayerState(List<PlayerStateInfo> playerStates)
    {
        int localId = _network.getClientInstance().getId();
        for (int i = 0; i < playerStates.size(); i++)
        {
            if (playerStates.get(i).getIdentityData().getId() == localId)
            {
                playerStates.remove(i);
                break;
            }
        }
    }

    /**
     * Method updates local character spatials based on received list of player
     * states.
     *
     * @param playerStates states of remote clients (no local player info).
     * @return Number of characters that were properly updated.
     */
    private int updateCharacters(List<PlayerStateInfo> playerStates)
    {
        int processed = 0;
        for (PlayerStateInfo state : playerStates)
        {
            for (CharacterManagerControl character : _characters)
            {
                if (character.getId() == state.getIdentityData().getId())
                {
                    character.setUsername(state.getIdentityData().getUsername());
                    character.setTargetPosition(state.getPlayerState().getLocalTranslation());
                    character.setTargetRotation(state.getPlayerState().getLocalRotation());
                    processed++;
                }
            }
        }
        return processed;
    }

    /**
     * Disables all characters currently simulated (detaches from node and
     * physics space).
     */
    private void disableCharacters()
    {
        Node root = _app.getRootNode();
        PhysicsSpace space = _bulletAppState.getPhysicsSpace();

        for (CharacterManagerControl character : _characters)
        {
            character.disableCharacter(root, space);
        }
    }

    /**
     * Enables all characters currently to be simulated (attaches to root node
     * and physics space).
     */
    private void enableCharacters()
    {
        Node root = _app.getRootNode();
        PhysicsSpace space = _bulletAppState.getPhysicsSpace();

        for (CharacterManagerControl character : _characters)
        {
            character.enableCharacter(root, space);
        }
    }

    /**
     * Resizes simulated characters list. (ALL CHARACTERS PRESENT IN THE LIST
     * MUST BE DISABLED EARLIER)
     *
     * @param size
     */
    private void setCharacterListSize(int size)
    {
        while (_characters.size() < size)
        {
            _characters.add(CharacterManagerControl.createCharacter(_app.getAssetManager()));
        }

        while (_characters.size() > size)
        {
            _characters.remove(_characters.size() - 1);
        }
    }
}
