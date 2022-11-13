package com.chinatelecom.rpaccbackend.common.util;


import com.alibaba.fastjson.JSON;

import java.util.*;

public class StringSplit {

    public static ArrayList<String> splitStringByPoint(String input, ArrayList<Integer> point){
        // 暂时默认点数量 > 1
        ArrayList<String> result = new ArrayList<>();
        // TODO : point列表为空
        for (int i = 0; i < point.size() - 1; i++){
            result.add(input.substring(point.get(i), point.get(i + 1)));
        }
        result.add(input.substring(point.get(point.size() - 1)));
        return result;
    }

    public static JSON split(String input, List<String> targetList){
        String redundantString = ":： .。,";
        Map<String, Object> dict= new HashMap<>();
        ArrayList<Integer> pointArray = new ArrayList<>();
        // 1. 查找并打点
        for (String target : targetList){
            pointArray.add(input.indexOf(target));
        }
        pointArray.sort(Comparator.naturalOrder());
//        System.out.println("pointArray : "+pointArray);
        // 2. 根据打点分割字符串
        ArrayList<String> subStringList = splitStringByPoint(input, pointArray);
//        System.out.println(subStringList);
        // 生成多余字符集集合
        HashSet<Character> redundantSet = new HashSet<>();
        for(int i = 0; i < redundantString.length(); i++){
            redundantSet.add(redundantString.charAt(i));
        }
        // 3. 生成必要的json
        for(String subString : subStringList){
            for(String target : targetList){
                if(subString.contains(target)){
                    //从头开始去除不必要字符
                    int begin = target.length();
                    for(; begin < subString.length(); begin++){
                        if(!redundantSet.contains(subString.charAt(begin))){
                            break;
                        }
                    }
                    //从尾开始去除不必要字符
                    int end = subString.length();
                    for(; end > target.length(); end--){
                        if(!redundantSet.contains(subString.charAt(end - 1))){
                            break;
                        }
                    }
                    dict.put(target, subString.substring(begin, end));
                    break;
                }
            }
        }
        return (JSON) JSON.toJSON(dict);
    }
}
