package com.iscsi

import org.scalatest.{FlatSpec, Matchers}

class IngestorSpec extends FlatSpec with Matchers with Ingestor {

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

  "Ingestors" should "create indexed map" in {
    assert(nodeMap.keys.size == 9)
    assert(nodeMap("Atlanta") == 0)
  }

  "Ingestors" should "create reverse indexed map" in {
    assert(revNodeMap.keys.size == 9)
    assert(revNodeMap(0) == "Atlanta")
  }

  "Ingestors" should "create edge pairs from indices" in {
    assert(edges.forall(_.productArity == 2))
  }

  override def debug: Boolean = false
}
