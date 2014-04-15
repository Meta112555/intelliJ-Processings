package com.etagefuenf.mb;

import javax.swing.*;

/**
 * Created by Johannes on 4/10/14.
 */

public class Nachrichtensimulator2013 implements Communicatable{

    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void receive(String msg) {
        showMessage("FUCK WORLD and "+msg);
    }

    @Override
    public void broadcast(String msg) {
        System.out.println("broadcast: 2"+msg);
    }

    @Override
    public void send(String msg, Communicatable receiver) {
        receiver.receive(msg);
    }
}

