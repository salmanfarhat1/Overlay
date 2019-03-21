class GeneratePath {

    private static final int NO_PARENT = -1;
    int[] parents;
    int[] shortestDistances;
    int nVertices;

    public GeneratePath(int nVertices) {
      this.nVertices = nVertices;
      parents = new int[nVertices];
      shortestDistances = new int[nVertices];
    }

    private void dijkstra(int[][] adjacencyMatrix, int startVertex, int dest)
    {
        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortestDistances[startVertex] = 0;

        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++)
        {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0 ; vertexIndex < nVertices ; vertexIndex++)
            {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            added[nearestVertex] = true;

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        printSolution1(startVertex, shortestDistances, parents , dest);
    }

    private void printSolution1(int startVertex, int[] distances, int[] parents , int dest)
    {
        System.out.print("Vertex\t Distance\tPath");

        System.out.print("\n" + startVertex + " -> ");
        System.out.print(dest + " \t\t ");
        System.out.print(distances[dest] + "\t\t");
        String S = printPath1(dest, parents);
        System.out.println("Path is :"+S);

    }
    private String printPath1(int dest , int[] parents ){
        int i = dest;
        String S =i+"";
        while(i!= NO_PARENT){
            i = parents[i];
            if(i!=NO_PARENT)
                S = S +"-"+ i ;
        }
        return S;
    }

    public static void main(String[] args)
    {
        int[][] adjacencyMatrix = {
                { 0,1,0,0 },
                { 1,0,1,0 },
                { 0,1,0,1 },
                { 0,0,1,0 }
        };
        // dijkstra(adjacencyMatrix, 1 , 3);
        System.out.println("HI HI HI HI HI");
        GeneratePath GP = new GeneratePath(4);
        GP.dijkstra(adjacencyMatrix , 0 , 2);
    }

}
