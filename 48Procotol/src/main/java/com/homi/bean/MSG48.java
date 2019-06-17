package com.homi.bean;

import java.util.Map;

public class MSG48 {
    private Byte sign;
    private Byte length;
    private Byte type;
    private Byte number;
    private Map<String,Object> data;
    private Byte code;

    public Byte getSign() {
        return sign;
    }

    public void setSign(Byte sign) {
        this.sign = sign;
    }

    public Byte getLength() {
        return length;
    }

    public void setLength(Byte length) {
        this.length = length;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getNumber() {
        return number;
    }

    public void setNumber(Byte number) {
        this.number = number;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MSG48{" +
                "sign=" + sign +
                ", length=" + length +
                ", type=" + type +
                ", number=" + number +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
