/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

/**
 *
 * @author Eryk
 */
public class MatchSettings
{

    private final float _gameLength;

    public MatchSettings(float gameLength)
    {
        _gameLength = gameLength;
    }

    public float getGameLength()
    {
        return _gameLength;
    }
}
