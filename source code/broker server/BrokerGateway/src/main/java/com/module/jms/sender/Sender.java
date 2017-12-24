package com.module.jms.sender;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.bean.Transaction;
import com.module.entity.OrderBook;

import javax.jms.JMSException;
import java.sql.Timestamp;

/**
 * Created by siqi.lou on 23/05/2017.
 */
public interface Sender {
    /* send Transaction to both firm
     *  MapMessage type:transaction
     *  transaction: transaction to json
     */
    void sendTransaction(Transaction transaction);

    /* send Order id to firm to confirm the order is under processing
     * MapMessage type:orderId
     * OrderIdinFirm: id in firm
     * OrderIdinBroker: id in broker
     */
    void sendOrder(Order order);

    /* send the status of the order being canceled to the trader
     * status:unfinished/canceled
     */
    void sendCancelOrder(Order order);


    /* send OrderBook to all firm
     * MapMessage type:OrderBook
     * product: product code
     * askBook:
     * bidBook:
     * */
    void sendOrderBook(OrderBook orderBook);
    void sendOrderBook(OrderBook orderBook, Double price, Timestamp time);

    void sendMessage(String status,String m);
}
