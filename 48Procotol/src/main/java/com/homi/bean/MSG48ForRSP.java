package com.homi.bean;


public class MSG48ForRSP extends MSG48{
    private int rspCode;

    public int getRspCode() {
        return rspCode;
    }

    public void setRspCode(int rspCode) {
        this.rspCode = rspCode;
    }
    @Override
    public String toString() {
        return "MSG48{" +
                "sign=" + getSign() +
                ", length=" + getLength() +
                ", type=" + getType() +
                ", number=" + getNumber() +
                ", code=" + getCode() +
                ", rspCode=" + getRspCode() +
                '}';
    }

}
