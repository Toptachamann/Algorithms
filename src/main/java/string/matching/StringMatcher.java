package string.matching;

import java.util.ArrayList;
import java.util.Collections;

public class StringMatcher {

  static ArrayList<Integer> naive(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    for (int i = 0; i <= text.length() - pattern.length(); i++) {
      int j = 0;
      for (; j < pattern.length(); j++) {
        if (text.charAt(i + j) != pattern.charAt(j)) {
          break;
        }
      }
      if (j == pattern.length()) {
        matched.add(i);
      }
    }
    return matched;
  }

  public static ArrayList<Integer> KMP(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    ArrayList<Integer> prefixFunction = computePrefixFunction(pattern);
    for (int i = 0, q = -1; i < text.length(); i++) {
      while (q > -1 && pattern.charAt(q + 1) != text.charAt(i)) {
        q = prefixFunction.get(q);
      }
      if (pattern.charAt(q + 1) == text.charAt(i)) {
        ++q;
      }
      if (q == pattern.length() - 1) {
        matched.add(i - q);
        q = prefixFunction.get(q);
      }
    }
    return matched;
  }

  public static ArrayList<Integer> computePrefixFunction(String str) {
    ArrayList<Integer> prefixFunction = new ArrayList<>(Collections.singletonList(-1));
    for (int q = 1, k = -1; q < str.length(); q++) {
      while (k > -1 && str.charAt(k + 1) == str.charAt(q)) {
        k = prefixFunction.get(k);
      }
      if (str.charAt(k + 1) == str.charAt(q)) {
        ++k;
      }
      prefixFunction.add(k);
    }
    return prefixFunction;
  }
}
