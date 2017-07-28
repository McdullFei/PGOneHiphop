package com.atlas.framework.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 通过fastjson进行序列化反序列化
 *
 * Created by renfei on 17/6/21.
 */
public class JsonSerializer<T> implements Serializer<T> {

    /**
     * 序列化为json
     * @param t
     * @return
     */
    @Override
    public String serialize(T t) {
        SerializerFeature[] featureArr = { SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.IgnoreErrorGetter };
        String json = JSON.toJSONString(t, featureArr);
        return json;
    }

    /**
     * 反序列化为javabean
     * @param jsonObject
     * @return
     */
    @Override
    public T deserialize(String jsonObject, Class<T> c) {
        Feature[] fArr = {Feature.IgnoreNotMatch};
        return JSON.parseObject(jsonObject, c, fArr);
    }
}
