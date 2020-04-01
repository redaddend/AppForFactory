/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.socket;

import com.application.view.MainJFram;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ObservationThread implements Runnable{
    public static long netCommOpenTime = System.currentTimeMillis(); 
    public static boolean flag = true;
    public static DatagramSocket dSocket = null;
    @Override
    public void run() {
        flag = true;
        byte[] inBuffer= new byte[500];
        ThreadOfProcession top = null;
        netCommOpenTime = System.currentTimeMillis(); 
        try
        {
            //建立UDPSOCKET
            dSocket = new DatagramSocket(Integer.parseInt(MainJFram.jTextFieldOfNetPort.getText()));
            MainJFram.jButtonOfMonitorCommand.setText("停止监听");
            MainJFram.jTextFieldOfNetPort.setEnabled(false);
            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
            while(flag) {
                dSocket.receive(inPacket);
                top = new ThreadOfProcession(dSocket, inPacket);
                threadPool.execute(top);
            }            
        } catch (IOException | NumberFormatException ex) {
            MainJFram.jButtonOfMonitorCommand.setText("开始监听");
            MainJFram.jTextFieldOfNetPort.setEnabled(true);
            if(ex.toString().equals("java.net.SocketException: socket closed")){            
                JOptionPane.showMessageDialog(MainJFram.getRootPaneOfMain(), "端口" + MainJFram.jTextFieldOfNetPort.getText().trim() + "已经关闭！");            
            }else{            
                JOptionPane.showMessageDialog(MainJFram.getRootPaneOfMain(), "尝试打开端口 " + MainJFram.jTextFieldOfNetPort.getText().trim() + " 时出现异常！");            
            }
        }finally{
            if(dSocket!=null){
                dSocket.close();
                top = null;
                dSocket = null;
            }
        }
    }
    public void close(){
        flag = false;
        dSocket.close();
        netCommOpenTime = 0;
    }
}
