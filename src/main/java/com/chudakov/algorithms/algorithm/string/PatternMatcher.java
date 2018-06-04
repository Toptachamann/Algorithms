package com.chudakov.algorithms.algorithm.string;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PatternMatcher {

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
    Map<Character, Integer> badCharacter = quickSearchBadCharacter(getAlphabet(text, pattern), pattern);
    int n = text.length();
    int m = pattern.length();
    for (int i = 0; i <= text.length() - pattern.length(); ) {
      if (pattern.equals(text.substring(i, i + m))) {
        matched.add(i);
      }
      if (i + m < n) {
        i += badCharacter.get(text.charAt(i + m));
      } else {
        break;
      }
    }
    return matched;
  }

  public static ArrayList<Integer> sunday(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    Map<Character, Integer> badCharacter = badCharacter(getAlphabet(text, pattern), pattern);
    int n = text.length();
    int m = pattern.length();
    int i = 0;
    while (i < n - m + 1) {
      if (pattern.equals(text.substring(i, i + m))) {
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
      }
      if (pattern.equals(text.substring(i, i + m))) {
        matched.add(i);
      }
      i += shift;
    }
    return matched;
  }

  public static ArrayList<Integer> shiftAnd(String text, String pattern) {
    Map<Character, BitSet> table = getShiftAndTable(getAlphabet(text, pattern), pattern);
    ArrayList<Integer> matched = new ArrayList<>();
    int m = pattern.length();
    BitSet d = new BitSet(m);
    for (int i = 0; i < text.length(); i++) {
      d = d.get(1, m);
      d.set(m - 1);
      d.and(table.get(text.charAt(i)));
      if (d.get(0)) {
        matched.add(i - m + 1);
      }
    }
    return matched;
  }

  public static ArrayList<Integer> shiftOr(String text, String pattern) {
    ArrayList<Integer> matched = new ArrayList<>();
    Map<Character, BitSet> table = getShiftOrTable(getAlphabet(text, pattern), pattern);
    int m = pattern.length();
    BitSet d = new BitSet(m);
    d.set(0, m);
    for (int i = 0; i < text.length(); i++) {
      d = d.get(1, m);
      d.or(table.get(text.charAt(i)));
      if (!d.get(0)) {
        matched.add(i - m + 1);
      }
    }
    return matched;
  }

  public static ArrayList<Integer> berryRanvingran(String text, String pattern){
    ArrayList<Integer> matched = new ArrayList<>();
    return matched;
  }

  private static Map<Character, BitSet> getShiftOrTable(Set<Character> alphabet, String pattern) {
    Map<Character, BitSet> table = new HashMap<>();
    int m = pattern.length();
    for (Character c : alphabet) {
      BitSet bitSet = new BitSet(m);
      bitSet.set(0, m);
      table.put(c, bitSet);
    }
    for (int i = 0; i < pattern.length(); i++) {
      table.get(pattern.charAt(i)).clear(m - i - 1);
    }
    return table;
  }

  private static Map<Character, BitSet> getShiftAndTable(Set<Character> alphabet, String pattern) {
    Map<Character, BitSet> table = new HashMap<>();
    int m = pattern.length();
    for (Character c : alphabet) {
      table.put(c, new BitSet(m));
    }
    for (int i = 0; i < pattern.length(); i++) {
      table.get(pattern.charAt(i)).set(m - i - 1);
    }
    return table;
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

  private static Map<Character, Integer> quickSearchBadCharacter(Set<Character> alphabet, String pattern) {
    Map<Character, Integer> map = new HashMap<>();
    int m = pattern.length();
    for (Character c : alphabet) {
      map.put(c, m + 1);
    }
    for (int i = 0; i < m; i++) {
      map.put(pattern.charAt(i), m - i);
    }
    return map;
  }


}
