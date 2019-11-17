package com.dangtuan.algorithm.educative;

import java.util.*;

class NoRepeatSubstring {

  public static int findLength(String str) {
    char pre = 0;
    int end = 0;
    int max = 0;
    List<Character> arr = new ArrayList<>();
    for (end = 0; end < str.length(); end++) {
      arr.add(str.charAt(end));
      if (arr.size() - 1 >= 0 && arr.get(arr.size() - 1).equals(pre)) {
        max = Math.max(max, arr.size() - 1);
        arr.clear();
        arr.add(str.charAt(end));
      }
      pre = str.charAt(end);
    }
    return max;
  }
}
