# Shortest Path / Minimal Spanning Tree
This was the final individual project for CS 3345(Introduction to Data Structures and Alogorithms). Its purpose was to take an undirected, unweighted graph and use both Dijkstra's and Kruskal's. Dijkstra's would be used to construct the shortest path from a given vertex S toi all other vertices. It would also use Kruskal's to constuct a minimal spanning tree for the graph.

The input for the project was given by the first line containing two space-seperated integers, the first of which would be the number of vertices in the graph(starting with 1, not 0), and the second number being the source vertex to use for Dijkstras. Each of the subsequent lines would contain 3 space-seperated integers, the first two being the connected vertex and the last integer being the weight of the connection. (See below example)
```
7 1        // number of vertices and source vertex
1 2 2      // undirected edge from v1 to v2 of weight 2
1 4 1
2 5 10     // undirected edge from v2 to v5 with weight 10
2 4 3
5 7 6      // there will not be comments in the input
3 1 4
3 6 5
4 3 2
7 6 1
4 5 2
4 7 4
4 6 8
0 0 0
```
The output for Dijkstra's required the printing of the source vertex, then the connecting nodes, then the target node followed by the cost of getting from the source to the target. 
```
1 1 0      // shortest path from 1 to 1 has length 0
1 2 2      // this list must be in increasing order of
1 4 3 3    // terminal vertex
1 4 1
1 4 5 3    // shortest path from 1 to 5 is via 4 and has length 3
1 4 7 6 6  // shortest path from 1 to 6 is via 4, 7 and has length 6
1 4 7 5    // do not print these comments
```
For printing Kruskal's it would print the two connected vertices with the first being the smaller vertex and the second being the other. For all of the lines containing the same starting vertices it will then order them based on the second number in the respective line. It would then also print out the total cost of the minimal spanning tree. (See below)
```
1 2   // each line must begin with the smaller vertex number
1 4   // lines with equal first numbers should be in order of the 
3 4   // second number
4 5
4 7
6 7
Finally print a line giving the total length of the edges in the minimal spanning tree as follows:

Minimal spanning tree length = 12
```

An example of the uncommented and full input/output:
```
Sample Input                     |Expected Output
---------------------------------|--------------
7 1                              |1 1 0
1 2 2                            |1 2 2
1 4 1                            |1 4 3 3
2 5 10                           |1 4 1
2 4 3                            |1 4 5 3
5 7 6                            |1 4 7 6 6
3 1 4                            |1 4 7 5
3 6 5                            |
4 3 2                            |1 2
7 6 1                            |1 4
4 5 2                            |3 4
4 7 4                            |4 5
4 6 8                            |4 7
0 0 0                            |6 7
                                 |Minimal spanning tree length = 12

```
