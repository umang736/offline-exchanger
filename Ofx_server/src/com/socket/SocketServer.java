package com.socket;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;

class ServerThread extends Thread { 
	
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;
    public server_frame ui;
    public ServerThread(SocketServer _server, Socket _socket){  
    	super();
        server = _server;
        socket = _socket;
        ID     = socket.getPort();
        ui = _server.ui;
        System.out.println("client create ho gaya at "+socket);
    }
    
    public void send(reply msg){
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
            System.out.println("sent message");
        } 
        catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }
    
    public int getID(){  
	    return ID;
    }
   
    @SuppressWarnings("deprecation")
	public void run(){  
    	ui.jTextArea1.append("\nServer Thread " + ID + " running.");
         
         Message msg=null;
        while (true){  
             
    	    try{  
                msg = (Message) streamIn.readObject();
    	    	server.handle(ID, msg);
            }
            catch(Exception ioe){  
            	System.out.println(ID + " ERROR reading: " + ioe.getMessage());
         server.remove(ID);
                
                stop();
            }
        }
    }
    
    public void open() throws IOException {  
        if(socket.isConnected())
        {
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamIn = new ObjectInputStream(socket.getInputStream());
        }
        else
        {
            System.out.println("connection lost to client");
           server.remove(ID);  
        }
        }
    
    public void close() throws IOException {  
    	if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
        if (streamOut != null) streamOut.close();
    }
    
    
}





public class SocketServer implements Runnable {
    
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread       thread = null;
    public int clientCount = 0, port;
    public server_frame ui;
    public Database db;
    
