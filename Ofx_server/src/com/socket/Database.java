package com.socket;

import static com.socket.connection.stmt;
import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
 
    public connection con;
    public Database() throws SQLException, ClassNotFoundException{
       con=new connection();
       
    }
    
    public boolean userExists(String username) throws SQLException{
      boolean found=false;
        try{        
          System.out.println("checking records in the table...");
       String sql = "SELECT user from tb where 1";
      ResultSet rs = stmt.executeQuery(sql);
      //Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         String user  = rs.getString("user");
          if(user.equals(username))
          {found=true;  break;}
              }
      rs.close();
   }catch(SQLException se){
      //Handle errors for JDBC
       System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
      return found;
    }
    
  
     public String checkLogin(String username, String password){
        String res="TRUE";
                 if(username.equals(""))
            {
                  return "Username can't be left blank";              
            }
                 if(password.equals(""))
            {
                 return "Password can't be left blank";       
            }
                 try{
      
      //Execute a query
      System.out.println("checking records in the table...");
      String sql = "select pass from tb where user='"+username+"'";
       ResultSet rs=stmt.executeQuery(sql);
       String pass="";
       while(rs.next())
       {
      pass=rs.getString("pass");
       }
       if(!password.equals(pass))
       {
          res="Invalid username or password";
       }
        rs.close();
        //"Login successful";
      System.out.println("User found in the table...");
   
   }catch(SQLException se){
      //Handle errors for JDBC
      System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
     return res;   
    }
    
    public String addUser(String username, String[] content){
         String res="TRUE";
 
                if(!content[0].matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$"))//alphanumeric with .,'
            {
                 return "invalid Name";
        
            }
                
                  if(!content[1].matches("^[a-zA-Z0-9]*$")) //alphanumeric
            {
                return "invalid Registration no.";
              
            }
              
               int Room;
               long Phone;
                  try
            {
            Room=Integer.parseInt(content[3]);
            }
            catch(NumberFormatException nfe)
            {
                return "Room no. should be valid";
                
            }
                  
                 if(content[4].length()<10)
             {
                return "Phone no. should be of valid length";
       
             }
                 
             try
            {
            Phone=Long.parseLong(content[4]);
            }
            catch(NumberFormatException nfe)
            {
                 return "Phone no. should be valid";
            }   
              
              if(!content[5].matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
            {
                 return "invalid Email";
                 
            }
                if(content[7].equals(""))
            {
                return "password can't be left blank";

            }
            else if(!content[7].equals(content[8]))
            {
                   return "passwords didn't match";//System.out.println(pass1+""+pass2);
                   
            }
              if(content[10].equals(""))
           {
               return "answer can't be left blank";
  
           }          
               try{
      
      //Execute a query
      System.out.println("Inserting records into the table...");
      String sql = "INSERT INTO tb VALUES ('"+content[0]+"','"+content[1]+"','"+content[2]+"','"+Room+"','"+Phone+"','"+content[5]+"','"+content[6]+"','"+username+"','"+content[7]+"','"+content[9]+"','"+content[10]+"')";
      stmt.executeQuery(sql);
       
      System.out.println("Inserted records into the table...");

   }catch(SQLException se){
      //Handle errors for JDBC
       System.out.println("JDBC error");
       
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");

      e.printStackTrace();
   }     
         return res;
	}
    
    public String updatepassword(String username, String[] arr){
         String res="TRUE";
       if(username.equals(""))
            {
                 return "Username can't be left blank";
                      
            }
            else if(arr[1].equals("")){
                return "Answer can't be left blank";
                  
            }
            else if(arr[2].equals(""))
            {
                    return "Password can't be left blank";
              
            }
            else if(!arr[2].equals(arr[3]))
            {
                   return "Passwords didn't match";

            }
       
       try{
      
      //Execute a query
      System.out.println("checking records in the table...");
      String sql = "update tb set pass='"+arr[2]+"' where user='"+username+"' and security='"+arr[0]+"' and answer='"+arr[1]+"'";
                int rs=stmt.executeUpdate(sql);
       if(rs==0)
       {
           res="No user found with such credentials";
       }
 
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
             return res;
	}
    
     public String additem(String username, String[] arr){
         String res="TRUE";
        if(arr[0].equals(""))
            {
        return "please fill Item Name";
   
            }
            if(arr[2].equals(""))
            {
             return "please enter Item Cost";
            }
            
             int i,c=0;
            for( i=3;i<9;i++)
            {
                System.out.println("marked"+arr[i]);
                if(arr[i].equals("1"))
                { c++; }  
            }
       
            if(c==0)
            {
            return "select some search tags";
    
            }
            //String[] details=getdetails(username);
             
            try{
                 String sqlsel = "select * from items where itemname='"+arr[0]+"' and user='"+username+"'";     
       ResultSet rs=stmt.executeQuery(sqlsel);
              c=0;  
              while(rs.next())
              {
                  c++;
              }
              if(c>0)
               return "item already present in list";
        Date dNow = new Date( );
      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
      
      //Execute a query
      System.out.println("Inserting records into the table...");
String sql = "INSERT INTO items VALUES  ('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+username+"','"+ft.format(dNow)+"')";     
// String sql = "INSERT INTO items VALUES  ('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+username+"','"+details[0]+"','"+details[1]+"','"+details[2]+"','"+details[3]+"','"+details[4]+"','"+ft.format(dNow)+"')";
      stmt.executeUpdate(sql);
         
      System.out.println("Inserted records into the table...");

   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
    
         return res;
	}
     
     
  public String[] searchhist(String username)
  {
      String[] content=null;
       String res="NO";//your search didn't match any items
           String cond="";
             int i,c=0;
 
       try{
      
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select * from history where searcher ='"+username+"'";
   System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
        c=0;
        ArrayList<String> list=new ArrayList<String>();
         while(rs.next())
      {
         c++;
             res="";
            //br used for  break
    res=res+rs.getString("itemname")+"<br>"+rs.getString("description")+"<br>"+rs.getString("price")+"<br>"+rs.getString("user")
   +"<br>"+rs.getString("name")+"<br>"+rs.getString("hostel")+"<br>"+rs.getString("room")+"<br>"+rs.getString("phone")+"<br>"+rs.getString("email");
         list.add(res);
      } 
           rs.close();
         if(c>0)
         {
                content=list.toArray(new String[list.size()]);        
         }
         else{
             content=new String[1];
                content[0]=res;
               //You earlier didn't search  items 
         }
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
      return content;
  }
     
     public String[] searchitem(String username,String[] arr) throws SQLException{
         String res="NO";//your search didn't match any items
          String[] content = null;
           String cond="";
             int i,c=0;
         
                System.out.println("marked"+arr[0]);
                if(arr[1].equals("1"))
                { c++;cond="i.books='1' "; }  
                 if(arr[2].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or i.gadgets='1' ";
                        else
                        cond="i.gadgets='1' ";
                 c++;  
                 }
                 if(arr[3].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or i.formals='1' ";
                        else
                        cond="i.formals='1' ";
                 c++;  
                }
                 if(arr[4].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or i.lab_practicals='1' ";
                        else
                        cond="i.lab_practicals='1' ";
                 c++;  
                }
                 if(arr[5].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or i.Hostels='1' ";
                        else
                        cond="i.Hostels='1' ";
                 c++;  
                }    
               
              if(arr[6].equals("1"))
              {
                   if(c>=1)
                        cond=cond+" or i.Miscellaneous='1' ";
                        else
                        cond="i.Miscellaneous='1' ";
                 c++;  
              }  
       
            if(c==0)
            {
                content=new String[2];
                content[0]=res;
                content[1]="2";//select some search tags
                return content;
            }
 
       try{
      
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select i.itemname as it,i.description as de,i.price as pr,i.user as us,t.name as n,t.hostel as h,t.room as r,t.phone as ph,t.email as em from items as i,tb as t where i.itemname like '%" +arr[0] +"%' and ("+cond+") and i.user=t.user and i.user !='"+username+"'";
   System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
        c=0;
        ArrayList<String> list=new ArrayList<String>();
         while(rs.next())
      {
         
             c++;
             res="";
            //br used for  break
    res=res+rs.getString("it")+"<br>"+rs.getString("de")+"<br>"+rs.getString("pr")+"<br>"+rs.getString("us")
   +"<br>"+rs.getString("n")+"<br>"+rs.getString("h")+"<br>"+rs.getString("r")+"<br>"+rs.getString("ph")+"<br>"+rs.getString("em");
         list.add(res);
      } 
          rs.close();
         if(c>0)
         {
                content=list.toArray(new String[list.size()]);
              
                   for(i=0;i<list.size();i++)
                  {
                 
                 String[] temp=list.get(i).split("<br>");
                 String sqldel="DELETE FROM history where itemname='"+temp[0]+"' and searcher='"+username+"'";
                  int resdel=stmt.executeUpdate(sqldel);
     if(resdel>0)
     { System.out.println("deleted item");}
          
                 System.out.println("Inserting records into the table...");
                 String sqlnew = "INSERT INTO history VALUES  ('"+temp[0]+"','"+temp[1]+"','"+temp[2]+"','"+temp[3]+"','"+temp[4]
                +"','"+temp[5]+"','"+temp[6]+"','"+temp[7]+"','"
                +temp[8]+"','"+username+"')";
               stmt.executeUpdate(sqlnew);
                System.out.println("Inserted records into the table...");
                   }
              
         }
         else{
             content=new String[2];
                content[0]=res;
                content[1]="1";//Your search didn't match any items 
         }
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
      
  return content;
	}
  
     
     public String[] updateitem(String username,String[] arr)
     {
         String[] res=null;
          try{
      
      //Execute a query
      System.out.println("checking records in the table...");
      String sql = "update items set description='"+arr[2]+"' ,price='"+arr[3]+"' ,books='"+arr[4]+"' ,gadgets='"+arr[5]+"' ,formals='"+arr[6]+"' ,lab_practicals='"+arr[7]+"' ,Hostels='"+arr[8]+"' ,Miscellaneous='"+arr[9]+"' where user='"+username+"' and itemname='"+arr[1]+"'";
                int rs=stmt.executeUpdate(sql);
       if(rs==0)
       {
           res=new String[1];
           res[0]="NO";
       }
        else
       {
           res=Arrays.copyOf(arr, arr.length);
            System.out.println("updated records in the table...");
       }
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
             return res;
   
     }
     
     public String[] getuseritems(String username,String[] arr){
         String res="NO";//you don't have any items
          String[] content = null;
           String cond="";
             int i,c=0;   
             
          System.out.println("search text"+arr[0]);
                if(arr[1].equals("1"))
                { c++;cond="books='1' "; }  
                 if(arr[2].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or gadgets='1' ";
                        else
                        cond="gadgets='1' ";
                 c++;  
                 }
                 if(arr[3].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or formals='1' ";
                        else
                        cond="formals='1' ";
                 c++;  
                }
                 if(arr[4].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or lab_practicals='1' ";
                        else
                        cond="lab_practicals='1' ";
                 c++;  
                }
                 
                 if(arr[5].equals("1"))
                { 
                    if(c>=1)
                        cond=cond+" or Hostels='1' ";
                        else
                        cond="Hostels='1' ";
                 c++;  
                }    
               
              if(arr[6].equals("1"))
              {
                   if(c>=1)
                        cond=cond+" or Miscellaneous='1' ";
                        else
                        cond="Miscellaneous='1' ";
                 c++;  
              }  
        
       try{
      
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select * from items where user ='" + username +"' and itemname like '%"+arr[0]+"%' and ("+cond+")";
   System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
        c=0;
        ArrayList<String> list=new ArrayList<String>();
         while(rs.next())
      {
             c++;         
       
    res=rs.getString("itemname")+"<br>"+rs.getString("description")+"<br>"+rs.getString("price")
   +"<br>"+rs.getString("books")+"<br>"+rs.getString("gadgets")+"<br>"+rs.getString("formals")+"<br>"
            +rs.getString("lab_practicals")+"<br>"+rs.getString("Hostels")+"<br>"+rs.getString("Miscellaneous");
         list.add(res);
      } 
          rs.close();
         if(c>0)
         {
                content=list.toArray(new String[list.size()]);
            
         }
         else{
             content=new String[1];
                content[0]=res;
             }
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
    
  return content;
	}
     
     public String[] getdetails(String username){
  
          String[] content = new String[5];
   
       try{
      
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select name,hostel,room,phone,email from tb where user='"+username+"'";
     System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
 
         while(rs.next())
      {
           content[0]=rs.getString("name");
            content[1]=rs.getString("hostel");
             content[2]=rs.getString("room");
              content[3]=rs.getString("phone");
               content[4]=rs.getString("email");
      } 
          rs.close();
      
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
    
  return content;
	}
     
      public String[] getnotify(String username){
  
          ArrayList<String> list = new ArrayList();
        String[] content=null;
        int c=0;
       try{
      
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select * from notific where recipient='"+username+"'";
     System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
 String res;
         while(rs.next())
      {
          c++;
            res="";
            
    res=res+rs.getString("itemname")+"<br>"+rs.getString("user")
   +"<br>"+rs.getString("name")+"<br>"+rs.getString("hostel")+"<br>"+rs.getString("room")+"<br>"+rs.getString("phone")+"<br>"+rs.getString("email")+"<br>"+rs.getString("status");
         list.add(res);
          
      } 
          rs.close();
      if(c>0)
      {
            content=list.toArray(new String[list.size()]);
      }
      else
      {
            content=new String[1];
                content[0]="NO";//You don't have notifications
      }
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
       
  return content;
	}
      
        public String[] getnewnotify(){
  
          ArrayList<String> list = new ArrayList();
        String[] content=null;
        int c=0;
       try{
        Date dNow = new Date( );
      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
      //Execute a query
      System.out.println("searching records into the table...");
      String sql = "select i.itemname as it,i.description as de,i.price as pr,i.user as us,i.books as ib,i.gadgets as ig,i.formals as ifo,i.lab_practicals as il,i.Hostels as iH,i.Miscellaneous as iM,t.name as n,t.hostel as h,t.room as r,t.phone as ph,t.email as em  from items as i,tb as t where i.date='"+ft.format(dNow)+"' and i.user=t.user";
     // String sql = "select i.itemname as it,i.description as de,i.price as pr,i.user as us,t.name as n,t.hostel as h,t.room as r,t.phone as ph,t.email as em from items as i,tb as t where i.itemname like '%" +arr[0] +"%' and ("+cond+") and i.user=t.user and i.user !='"+username+"'";
      System.out.println(sql);
      ResultSet rs= stmt.executeQuery(sql);
 String res;
         while(rs.next())
      {
          c++;
            res="";
            
    res=res+rs.getString("it")+"<br>"+rs.getString("de")+"<br>"+rs.getString("pr")+"<br>"+rs.getString("us")
   +"<br>"+rs.getString("n")+"<br>"+rs.getString("h")+"<br>"+rs.getString("r")+"<br>"+rs.getString("ph")+"<br>"+rs.getString("em")+"<br>"
            +rs.getString("ib")+"<br>"+rs.getString("ig")+"<br>"+rs.getString("ifo")+"<br>"+rs.getString("il")+"<br>"+rs.getString("iH")+"<br>"+rs.getString("iM");
         list.add(res);
           
      } 
          rs.close();
      if(c>0)
      {
            content=list.toArray(new String[list.size()]);
      }
      else
      {
            content=new String[1];
                content[0]="NO";//You don't have notifications
      }
      System.out.println("Returning records from the table...");
      
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.print("JDBC error");
        se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");
      e.printStackTrace();
   }
       
  return content;
	}
        
        public String[] sendnewitemdet(String sender,String[] arr){
     String[] res=new String[1];
     String[] details=getdetails(sender);
     res[0]=arr[0]+"<br>"+arr[1]+"<br>"+arr[2]+"<br>"+sender
   +"<br>"+details[0]+"<br>"+details[1]+"<br>"+details[2]+"<br>"+details[3]+"<br>"+details[4]+"<br>"
            +arr[3]+"<br>"+arr[4]+"<br>"+arr[5]+"<br>"+arr[6]+"<br>"+arr[7]+"<br>"+arr[8];
    return res;
          }
        
      public String[] deleteitem(String username,String item)
      {
          String[] res={"NO",item};
          try{
   
      String sql = "DELETE FROM items where user= '"+username+"' and itemname ='"+item+"'";
                 
      int rs=stmt.executeUpdate(sql);
     if(rs>0)
     { System.out.println("deleted item");res[0]="TRUE";}
          }
          catch(SQLException se){
      //Handle errors for JDBC
        System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
          return res;
      }
      
      public boolean deletehistory(String username)
      {
          boolean res=false;
           try{
   
      String sql = "DELETE FROM notific where recipient='"+username+"'";
                 
      int rs=stmt.executeUpdate(sql);
     if(rs>0)
     { System.out.println("deleted item");res=true;}
          }
          catch(SQLException se){
      //Handle errors for JDBC
        System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
           return res;
      }
      
      public void updatenotifylist(String username)
      {
           try{
      
      //Execute a query
      System.out.println("checking records in the table...");
      String sql = "update notific set status='1' where recipient='"+username+"'";
               int rs= stmt.executeUpdate(sql);
     System.out.println(rs+ "rows updated");
   }catch(SQLException se){
      //Handle errors for JDBC
        System.out.println("JDBC error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
       System.out.println("Registering Driver error");
      e.printStackTrace();
   }
      }
      
     public void sendnotifytodata(String[] content,String sender,String itemname,String recipient,String status)
     {
          
           try{
      
      //Execute a query
      System.out.println("Inserting records into the table...");
      String sql = "INSERT INTO notific VALUES ('"+itemname+"','"+recipient+"','"+sender+"','"+content[0]+"','"+content[1]+"','"+content[2]+"','"+content[3]+"','"+content[4]+"','"+status+"')";
      stmt.executeUpdate(sql);
       
      System.out.println("Inserted records into the table...");

   }catch(SQLException se){
      //Handle errors for JDBC
       System.out.println("JDBC error");
       
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      System.out.println("Registering Driver error");

      e.printStackTrace();
   } 
     }
     
}
