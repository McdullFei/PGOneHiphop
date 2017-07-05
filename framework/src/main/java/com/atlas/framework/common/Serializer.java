package com.atlas.framework.common;

/**
 * Created by renfei on 17/6/21.
 */
public interface Serializer<T> {
    String serialize(T t);
    T deserialize(String jsonObject, Class<T> c);
}
