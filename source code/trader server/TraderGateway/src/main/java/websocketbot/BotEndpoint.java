/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package websocketbot;

import com.module.bean.Broker;
import com.module.entity.OrderBook;
import com.module.service.BrokerService;
import websocketbot.decoders.MessageDecoder;
import websocketbot.encoders.*;
import websocketbot.messages.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.annotation.Resource;
//import javax.enterprise.concurrent.ManagedExecutorService;
//import javax.inject.Inject;

/* Websocket endpoint */
@ServerEndpoint(
        value = "/websocketbot",
        decoders = { MessageDecoder.class }, 
        encoders = {
                   BuyBookMessageEncoder.class,
                   SellBookMessageEncoder.class}
        )
/* There is a BotEndpoint instance per connetion */
public class BotEndpoint {
    private static final Logger logger = Logger.getLogger("BotEndpoint");
    private static final List<Session> setsession=new ArrayList<Session>();


    @OnOpen
    public void openConnection(Session session) {
        logger.log(Level.INFO, "Connection opened.");
        setsession.add(session);
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        logger.log(Level.INFO, "Connection error ({0})", t.toString());
    }
    public void sendAll(OrderBook orderBook,Double price){
        Integer brokerid=orderBook.getBrokerId();
        String productCode=orderBook.getProductCode();
        BuyBookMessage buyBookMessage=new BuyBookMessage(orderBook.buybookToString(),brokerid.toString(),productCode,price.toString());
        helpsendAll(buyBookMessage);
        SellBookMessage sellBookMessage=new SellBookMessage(orderBook.sellbookToString(),brokerid.toString(),productCode);
        helpsendAll(sellBookMessage);

    }
    @OnClose
    public void closedConnection(Session session) {
        setsession.remove(session);
        logger.log(Level.INFO, "Connection closed.");
    }
    /* Forward a message to all connected clients
     * The endpoint figures what encoder to use based on the message type */
    public synchronized void helpsendAll( Object msg) {
        for(int i=0;i<setsession.size();++i) {
            try {
                for (Session s : setsession.get(i).getOpenSessions()) {
                    if (s.isOpen()) {
                        s.getBasicRemote().sendObject(msg);
                        logger.log(Level.INFO, "Sent: {0}", msg.toString());
                    }
                }
            } catch (IOException e) {
                logger.log(Level.INFO, e.toString());
            } catch (EncodeException e) {
                logger.log(Level.INFO, e.toString());
            }
        }
    }
    

}
