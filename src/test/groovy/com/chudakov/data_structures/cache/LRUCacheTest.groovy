package com.chudakov.data_structures.cache

import spock.lang.Specification

class LRUCacheTest extends Specification {

  def "test LRU cache 1"() {
    def cache = new LRUCache(1)
    expect:
    cache.refer(1) == -1
    cache.refer(1) == 1
    cache.refer(2) == -1
    cache.refer(2) == 2
  }

  def "test LRU cache 2"() {
    def cache = new LRUCache(2)
    expect:
    cache.refer(1) == -1
    cache.refer(2) == -1
    cache.refer(1) == 1
    cache.refer(3) == -1
    cache.refer(2) == -1
  }
}
