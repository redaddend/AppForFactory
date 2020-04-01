/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.deviceBean;

import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 *
 * @author Redaddend
 */
public class RequestDeviceBean {
    
    final private static DecimalFormat DECIMAL_FORMAT_TEMP = new DecimalFormat(".0");
    final private static DecimalFormat DECIMAL_FORMAT_VOL = new DecimalFormat(".00");
    
    private String deviceID;
    private final LinkedList<Float>tempLinkedList = new LinkedList<>();
    private float voltage;
    private float coefficient = 1.0f;
    private String message;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }
    
    public void insertNewTemp(Float temperature) {
        Float temp = temperature;
        if(tempLinkedList.size() > 4){
            tempLinkedList.pollLast();
        }
        tempLinkedList.offerFirst(temp);
    }

    public LinkedList<Float> getTempLinkedList() {
        return tempLinkedList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "RequestDeviceBean{" + "deviceID=" + deviceID + ", tempLinkedList=" + tempLinkedList + ", voltage=" + voltage + ", coefficient=" + coefficient + ", message=" + message + '}';
    }
}
