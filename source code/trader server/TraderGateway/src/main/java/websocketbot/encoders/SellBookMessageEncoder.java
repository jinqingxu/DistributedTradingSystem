package websocketbot.encoders;

import websocketbot.messages.BuyBookMessage;
import websocketbot.messages.SellBookMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

/**
 * Created by jinqingxu on 2017/6/5.
 */
public class SellBookMessageEncoder implements Encoder.Text<SellBookMessage>{
    public void init(EndpointConfig ec) { }


    public void destroy() { }


    public String encode(SellBookMessage sellBookMessage) throws EncodeException {
        StringWriter swriter = new StringWriter();
        JsonGenerator jsonGen = Json.createGenerator(swriter);
        jsonGen.writeStartObject().write("type", "sellbook").write("sellbook", sellBookMessage.getSellBook()).write("brokerid",sellBookMessage.getBrokerid()).write("productCode",sellBookMessage.getProductCode()).writeEnd();
        jsonGen.flush();
        return swriter.toString();
    }
}
