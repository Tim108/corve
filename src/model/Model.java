package model;

import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tim on 20/02/2017.
 */
public abstract class Model extends Observable{
    public Queue<Update> updates = new ConcurrentLinkedQueue<>();

    public void update(){
        //update, get the update object and add it to the queue
        this.setChanged();
        this.notifyObservers();
    }
}
