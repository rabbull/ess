package models;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public abstract class Model implements java.io.Serializable, common.Serializable {
    protected Integer id;

    public Model() {
    }

    public Model(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public byte[] serialize() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }
}
