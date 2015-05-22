package com.demo.qa.utils;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Utils {
    
    private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    
    public static Map<String, RecordHandler> sortmap(Map<String, RecordHandler> mp) {
        
        if (mp == null || mp.isEmpty()) {
            return null;
        }
        Map<String, RecordHandler> sortMap = new TreeMap<String, RecordHandler>(new MapKeyComparator());
        sortMap.putAll(mp);
        return sortMap;
    }
    
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
    
    public static void main(String[] args) {
//        Map<String, String> map = new TreeMap<String, String>();  
//        map.put("KFC", "kfc");  
//        map.put("WNBA", "wnba");  
//        map.put("NBA", "nba");  
//        map.put("CBA", "cba");  
//        Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序  
//        for (Map.Entry<String, String> entry : resultMap.entrySet()) {  
//            System.out.println(entry.getKey() + " " + entry.getValue());  
//        }  
        
        HashMap<String,String> map1 = new HashMap<String, String>();
        map1.put("KFC", "kfc");
        map1.put("WNBA", "wnba");
        map1.put("NBA", "nba");
        map1.put("CBA", "cba");
        HashMap<String,String> map2 = new HashMap<String, String>();
        map2.put("KFC", "kfc");
        map2.put("123", "wnba");
        map2.put("fds21", "nba");
        map2.put("12jh", "cba");
        
//        Map<String, String> resultMap3 = sortMapByKey(map2);
//        
//        for (Map.Entry<String, String> entry3 : map2.entrySet()){
//            
//            System.out.println(entry3.getKey()+""+entry3.getValue()); 
//            
//        }
//        
//        for (Map.Entry<String, String> entry4 : resultMap3.entrySet()){
//            
//            System.out.println(entry4.getKey()+""+entry4.getValue()); 
//            
//        }
        
        Map<String, RecordHandler> mp= new HashMap<String, RecordHandler>();
        
        
        RecordHandler rh1 = new RecordHandler(map1);
        RecordHandler rh2 = new RecordHandler(map2);
        
        mp.put("ffff", rh1);
        
        mp.put("dddd", rh2);
        
        Map<String, RecordHandler> resultMap = sortmap(mp);   
        
        //按Key进行排序  
      for (Map.Entry<String, RecordHandler> entry : resultMap.entrySet()) {  
          
          
          
          System.out.println(entry.getKey() + " ");  
          
          RecordHandler resultMapMap = (RecordHandler) entry.getValue();
          
          Map<String,String> resultmp = resultMapMap.get_map();
          
          resultmp = sortMapByKey(resultmp);   
          
          for (Map.Entry<String, String> entry2 : resultmp.entrySet()){
              
              System.out.println(entry2.getKey()+""+entry2.getValue()); 
              
          }
                
          
      } 
        
        
        //Map<String, RecordHandler> resultMap = sortMapByKey(rh);
        
        
        
    }
}

// 比较器类
class MapKeyComparator implements Comparator<String> {
    
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
    
}
