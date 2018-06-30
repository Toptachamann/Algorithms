package com.chudakov.algorithms.numeric;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Euclid {
  public static int gcd(int m, int n) {
    int r;
    while ((r = m % n) != 0) {
      m = n;
      n = r;
    }
    return n;
  }

  public static Triple<Integer, Integer, Integer> gcdExtended(int m, int n) {
    int aFirst, bFirst, aSecond, bSecond, c, d, q, r, t;
    aFirst = bSecond = 1;
    aSecond = bFirst = 0;
    c = m;
    d = n;
    while ((r = c % d) != 0) {
      q = (c - r) / d;
      c = d;
      d = r;

      t = aFirst;
      aFirst = aSecond;
      aSecond = t - q * aSecond;

      t = bFirst;
      bFirst = bSecond;
      bSecond = t - q * bSecond;
    }
    return new ImmutableTriple<>(aSecond, bSecond, d);
  }
}
