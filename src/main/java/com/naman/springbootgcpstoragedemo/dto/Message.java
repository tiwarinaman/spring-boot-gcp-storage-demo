package com.naman.springbootgcpstoragedemo.dto;

public class Message {

    private byte[] contents;

    public Message(byte[] contents) {
        this.contents = contents;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
