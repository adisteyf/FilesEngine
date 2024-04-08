package com.fe.components;

import com.fe.jade.Component;

public class SpriteRender extends Component {

    private boolean firstTime = true;
    @Override
    public void start() {
        System.out.println( "im start" );
    }
    @Override
    public void update(float dt) {
        if (firstTime) {
            System.out.println("update");
            firstTime = false;
        }
    }
}
