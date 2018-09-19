README

Designed an example to take input from STDIN of the format supplied in the
example provided. The input would form a graph. The givens were bidirectional, unweighted edges.
The inputs were doubled to instantiate the reverse direction in order to make paths bidirectional.
The graph would be used to generate an adjacency list for the set of nodes next to each current
node. All adjacencies and path traversals are recursive and use the @tailrec annotation to optimize
with loop unrolling by the compiler.

Assumptions:  
Scala 12 used to leverage native Stdin library.  
breakable was used to permit escape from for comprehension on stream ingestion of data.  

The following schematic was used to visually simplify testing the inputs for the queries sought:

https://puu.sh/BxF5w/67a43b88a4.png

Example Input/Output:

Washington - Baltimore  
Washington - Atlanta  
Baltimore - Philadelphia  
Philadelphia - New York  
Los Angeles - San Francisco  
San Francisco - Oakland  
Los Angeles - Oakland  
Seattle - New York  
Seattle - Baltimore  

cities from Seattle in 1 jumps: New York,Baltimore  
cities from Seattle in 2 jumps: Washington,New York,Philadelphia,Baltimore  
can I teleport from New York to Atlanta: yes  
can I teleport from Oakland to Atlanta: no  
loop possible from San Francisco: yes  
loop possible from Atlanta: no  
loop possible from Washington: no  
loop possible from Los Angeles: yes  
loop possible from New York: yes  
loop possible from Philadelphia: yes  
loop possible from Seattle: yes  
loop possible from Baltimore: yes  
loop possible from Oakland: yes  
