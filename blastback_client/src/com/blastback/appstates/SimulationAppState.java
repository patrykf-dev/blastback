package com.blastback.appstates;

import com.blastback.GameClient;
import com.blastback.controls.CharacterManagerControl;
import com.blastback.listeners.ClientListener;
import com.blastback.shared.messages.PlayerStateInfosMessage;
import com.blastback.shared.messages.data.PlayerStateInfo;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

public class SimulationAppState extends BaseAppState
{
    private GameClient _app;
    private BulletAppState _bulletAppState;
    private NetworkAppState _network;
    
    private final ArrayList<CharacterManagerControl> _characters;
    
    private ClientListener _listener;
    
    public SimulationAppState()
    {
        initListener();
        _characters = new ArrayList<>();
    }
    
    @Override
    protected void initialize(Application app)
    {
        _app = (GameClient)app;
        _bulletAppState = _app.getStateManager().getState(BulletAppState.class);
        _network = _app.getStateManager().getState(NetworkAppState.class);
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
        for(CharacterManagerControl character : _characters)
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
    
    
    public void setClientsInfo(List<PlayerStateInfo> playerStates)
    {    
        removeLocalPlayerState(playerStates);
        
        if (playerStates.size() != _characters.size())
        {
            correctCharacters(playerStates);
        }
        
        int processed = updateCharacters(playerStates);
        
        if(processed != playerStates.size())
        {
            correctCharacters(playerStates);
        }
    }
    
    /**
     * Method updates number of simulated characters based on received list of player states. 
     * Then all characters are reset (id, position and rotation) to match given state.
     * @param playerStates List of characters' states that need to be simulated (must not contain local player state!).
     */
    private void correctCharacters(List<PlayerStateInfo> playerStates)
    {
        disableCharacters();
        
        setCharacterListSize(playerStates.size());
        
        for(int i = 0; i < playerStates.size(); i++)
        {
            _characters.get(i).setFromState(playerStates.get(i));
        }
        
        enableCharacters();
    }
    
    /**
     * Creates a spatial with necessary controls and returns its CharacterManagerControl.
     * @return 
     */
    private CharacterManagerControl createCharacter()
    {
        Spatial character = _app.getAssetManager().loadModel("Models/Player.j3o");

        CollisionShape shape = new CapsuleCollisionShape(0.5f, 1f, 1);
        CharacterControl charControl = new CharacterControl(shape, 0.1f);
        charControl.setGravity(new Vector3f(0f, -1f, 0f));

        // Add controls to spatials
        character.addControl(charControl);

        CharacterManagerControl manager = new CharacterManagerControl();
        character.addControl(manager);

        return manager;
    }
    
    /**
     * Removes a state that has an id equal to this client id.
     * @param playerStates Received list of player states from the server.
     */
    private void removeLocalPlayerState(List<PlayerStateInfo> playerStates)
    {
        int localId = _network.getClientInstance().getId();
        for (int i = 0; i < playerStates.size(); i++)
        {
            if (playerStates.get(i).getClientId() == localId)
            {
                playerStates.remove(i);
                break;
            }
        }
    }
    
    /**
     * Method updates local character spatials based on received list of player states.
     * @param playerStates
     * @return 
     */
    private int updateCharacters(List<PlayerStateInfo> playerStates)
    {
        int processed = 0;
        for (PlayerStateInfo state : playerStates)
        {
            for (CharacterManagerControl character : _characters)
            {
                if (character.getId() == state.getClientId())
                {
                    character.setPosition(state.getPlayerState().getLocalTranslation());
                    character.setRotation(state.getPlayerState().getLocalRotation());
                    processed++;
                }
            }
        }
        return processed;
    }
    
    /**
     * Disables all characters currently simulated (detaches from node and physics space).
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
     * Enables all characters currently to be simulated (attaches to root node and physics space).
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
     * Resizes simulated characters list.
     * @param size 
     */
    private void setCharacterListSize(int size)
    {
        while (_characters.size() < size)
        {
            _characters.add(createCharacter());
        }

        while (_characters.size() > size)
        {
            _characters.remove(_characters.size() - 1);
        }
    }
    
    private void initListener()
    {
        _listener = new ClientListener()
        {
            @Override
            public void messageReceived(Client source, Message message)
            {
                if(message instanceof PlayerStateInfosMessage)
                {
                    PlayerStateInfosMessage xD = (PlayerStateInfosMessage)message;
                    //System.out.println(xD.getContent());
                    //ArrayList<PlayerStateInfo> infos = (ArrayList<PlayerStateInfo>)xD.deserialize();
                    //System.out.println(infos.get(0).toString());
//                    if(infos != null)
//                    {
//                        setClientsInfo(infos);
//                    }
                }
            }
            
        };
    }
    
}
