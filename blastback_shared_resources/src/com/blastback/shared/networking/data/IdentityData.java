/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.shared.networking.data;

/**
 *
 * @author Marcin
 */
public class IdentityData
{

    private int _id;
    private String _username;

    public IdentityData(String username)
    {
        _username = username;
        _id = -1;
    }

    public IdentityData(int id, String username)
    {
        _id = id;
        _username = username;
    }

    public void setId(int id)
    {
        _id = id;
    }

    public int getId()
    {
        return _id;
    }

    public String getUsername()
    {
        return _username;
    }

    public void setUsername(String username)
    {
        _username = username;
    }

}
