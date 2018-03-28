package string.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

  public static ArrayList<Integer> quickSearch(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    Map<Character, Integer> badCharacter = badCharacter(getAlphabet(text, pattern), pattern);
    int m = pattern.length();
    for (int i = 0; i <= text.length() - pattern.length(); ) {
      int j = m - 1;
      for (; j >= 0; j--) {
        if (pattern.charAt(j) != text.charAt(i + j)) {
          break;
        }
      }
      if (j == -1) {
        matched.add(i);
      }
      i += badCharacter.get(text.charAt(i + m - 1));
    }
    return matched;
  }

  public static ArrayList<Integer> tunedBoyerMoore(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    Map<Character, Integer> badCharacter = badCharacter(getAlphabet(text, pattern), pattern);
    int m = pattern.length();
    int n = text.length();
    int shift = badCharacter.get(pattern.charAt(m - 1));
    badCharacter.put(pattern.charAt(m - 1), 0);
    int i = 0;
    while (i < n) {
      int k = badCharacter.get(text.charAt(i + m - 1));
      while (k > 0) {
        i += k;
        k = badCharacter.get(text.charAt(i + m - 1));
        i += k;
        k = badCharacter.get(text.charAt(i + m - 1));
        i += k;
        k = badCharacter.get(text.charAt(i + m - 1));
      }
      int j = 0;
      for (; j < m; j++) {
        if (pattern.charAt(j) != text.charAt(i + j)) {
          break;
        }
      }
      if (j == m) {
        matched.add(i);
      }
      i += shift;
    }
    return matched;
  }

  private static Set<Character> getAlphabet(String text, String pattern) {
    Set<Character> alphabet = new HashSet<>();
    for (int i = 0; i < text.length(); i++) {
      alphabet.add(text.charAt(i));
    }
    for (int i = 0; i < pattern.length(); i++) {
      alphabet.add(pattern.charAt(i));
    }
    return alphabet;
  }

  private static Map<Character, Integer> badCharacter(Set<Character> alphabet, String pattern) {
    Map<Character, Integer> map = new HashMap<>();
    int m = pattern.length();
    for (Character c : alphabet) {
      map.put(c, m);
    }
    for (int i = 0; i < m - 1; i++) {
      map.put(pattern.charAt(i), m - i - 1);
    }
    return map;
  }
}
