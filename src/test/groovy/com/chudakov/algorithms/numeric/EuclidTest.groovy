package com.chudakov.algorithms.numeric

import org.apache.commons.lang3.tuple.ImmutableTriple
import spock.lang.Specification

class EuclidTest extends Specification {
  def "test gcd"() {
    expect:
    Euclid.gcd(m, n) == d
    where:
    m  | n  | d
    1  | 1  | 1
    4  | 6  | 2
    12 | 15 | 3
  }

  def "test extended gcd"() {
    expect:
    Euclid.gcdExtended(m, n) == triple
    where:
    m    | n   | triple
    1769 | 551 | new ImmutableTriple<>(5, -16, 29)
  }
}
