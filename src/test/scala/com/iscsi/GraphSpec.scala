package com.iscsi

import org.scalatest.{FlatSpec, Matchers}

class GraphSpec extends FlatSpec with Matchers with Ingestor with Graph {

  val cityPairs = Seq(("Washington", "Baltimore"),
    ("Washington", "Atlanta"),
    ("Baltimore", "Philadelphia"),
    ("Philadelphia", "New York"),
    ("Los Angeles", "San Francisco"),
    ("San Francisco", "Oakland"),
    ("Los Angeles", "Oakland"),
    ("Seattle", "New York"),
    ("Seattle", "Baltimore"))

  val nodeMap = parseNodes(cityPairs)

  val revNodeMap = reverseNodes(nodeMap)

  // make the edge pairs using index maps
  val edges = nodeIndices(cityPairs, nodeMap)

  // get adjacency list
  val adjList = adjacencyMatrix(edges)

  "Graph" should "create adjacency list" in {
    assert(adjList.keys.size == 6)
  }

  override def debug: Boolean = false
}
