package com.iscsi

import scala.annotation.tailrec

trait Graph {
  /**
    * Adjacency list from edge pairs with first elt as key and list of values
    * @param edges list of edge pairs
    * @return map of edge keys with list of values
    */
  def adjacencyMatrix(edges: Seq[(Int, Int)]): Map[Int, IndexedSeq[Int]] =
    edges.
      groupBy(_._1).// use first edge as key
      mapValues{_.map{_._2}.toIndexedSeq}  // creating a list of values from second edge

  /**
    * Recursively build list of cities reachable in depth jumps
    * @param seedNodes key into adjMatrix
    * @param depth    recursion depth
    * @param adjMatrix  adjacency list
    * @return
    */
  def adjacencies(seedNodes: Int, depth: Int, adjMatrix: Map[Int, IndexedSeq[Int]]): Set[Int] = {

    @tailrec
    def adjRecurse(startnodes: Set[Int], depth: Int, nodes: Set[Int]): Set[Int] = depth match {
      case 0 =>
        startnodes
      case _ =>
        val newNodes = nodes ++ startnodes.flatMap { node => adjMatrix(node) }
        adjRecurse(newNodes, depth - 1, newNodes)
    }

    adjRecurse(Set(seedNodes), depth, Set.empty[Int])
  }

  /**
    * DFS from origin constructing paths to determine if cycle exists with any particular path
    * @param seedNodes origin node
    * @param adjMatrix adjacency list
    * @return
    */
  def loops(seedNodes: Int, adjMatrix: Map[Int, IndexedSeq[Int]]): Boolean = {

    @tailrec
    def adjRecurse(visitedNodes: Set[Int], edges: Set[Path]): Boolean = {
      // get unvisited adjacent nodes
      val newNodes = visitedNodes.flatMap { node => adjMatrix(node) }.filterNot(node => visitedNodes.contains(node))
      // elongate existing paths
      val newPaths = visitedNodes.flatMap { node =>
        adjMatrix(node).flatMap{targetNode =>
          edges.filter(path => path.destination == node &&  path.willGrow(targetNode)).map {_.extendPath(targetNode)}
        }
      }
      // no new paths
      newPaths.size match {
        case 0 =>          false
        case _ =>          // check for cycle in the paths
          if (newPaths.exists { _.isCycle }) true else adjRecurse(newNodes ++ visitedNodes, newPaths)
      }
    }

    // invoke with origin as visited node and as initial path
    adjRecurse(Set(seedNodes), Set(new Path(IndexedSeq(seedNodes))))
  }
}
