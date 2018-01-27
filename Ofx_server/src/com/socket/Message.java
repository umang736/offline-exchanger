package com.socket;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String type, sender, recipient;
    public String[] content;
    
    public Message(String type, String sender, String[] content, String recipient){
        this.type = type; this.sender = sender; this.content = Arrays.copyOf(content, content.length); this.recipient = recipient;
    }
    
}
