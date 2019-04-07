class ShortestPathGeneration {
        
        private static final int NO_PARENT = -1;
        private int[] parents;
        private int[] shortestDistances;
        private int nVertices;
        
        public ShortestPathGeneration(int nVertices) {
                this.nVertices = nVertices;
                parents = new int[nVertices];
                shortestDistances = new int[nVertices];
        }
        
        public String dijkstra(int[][] adjacencyMatrix, int startVertex, int dest)
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
                return printPath1(dest);
        }
        
        
        private String printPath1(int dest  ){
                int i = dest;
                String S =i+"";
                while(i!= NO_PARENT){
                        i = parents[i];
                        if(i!=NO_PARENT)
                                S = S +"-"+ i ;
                }
                String St[];
                St = S.split("-");
                S="";
                for(int k = St.length -1 ; k>=0 ; k--){
                        if(k!=0)
                                S +=St[k] + "-";
                        else
                                S += St[k];
                }
                
                
                return S;
        }

//    public static void main(String[] args)
//    {
//        int[][] adjacencyMatrix = {
//                { 0,1,0,1 },
//                { 1,0,1,0 },
//                { 0,1,0,0 },
//                { 1,0,0,0 }
//        };
//        String S;
//        ShortestPathGeneration GP = new ShortestPathGeneration(4);
//        S = GP.dijkstra(adjacencyMatrix , 2, 1); // src is 3 dest is 2
//        System.out.println("Path is :"+S);
//
//
//
//    }
}
