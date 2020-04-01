package com.application.serialComm;

import com.application.view.MainJFram;
import static com.application.view.MainJFram.jComboBoxOfDevicePort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;   
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * 串口类
 * @author Redaddend
 */
public class SerialComm implements SerialPortEventListener, Runnable {
    /*端口拥有者*/
    private final static String PORT_OWER = "MonitorApp";   
    /*端口是否打开*/
    private boolean isOpen;   
    /*端口是否打开*/
    private boolean isStart;   
    /*端口是否保存*/
    private final boolean isSave;   
    /*端口是否打印*/
    private boolean isPrint;   
    /*读线程*/
    private Thread readThread;   
    /*端口名*/
    private String portName = "COM1";   
    /*端口地址*/
    private String portAddress;   
    /**/
    private CommPortIdentifier portId;   
    /*串口对象*/
    private SerialPort serialPort;   
    /*数据输入流*/
    private DataInputStream inputStream;   
    /*数据输出流*/
    private OutputStream outputStream;   
    /*格式化*/
    private final SimpleDateFormat formatter;   
    /*数据协议*/
    private String dataProtocol; 
    /*锁对象*/
    private final Object readWriteLock = new Object();
    
    final static int BYTE_LENGTH = 1024;
    
    private final static ArrayList<CommPortIdentifier> CPIlist = new ArrayList<>();
    private final static  ArrayList<String> portNamelist = new ArrayList<>();

    public static ArrayList<CommPortIdentifier> getCPIlist() {
        return CPIlist;
    }

