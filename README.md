# EX2 - OOP COURSE


## Directed Weighted Graph

In this task we implemented interfaces that creates a Directed Weighted Graph build from nodes and edges, later, we implemented algorithms class which run algorithms on the graph and w created a GUI to present our code in a graphic way. 



## main algorithms in our code
isConnected- loop over the nodes in the graph by using BFS algorithm
to check if the graph is connected, using a transpose to change the edges directions and then another BFS.

shortestPathDist- finds the shortest path between 2 nodes, using the dijkstra algorithm to find it.

shortestPath- similar to shortestPathDist and return the path node list.

center- return the node which it's max distance from the other nodes are the lowest.
we use dijkstra algorithm and our method shortestPathDist to calculate it.

tsp- solve the Travelling Salesman Problem and returns back the shortest path to loop over all the nodes in the input. we use a greedy algorithm to choose the next node every time.

## Review to our algorithms
httpswww.baeldung.comjava-dijkstra

httpswww.techopedia.comdefinition16931greedy-algorithm

httpswww.ics.uci.edu~eppstein161960215.html


[ridmi.pdf](https://github.com/ChaimW25/ex2/files/7679242/ridmi.pdf)


