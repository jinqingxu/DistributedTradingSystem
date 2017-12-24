package websocketbot.messages;

import com.module.entity.DisRecord;

import java.util.List;

/**
 * Created by jinqingxu on 2017/6/3.
 */
public class SellBookMessage extends Message {
    private String sellBook;
    private String productCode;
    private String brokerid;

    public void setSellBook(String sellBook) {
        this.sellBook = sellBook;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

    public String getBrokerid() {
        return brokerid;
    }

    public String getSellBook() {
        return sellBook;
    }

    public SellBookMessage(String sellbook, String brokerid, String productCode){
        this.sellBook=sellbook;
        this.brokerid=brokerid;
        this.productCode=productCode;
    }

}
