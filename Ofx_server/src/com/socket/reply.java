/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.socket;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author umang gupta
 */
public class reply implements Serializable {
     private static final long serialVersionUID = 1L;
    public String type,sender,recipient;
    public String[] content; 
    
    public reply(String type, String sender, String[] content, String recipient){
        this.type = type; this.sender = sender; this.content =Arrays.copyOf(content, content.length); this.recipient = recipient;
    }
    
    @Override
    public String toString(){
        return "{type='"+type+"', sender='"+sender+"', content='"+content+"', recipient='"+recipient+"'}";
    }
}
