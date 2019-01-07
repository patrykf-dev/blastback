/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.appstates;

import com.blastback.GameClient;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;

/**
 *
 * @author Artur
 */
public class BackgroundAudioAppState extends BaseAppState
{

    private GameClient _app;
    private AudioNode Sound;
    
    @Override
    protected void initialize(Application app) 
    {
        _app = (GameClient) app;    
        initAudio();
    }

    @Override
    protected void cleanup(Application app) 
    {
        
    }

    @Override
    protected void onEnable() 
    {
        _app.getRootNode().attachChild(Sound);
        Sound.play();
    }

    @Override
    protected void onDisable() 
    {
        _app.getRootNode().detachChild(Sound);
    }
    
    private void initAudio() 
    {
    /* nature sound - keeps playing in a loop. */
    Sound = new AudioNode(_app.getAssetManager(), "Sound/Grzenio.wav", DataType.Stream);
    Sound.setLooping(true);
    Sound.setPositional(false);
    Sound.setVolume(1);
  }
    
}
