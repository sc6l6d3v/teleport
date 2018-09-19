package com.iscsi

/**
  * Path contains existing nodes traversed from adjacency matrix and is a unique set from origin node but for origin
  * @param curNodes
  */
class Path(curNodes: IndexedSeq[Int]) {
  def pathLength: Int = curNodes.size

  def origin: Int = curNodes.head

  def destination: Int = curNodes.last

  def inPath(node: Int): Boolean = curNodes.contains(node)

  /**
    * Can node extension occur?
    * @param node unique index
    * @return
    */
  def willGrow(node: Int): Boolean = if (!inPath(node) || (node == curNodes.head && pathLength > 2)) true else false

  /**
    * Preserve unique path unless we encounter our origin node
    * @param node unique index of each node
    * @return
    */
  def extendPath(node: Int): Path = if (!inPath(node) || (node == curNodes.head && pathLength > 2)) new Path(curNodes :+ node) else this

  /**
    * Loop is a path greater than 2 nodes whose final node is same as origin
    * @return
    */
  def isCycle: Boolean = if (pathLength > 2) curNodes.head == curNodes.last else false

  /**
    * check if path exists
    * @param origin first node in path
    * @param destination last one
    * @return
    */
  def hasPath(origin: Int, destination: Int): Boolean = curNodes.head == origin && curNodes.last == destination
}
