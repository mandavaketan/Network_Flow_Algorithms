## Dijkstra's Algorithm For Shortest Path

Here I have programmed Dijkstra's Algorithm for Shortest Path in Java. All core files for the algorithm are in the src folder. 
There are no 3rd party dependencies for this algorithm.

Our "Main" file where compilation and building will be completed is "Graph.java", where the algorithm code lives (first function in the program). 
I have additionally included the Bellman-Ford Algorithm for shortest path (below the psvm function at the bottom of the file).

Two test cases have been included (testGraph1.txt & testGraph2.txt), with the first being less complex than the second. In order to change
which test graph you are using, change the "filename" variable to either "testGraph1" or "testGraph2" (example is included in code).
Feel free to make your own!

## Files
- Graph.java: Our main file where the graph is initialized and Dijkstra's algorithm is completed

- Arc.java: Our Arc class where we define the initialization function for an arc and the coinciding parameters for an arc

- Node.java: Our Node class where we define the initialization function for an node and the coinciding parameters for an node

- testGraph#.txt: Example graph files. Structure is as follows:
    1| [source node] [sink node]
    2| [arc name] [arc node 1] [arc node 2] [distance/cost]
    3| [arc name] [arc node 1] [arc node 2] [distance/cost]
    ...

# VS Code Structure Notes

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources - WHERE ALL FILES LIVE
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.
