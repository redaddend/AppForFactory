/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.socket;

import com.application.deviceBean.RequestDeviceBean;
import com.application.tools.CommonTool;
import com.application.tools.PackageTool;
import static com.application.tools.PackageTool.*;
import com.application.view.MainJFram;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Administrator
 */
public class ThreadOfProcession implements Runnable{
    public ThreadOfProcession(DatagramSocket dSocket,DatagramPacket inPacket) {
        this.dSocket = dSocket;
        this.inPacket = inPacket;
        this.cAddr = inPacket.getAddress();
        this.cPort = inPacket.getPort();
        this.dataOfChars = inPacket.getData();
    }
    private DatagramSocket dSocket;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;
    private InetAddress cAddr;
    private int cPort;
    private byte[] outBuffer;
    private byte[] dataOfChars;

    
    
    @Override
    public void run() {
        byte[] buffer = encryption(transferDBtoCommon(cutPackageByTail(dataOfChars)));
        byte[] output = null;
        if(recieveCheckData(buffer)){
            if(buffer[11] == 0x01){
                RequestDeviceBean rdbNow = null;
                String deviceid = null;
                synchronized(MainJFram.synobj){
                    try{
                        /* 接收解析 */
                        deviceid = new String(buffer,"utf-8").substring(1,11);
                        float temp = (((float)((((int)buffer[12] & 0x00ff)<<8) + ((int)buffer[13] & 0x00ff)) - 300)/10);
                        /* 接收解析 */
                        rdbNow = new RequestDeviceBean();
                        rdbNow.setDeviceID(deviceid);
                        /* 如果接收设备列表为空 */
                        if(MainJFram.devices.isEmpty()){
                            /* 插入温度 */
                            rdbNow.insertNewTemp(null);
                            rdbNow.insertNewTemp(null);
                            rdbNow.insertNewTemp(null);
                            rdbNow.insertNewTemp(null);
                            rdbNow.insertNewTemp(temp);
                            /* 加入列表 */
                            MainJFram.devices.add(2, rdbNow);
                        }else{/* 不空 */
                            if(MainJFram.isFlushTable()){
                                int i = 0;
                                /* 遍历ID号 */
                                for(;i<MainJFram.devices.size();i++){ 
                                    if(deviceid.equals(MainJFram.devices.get(i).getDeviceID())){
                                        /* 刷新 */
                                        rdbNow = MainJFram.devices.get(i);
                                        MainJFram.devices.remove(i);
                                        /* 插入新温度 */
                                        rdbNow.insertNewTemp(temp);
                                        rdbNow.setVoltage((float)((int)buffer[14] & 0x00ff)/100 + 2.5f);
                                        /* */
                                        /* 替换 */
                                        if(i < 2){
                                            MainJFram.devices.add(i, rdbNow);
                                            //System.out.println(rdbNow.toString());
                                        }else{
                                            MainJFram.devices.add(2, rdbNow);
                                        }
                                        break;
                                    }
                                }
                                /* 若无 */
                                if(i == MainJFram.devices.size()){
                                    rdbNow.insertNewTemp(null);
                                    rdbNow.insertNewTemp(null);
                                    rdbNow.insertNewTemp(null);
                                    rdbNow.insertNewTemp(null);
                                    rdbNow.insertNewTemp(temp);
                                    rdbNow.setVoltage((float)((int)buffer[14] & 0x00ff)/100 + 2.5f);
                                    MainJFram.devices.add(2, rdbNow);
                                }
                                MainJFram.devices = CommonTool.sortArrayList(MainJFram.devices, MainJFram.THE_IDEAL_RANGE, MainJFram.THE_MAX_RANGE, MainJFram.STATEOFVOLTAGE);
                                MainJFram.showList(MainJFram.devices);
    //                            for(int j = 0;j<MainJFram.devices.size();j++){
    //                                System.out.println(MainJFram.devices.get(j).toString());
    //                            }
                            }else{
                                //返回校准系数
                                for(int i = 2;i<MainJFram.devices.size();i++){ 
                                    //如果ID号相等
                                    if(deviceid.equals(MainJFram.devices.get(i).getDeviceID())){
                                        /* 获取bean */
                                        rdbNow = MainJFram.devices.get(i);
                                        /* 替换 */
                                        if(!(rdbNow.getMessage().equals("")||(rdbNow.getMessage() == null))){
                                            switch(rdbNow.getMessage()){
                                                case "lowBattery":
                                                case "outOfRange":
                                                case "needMore":
                                                    break;
                                                case "inRange"://系数为1
                                                    output = new byte[25];
                                                    System.arraycopy(buffer, 0, output, 0, 11);	
                                                    output[11] = 0x02;
                                                    output[12] = 25;
                                                    output[13] = (byte)(((output[1] - 0x30) + (output[2] - 0x30)  + (output[3] - 0x30) + (output[9] - 0x30))>>((output[16] - 0x30)/2));
                                                    output[14] = (byte)(((output[5] - 0x30) + (output[7] - 0x30)  + (output[8] - 0x30) + (output[9] - 0x30))>>((output[15] - 0x30)/2));								//校验位
                                                    output[15] = PackageTool.checkData(output,15);						
                                                    output[16] = (byte)0xc0;		
                                                    PackageTool.decode(output,15);
                                                    output = PackageTool.toGetDBEscapeByte(output,15);
                                                    outPacket = new DatagramPacket(output,output.length,cAddr,cPort);
                                                    try{
                                                        dSocket.send(outPacket);
                                                        //if(rdbNow.getMessage().equals("done")) {
                                                        //}else{
                                                            rdbNow.setMessage("done");
                                                            //MainJFram.doneNumber ++;
                                                        //}
                                                        MainJFram.devices.remove(i);
                                                        MainJFram.devices.add(2, rdbNow);
                                                        //将bean放到头部
                                                        //MainJFram.getRequestNumber() - MainJFram.doneNumber
                                                        //int number = Integer.valueOf(MainJFram.jTextFieldOfuncalibratedDeviceNumber.getText());//获取未校准数量
                                                        MainJFram.jTextFieldOfuncalibratedDeviceNumber.setText(String.valueOf(MainJFram.getRequestNumber() - 2 - CommonTool.statisticsOfDone(MainJFram.devices)));
                                                        MainJFram.jTextFieldOfCalibratedDeviceNumber.setText(String.valueOf(CommonTool.statisticsOfDone(MainJFram.devices)));
                                                        System.out.println("配置温度系数请求已经成功发送，请检查设备亮灯状况确定配置是否成功");
                                                    }catch(IOException ex){
                                                        ex.printStackTrace();
                                                    }
                                                    //发送1系数
                                                    break;
                                                case "done":System.out.println("done");
                                                    break;
                                                default:
                                                    System.out.println("default");
                                                    String a = rdbNow.getMessage().trim();
                                                    int b = Integer.valueOf(a);
                                                    if((Math.abs(b) >= (int)MainJFram.THE_IDEAL_RANGE*10)&&(Math.abs(b) <= (int)MainJFram.THE_MAX_RANGE*10))
                                                    {
                                                        System.out.println("符合要求");
                                                        output = new byte[25];
                                                        System.arraycopy(buffer, 0, output, 0, 11);
                                                        output[11] = 0x02;
                                                        output[12] = (byte)(25 + b);
                                                        output[13] = (byte)(((output[1] - 0x30) + (output[2] - 0x30)  + (output[3] - 0x30) + (output[9] - 0x30))>>((output[16] - 0x30)/2));
                                                        output[14] = (byte)(((output[5] - 0x30) + (output[7] - 0x30)  + (output[8] - 0x30) + (output[9] - 0x30))>>((output[15] - 0x30)/2));								//校验位
                                                        output[15] = PackageTool.checkData(output,15);
                                                        output[16] = (byte)0xc0;
                                                        PackageTool.decode(output,15);
                                                        output = PackageTool.toGetDBEscapeByte(output,15);
                                                        outPacket = new DatagramPacket(output,output.length,cAddr,cPort);
                                                        try{
                                                            dSocket.send(outPacket);
                                                            System.out.println("配置温度系数请求已经成功发送，请检查设备亮灯状况确定配置是否成功");
                                                            rdbNow.setMessage("done");
                                                            MainJFram.devices.remove(i);
                                                            MainJFram.devices.add(2, rdbNow);
                                                            
                                                            MainJFram.jTextFieldOfuncalibratedDeviceNumber.setText(String.valueOf(MainJFram.getRequestNumber() - 2 - CommonTool.statisticsOfDone(MainJFram.devices)));
                                                            MainJFram.jTextFieldOfCalibratedDeviceNumber.setText(String.valueOf(CommonTool.statisticsOfDone(MainJFram.devices)));
                                                        }catch(IOException ex){
                                                            ex.printStackTrace();
                                                        }
                                                    }else{
                                                        System.out.println("不符合要求，未下发");
                                                    }
                                                    //发送系数
                                                    break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                MainJFram.showList(MainJFram.devices);
                            }                            
                        }
                    }catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }finally{
                        rdbNow = null;
                        deviceid = null;
                    }
                }
            }
            dSocket = null;
            inPacket = null;
            outPacket = null;
            cAddr = null;
        }else{
            System.out.println("不符合校验规则");
        }
    }
}
