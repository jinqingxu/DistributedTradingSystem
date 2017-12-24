package com.module.util.Serialization;

/**
 * Created by siqi.lou on 21/05/2017.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListTranscoder<M extends Serializable> extends SerializeTranscoder {

    @SuppressWarnings("unchecked")
    public List<M> deserialize(byte[] in) throws IOException {
        List<M> list = new ArrayList();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                list = (List<M>)is.readObject();
                /*while (true) {
                    M m = (M)is.readObject();
                    if (m == null) {
                        break;
                    }
                    list.add(m);
                }*/
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            is.close();
            bis.close();
        }
        return  list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(Object value) {
        if (value == null)
            throw new NullPointerException("Can't serialize null");

        List<M> values = (List<M>) value;
        byte[] results = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;

        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            /*for (M m : values) {
                os.writeObject(m);
            }*/
            os.writeObject(values);
            results = bos.toByteArray();
            os.close();
            bos.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return results;
    }
}
