package com.module.util.Serialization;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by siqi.lou on 2017/6/3.
 */
public class QueueTran <M extends Serializable> {
    public List<M> toList(Queue<M> ms){
        List<M> list = new ArrayList<M>();
        for(M m:ms){
            list.add(m);
        }
        return list;
    }

    public Queue<M> toQueue(List<M> list){
        Queue<M> queue = new ArrayDeque<M>();
        for(M m:list){
            queue.add(m);
        }
        return queue;
    }
}
