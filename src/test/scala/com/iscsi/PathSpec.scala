package com.iscsi

import org.scalatest.{FlatSpec, Matchers}
import scala.util.Random

class PathSpec extends FlatSpec with Matchers {

  val nodeSeq = 0 to 9 toIndexedSeq
  val testPath = new Path(nodeSeq)

  "Paths" should "have non-zero length" in {
    assert(testPath.pathLength > 0)
  }

  "Paths" should "be checked for node inclusion" in {
    val randomNode = Random.nextInt(nodeSeq.size)
    assert(testPath.inPath(randomNode))
  }

  "Paths" should "confirm if node is external to path" in {
    val randomNode = Random.nextInt(nodeSeq.size) + testPath.pathLength
    assert(!testPath.inPath(randomNode))
  }

  "Paths" should "accept extension with a new node " in {
    val randomNode = Random.nextInt(nodeSeq.size) + testPath.pathLength
    val initLen = testPath.pathLength
    val extendedPath = testPath.extendPath(randomNode)
    assert(extendedPath.pathLength - initLen == 1)
  }

  "Paths" should "reject extension with a duplicate node " in {
    val randomNode = Random.nextInt(nodeSeq.size - 1) + 1
    val initLen = testPath.pathLength
    val extendedPath = testPath.extendPath(randomNode)
    assert(extendedPath.pathLength - initLen == 0)
  }

  "Paths" should "accept extension with a origin node " in {
    val originNode = testPath.origin
    val initLen = testPath.pathLength
    val extendedPath = testPath.extendPath(originNode)
    assert(extendedPath.pathLength - initLen == 1)
  }

  "Paths" should "detect cycle if origin node revisited" in {
    val originNode = testPath.origin
    val initLen = testPath.pathLength
    val extendedPath = testPath.extendPath(originNode)
    assert(extendedPath.isCycle)
  }

  "Paths" should "detect path if present" in {
    val newPath = new Path(Range(1,4))
    val (originNode, destination) = (1, 3)
    assert(newPath.hasPath(originNode, destination))
  }

  "Paths" should "detect if path not present" in {
    val newPath = new Path(Range(1,6,2))
    val (originNode, destination) = (1, 4)
    assert(!newPath.hasPath(originNode, destination))
  }

}
