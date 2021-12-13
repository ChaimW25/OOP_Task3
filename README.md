

# EX2 - OOP COURSE


## Directed Weighted Graph

In this task we implemented interfaces that creates a Directed Weighted Graph build from nodes and edges, later, we implemented algorithms class which run algorithms on the graph and w created a GUI to present our code in a graphic way. 



## Main algorithms in our code:
IsConnected- loop over the nodes in the graph by: using BFS algorithm
to check if the graph is connected, using a transpose to change the edges directions and then another BFS.

shortestPathDist- finds the shortest path between 2 nodes, using the dijkstra algorithm to find it.

shortestPath- similar to shortestPathDist and return the path node list.

center- return the node which it's max distance from the other nodes are the lowest.
we use dijkstra algorithm and our method shortestPathDist to calculate it.

tsp- solve the Travelling Salesman Problem and returns back the shortest path to loop over all the nodes in the input. we use a greedy algorithm to choose the next node every time.

## Repositiry stucture
We actually accomplished three main parts of this task:
First, we implemented a class of graph that includes Nodes and Edges and combines them into one graph.
Second, we implemented a class of algorithms that perform on graphs, some of which we noted above.
At last, we implemented a graphical interface- 'GUI', for graphs and algorithms. We used the Swing library and its jPanel and JFrame subdirectories. The graphical interface is added to the testers we wrote to test our code.
In some of the algorithms and implementations, we used material that we found throughout the Internet, some of which we referred to in the links below.

In the graphical interface we implemented two main classes: JFrame which is responsible for the frame and menu buttons. JPanel which is responsible for the exact locations of each point and shape on the screen.
We implemented additional classes that: first, match between the api commands to their execution in the GUI, and second design each operation in a unique way.

## UML of our project:


![0001](https://user-images.githubusercontent.com/74601548/145879587-8a7c6a62-ab42-423d-b7a2-75372d13af6e.jpg)

## How does it work?

To run the GUI you can run the Ex2 class or run the jar file Ex2.jar through the cmd follow this steps:
1. go to the command prompt and reach root folder/build/libs.
2. Enter the command: java –jar <ExecutableJarFileName>.jar.
3. Verify the result..

## Review to our algorithms and to our code


https://www.techopedia.com/definition/16931/greedy-algorithm

https://www.baeldung.com/java-dijkstra

https://cp-algorithms.com/graph/breadth-first-search.html

https://javapointers.com/java/java-se/the-jpanel/

https://sites.google.com/site/simplestjava/jframe



  
  ## our algorithm performances:
  
 
<img width="691" alt="ביצועים" src="https://user-images.githubusercontent.com/74601548/145386112-e936e6d2-80b5-4c9b-a4f7-355de3f5ef26.png">



