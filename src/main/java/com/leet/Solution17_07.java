package com.leet;

import java.util.HashMap;
import java.util.Map;

/**
 * @link: https://leetcode-cn.com/problems/baby-names-lcci/
 */
public class Solution17_07 {
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        Map<String, Integer> nameMap = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String[] split = name.split("\\(");
            Integer num = Integer.valueOf(split[1].substring(0, split[1].length() - 1));
            nameMap.put(split[0], num);
        }
        Map<String, String> synonymMap = new HashMap<>();
        for (int i = 0; i < synonyms.length; i++) {
            String synonym = synonyms[i];
            int idx = synonym.indexOf(',');
            String name1 = synonym.substring(1, idx);
            String name2 = synonym.substring(idx + 1, synonym.length() - 1);

            //找name1的父节点
            while (synonymMap.containsKey(name1)) {
                name1 = synonymMap.get(name1);
            }
            //找name2的父节点
            while (synonymMap.containsKey(name2)) {
                name2 = synonymMap.get(name2);
            }
            //不是一个父节点时，需要合并
            //这里要注意，找到字典序小的为父节点
            //synonymMap中key是子节点，value是父节点
            //合并后更新name父节点的value，并删除子节点的key
            if (!name1.equals(name2)) {
                String father = name1.compareTo(name2) < 0 ? name1 : name2;
                String child = name1.compareTo(name2) > 0 ? name1 : name2;
                synonymMap.put(child, father);
                Integer fatherNum = nameMap.getOrDefault(father, 0);
                Integer childNum = nameMap.getOrDefault(child, 0);
                nameMap.put(father, fatherNum + childNum);
                nameMap.remove(child);
            }
        }
        String[] res = new String[nameMap.size()];
        int index = 0;
        for (String name : nameMap.keySet()) {
            res[index++] = name + '(' + nameMap.get(name) + ')';
        }
        return res;
    }
}
