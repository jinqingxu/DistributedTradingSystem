package websocketbot.encoders;

import websocketbot.messages.BuyBookMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

/**
 * Created by jinqingxu on 2017/6/3.
 */
public class BuyBookMessageEncoder implements Encoder.Text<BuyBookMessage>{
    public void init(EndpointConfig ec) { }


    public void destroy() { }


    public String encode(BuyBookMessage buyBookMessage) throws EncodeException {
        StringWriter swriter = new StringWriter();
        JsonGenerator jsonGen = Json.createGenerator(swriter);
        jsonGen.writeStartObject().write("type", "buybook").write("buybook", buyBookMessage.getBuyBook()).write("productCode",buyBookMessage.getProductCode()).write("price",buyBookMessage.getPrice()).write("time",buyBookMessage.getTime()).writeEnd();
        jsonGen.flush();
        return swriter.toString();
    }
}
