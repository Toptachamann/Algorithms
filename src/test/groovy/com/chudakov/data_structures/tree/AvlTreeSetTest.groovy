package com.chudakov.data_structures.tree

import spock.lang.Specification
import spock.lang.Timeout

class AvlTreeSetTest extends Specification {

  def "smoke test"() {
    def set = new AvlTreeSet<>()
    set.add(1)
    expect:
    set.contains(1)
    set.size() == 1
    isSortedWithoutDuplicates(set.getSorted())
  }

  @Timeout(value = 1000)
  def "test tree 1"() {
    def set = new AvlTreeSet<>()
    set.add(1)
    set.add(2)
    set.add(3)
    expect:
    set.contains(1)
    set.contains(2)
    set.contains(3)
    !set.contains(4)
    set.size() == 3
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 2"() {
    def set = new AvlTreeSet<>()
    set.add(3)
    set.remove(10)
    set.remove(10)
    set.remove(5)
    set.remove(3)
    expect:
    set.size() == 0
    !set.contains(3)
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 3"() {
    def set = new AvlTreeSet<>()
    set.remove(10)
    set.add(6)
    set.add(0)
    set.add(1)
    set.add(0)
    expect:
    set.contains(0)
    set.size() == 3
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 4"() {
    def set = new AvlTreeSet<>()
    set.add(6)
    set.add(10)
    set.add(2)
    set.add(3)
    set.remove(10)
    set.remove(4)
    set.remove(2)
    set.add(0)
    expect:
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 5"() {
    def set = new AvlTreeSet<>()
    set.add(5)
    set.add(6)
    set.add(1)
    set.remove(9)
    set.add(3)
    set.add(10)
    set.remove(9)
    set.remove(5)
    expect:
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 6"() {
    def set = new AvlTreeSet<>()
    Object[][] queries = [['a', 1], ['d', 4], ['a', 8], ['a', 10], ['a', 1], ['a', 0]]
    for (Object[] query : queries) {
      int i = (Integer) query[1]
      if (query[0] == 'a') {
        set.add(i)
      } else {
        set.remove(i)
      }
    }
    expect:
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 7"() {
    def set = new AvlTreeSet<>()
    Object[][] queries = [['d', 1], ['a', 10], ['a', 7], ['d', 8], ['a', 8], ['a', 3], ['d', 4], ['d', 1],
                          ['d', 6], ['a', 1], ['a', 3], ['a', 9], ['d', 10], ['d', 0], ['d', 10],
                          ['a', 10], ['d', 4], ['a', 0], ['a', 2], ['a', 3], ['a', 3], ['d', 4],
                          ['d', 8], ['d', 8], ['d', 9], ['d', 7], ['a', 5], ['d', 3], ['a', 10],
                          ['d', 9]]
    executeQueries(queries)
    expect:
    isSortedWithoutDuplicates(set.getSorted())
  }

  def "test case 8"() {
    def set = new AvlTreeSet<>()
    set.add(1)
    set.remove(1)
    expect:
    set.size() == 0
  }

  def "test case 9"() {
    def set = new AvlTreeSet()
    set.add(1)
    set.add(3)
    set.add(5)
    set.add(7)
    set.add(6)
    expect:
    set.prev(0) == null
    set.prev(1) == null
    set.prev(2) == 1
    set.prev(3) == 1
    set.prev(4) == 3
    set.prev(5) == 3
    set.prev(6) == 5
    set.prev(7) == 6
    set.prev(8) == 7
    set.next(0) == 1
    set.next(1) == 3
    set.next(2) == 3
    set.next(3) == 5
    set.next(4) == 5
    set.next(5) == 6
    set.next(6) == 7
    set.next(7) == null
    set.next(8) == null
    set.orderStatistics(0) == null
    set.orderStatistics(1) == 1
    set.orderStatistics(2) == 3
    set.orderStatistics(3) == 5
    set.orderStatistics(4) == 6
    set.orderStatistics(5) == 7
    set.orderStatistics(6) == null
  }

  def "test case 10"() {
    def set = new AvlTreeSet<>()
    Object[][] arr = [['n', 26],['o', 23],['a', 48],['p', 2],['n', 41],['a', 31],['d', 30],['d', 30],
                      ['o', 9],['d', 25],['a', 12],['c', 5],['n', 33],['c', 36],['c', 31],
                      ['o', 34],['a', 33],['a', 21],['c', 46],['a', 27],['c', 3],['a', 31],
                      ['n', 1],['d', 10],['a', 27],['n', 23],['c', 46],['o', 6],['o', 39],
                      ['p', 46]]
    executeQueries(arr, set)
    expect:
    isSortedWithoutDuplicates(set.getSorted())

  }

  def "test case 11"() {}

  def "test case 12"() {}

  def isSortedWithoutDuplicates(List<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i) <= list.get(i - 1)) {
        return false
      }
    }
    return true
  }

  def executeQueries(Object[][] queries, AvlTreeSet<Integer> set) {
    for (Object[] query : queries) {
      int i = (Integer) query[1]
      if(i == 21){
        int x = 10
      }
      switch (query[0]) {
        case 'a':
          set.add(i)
          break
        case 'd':
          set.remove(i)
          break
        case 'c':
          set.contains(i)
          break
        case 'o':
          set.orderStatistics(i)
          break
        case 'n':
          set.next(i)
          break
        case 'p':
          set.prev(i)
          break
      }
      if(!set.checkIsAvl()){
        println query
        throw new RuntimeException()
      }
    }
  }


}
