package com.inkenkun.x1.serialization.benchmark

trait Loop {
  
  val   n1k = 10 * 10 * 10
  val  n10k = 10 * 10 * 10 * 10
  val n100k = 10 * 10 * 10 * 10 * 10

  def loop1k( f: Int => Unit )   = 1 to   n1k foreach ( i => f(i) )
  def loop10k( f: Int => Unit )  = 1 to  n10k foreach ( i => f(i) )
  def loop100k( f: Int => Unit ) = 1 to n100k foreach ( i => f(i) )
}
