package com.socket;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import ofx.Home;
import static ofx.NewItems.newitemlist;
import static ofx.Notifications.notlist;
import static ofx.Notifications.notoldlist;
import static ofx.Ofx.editframe;
import static ofx.Ofx.tb;
import static ofx.SearchItemn.itemdetails;
import static ofx.SearchItemn.searchlist;
import static ofx.Searchhistory.searchhist;
import static ofx.Update.ownitems;
import static ofx.Update.updatelist;
import ofx.loginn;
import static ofx.loginn.check;
import static ofx.loginn.forgotpanel;
import static ofx.loginn.username;
public class SocketClient implements Runnable{
    
    public int port;
    public String serverAddr;
    public Socket socket;
    public Home ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    public ofx.Chat ch;
    public ofx.loginn logclass;
    public SocketClient(Home frame,ofx.Chat ch,ofx.loginn logclass) throws IOException{
        ui = frame; this.serverAddr = ui.serverAddr; this.port = ui.port;
        socket = new Socket(InetAddress.getByName(serverAddr), port);
          this.ch=ch;
          this.logclass=logclass;
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        
    }

    @Override
    public void run() {
        boolean keepRunning = true;
        while(keepRunning){
            try {
                reply msg = (reply) In.readObject();
              System.out.print("Incoming : type="+msg.type+" ,sender="+msg.sender+" ,content= ");
            for(int i=0;i<msg.content.length;i++)
            System.out.print(msg.content[i]+" ,");
            System.out.println(" recipient="+msg.recipient);
                
                
                if(msg.type.equals("message")){
                    if(msg.recipient.equals(username)){
                        
                        ch.jTextArea1.append("["+msg.sender +" > Me] : " + msg.content[0] + "\n");
                    }
                    else{
                        ch.jTextArea1.append("["+ msg.sender +" > "+ msg.recipient +"] : " + msg.content[0] + "\n");
                    }
                                            
                }
               else if(msg.type.equals("login")){
                    if(msg.content[0].equals("TRUE")){  
                       ch.jButton1.setEnabled(true); ch.jButton2.setEnabled(true);
                       username=msg.recipient;
                       JOptionPane.showMessageDialog(tb, "Login Successful");
                     logclass.logbut.setEnabled(false);
                     logclass.user.setEditable(false);
                     logclass.pass.setEditable(false);
                       String[] content=new String[1];
                       content[0]="";
                      send(new Message("givenotific", username,content, "SERVER"));
                     System.out.println("asked for notification to server");
                     send(new Message("givenewnot", username,content, "SERVER"));
                      send(new Message("givesearchhist", username,content, "SERVER"));
                    }
                    else{
                        JOptionPane.showMessageDialog(tb,msg.content[0]);
                    }
             
               }
                    
                else if(msg.type.equals("test")){
                    ui.jButton1.setEnabled(false);
                    ui.jTextField1.setEditable(false); ui.jTextField2.setEditable(false);
                  
                        JOptionPane.showMessageDialog(tb,"connected to server");
                }
                
                else if(msg.type.equals("newuser")){
                    if(!msg.content[0].equals(username)){
                        boolean exists = false;
                        for(int i = 0; i < ch.model.getSize(); i++){
                            if(ch.model.getElementAt(i).equals(msg.content[0])){
                                exists = true; break;
                            }
                        }
                        if(!exists){ch.model.addElement(msg.content[0]); }
                    }
                }
                else if(msg.type.equals("updatepass")){
                    if(msg.content[0].equals("TRUE")){
                        JOptionPane.showMessageDialog(tb,"Password updated successfully");
                        forgotpanel.setVisible(false);
                       check=false;
                    }
                    else{
                        JOptionPane.showMessageDialog(tb,msg.content[0]);
                    }
                }
                
                else if(msg.type.equals("searchown"))
                {
                     if(!msg.content[0].equals("NO")){
                         for(String s:msg.content)
                        { 
                            ownitems.add(s);
                            int i=0;String ans="";
                            while(true)
                            { 
                           if('<'==s.charAt(i)&&'b'==s.charAt(i+1)&&'r'==s.charAt(i+2)&&'>'==s.charAt(i+3))
                               break;
                            else
                            {ans= ans+s.charAt(i);i++;}
                            
                            }
                            updatelist.addElement(ans);
                        } 
                     }
                      else{
                        JOptionPane.showMessageDialog(tb,"you don't have any items");
                    }
                }
                else if(msg.type.equals("delete"))
                {
                    if(msg.content[0].equals("TRUE"))
                    {
                        System.out.println("initial arraylist size="+ownitems.size());
                        int index=updatelist.indexOf(msg.content[1]);
                        ownitems.remove(index);
                        System.out.println("final arraylist size="+ownitems.size());
                        updatelist.remove(index);
                     JOptionPane.showMessageDialog(tb,"Item deleted");   
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(tb,"unable to delete item");
                    }
                }
                 else if(msg.type.equals("updateitem")){
                    if(!msg.content[0].equals("NO"))
                    {
                         System.out.println("initial arraylist size="+ownitems.size());
                        int index=Integer.parseInt(msg.content[0]);//msg.content[0] tells index position
                        String res=msg.content[1];
                        for(int i=2;i<msg.content.length;i++)//length here is 10
                        {
                            res=res+"<br>"+msg.content[i];
                        }
                        ownitems.remove(index);
                        ownitems.add(index,res);
                        System.out.println("final arraylist size="+ownitems.size());
                        updatelist.remove(index);
                        updatelist.add(index, msg.content[1]);
                        editframe.setVisible(false);
                        JOptionPane.showMessageDialog(tb,"Item updated");
                    }
                  else
                    {
                        JOptionPane.showMessageDialog(tb,"unable to update details");
                    }
                }
                 else if(msg.type.equals("givesearchhist")){
                    System.out.println("reply came for search history ");  
                     if(!msg.content[0].equals("NO"))
                     {
                        for(String s:msg.content)
                        {    
                               String[] ans=s.split("<br>");                              
                                  String res="<html>";
                                  //res=res+c+")<br/>";
                            res=res+"Itemname:&nbsp;"+ans[0];
                            if(!ans[1].equals(""))res=res+"<br/>"+"Description:&nbsp;"+ans[1];
                             res=res+"<br/>"+"Price:&nbsp;"+ans[2];
                             res=res+"<br/>"+"Seller:&nbsp;"+ans[3]+"("+ans[4]+")";
                             res=res+"&nbsp;&nbsp;"+"Room:&nbsp;"+ans[6];
                             switch(ans[5])
                             {
                                 case "0":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tandan";
                                     break;
                                     case "1":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Malviya";
                                     break;
                                         case "2":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Raman";
                                     break;
                                             case "3":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Patel";
                                     break;
                                                 case "4":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"KNGH";
                                     break;
                                                           case "5":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tagore";
                                     break;
                                                                     case "6":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tilak";
                                     break;
                                                                             case "7":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"SVBH";
                                     break;
                                                                         
                             }
                           
                               res=res+"<br/>"+"Phone:&nbsp;"+ans[7];
                                 res=res+
                                         "&nbsp;&nbsp;&nbsp;&nbsp;"+"Email:&nbsp;"+ans[8];
                                  res=res+"</html>";
                        searchhist.addElement(res);
                        }         
                         
                     }
                 }
                 
                else if(msg.type.equals("searchitem")){
                    System.out.println("reply came for search item ");
                    if(!msg.content[0].equals("NO")){
                        
                        for(String s:msg.content)
                        {    
                            itemdetails.add(s);
                               String[] ans=s.split("<br>");                              
                                  String res="<html>";
                                  //res=res+c+")<br/>";
                            res=res+"Itemname:&nbsp;"+ans[0];
                            if(!ans[1].equals(""))res=res+"<br/>"+"Description:&nbsp;"+ans[1];
                             res=res+"<br/>"+"Price:&nbsp;"+ans[2];
                             res=res+"<br/>"+"Seller:&nbsp;"+ans[3]+"("+ans[4]+")";
                             res=res+"&nbsp;&nbsp;"+"Room:&nbsp;"+ans[6];
                             switch(ans[5])
                             {
                                 case "0":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tandan";
                                     break;
                                     case "1":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Malviya";
                                     break;
                                         case "2":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Raman";
                                     break;
                                             case "3":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Patel";
                                     break;
                                                 case "4":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"KNGH";
                                     break;
                                                           case "5":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tagore";
                                     break;
                                                                     case "6":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tilak";
                                     break;
                                                                             case "7":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"SVBH";
                                     break;
                                                                         
                             }
                           
                               res=res+"<br/>"+"Phone:&nbsp;"+ans[7];
                                 res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Email:&nbsp;"+ans[8];
                                  res=res+"</html>";
                        searchlist.addElement(res);
                        }                       
                    }
                    else{
                        if(msg.content[1].equals("1"))
                        JOptionPane.showMessageDialog(tb,"Your search didn't match any items");
                        else//conttent[1].equals("2")
                        JOptionPane.showMessageDialog(tb,"select some search tags");
                    }
                }
                else if(msg.type.equals("additem")){
                    if(msg.content[0].equals("TRUE")){
                        JOptionPane.showMessageDialog(tb,"Item Added");
                    }
                    else{
                        JOptionPane.showMessageDialog(tb,msg.content[0]);
                    }
                }
                else if(msg.type.equals("delhis")){
                    if(msg.content[0].equals("TRUE"))
                    {notlist.clear();
                    notoldlist.clear();}
                }
                else if(msg.type.equals("buy")){
                    if(msg.content[0].equals("TRUE")){
                        JOptionPane.showMessageDialog(tb,"Notification has been sent to seller");
                    }
                    else{
                        JOptionPane.showMessageDialog(tb,"seller will get notification when he gets online");
                    }
                }
                else if(msg.type.equals("givenewnot"))
                {
                    System.out.println("entered newnot");
                    
                    if(!msg.content[0].equals("NO"))
                    {
                      int count=0;
                         for(String s:msg.content)
                        {
                            String[] notify=s.split("<br>");
                            System.out.println("entered loop with size="+notify.length);
                             String category="";
                             int c=0;
                            if(notify[9].equals("1"))
                            { category+="books";c++;}
                             if(notify[10].equals("1"))
                             {if(c>0) category+=",gadgets"; else category+="gadgets";c++; }
                              if(notify[11].equals("1"))
                              {if(c>0) category+=",formals"; else category+="formals";c++; }
                                if(notify[12].equals("1"))
                              {if(c>0) category+=",lab_practicals"; else category+="lab_practicals";c++; }
                                  if(notify[13].equals("1"))
                              {if(c>0) category+=",Hostels"; else category+="Hostels"; c++;}
                                    if(notify[14].equals("1"))
                              {if(c>0) category+=",Miscellaneous"; else category+="Miscellaneous"; }
                            String res="<html>"+"Itemname:&nbsp;"+notify[0]+"&nbsp;&nbsp;&nbsp;&nbsp;Category:&nbsp;"+category;
                             res=res+"<br/>"+"Description:&nbsp;"+notify[1];
                             res=res+"<br/>"+"Price:&nbsp;"+notify[2];
                    res=res+"<br/>"+"Seller:&nbsp;"+notify[3]+"("+notify[4]+")";
                     res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Room:&nbsp;"+notify[6];
                      switch(notify[5])
                             {
                                 case "0":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tandan";
                                     break;
                                     case "1":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Malviya";
                                     break;
                                         case "2":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Raman";
                                     break;
                                             case "3":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Patel";
                                     break;
                                                 case "4":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"KNGH";
                                     break;
                                                           case "5":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tagore";
                                     break;
                                                                     case "6":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tilak";
                                     break;
                                                                             case "7":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"SVBH";
                                     break;
                                                                         
                             }
                      
                        res=res+"<br/>"+"Phone:&nbsp;"+notify[7];
                                 res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Email:&nbsp;"+notify[8];
                                  res=res+"</html>";
                                   newitemlist.add(0,res);count++;
                        }
                    }
                }
                else if(msg.type.equals("givenotific"))
                {
                    System.out.println("entered notific");
                    if(!msg.content[0].equals("NO"))
                    {
                      int count=0;
                         for(String s:msg.content)
                        {
                            String[] notify=s.split("<br>");
                            System.out.println("entered loop with size="+notify.length);
                            String res="<html>"+"Itemname:&nbsp;"+notify[0];
                    res=res+"<br/>"+"Buyer:&nbsp;"+notify[1]+"("+notify[2]+")";
                     res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Room:&nbsp;"+notify[4];
                      switch(notify[3])
                             {
                                 case "0":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tandan";
                                     break;
                                     case "1":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Malviya";
                                     break;
                                         case "2":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Raman";
                                     break;
                                             case "3":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Patel";
                                     break;
                                                 case "4":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"KNGH";
                                     break;
                                                           case "5":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tagore";
                                     break;
                                                                     case "6":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tilak";
                                     break;
                                                                             case "7":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"SVBH";
                                     break;
                                                                         
                             }
                      
                     res=res+"<br/>"+"Phone:&nbsp;"+notify[5];
                                 res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Email:&nbsp;"+notify[6];
                                  res=res+"</html>";
                                  if(notify[7].equals("0"))
                                  { notlist.add(0,res);count++;}
                                  else
                              notoldlist.add(0,res);
                        }
                         if(count>0)
                         JOptionPane.showMessageDialog(tb,"someone wants to buy something");
                         String[] content=new String[1];
                         content[0]="";
                          send(new Message("updatenoti", username, content, "SERVER"));
                    }
                }
                  else if(msg.type.equals("sell")){
                    String res="<html>"+"Itemname:&nbsp;"+msg.content[0];
                    res=res+"<br/>"+"Buyer:&nbsp;"+msg.sender+"("+msg.content[1]+")";
                     res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Room:&nbsp;"+msg.content[3];
                      switch(msg.content[2])
                             {
                                 case "0":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tandan";
                                     break;
                                     case "1":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Malviya";
                                     break;
                                         case "2":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Raman";
                                     break;
                                             case "3":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Patel";
                                     break;
                                                 case "4":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"KNGH";
                                     break;
                                                           case "5":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tagore";
                                     break;
                                                                     case "6":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"Tilak";
                                     break;
                                                                             case "7":
                                   res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Hostel:&nbsp;"+"SVBH";
                                     break;
                                                                         
                             }
                     res=res+"<br/>"+"Phone:&nbsp;"+msg.content[4];
                                 res=res+"&nbsp;&nbsp;&nbsp;&nbsp;"+"Email:&nbsp;"+msg.content[5];
                                  res=res+"</html>";
                      notlist.add(0,res);
                        JOptionPane.showMessageDialog(tb,"someone wants to buy something");
                       
                }
                
                else if(msg.type.equals("register")){
                    if(msg.content[0].equals("TRUE")){
                        JOptionPane.showMessageDialog(tb,"Registration Successful");
                    }
                    else{
                        JOptionPane.showMessageDialog(tb,msg.content[0]);
                    }
                }
                
                else if(msg.type.equals("signout")){
                    if(msg.content[0].equals(username)){
                       ch.jTextArea1.append("["+ msg.sender +" > Me] : Bye\n");
                      ui.jButton1.setEnabled(true);ch.jButton1.setEnabled(false); ch.jButton2.setEnabled(false); 
                       ui.jTextField1.setEditable(true); ui.jTextField2.setEditable(true);
                        
                        for(int i = 1; i < ch.model.size(); i++){
                            ch.model.removeElementAt(i);
                           
                        }
                        
                        ui.clientThread.stop();
                    }
                    else{
                       ch.model.removeElement(msg.content[0]);
                        ch.jTextArea1.append("["+ msg.sender +" > All] : "+ msg.content[0] +" has signed out\n");
                    }
                }
                else if(msg.type.equals("upload_req")){
                    
                    if(JOptionPane.showConfirmDialog(tb, ("Accept '"+msg.content[0]+"' from "+msg.sender+" ?")) == 0){
                        
                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(msg.content[0]));
                        int returnVal = jf.showSaveDialog(tb);
                       
                        String saveTo = jf.getSelectedFile().getPath();
                        if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
                            Download dwn = new Download(saveTo);
                            Thread t = new Thread(dwn);
                            t.start();
                            //send(new Message("upload_res", (""+InetAddress.getLocalHost().getHostAddress()), (""+dwn.port), msg.sender));
                             String[] content=new String[1];
                             content[0]=""+dwn.port;
                            send(new Message("upload_res", username, content, msg.sender));
                        }
                        else{
                            String[] content={"NO"};
                            send(new Message("upload_res", username, content, msg.sender));
                        }
                    }
                    else{
                        String[] content={"NO"};
                        send(new Message("upload_res", username, content, msg.sender));
                    }
                }
                else if(msg.type.equals("upload_res")){
                    if(!msg.content[0].equals("NO")){
                        int port  = Integer.parseInt(msg.content[0]);
                        String addr = msg.sender;
                        
                        ch.jButton2.setEnabled(false); ch.jButton3.setEnabled(false);
                        Upload upl = new Upload(addr, port, ch.file, ch);
                        Thread t = new Thread(upl);
                        t.start();
                    }
                    else{
                        ch.jTextArea1.append("[SERVER > Me] : "+msg.sender+" rejected file request\n");
                    }
                }
                else{
                    ch.jTextArea1.append("[SERVER > Me] : Unknown message type\n");
                }
            }
            catch(Exception ex) {
                keepRunning = false;
                System.out.println("error  aya");
                JOptionPane.showMessageDialog(tb,"Connection Failure");
                ui.jButton1.setEnabled(true); ui.jTextField1.setEditable(true); ui.jTextField2.setEditable(true);
                ch.jButton1.setEnabled(false); ch.jButton2.setEnabled(false); logclass.logbut.setEnabled(true);
                     logclass.user.setEditable(true);
                     logclass.pass.setEditable(true);
                
                for(int i = 1; i < ch.model.size(); i++){
                    ch.model.removeElementAt(i);
                }
                
                ui.clientThread.stop();
                
                System.out.println("Exception SocketClient run()");
                ex.printStackTrace();
            }
        }
    }
    
    public void send(Message msg){
        try {
            Out.writeObject(msg);
            Out.flush();
            System.out.print("Outgoing : type="+msg.type+" ,sender="+msg.sender+" ,content= ");
            for(int i=0;i<msg.content.length;i++)
            System.out.print(msg.content[i]+" ,");
            System.out.println(" recipient="+msg.recipient);
            
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
            JOptionPane.showMessageDialog(tb, "connection failure");
            
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
}