    public static ArrayList<String> getPortNamelist() {
        return portNamelist;
    }
    
    
    /**
     * 获取端口号
     * @return 端口号
     */
    public String getPortName() {
        return portName;
    }
    /**
     * 无参数构造方法
     */
    public SerialComm() {
        isOpen = false;   
        isStart = false;   
        isSave = true;   
        isPrint = false;   
        formatter = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss,SSS]");  
        portName = "COM1";   
        portAddress = "LOCAL";   
        dataProtocol = "Gooseli";   
    }   
    /**
     * 端口初始化 带参数
     * @param port 端口号
     * @param protocol 协议
     * @throws IOException IO流获取失败
     * @throws gnu.io.NoSuchPortException 无此端口异常
     * @throws gnu.io.PortInUseException 该串口已经被使用异常
     * @throws gnu.io.UnsupportedCommOperationException 不支持的串口协议异常
     */
    public void init(String port, String protocol) throws  NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException 
    {   
        portName = port;   
        portAddress = portName;   
        dataProtocol = protocol;   
  
        init();   
    }   
    /**
     * 初始化串口 带参数
     * @param port 端口号
     * @param address 端口地址
     * @param protocol 协议 
     * @throws IOException IO流获取失败
     * @throws gnu.io.NoSuchPortException 无此端口异常
     * @throws gnu.io.PortInUseException 该串口已经被使用异常
     * @throws gnu.io.UnsupportedCommOperationException 不支持的串口协议异常
     */
    public void init(String port, String address, String protocol) throws  NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
    {   
        portName = port;   
        portAddress = address;   
        dataProtocol = protocol;   
  
        init();   
    }   
    /**
     * 初始化串口N 无参数
     * @throws IOException IO流获取失败
     * @throws gnu.io.NoSuchPortException 无此端口异常
     * @throws gnu.io.PortInUseException 该串口已经被使用异常
     * @throws gnu.io.UnsupportedCommOperationException 不支持的串口协议异常
     */
    public void init() throws  NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
    {   
        /*若端口为打开，则关闭端口*/
        if (isOpen)   
        {   
            close();   
        }   
        /* 获取COM1的CommPortIdentifier对象 */
        portId = CommPortIdentifier.getPortIdentifier(portName);
        /* 多态：强转为SerialPort串口对象，并且调用open方法 */
        serialPort = (SerialPort) portId.open(PORT_OWER, 2000);
        /* 定义串口协议 为波特率9600 8个数据位 1个停止位 无校验*/
        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        /* 获得输入流 */
        inputStream = new DataInputStream(serialPort.getInputStream());
        /* 获得输出流 */
        outputStream = serialPort.getOutputStream();
        /* 标记 */
        isOpen = true;   
    }   
    /**
     * 开始线程
     * @throws TooManyListenersException 过多监听
     * @throws Exception 未开启端口
     */
    public void start() throws TooManyListenersException, Exception
    {   
        if (!isOpen)   
        {   
            throw new Exception(portName + " has not been opened.");   
        }   
  
        readThread = new Thread(this);
        /*开线程*/
        readThread.start();
        /*等待数据*/
        serialPort.notifyOnDataAvailable(true);
        if(!isStart) {
            /*装载监听器*/
            serialPort.addEventListener((SerialPortEventListener) this);
        }
        /*标记*/
        isStart = true;
    }   
    /**
     * 线程体
     */
    @Override
    public void run() {   
        String at = null;
        if(MainJFram.getjLabelOfAPNVaule().getText().trim().equals("CMNET")) {        
            at = "DEVICE ID:"+ MainJFram.getjLabelOfDeviceID().getText() +"\r\n";          
        }else{        
            at = "APN:\""+ MainJFram.getjLabelOfAPNVaule().getText() +"\"\r\n";        
        }
        /*准备需要发送的数据*/
        writeComm(at);  
        at = null;
        isPrint = true;                        
    }
    /**
     * 停止串口
     */
    public void stop() {
        /* 如果已经开启 */
        if (isStart) {
            serialPort.notifyOnDataAvailable(false);   
            serialPort.removeEventListener();   
  
            isStart = false;   
        }   
    }   
    /**
     * 关闭串口
     */
    public void close() {   
        stop();   
  
        if (isOpen) {   
            try {   
                inputStream.close();   
                outputStream.close();   
                serialPort.close();   
  
                isOpen = false;   
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally{
                inputStream = null;
                outputStream = null;
                serialPort.close();
            }
        }   
    }   
  
    /**
     * 串口事件
     * @param event 
     */
    @Override
    public void serialEvent(SerialPortEvent event) {   
        switch (event.getEventType()) {   
            case SerialPortEvent.BI:   
            case SerialPortEvent.OE:   
            case SerialPortEvent.FE:   
            case SerialPortEvent.PE:   
            case SerialPortEvent.CD:   
            case SerialPortEvent.CTS:   
            case SerialPortEvent.DSR:   
            case SerialPortEvent.RI:   
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:   
                break;   
            case SerialPortEvent.DATA_AVAILABLE: 
                readComm();   
                break;   
            default:   
                break;   
        }   
    }
    /**
     * 静态方法，枚举所有可用的虚拟串口，无需打开串口
     */
    public void showAllPort(){
        Enumeration portsEnumer = null;
        CommPortIdentifier cpi = null;
        CPIlist.clear();
        portNamelist.clear();
        portsEnumer = CommPortIdentifier.getPortIdentifiers();
        while(portsEnumer.hasMoreElements()){
            cpi = (CommPortIdentifier)portsEnumer.nextElement();
            if(cpi.getName().substring(0, 3).equals("COM")){
                CPIlist.add(cpi);
                portNamelist.add(cpi.getName());
            }
        } 
        portsEnumer = null;
        cpi = null;
    }
    /**
     * 读方法
     */
    public void readComm() {   
        int bytesRead = 0;
        byte[] input = new byte[BYTE_LENGTH];
        String dataRecieve = null;
        try{
            while(bytesRead < BYTE_LENGTH) {
                    int result = inputStream.read(input,bytesRead,BYTE_LENGTH - bytesRead);
                    if(result == 0)
                        break;
                    bytesRead += result;
            }
            inputStream.close();
            dataRecieve = new String(input,"utf-8");
            MainJFram.legalComm = MainJFram.jComboBoxOfDevicePort.getSelectedItem().toString();
            //TestJFram.setHaveReceived(true);
            /* 收到device ID数据 */
            if(dataRecieve.substring(0,22).equals("DEVICE ID:"+ MainJFram.getjLabelOfDeviceID().getText() +"\r\n")) {            
                MainJFram.jTextAreaOfMessage.insert("设备收到了ID配置命令\n",0);
                /* 配置成功 */
                if(dataRecieve.substring(24,41).equals("ID SET SUCESSFUL!")) {
                    MainJFram.jTextAreaOfMessage.insert("设备ID配置成功!\n此设备ID为:" + MainJFram.getjLabelOfDeviceID().getText() + "\n",0);
                    MainJFram.jTextAreaOfMessage.setText("配置成功!\r\n此设备ID为:" + MainJFram.getjLabelOfDeviceID().getText());
                /* 无此命令 */
                }else if(dataRecieve.substring(24,40).equals("No such command!")) {  
                    MainJFram.jTextAreaOfMessage.insert("配置失败!非法命令\n",0);
                    
                /* 已经存在 id 号 */
                }else if(dataRecieve.substring(24,41).equals("ID SET Type ERRO!")){
                    MainJFram.jTextAreaOfMessage.insert("请配置与设备匹配的ID号\n",0);
                }else{                
                    MainJFram.jTextAreaOfMessage.insert("配置失败!此设备已有ID号:" + dataRecieve.substring(22,32) + "\n",0);                
                }
            /* 收到APN数据 */
            }else if(dataRecieve.substring(0,(MainJFram.getjLabelOfAPNVaule().getText().length() + 8)).equals("APN:\""+ MainJFram.getjLabelOfAPNVaule().getText() +"\"\r\n")) {            
                MainJFram.jTextAreaOfMessage.insert("设备收到了APN配置命令\n",0);
                /* 成功配置VPN */
                if(dataRecieve.substring((MainJFram.getjLabelOfAPNVaule().getText().length() + 10),(MainJFram.getjLabelOfAPNVaule().getText().length() + 28)).equals("APN SET SUCESSFUL!")) {                
                    MainJFram.jTextAreaOfMessage.insert("APN配置成功!\n",0);
                    MainJFram.jTextAreaOfMessage.insert("正在配置设备ID，请不要拔出设备!\n",0);
                    String at;
                    at = "DEVICE ID:"+ MainJFram.getjLabelOfDeviceID().getText() +"\r\n";
                    writeComm(at);   
                    isPrint = true;
                }else{  /* 配置失败 */                  
                    MainJFram.jTextAreaOfMessage.insert("APN配置失败!非法命令\n",0);
                    MainJFram.jTextAreaOfMessage.insert("将不会配置设备ID,请重新配置\n",0);     
                }            
            }else {/* 异常数据 */            
                MainJFram.jTextAreaOfMessage.insert("没有收到了或者收到了错误的配置命令，请重新配置！\n",0);
            }
            MainJFram.jTextAreaOfMessage.insert("按向上方向键可直接跳至\"断开设备\"的位置..\n",0);
            //TestJFram.jTextAreaOfMessage.setText(new String(input,"utf-8"));
            //TestJFram.jTextAreaOfMessage.insert(dataRecieve.substring(24,41));   
        } catch (IOException ex) {
            MainJFram.legalComm = null;
        } finally{
            dataRecieve = null;
            input = null;
        }
    }   
    /**
     * 写串口方法
     * @param outString 需要写的字符串
     */
    public void writeComm(String outString) {
        if(outString != null){
            synchronized (readWriteLock) {   
                try {   
                    outputStream.write(outString.getBytes());   
                } catch (IOException ex) {   
                    ex.printStackTrace();
                }   
            } 
        }
    }   
    
}  
