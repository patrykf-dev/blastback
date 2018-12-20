/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.messages.data;

import com.jme3.math.Vector3f;

/**
 *
 * @author Artur
 */
public class ClientCoordinates {

    //coordinates
    private final float _x, _y, _z; //translation coordinates
    private final float _rX, _rY, _rZ; //rotation coordinates

    public ClientCoordinates(Vector3f localTranslation, Vector3f localRotation) {
        _x = localTranslation.x;
        _rX = localRotation.x;

        _y = localTranslation.y;
        _rY = localRotation.y;

        _z = localTranslation.z;
        _rZ = localRotation.z;

    }

    public String getCoordinates() {
        return "Coordinates: [" + _x + "," + _y + "," + _z + "]";
    }

    public Vector3f geTranslationVector() {
        return new Vector3f(_x, _y, _z);
    }

    public Vector3f getRotationVector() {
        return new Vector3f(_rX, _rY, _rZ);
    }

}
