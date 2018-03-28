package string.matching

import spock.lang.Specification
import spock.lang.Unroll

class StringMatcherTest extends Specification {

  @Unroll
  def "test naive matcher"() {
    def result = StringMatcher.naive(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "test KMP"() {
    def result = StringMatcher.KMP(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "text quick search"(){
    def result = StringMatcher.quickSearch(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }

  @Unroll
  def "text tuned Boyer-Moore"(){
    def result = StringMatcher.tunedBoyerMoore(text, pattern)
    expect:
    result == ans
    where:
    text        | pattern | ans
    "abbaabbab" | "ab"    | [0, 4, 7]
    "GAGAVAGAV" | "GAV"   | [2, 6]
  }
}
