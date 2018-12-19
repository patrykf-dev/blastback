/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blastback.gsonclassClient;

/**
 *
 * @author Artur
 */
public class Person {
    private final String name;
    private final int age;
    private final boolean flag;
    
    public Person(String _name, int _age, boolean _flag)
    {
        name = _name;
        age = _age;
        flag = _flag;
    }
    
    public String getinfo()
    {
        return "Object class Person: name: " + name + " age: " + age + " flag: " + flag;
    }
}
