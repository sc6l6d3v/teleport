package com.iscsi

import com.manyangled.breakable._
import scala.io.StdIn

trait Ingestor extends Debug{
  def ingestNodes: Seq[(String, String)] = {
    val pairExtractor = "([ a-zA-Z]+) - ([ a-zA-Z]+)".r
    val pairs = {
      for {
        (cityLine, cityLab) <- breakable(Stream.continually(StdIn.readLine()))
        if (cityLine.isEmpty) break (cityLab)
      } yield {
        val pairExtractor(city1, city2) = cityLine
        List(city1 -> city2, city2 -> city1)
      }
    }.toSeq.flatten
    val numCityPairs = pairs.size
    if (debug) {
      println(s"got $numCityPairs pairs:")
      pairs.foreach(println)
    }
    pairs.take(numCityPairs)
  }

  // creating a list of values from second edge

  /**
    * Read a list of object pairs and create an map using an alpha based index
 *
    * @param nodePairs undirected path from object to object
    * @return
    */
  def parseNodes(nodePairs: Seq[(String, String)]): Map[String, Int] =
    nodePairs.
      map(t =>
        List(t._1, t._2)).
      flatten.
      distinct.
      sorted.
      zipWithIndex.
      toMap

  /**
    * Read object list pairs and map to indexed edge list
    * @param nodePairs object pairs
    * @param nodeMap object -> index mapping
    * @return
    */
  def nodeIndices(nodePairs: Seq[(String, String)], nodeMap: Map[String, Int]): Seq[(Int, Int)] = {
    val edges = nodePairs.map { case (city1, city2) =>
      (nodeMap(city1), nodeMap(city2))
    }
    if (debug) {
      edges.foreach(x =>  println(x._1, x._2))
    }
    edges
  }

  /**
    * eliminate two way from each route
    * @param edges
    * @return
    */
  def uniqueEdges(edges: Seq[(Int, Int)]): Set[(Int, Int)] = {
    def recurseUniqueEdges(firstEdge: (Int, Int), rest: Seq[(Int, Int)]): Set[(Int, Int)] = {
      val newEdges = rest.filterNot(_ == (firstEdge._2, firstEdge._1))
      recurseUniqueEdges(newEdges.head, newEdges.tail)
    }

    recurseUniqueEdges(edges.head, edges.tail)
  }

  /**
    * Reverse object -> index map => index -> object map
    * @param nodeMap object -> index map
    * @return
    */
  def reverseNodes(nodeMap: Map[String, Int]): Map[Int, String] =
    nodeMap.map { case(node, index) =>
      (index -> node)
    }

}
