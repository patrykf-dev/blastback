/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.networking.data;

/**
 *
 * @author Eryk
 */
public class MatchStats
{
    private int _score;
    private int _deaths;
    
    public MatchStats()
    {
        resetStats();
    }
    
    public final void resetStats()
    {
        _score = 0;
        _deaths = 0;
    }
    
    public void addScore(int score)
    {
        _score += score;
    }
    
    public void addDeath()
    {
        _deaths++;
    }

    public int getScore()
    {
        return _score;
    }

    public int getDeaths()
    {
        return _deaths;
    }
}
