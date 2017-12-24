package websocketbot.messages;

import com.module.entity.DisRecord;

import java.util.List;

/**
 * Created by jinqingxu on 2017/6/3.
 */
public class BuyBookMessage extends Message {
    private String buyBook;
    private String productCode;
    private String brokerid;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setBuyBook(String buyBook) {
        this.buyBook = buyBook;
    }

    public String getBuyBook() {
        return buyBook;
    }
    public BuyBookMessage(String buybook,String brokerid,String productCode,String price){
        this.buyBook=buybook;
        this.brokerid=brokerid;
        this.productCode=productCode;
        this.price=price;
    }
}
