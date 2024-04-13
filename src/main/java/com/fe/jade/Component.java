package com.fe.jade;

import com.fe.jade.gObject;

public abstract class Component {

    public gObject gameObj = null;
    public  void start() {

    }
    public abstract void update(float dt);

}
