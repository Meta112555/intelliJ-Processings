package com.etagefuenf.mb;

/**
 * Created by Johannes on 4/10/14.
 */

public interface Communicatable {
    void receive(String msg);
    void broadcast(String msg);
    void send(String msg, Communicatable receiver);
}
