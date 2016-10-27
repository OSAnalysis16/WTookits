package com.util;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/25.
 */

public class MapComparator {

    //value must be int or double or float
    public static Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list,
//                (o1, o2) -> {
//            return -((Comparable) ((Map.Entry) o1).getValue())
//                        .compareTo(((Map.Entry) o2).getValue());
                new Comparator() {
            public int compare(Object o1, Object o2) {
                return -((Comparable) ((Map.Entry) o1).getValue())
                        .compareTo(((Map.Entry) o2).getValue());
            }
        });
        Map result = new LinkedHashMap();

        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }

}
