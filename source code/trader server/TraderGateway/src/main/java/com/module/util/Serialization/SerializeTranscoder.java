package com.module.util.Serialization;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by siqi.lou on 21/05/2017.
 */
public abstract class SerializeTranscoder {

    public abstract byte[] serialize(Object value);

    public abstract Object deserialize(byte[] in) throws IOException;

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
