package websocketbot.messages;

import com.module.entity.DisRecord;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jinqingxu on 2017/6/3.
 */
public class BuyBookMessage extends Message {
    private String buyBook;
    private String productCode;
    private String price;
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
    public BuyBookMessage(String buybook,String productCode,String price,String time){
        this.buyBook=buybook;
        this.productCode=productCode;
        this.price=price;
        this.time=time;
    }
}
