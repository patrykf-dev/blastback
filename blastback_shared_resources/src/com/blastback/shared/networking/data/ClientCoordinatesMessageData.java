/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.networking.data;

/**
 *
 * @author Artur
 */
public class ClientCoordinatesMessageData {
    //coordinates
    private final float x;
    private final float y;
    private final float z;
    
    public ClientCoordinatesMessageData(float _x, float _y, float _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }
    
    public String getCoordinates()
    {
        return "Coordinates: [" + x + "," + y +"," + z + "]";
    }
}