    public SocketServer(server_frame frame) throws IOException, SQLException, ClassNotFoundException{
       
        clients = new ServerThread[500000];
        ui = frame;
       db=new Database();
	try{  
	    server = new ServerSocket(0);
            port = server.getLocalPort();
             frame.jButton1.setEnabled(false);
	     JOptionPane.showMessageDialog(frame, "Server started. IP : " + InetAddress.getLocalHost() + "\n Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
            JOptionPane.showMessageDialog(frame,"no ports empty");
              this.stop(); 
	}
    }
    
    public SocketServer(server_frame frame, int Port) throws SQLException, ClassNotFoundException{
       
        clients = new ServerThread[500000];
        ui = frame;
        port = Port;
     db=new Database();
	try{  
	    server = new ServerSocket(port);
            frame.jButton1.setEnabled(false);
	   JOptionPane.showMessageDialog(frame, "Server started. IP : " + InetAddress.getLocalHost() + "\n Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
             JOptionPane.showMessageDialog(frame,"Can not bind to port " + port + ": " + ioe.getMessage()+"\n searching for empty ports"); 
	}
    }
	
    public void run(){  
       ui.jTextArea1.append("\nWaiting for clients ..."); 

	while (thread != null){  
            try{  
	        addThread(server.accept()); 
	    }
	    catch(Exception ioe){ 
                ui.jTextArea1.append("\nServer accept error: \n");
              
	    }
        }
    }
	
    public void start(){  
    	if (thread == null){  
            thread = new Thread(this); 
	    thread.start();
	}
    }
    
    @SuppressWarnings("deprecation")
    public void stop(){  
        if (thread != null){  
            thread.stop();
	    thread = null;
	}
    }
    
    
    private void addThread(Socket socket){  
	if (clientCount < clients.length){  
            ui.jTextArea1.append("\nClient accepted: " + socket);
	    clients[clientCount] = new ServerThread(this, socket);
	    try{  
	      	clients[clientCount].open(); 
	        clients[clientCount].start();  
	        clientCount++; 
	    }
	    catch(IOException ioe){  
	      	ui.jTextArea1.append("\nError opening thread: " + ioe); 
	    } 
             System.out.println("try/catch par kar gaya");
	}
	else{
            ui.jTextArea1.append("\nClient refused: maximum " + clients.length + " reached.");
	}
    }
    
     private int findClient(int ID){  
    	for (int i = 0; i < clientCount; i++){
        	if (clients[i].getID() == ID){
                    return i;
                }
	}
	return -1;
    }
	
    public synchronized void handle(int ID, Message msg) throws SQLException{
        System.out.println("got message for user");
        if (msg.content[0].equals(".bye")){
            if(!msg.sender.equals(""))
            {
            String[] content=new String[1];
            content[0]=msg.sender;
            Announce("signout", "SERVER",content );
            }
            remove(ID); 
	}
	else{
            if(msg.type.equals("login")){
                String[] content=new String[1];;
                if(findUserThread(msg.sender) == null){
                     String result;
                    if((result=db.checkLogin(msg.sender, msg.content[0])).equals("TRUE")){
                        clients[findClient(ID)].username = msg.sender;
                        
                        content[0]="TRUE";
                        clients[findClient(ID)].send(new reply("login", "SERVER", content, msg.sender));
                        content[0]=msg.sender;
                        Announce("newuser", "SERVER", content);
                        SendUserList(msg.sender);
                    }
                    else{
                        
                        content[0]=result;
                        clients[findClient(ID)].send(new reply("login", "SERVER", content, msg.sender));
                    } 
                }
                else{
                    content[0]="you are already logged in from other device";
                    clients[findClient(ID)].send(new reply("login", "SERVER", content, msg.sender));
                }
            }
            else if(msg.type.equals("message")){
                String[] content=new String[1];
                if(msg.recipient.equals("All")){
                    content[0]=msg.content[0];
                    Announce("message", msg.sender,content);
                }
                else{
                    
                    content[0]=msg.content[0];
                    findUserThread(msg.recipient).send(new reply(msg.type, msg.sender,content , msg.recipient));//recipient ko bheja
                    clients[findClient(ID)].send(new reply(msg.type, msg.sender, content, msg.recipient));//sender ko bheja
                }
            }
            else if(msg.type.equals("test")){
                String[] content={"OK"};
                    
                 clients[findClient(ID)].send(new reply("test", "SERVER", content, msg.sender));
            }
            else if(msg.type.equals("register")){
                String[] content=new String[1];
                if(!db.userExists(msg.sender)){
                     String result;
             
                    if((result=db.addUser(msg.sender, msg.content)).equals("TRUE")){
                        content[0]="TRUE";
                        clients[findClient(ID)].send(new reply("register", "SERVER", content, msg.sender));
                    }
                    else{
                        content[0]=result;
                        clients[findClient(ID)].send(new reply("register", "SERVER", content, msg.sender));
                    }
                }
                else{
                    content[0]= "Username already exists";
                    clients[findClient(ID)].send(new reply("register", "SERVER",content, msg.sender));
                }
            }
            
            else if(msg.type.equals("updatepass")){
                String res;    String[] content=new String[1];
                if((res=db.updatepassword(msg.sender,msg.content)).equals("TRUE")){
                    content[0]="TRUE";
                        clients[findClient(ID)].send(new reply("updatepass", "SERVER", content, msg.sender));                       
                    }
                    else{
                    content[0]=res;
                       clients[findClient(ID)].send(new reply("updatepass", "SERVER", content, msg.sender));
                    }
                }
                 else if(msg.type.equals("searchitem")){
                      String[] res=db.searchitem(msg.sender,msg.content);    
  
                       clients[findClient(ID)].send(new reply("searchitem", "SERVER", res, msg.sender));                       
                } 
                  else if(msg.type.equals("givesearchhist")){
                      System.out.println("search history request");
                      String[] res=db.searchhist(msg.sender);    
  
                       clients[findClient(ID)].send(new reply("givesearchhist", "SERVER", res, msg.sender));                       
                } 
                 else if(msg.type.equals("updateitem")){
                     String[] content=db.updateitem(msg.sender,msg.content);
                      clients[findClient(ID)].send(new reply("updateitem", "SERVER", content, msg.sender));  
                 }
                 else if(msg.type.equals("searchown")){
                      String[] res=db.getuseritems(msg.sender,msg.content);    
  
                       clients[findClient(ID)].send(new reply("searchown", "SERVER", res, msg.sender));                       
                } 
                  else if(msg.type.equals("delete")){
                      String[] res=db.deleteitem(msg.sender,msg.content[0]);                   
                       clients[findClient(ID)].send(new reply("delete", "SERVER", res, msg.sender));                       
                } 
             else if(msg.type.equals("additem")){
                 String res;String[] content=new String[1];
                    if((res=db.additem(msg.sender,msg.content)).equals("TRUE")){
                        content[0]="TRUE";
                        System.out.println("got the result for additem query returning to client");
                        clients[findClient(ID)].send(new reply("additem", "SERVER", content, msg.sender));
                        System.out.println("sending to client.send");
                        String[] newitemdetails=db.sendnewitemdet(msg.sender,msg.content);
                      Announce("givenewnot", "SERVER", newitemdetails);
                    }
                    else{
                        content[0]=res;
                        clients[findClient(ID)].send(new reply("additem", "SERVER",content, msg.sender));
                    }
                   
                }
             
              else if(msg.type.equals("buy")){
                  ServerThread clint;
                 
                  if((clint=findUserThread(msg.recipient)) != null)
                  { 
                       String[] content=new String[6];
                      content[0]=msg.content[0];
                          String[] temp=db.getdetails(msg.sender);
                        db.sendnotifytodata(temp,msg.sender,msg.content[0],msg.recipient,"1");
                          for(int i=0;i<5;i++)
                              content[i+1]=temp[i];
                       clint.send(new reply("sell", msg.sender,content , msg.recipient));
                       String[] res=new String[1];
                       res[0]="TRUE";
                       clients[findClient(ID)].send(new reply("buy", "SERVER" , res, msg.recipient));  
                  }
                    else{
                        //store in db
                       String[] temp=db.getdetails(msg.sender);
                      db.sendnotifytodata(temp,msg.sender,msg.content[0],msg.recipient,"0");
                       String[] res=new String[1];
                       res[0]="offline";
                       clients[findClient(ID)].send(new reply("buy", "SERVER" , res, msg.recipient));
                    }
                }
              else if(msg.type.equals("givenotific"))
              {
                  System.out.println("notification aaya");
                  String[] content=db.getnotify(msg.sender);
                  clients[findClient(ID)].send(new reply("givenotific", "SERVER" , content, msg.sender));
              }
               else if(msg.type.equals("givenewnot"))
              {
                  System.out.println("new notification aaya");
                  String[] content=db.getnewnotify();
                  clients[findClient(ID)].send(new reply("givenewnot", "SERVER" , content, msg.sender));
              }
              else if(msg.type.equals("delhis"))
              {
                  
                 boolean res=db.deletehistory(msg.sender);
                    System.out.println("history deleted");
                    String[] content=new String[1];
                    content[0]=((res==true)?"TRUE":"NO");
                    clients[findClient(ID)].send(new reply("delhis", "SERVER" , content, msg.sender));
              }
               else if(msg.type.equals("updatenoti"))
              {
                  db.updatenotifylist(msg.sender);
                   System.out.println("notification list update ki ");
              }
            else if(msg.type.equals("upload_req")){
                String[] content=new String[1];       
                if(msg.recipient.equals("All")){
                     content[0]="Uploading to 'All' forbidden";
                    clients[findClient(ID)].send(new reply("message", "SERVER",content , msg.sender));
                }
                else{
                    content[0]=msg.content[0];
                    findUserThread(msg.recipient).send(new reply("upload_req", msg.sender, content, msg.recipient));
                }
            }
            else if(msg.type.equals("upload_res")){
                String[] content=new String[1];
                if(!msg.content[0].equals("NO")){
                    content[0]=msg.content[0];
                    String IP = findUserThread(msg.sender).socket.getInetAddress().getHostAddress();
                    findUserThread(msg.recipient).send(new reply("upload_res", IP,content , msg.recipient));
                }
                else{
                     content[0]=msg.content[0];
                    findUserThread(msg.recipient).send(new reply("upload_res", msg.sender, content, msg.recipient));
                }
            }
	}
    }
    
    public void Announce(String type, String sender, String[] content){
        reply msg = new reply(type, sender, content, "All");
        for(int i = 0; i < clientCount; i++){
            clients[i].send(msg);
        }
    }
    
    public void SendUserList(String toWhom){
        String[] content=new String[1];
        for(int i = 0; i < clientCount; i++){
            content[0]=clients[i].username;
            findUserThread(toWhom).send(new reply("newuser", "SERVER", content, toWhom));
        }
    }
    
    public ServerThread findUserThread(String usr){
        for(int i = 0; i < clientCount; i++){
            if(clients[i].username.equals(usr)){
                return clients[i];
            }
        }
        return null;
    }
    
      @SuppressWarnings("deprecation")
   public void remove(int ID){  
    int pos = findClient(ID);
        if (pos >= 0){  
            ServerThread toTerminate = clients[pos];
            ui.jTextArea1.append("\nRemoving client thread " + ID + " at " + pos);
	    if (pos < clientCount-1){
                for (int i = pos+1; i < clientCount; i++){
                    clients[i-1] = clients[i];
	        }
	    }
	    clientCount--;
	    try{  
	      	toTerminate.close(); 
	    }
	    catch(IOException ioe){  
	      	ui.jTextArea1.append("\nError closing thread: " + ioe); 
	    }
	    toTerminate.stop(); 
	}
    }
}
