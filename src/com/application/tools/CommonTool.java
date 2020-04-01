package com.application.tools;

import com.application.deviceBean.RequestDeviceBean;
import com.application.view.MainJFram;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author Redaddend
 */
public class CommonTool {
    
    public static RequestDeviceBean first = null;
    public static RequestDeviceBean second = null;
    public static float firstTemp = 2f;
    public static float secondTemp = 2f;
    public static RequestDeviceBean rdb = null;
    public static Iterator itr = null;
    public static boolean checkJTextFieldNumber(JTextField jtf){
        if(jtf.getText().toCharArray().length == 10) {            
            char[] c = jtf.getText().toCharArray();
            for (int i = 0;i < c.length;i ++) {                
                if(!(c[i] >='0' && c[i] <='9')) {                    
                    return false;                  
                }                
            }            
        }else{            
            return false;
        }
        return true;
    }
    public static void FitTableColumns(JTable myTable){  
        JTableHeader header = myTable.getTableHeader();  
        int rowCount = myTable.getRowCount();  

        Enumeration columns = myTable.getColumnModel().getColumns();  
        while(columns.hasMoreElements()){  
            TableColumn column = (TableColumn)columns.nextElement();  
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());  
            int width = (int)myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier() 
                            , false, false, -1, col).getPreferredSize().getWidth();  
            for(int row = 0; row<rowCount; row++){  
                int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
                        myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();  
                width = Math.max(width, preferedWidth);  
            }  
            header.setResizingColumn(column); // 此行很重要  
            column.setWidth(width+myTable.getIntercellSpacing().width);  
        }  
    }
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public static String fliter(String s){
        String des = null;
        if (s!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(s);
            des = m.replaceAll("");
        }
        return des;
    }
    public static synchronized LinkedList<RequestDeviceBean> sortArrayList(LinkedList<RequestDeviceBean> devices,float min,float max,float lowBattery) {
        LinkedList<RequestDeviceBean> temp = devices;
        if(devices.size()>2){
            MainJFram.errorDevicesNumber = 0;
            MainJFram.standardDeviceIsReady = true;
            rdb = null;
            itr = null;
            //取标准设备
            first = temp.get(0);
            second = temp.get(1);            
            if(hasNull(first.getTempLinkedList())){
                first.setMessage("Not ready");
                MainJFram.standardDeviceIsReady = false;
            }else{
                firstTemp = getAverage(first.getTempLinkedList());
                first.setMessage(String.valueOf(firstTemp));
            }
            if(hasNull(second.getTempLinkedList())){
                second.setMessage("Not ready");
                MainJFram.standardDeviceIsReady = false;
            }else{
                secondTemp = getAverage(second.getTempLinkedList());
                second.setMessage(String.valueOf(secondTemp));
            }
            //存标准设备
            temp.set(0, first);
            temp.set(1, second);
            //获得最终标准温度
            float standardTemp = (firstTemp + secondTemp)/2;
            float average;
            int count = 2;
            for(int i = 2;i<temp.size();i++){
                rdb = temp.getLast();
                temp.pollLast();
                average = getAverage(rdb.getTempLinkedList());
                if(rdb.getVoltage() < lowBattery){
                    rdb.setMessage("lowBattery");
                    MainJFram.errorDevicesNumber ++;
                    temp.add(2, rdb);
                    count ++;
                }else if(Math.abs(average - standardTemp) > max){
                    rdb.setMessage("outOfRange");
                    MainJFram.errorDevicesNumber ++;
                    temp.add(2, rdb);
                    count ++;
                }else if(hasNull(rdb.getTempLinkedList())){
                    rdb.setMessage("needMore");
                    temp.add(2, rdb);
                    count ++;
                }else if(Math.abs(average - standardTemp) < min){
                    rdb.setMessage("inRange");
                    temp.add(2, rdb);
                    count ++;
                }else{
                    rdb.setMessage(String.valueOf((int)((standardTemp - average)*10)));
                    temp.add(count, rdb);
                }
            }
            rdb = null;
        }
//        for(int j = 0;j<MainJFram.devices.size();j++){
//            System.out.println(MainJFram.devices.get(j).toString());
//        }
        return temp;
    }
    public static float getAverage(LinkedList<Float> ll){
        float average = 0;
        int count = 0;
        for(int i = 0;i<ll.size();i++){
            if(ll.get(i) != null){
                average += ll.get(i);
                count ++;
            }
        }
        if(average != 0){
            average /= count;
        }
        return average;
    }
    public static boolean hasNull(LinkedList<Float> ll){
        boolean b = false;
        for(int i = 0;i<ll.size();i++){
            if(ll.get(i) == null){
                b = true;
            }
        }
        return b;
    }
    public static int statisticsOfDone(LinkedList<RequestDeviceBean> devices){
        int a = 0;
        RequestDeviceBean rdbTemp = null;
        if(devices.size()>2){
            for(int i = 2;i < devices.size();i++){
                rdbTemp = devices.get(i);
                if(rdbTemp.getMessage().equals("done")){
                    a ++;
                }
            }
        }
        return a;
    }
}
