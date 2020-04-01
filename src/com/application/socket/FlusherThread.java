/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.socket;

import com.application.view.MainJFram;

/**
 * 
 * @author Redaddend
 */
public class FlusherThread implements Runnable{
    public static boolean flagOfFlash = true;
    public static long NetCommRunTime = 0;
    private static int minutes = 0;
    private static int seconds = 0;    
    @Override
    public void run() {
        while(flagOfFlash) { 
            NetCommRunTime = System.currentTimeMillis() - ObservationThread.netCommOpenTime;
            seconds = (int)(NetCommRunTime/1000%60);
            minutes = (int)(NetCommRunTime/1000/60);
            if(minutes >= 2) { 
                MainJFram.setTimeIsReady(true);
            }else{     
                MainJFram.setTimeIsReady(false);             
            }
            if(
                    (MainJFram.isTimeIsReady())
                &&(MainJFram.isStandardDeviceIsReady())){
                MainJFram.jButtonOfCalibrationCommand.setEnabled(true);
            }
            MainJFram.jTextFieldOfRunningTime.setText(" " + minutes + " 分" + seconds + "秒");
            try{               
                Thread.sleep(1000);           
            }catch(InterruptedException e){           
                System.out.println("flagOfFlash异常");           
            }       
       }
    }
    public void close(){
        flagOfFlash = false;
    }
}
