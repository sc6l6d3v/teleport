package example

import com.iscsi.{Graph, Ingestor}

object CityDemo extends App with Graph with Ingestor {

  // launch by acquiring objects
  val cityPairs = ingestNodes

  // make a indexed map from objects
  val nodeMap = parseNodes(cityPairs)

  // make a reverse index from above
  val revNodeMap = reverseNodes(nodeMap)

  // make the edge pairs using index maps
  val edges = nodeIndices(cityPairs, nodeMap)

  // get adjacency list
  val adjList = adjacencyMatrix(edges)

  // queries
  //1. path from Seattle - 1 jump
  showPath("Seattle", 1)

  showPath("Seattle", 2)

  hasPath("New York", "Atlanta")

  hasPath("Oakland", "Atlanta")

  nodeMap.keys.foreach{ city =>
    canLoop(city)
  }

  /**
    * Build list of adjacencies for requested depth to traverse all possibly traveled cities
    * @param seedCity origin
    * @param depth how many jumps
    */
  def showPath(seedCity: String, depth: Int) = {
    val seedNdx = nodeMap(seedCity)
    val citiesReached = adjacencies(seedNdx, depth, adjList) - seedNdx
    print(s"cities from $seedCity in $depth jumps: ")
    println(s"${citiesReached.map(city => revNodeMap(city)).mkString(",")}")
  }

  /**
    * Use adjacencies at maximum depth or entire set of cities to see if a route exists between two cities
    * @param city1 start
    * @param city2 end
    */
  def hasPath(city1: String, city2: String) = {
    val seedNdx = nodeMap(city1)
    val targetNdx = nodeMap(city2)
    val maxDepth = nodeMap.keys.size
    val citiesReached = adjacencies(seedNdx, maxDepth, adjList) - seedNdx
    val found = if (citiesReached.contains(targetNdx)) "yes" else "no"
    println(s"can I teleport from $city1 to $city2: $found")
  }

  /**
    * Can a route be constructed of unique paths from the origin back to itself?
    * @param city origin
    */
  def canLoop(city: String) = {
    val seedNdx = nodeMap(city)
    val found = if (loops(seedNdx, adjList)) "yes" else "no"
    println(s"loop possible from $city: $found")
  }

  // verbose mode
  override def debug = System.getProperty("debug").toBoolean
}
