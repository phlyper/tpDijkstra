package tpgraphe;

public class TestDijkstra {
    
    public static void main(String[] args) {
        
        
        GraphNode S = new GraphNode("S");
        GraphNode A = new GraphNode("A");
        GraphNode B = new GraphNode("B");
        GraphNode C = new GraphNode("C");
        GraphNode D = new GraphNode("D");
        GraphNode E = new GraphNode("E");
        GraphNode F = new GraphNode("F");
        //GraphNode G = new GraphNode("G");

        //log.error("Vladimir Sutskever");
        //---------------------------------------------
        //
        //---------------------------------------------
		
         A.AddOutgoingEdge(B, 2f); 
         A.AddOutgoingEdge(C, -1f);
         B.AddOutgoingEdge(C, -5f);
         

        /*
         * S.AddOutgoingEdge(A, 1); S.AddOutgoingEdge(B, 3);
         * S.AddOutgoingEdge(C, 4); A.AddOutgoingEdge(B, 6);
         * A.AddOutgoingEdge(D, 2); B.AddOutgoingEdge(E, 4);//1
         * C.AddOutgoingEdge(B, 2); C.AddOutgoingEdge(E, 3);
         * C.AddOutgoingEdge(F, 2); D.AddOutgoingEdge(E, 3);//-2
         * D.AddOutgoingEdge(T, 6); E.AddOutgoingEdge(T, 3);
         * F.AddOutgoingEdge(E, 1); F.AddOutgoingEdge(T, 7);
         */

        /*
         * S.AddOutgoingEdge(A, 3); S.AddOutgoingEdge(B, 2);
         * A.AddOutgoingEdge(B, 1); A.AddOutgoingEdge(C, 1);
         * A.AddOutgoingEdge(D, 3); B.AddOutgoingEdge(E, 1);//1
         * C.AddOutgoingEdge(B, 1); C.AddOutgoingEdge(D, 1);
         * C.AddOutgoingEdge(E, 2); D.AddOutgoingEdge(F, 1);
         * //E.AddOutgoingEdge(C, 2);//-2 E.AddOutgoingEdge(D, 2);
         * E.AddOutgoingEdge(F, 6);
         */
        /*
         * A.AddOutgoingEdge(C, 1); A.AddOutgoingEdge(B, 6);
         * B.AddOutgoingEdge(D, 4); B.AddOutgoingEdge(F, 2);
         * B.AddOutgoingEdge(E, -3); C.AddOutgoingEdge(B, 5);
         * C.AddOutgoingEdge(E, 7); D.AddOutgoingEdge(F, 5);
         * D.AddOutgoingEdge(G, 4); E.AddOutgoingEdge(F, 3);
         * F.AddOutgoingEdge(G, 8);
         */

        /*S.AddOutgoingEdge(A, 3);
        S.AddOutgoingEdge(B, 2);
        A.AddOutgoingEdge(B, -3);
        A.AddOutgoingEdge(C, 2);
        A.AddOutgoingEdge(D, 2);
        B.AddOutgoingEdge(E, 1);
        B.AddOutgoingEdge(C, 10);
        //C.AddOutgoingEdge(B, 0);
        C.AddOutgoingEdge(D, 2);
        D.AddOutgoingEdge(F, 3);
        E.AddOutgoingEdge(C, 2);//-2
        E.AddOutgoingEdge(D, 3);
        E.AddOutgoingEdge(F, 6);*/

        //---------------------------------------------
        //
        //---------------------------------------------
        Graph myGraph = new Graph(A);
        //myGraph.setStartNode(F);
        Dijkstra dAlg = new Dijkstra(myGraph);
        dAlg.go();
        //dAlg.go2();

        //dAlg.PrintStatusOfPriorityQ();
        myGraph.print();
        dAlg.print();

        p("\n*** apres amelioration ***\n");

        dAlg.improvement();

       // dAlg.PrintStatusOfPriorityQ();
        myGraph.print();
        dAlg.print();
    }

    private static void p(String s) {
        System.out.println(s);
    }
}
