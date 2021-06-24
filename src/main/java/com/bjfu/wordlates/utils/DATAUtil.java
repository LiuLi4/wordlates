package com.bjfu.wordlates.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DATAUtil {
    //生成当天日期
    public static String newDate(Date time){
        String format=new SimpleDateFormat("yyyy年-MM月").format(time);
        return format;
    }

    //列表去重
    public static List newList(List o){
        if(o!=null) {
            return (List) o.stream().distinct().collect(Collectors.toList());
        }else {
            return null;
        }
    }

    //返回列表字符串,去空格和[],太多只输出前9个
    public static String newString(List o){
        List i = newList(o);
        if(i.size()>9) {
            i = i.subList(0, 9);
        }
        return i.toString().replaceAll("\\s*", "").replaceAll("(?:\\[|null|\\]| +)", "");
    }

    //返回去重后列表长度
    public static int newSize(List o){
        List i=newList(o);
        return i.size();
    }

    //返回抽查不合格的商品的详细情况
    /*s商品名字，p检查项目，o是否合格
     * */
    public static Map unqualifiedMap(List s, List p,List o){
        Map i=new HashMap();
        int count=0;
        for(Object k:o){
            if(k.equals("不合格项\t")){
                i.put(s.get(count),p.get(count));
            }
            count++;
        }
        return i;
    }

    //返回map的key
    public static List<String> returnKey(Map o){
        List<String> returnResult = new LinkedList<String>();
        Set<Map.Entry<Integer, String>> eSet  =  o.entrySet();
        Iterator<Map.Entry<Integer, String>> it = eSet.iterator();
        while(it.hasNext()) {
            returnResult.add(String.valueOf(it.next().getKey()));
        }
        return returnResult;
    }

    //返回map的value
    public static List returnValue(Map o){
        List<Number> returnResult = new LinkedList<Number>();
        Set<Map.Entry<Integer, Number>> eSet  =  o.entrySet();
        Iterator<Map.Entry<Integer, Number>> it = eSet.iterator();
        while(it.hasNext()) {
            returnResult.add(it.next().getValue());
        }
        return returnResult;
    }

    //返回map的多个value
    public static List returnValues(Map o,int q){
        List<Number> returnResult = new LinkedList<Number>();
        Set<Map.Entry<Integer, List<Number>>> eSet  =  o.entrySet();
        Iterator<Map.Entry<Integer, List<Number>>> it = eSet.iterator();
        while(it.hasNext()) {
            returnResult.add(it.next().getValue().get(q));
        }
        return returnResult;
    }

    //计算合格率 o1是总数，o2是不合格数
    public static String culQuality(int o1,int o2){
        double i=(double)(o1-o2)/o1;
        DecimalFormat df1 = new DecimalFormat("#.00%");
        return df1.format(i);
    }

    //获取和大类相关的表格数据
    public static Map tableSort(List o1,List o2){
        int num1=1;//大类抽查次数
        int num2=0;//不合格次数
        int count=0;
        Map<String,List<Integer>> i=new HashMap();
        for(Object k:o1){
            if(i.containsKey(k)){
                List<Integer> temp=i.get(k);
                List<Integer> arrList = new ArrayList(temp);
                Integer t0= arrList.get(0)+1;
                arrList.remove(0);
                arrList.add(0,t0);
                if(o2.get(count).equals("不合格项\t")){
                    Integer t1= arrList.get(1)+1;
                    arrList.remove(1);
                    arrList.add(1,t1);
                }
                i.put((String) k,arrList);
            }else {
                if(o2.get(count).equals("不合格项\t")){
                    num2=1;
                }
                List<Integer> list=Arrays.asList(num1,num2);
                num2=0;
                i.put((String) k,list);
            }
            count++;
        }
        return i;
    }

    //获取和采样地点_2相关的图标数据
    public static Map chartSampling(List o){
        int num=1;
        Map<String,Integer> i=new HashMap();
        for(Object k:o){
            if(i.containsKey(k)){
                int temp=i.get(k);
                temp++;
                i.put((String) k,temp);
            }else{
                i.put((String) k,num);
            }
        }
        return i;
    }
}
