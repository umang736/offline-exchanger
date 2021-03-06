/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ofx;

import com.socket.Message;
import com.socket.SocketClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import static ofx.Ofx.tb;
import static ofx.loginn.username;

/**
 *
 * @author umang gupta
 */
public class Update extends javax.swing.JPanel {
public Home ui;
public SocketClient client;
public static DefaultListModel updatelist;
public static ArrayList<String> ownitems;
public Edit editframe;
    /**
     * Creates new form Update
     */
    public Update(Home ui,Edit editframe) {
        this.ui=ui;
        this.editframe=editframe;
        initComponents();
        ownitems=new ArrayList<String>();
          
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        editbut = new javax.swing.JButton();
        deletebut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        itemname = new javax.swing.JTextField();
        searchbut = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Box1 = new javax.swing.JCheckBox();
        Box2 = new javax.swing.JCheckBox();
        Box3 = new javax.swing.JCheckBox();
        Box4 = new javax.swing.JCheckBox();
        Box5 = new javax.swing.JCheckBox();
        Box6 = new javax.swing.JCheckBox();

        jList1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jList1.setModel((updatelist=new DefaultListModel()));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setToolTipText("");
        jList1.setFixedCellHeight(20);
        jScrollPane1.setViewportView(jList1);

        editbut.setText("Edit");
        editbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbutActionPerformed(evt);
            }
        });

        deletebut.setText("Delete");
        deletebut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebutActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ofx/images/teal-search-icon-32-x-32.gif"))); // NOI18N
        jLabel1.setText("<html>ItemName<br>(optional)</html>");

        searchbut.setText("search");
        searchbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbutActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("<html>Search<br>&nbsp;Tags<br/>(optional)</html>");

        Box1.setText("Books");

        Box2.setText("Gadgets");
        Box2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Box2ActionPerformed(evt);
            }
        });

        Box3.setText("Formals");

        Box4.setText("Lab Practicals");

        Box5.setText("Hostels");

        Box6.setText("Miscellaneous");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(itemname)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchbut))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Box1)
                                    .addComponent(Box4))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Box2)
                                    .addComponent(Box5))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Box6)
                                    .addComponent(Box3))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deletebut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editbut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(itemname, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchbut, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Box1)
                            .addComponent(Box2)
                            .addComponent(Box3))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Box4)
                            .addComponent(Box5)
                            .addComponent(Box6)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(editbut)
                        .addGap(67, 67, 67)
                        .addComponent(deletebut)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbutActionPerformed
        // TODO add your handling code here:
          this.client=ui.client;
                       
            if(client!=null)
            {
                if(!username.equals(""))
                {
         int index=jList1.getSelectedIndex();
          String[] selected=ownitems.get(index).split("<br>");
                     editframe.fillframe(selected[0], selected[1], selected[2], selected[3], selected[4], selected[5],selected[6],selected[7],selected[8],index);
                     editframe.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(tb, "please login first");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(tb, "connection failure");  
            }
    }//GEN-LAST:event_editbutActionPerformed

    private void deletebutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebutActionPerformed
        // TODO add your handling code here:
          this.client=ui.client;
                       
            if(client!=null)
            {
                if(!username.equals(""))
                {       
          String[] selected=new String[1];
              selected[0]=(String) jList1.getSelectedValue();
                 
                    client.send(new Message("delete", username, selected , "SERVER")); 
                }
                else
                {
                    JOptionPane.showMessageDialog(tb, "please login first");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(tb, "connection failure");  
            }
    }//GEN-LAST:event_deletebutActionPerformed

    private void searchbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbutActionPerformed
        // TODO add your handling code here:
         this.client=ui.client;
         updatelist.removeAllElements();
         ownitems.clear();
            if(client!=null)
            {
                if(!username.equals(""))
                {
              view_field view = new view_field();
                 try {   
                     control_field ctrl = new control_field(view);
                 } catch (IOException ex) {
                     Logger.getLogger(Registern.class.getName()).log(Level.SEVERE, null, ex);
                 }
                  }
                else
                {
                    JOptionPane.showMessageDialog(tb, "please login first");
                }
            }
               else
         {
             JOptionPane.showMessageDialog(tb, "connection failure");
         }
    }//GEN-LAST:event_searchbutActionPerformed

    private void Box2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Box2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Box2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Box1;
    private javax.swing.JCheckBox Box2;
    private javax.swing.JCheckBox Box3;
    private javax.swing.JCheckBox Box4;
    private javax.swing.JCheckBox Box5;
    private javax.swing.JCheckBox Box6;
    private javax.swing.JButton deletebut;
    private javax.swing.JButton editbut;
    private javax.swing.JTextField itemname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchbut;
    // End of variables declaration//GEN-END:variables

     private class control_field {
    
     private view_field  view;

        public control_field( view_field viewnew) throws IOException {
            
            view=viewnew;
           //fetch fields
            String[] arr=new String[7];
            arr[0]=view.get_Itemname();
            String[] checked =Arrays.copyOf(view.get_Tags(), view.get_Tags().length);
            for(int i=1;i<7;i++)
                arr[i]=checked[i-1];
             for(int i=1;i<7;i++)
            {
              System.out.println(arr[i]);
            }
        //pass arguments
             
            client.send(new Message("searchown", username, arr, "SERVER"));     
      
        }  
       
    }
     private class view_field {
    public ArrayList<JCheckBox> checkboxes;
        public String get_Itemname()
        {
            return itemname.getText();
        }
     public String[] get_Tags()
        {
            /*
           boolean[] arr=new boolean[6];//or   Boolean[] array = new Boolean[size];Arrays.fill(array, Boolean.FALSE); 
          */ checkboxes= new ArrayList<JCheckBox>();
          checkboxes.add(Box1);checkboxes.add(Box2);checkboxes.add(Box3);checkboxes.add(Box4);checkboxes.add(Box5);checkboxes.add(Box6);
          String[] arr=new String[6]; 
          Arrays.fill(arr,"0");
            for(int i=0;i<checkboxes.size();i++)
          {
              if(checkboxes.get(i).isSelected())
              {
                  arr[i]="1";
                  System.out.println((i+1)+"marked");
              }
          }
          return arr;
        }  
    }
}
