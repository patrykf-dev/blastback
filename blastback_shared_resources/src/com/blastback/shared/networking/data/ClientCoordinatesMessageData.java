/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.networking.data;

import com.jme3.math.Vector3f;

/**
 *
 * @author Artur
 */
public class ClientCoordinatesMessageData {
    //coordinates
    private float x;
    private float y;
    private float z;
    
    public ClientCoordinatesMessageData(float _x, float _y, float _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }
    
    //get
    public float getx(){
        return x;
    }
    public float gety(){
        return y;
    }
    public float getz(){
        return z;
    }
    
    //set
    public void setx(float _x){
        x = _x;
    }
    public void sety(float _y){
        y = _y;
    }
    public void setz(float _z){
        z = _z;
    }
    
    public String getCoordinates()
    {
        return "Coordinates: [" + x + "," + y +"," + z + "]";
    }
    
    public Vector3f getVector()
    {
        return new Vector3f(x,y,z);
    }
}
