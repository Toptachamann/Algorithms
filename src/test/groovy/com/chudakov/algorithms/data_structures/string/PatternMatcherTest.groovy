package com.chudakov.algorithms.data_structures.string

import spock.lang.Specification
import spock.lang.Unroll

class PatternMatcherTest extends Specification {

  @Unroll
  def "test naive matcher"() {
    def result = PatternMatcher.naive(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test KMP"() {
    def result = PatternMatcher.KMP(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "text quick search"(){
    def result = PatternMatcher.quickSearch(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test tuned Boyer-Moore"(){
    def result = PatternMatcher.tunedBoyerMoore(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test Shift-And"(){
    def result = PatternMatcher.shiftAnd(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test Shift-Or"(){
    def result = PatternMatcher.shiftOr(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test sunday"(){
    def result = PatternMatcher.sunday(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }
}
