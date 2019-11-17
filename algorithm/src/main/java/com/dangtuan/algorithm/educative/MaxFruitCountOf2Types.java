package com.dangtuan.algorithm.educative;

import java.util.*;

class MaxFruitCountOf2Types {

  public static int findLength(char[] arr) {
    int start = 0;
    int end = 0;
    int max = 0;
    Map<Character, Integer> fruits = new HashMap<>();
    for (end = 0; end < arr.length; end++) {
      fruits.put(arr[end], fruits.get(arr[end] + 1));
      if (fruits.size() > 2) {
        fruits.remove(arr[start]);
        start++;
      }
      max = Math.max(max, end - start + 1);
    }
    return max;
  }
}
