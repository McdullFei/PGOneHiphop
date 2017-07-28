package com.atlas.framework.common.redis;

import org.msgpack.MessagePack;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by renfei on 17/6/12.
 */
public class MessagePackSerializer<T> implements RedisSerializer<T> {

    private MessagePack msgPack;

    public void setMsgPack(MessagePack msgPack) {
        this.msgPack = msgPack;
    }

    public MessagePackSerializer(MessagePack msgPack){
        this.msgPack = msgPack;

    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if(t == null){

            return new byte[0];
        }
        try {
            return msgPack.write(t);
        } catch (Exception e) {
            throw new SerializationException( "Cannot serialize" , e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            T t = (T)msgPack.read(bytes);
            return t;
        } catch (Exception e) {
            throw new SerializationException( "Cannot deserialize" , e);
        }
    }
}
