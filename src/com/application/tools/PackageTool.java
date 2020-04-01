package com.application.tools;

/**
 * 包相关静态工厂
 * @author Redaddend
 */
public class PackageTool {
    final private static byte[] KEY = {
        (byte)0x53,(byte)0x22,(byte)0x1e,(byte)0x22,(byte)0xbb,(byte)0x21,(byte)0x87,(byte)0x21,(byte)0x54,(byte)0x21,
        (byte)0x21,(byte)0x21,(byte)0xef,(byte)0x20,(byte)0xbd,(byte)0x20,(byte)0x8b,(byte)0x20,(byte)0x5a,(byte)0x20,
        (byte)0x29,(byte)0x20,(byte)0xf8,(byte)0x1f,(byte)0xc8,(byte)0x1f,(byte)0x98,(byte)0x1f,(byte)0x68,(byte)0x1f,
        (byte)0x39,(byte)0x1f,(byte)0x09,(byte)0x1f,(byte)0xdb,(byte)0x1e,(byte)0xac,(byte)0x1e,(byte)0x7e,(byte)0x1e,
        (byte)0x50,(byte)0x1e,(byte)0x23,(byte)0x1e,(byte)0xf5,(byte)0x1d,(byte)0xc8,(byte)0x1d,(byte)0x9c,(byte)0x1d,
        (byte)0x6f,(byte)0x1d,(byte)0x43,(byte)0x1d,(byte)0x17,(byte)0x1d,(byte)0xec,(byte)0x1c,(byte)0xc1,(byte)0x1c,
        (byte)0x96,(byte)0x1c,(byte)0x6b,(byte)0x1c,(byte)0x41,(byte)0x1c,(byte)0x17,(byte)0x1c,(byte)0xed,(byte)0x1b,
        (byte)0xc3,(byte)0x1b,(byte)0x9a,(byte)0x1b,(byte)0x4e,(byte)0x1b,(byte)0x26,(byte)0x1b,(byte)0xfe,(byte)0x1a,
        (byte)0xd6,(byte)0x1a,(byte)0xae,(byte)0x1a,(byte)0x87,(byte)0x1a,(byte)0x60,(byte)0x1a,(byte)0x39,(byte)0x1a,
        (byte)0x12,(byte)0x1a,(byte)0xec,(byte)0x19,(byte)0xc6,(byte)0x19,(byte)0xa0,(byte)0x19,(byte)0x7a,(byte)0x19,
        (byte)0x55,(byte)0x19,(byte)0x30,(byte)0x19,(byte)0x0b,(byte)0x19,(byte)0xe6,(byte)0x18,(byte)0xc2,(byte)0x18,
        (byte)0x9e,(byte)0x18,(byte)0x7a,(byte)0x18,(byte)0x56,(byte)0x18,(byte)0x33,(byte)0x18,(byte)0x10,(byte)0x18,
        (byte)0xed,(byte)0x17,(byte)0xca,(byte)0x17,(byte)0xa7,(byte)0x17,(byte)0x85,(byte)0x17,(byte)0x63,(byte)0x17,
        (byte)0x41,(byte)0x17,(byte)0x1f,(byte)0x17,(byte)0xfe,(byte)0x16,(byte)0xdc,(byte)0x16,(byte)0xbb,(byte)0x16,
        (byte)0x9b,(byte)0x16,(byte)0x7a,(byte)0x16,(byte)0x5a,(byte)0x16,(byte)0x39,(byte)0x16,(byte)0x19,(byte)0x16,
        (byte)0xfa,(byte)0x15,(byte)0xda,(byte)0x15,(byte)0xbb,(byte)0x15,(byte)0x9b,(byte)0x15,(byte)0x7c,(byte)0x15,
        (byte)0x5e,(byte)0x15,(byte)0x3f,(byte)0x15,(byte)0x21,(byte)0x15,(byte)0x02,(byte)0x15,(byte)0xe4,(byte)0x14,
        (byte)0xc7,(byte)0x14,(byte)0xa9,(byte)0x14,(byte)0x8b,(byte)0x14,(byte)0x6e,(byte)0x14,(byte)0x51,(byte)0x14,
        (byte)0x34,(byte)0x14,(byte)0x17,(byte)0x14,(byte)0xfb,(byte)0x13,(byte)0xdf,(byte)0x13,(byte)0xc2,(byte)0x13,
        (byte)0xa6,(byte)0x13,(byte)0x8b,(byte)0x13,(byte)0x6f,(byte)0x13,(byte)0x53,(byte)0x13,(byte)0x38,(byte)0x13,
        (byte)0x1d,(byte)0x13,(byte)0x02,(byte)0x13,(byte)0xe7,(byte)0x12,(byte)0xcd,(byte)0x12,(byte)0xb2,(byte)0x12,
        (byte)0x98,(byte)0x12,(byte)0x7e,(byte)0x12,(byte)0x64,(byte)0x12,(byte)0x4a,(byte)0x12,(byte)0x30,(byte)0x12,
        (byte)0x17,(byte)0x12,(byte)0xfd,(byte)0x11,(byte)0xe4,(byte)0x11,(byte)0xcb,(byte)0x11,(byte)0xb2,(byte)0x11,
        (byte)0x9a,(byte)0x11,(byte)0x81,(byte)0x11,(byte)0x69,(byte)0x11,(byte)0x51,(byte)0x11,(byte)0x38,(byte)0x11,
        (byte)0x20,(byte)0x11,(byte)0x09,(byte)0x11,(byte)0xf1,(byte)0x10,(byte)0xda,(byte)0x10,(byte)0xc2,(byte)0x10,
        (byte)0xab,(byte)0x10,(byte)0x94,(byte)0x10,(byte)0x7d,(byte)0x10,(byte)0x66,(byte)0x10,(byte)0x4f,(byte)0x10,
        (byte)0x39,(byte)0x10,(byte)0x23,(byte)0x10,(byte)0x0c,(byte)0x10,(byte)0xf6,(byte)0x0f,(byte)0xe0,(byte)0x0f,
        (byte)0xca,(byte)0x0f,(byte)0xb5,(byte)0x0f,(byte)0x9f,(byte)0x0f,(byte)0x8a,(byte)0x0f,(byte)0x74,(byte)0x0f,
        (byte)0x5f,(byte)0x0f,(byte)0x4a,(byte)0x0f,(byte)0x35,(byte)0x0f,(byte)0x20,(byte)0x0f,(byte)0x0c,(byte)0x0f,
        (byte)0xf7,(byte)0x0e,(byte)0xe3,(byte)0x0e,(byte)0xcf,(byte)0x0e,(byte)0xba,(byte)0x0e,(byte)0xa6,(byte)0x0e,
        (byte)0x92,(byte)0x0e,(byte)0x7f,(byte)0x0e,(byte)0x6b,(byte)0x0e,(byte)0x57,(byte)0x0e,(byte)0x44,(byte)0x0e,
        (byte)0x31,(byte)0x0e,(byte)0x1d,(byte)0x0e,(byte)0x0a,(byte)0x0e,(byte)0xf7,(byte)0x0d,(byte)0xe4,(byte)0x0d,
        (byte)0xd2,(byte)0x0d,(byte)0xbf,(byte)0x0d,(byte)0xac,(byte)0x0d,(byte)0x9a,(byte)0x0d,(byte)0x88,(byte)0x0d,
        (byte)0x75,(byte)0x0d,(byte)0x63,(byte)0x0d,(byte)0x51,(byte)0x0d,(byte)0x3f,(byte)0x0d,(byte)0x2e,(byte)0x0d,
        (byte)0x12,(byte)0x0d,(byte)0x00,(byte)0x0d,(byte)0xef,(byte)0x0c,(byte)0xde,(byte)0x0c,(byte)0xcc,(byte)0x0c,
        (byte)0xbb,(byte)0x0c,(byte)0xaa,(byte)0x0c,(byte)0x9a,(byte)0x0c,(byte)0x89,(byte)0x0c,(byte)0x78,(byte)0x0c,
        (byte)0x68,(byte)0x0c,(byte)0x57,(byte)0x0c,(byte)0x47,(byte)0x0c,(byte)0x37,(byte)0x0c,(byte)0x26,(byte)0x0c,
        (byte)0x16,(byte)0x0c,(byte)0x06,(byte)0x0c,(byte)0xf6,(byte)0x0b,(byte)0xe7,(byte)0x0b,(byte)0xd7,(byte)0x0b,
        (byte)0xc7,(byte)0x0b,(byte)0xb8,(byte)0x0b,(byte)0xa8,(byte)0x0b,(byte)0x99,(byte)0x0b,(byte)0x8a,(byte)0x0b,
        (byte)0x7b,(byte)0x0b,(byte)0x6c,(byte)0x0b,(byte)0x5d,(byte)0x0b,(byte)0x5e,(byte)0x0b,(byte)0x4f,(byte)0x0b,
        (byte)0x40,(byte)0x0b,(byte)0x31,(byte)0x0b,(byte)0x22,(byte)0x0b,(byte)0x14,(byte)0x0b,(byte)0x05,(byte)0x0b,
        (byte)0xf7,(byte)0x0a,(byte)0xe8,(byte)0x0a,(byte)0xda,(byte)0x0a,(byte)0xcc,(byte)0x0a,(byte)0xbe,(byte)0x0a,
        (byte)0xb0,(byte)0x0a,(byte)0xa2,(byte)0x0a,(byte)0x94,(byte)0x0a,(byte)0x86,(byte)0x0a,(byte)0x79,(byte)0x0a,
        (byte)0x6b,(byte)0x0a,(byte)0x5d,(byte)0x0a,(byte)0x5d,(byte)0x0a,(byte)0x50,(byte)0x0a,(byte)0x42,(byte)0x0a,
        (byte)0x35,(byte)0x0a,(byte)0x27,(byte)0x0a,(byte)0x1a,(byte)0x0a,(byte)0x0d,(byte)0x0a,(byte)0x00,(byte)0x0a,
        (byte)0xf3,(byte)0x09,(byte)0xe6,(byte)0x09,(byte)0xd9,(byte)0x09,(byte)0xcd,(byte)0x09,(byte)0xc0,(byte)0x09,
        (byte)0xb3,(byte)0x09,(byte)0xa7,(byte)0x09,(byte)0xa0,(byte)0x09,(byte)0x93,(byte)0x09,(byte)0x87,(byte)0x09,
        (byte)0x7b,(byte)0x09,(byte)0x6e,(byte)0x09,(byte)0x62,(byte)0x09,(byte)0x56,(byte)0x09,(byte)0x4a,(byte)0x09,
        (byte)0x3e,(byte)0x09,(byte)0x32,(byte)0x09,(byte)0x27,(byte)0x09,(byte)0x1b,(byte)0x09,(byte)0x0f,(byte)0x09,
        (byte)0x03,(byte)0x09,(byte)0xf8,(byte)0x08,(byte)0xec,(byte)0x08,(byte)0xe1,(byte)0x08,(byte)0xd6,(byte)0x08,
    };
    /**
     * 将收到的字节流从0xc0处开始切断,已验证
     * @param input 需要处理的字节流
     * @return 处理后的字节流
     */
    public static byte[] cutPackageByTail(byte [] input){
        int len = 0;
        int i;
        for (i = 1; i < input.length; i++){   
            if(input[i] == (byte)0xc0){
                len = i + 1;
                break;
            }
        }        
        byte[]output = new byte[len];
        System.arraycopy(input, 0, output, 0, len);
        return output;
    }
    /**
     * 将接收到的数据包进行转义,已验证
     * @param input 需要处理的字节流
     * @return 处理后的字节流
     */    
    public static byte[] transferDBtoCommon(byte [] input){
        boolean dataOfTropeEnbled = false;
        int i,len;
        for (i = 0,len = 0; i < input.length; i++) {   
            byte ch = input[i];
            if(dataOfTropeEnbled){
                switch(ch){
                    case (byte)0x01:ch = (byte)0x0d;break;
                    case (byte)0x02:ch = (byte)0x0a;break;
                    case (byte)0x03:ch = (byte)0x1a;break;
                    case (byte)0x04:ch = (byte)0xdb;break;
                    case (byte)0x05:ch = (byte)0xc0;break;
                    case (byte)0x06:ch = (byte)0xc1;break;
                }
                dataOfTropeEnbled = false;
                input[len] = (byte)ch;
                len++;
            }else{
                if(ch == (byte)0xdb){
                    dataOfTropeEnbled = true;
                }else{
                    input[len] = (byte)ch;
                    len++;
                }
            }
        }
        byte[]output = new byte[len];
        System.arraycopy(input, 0, output, 0, len);
        return output;
    }
    public static byte[] toGetDBEscapeByte(byte [] DataBuf,int length){		
        byte[] DataBufTemp = new byte[300];
        int j = 1;
        DataBufTemp[0] = (byte)0xc0;
        for(int i = 1;i <= length;i ++){
            switch(DataBuf[i]){
                case((byte)0x1a):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x03;
                                j++;
                                break;
                case((byte)0xdb):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x04;
                                j++;
                                break;
                case((byte)0x0d):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x01;
                                j++;
                                break;
                case((byte)0x0a):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x02;
                                j++;
                                break;
                case((byte)0xc1):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x06;
                                j++;
                                break;
                case((byte)0xc0):DataBufTemp[j] = (byte)0xdb;
                                j++;
                                DataBufTemp[j] = (byte)0x05;
                                j++;
                                break;
                default:	DataBufTemp[j] = DataBuf[i];
                                j++;
                                break;
            }
        }
        DataBufTemp[j] = (byte)0xc0;
        j++;
        byte[] DataBufOfSend = new byte[j];
        System.arraycopy(DataBufTemp, 0, DataBufOfSend, 0, j);
        return DataBufOfSend;
    }
    /**
     * 对长为length的数据包进行解密处理,头和尾不解密固定为0xC0
     * @param buffer 需要解密的字节流
     * @return 解密后的结果
     */
    public static byte[] encryption(byte[] buffer){
        byte[]buff = null;
        if(buffer.length > 2) {
            buff = new byte[buffer.length];
            buff[0] = buffer[0];
            buff[buffer.length - 1] = buffer[buffer.length - 1];
            for(int i = 1; i <= buffer.length - 2;i++){
                buff[i] = (byte) (buffer[i]^KEY[i - 1]);
            }
        }
        return buff;
    }
    /**
     * 加密
     * @param DataBuf 需加密字节流
     * @param length 加密范围
     */
    public static void decode(byte [] DataBuf,int length){
        for(int i = 1;i<=length;i++){
            DataBuf[i] ^= (byte)KEY[i - 1];
        }
    }
    /**
     * 检查数据报校验和
     * @param input 需校验字节流数组
     * @return 符合校验规则或否
     */
    public static boolean recieveCheckData(byte[] input) {
        byte checkData = 0;
        for(int i = 1;i < input.length - 2;i++) {
            checkData ^= input[i];
        }
        if(checkData == input[input.length - 2]) {
            return true;
        }else{
            String hex = Integer.toHexString(checkData & 0xFF); 
            System.out.println("" + hex.toUpperCase() + "");
            return false;
        }
    }
    /**
     * 校验
     * @param DataBuf 需校验数据包
     * @param length 校验范围 0-length
     * @return 校验和字节
     */
    public static byte checkData(byte [] DataBuf,int length){
        byte CheckCode = 0;
        for(int i = 1;i<length;i++){
            CheckCode ^= (byte)DataBuf[i];
        }
        return (byte)CheckCode;
    }
    /**
     * 从字节数组中截取一段
     * @param b 需从中截取的数组
     * @param j 截取到j个元素为止
     * @param k 从第k个元素开始截取
     * @return 截取出来的数组
     */
    public static byte[] cutOutByte(byte[] b,int j,int k){  
        if(b.length==0 || j==0){  
            return null;  
        }  
        byte[] bjq = new byte[j];  
        for(int i = 0,l = k; l<j;l++){  
            bjq[i]=b[l];
            i++;
        }  
        return bjq;  
    }
}
