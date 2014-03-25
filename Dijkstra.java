package tpgraphe;

import java.util.ArrayList;

public class Dijkstra {

    private Graph graph;
    // priority queue stores all of the nodes, reachable from the start node
    // The queue is sorted by the node.distance
    //private GraphNodePriorityQueue priorityQ = new GraphNodePriorityQueue();
    // private Hashtable <GraphNode,Integer> distance = new Hashtable<GraphNode, Integer>();
    private ArrayList<GraphNode> NodeXP = new ArrayList<GraphNode>();// xp
    private ArrayList<Edge> EdgeXP = new ArrayList<Edge>();// Axp
    String v = "";

    public Dijkstra(Graph g) {
        this.graph = g;
        if (this.graph.getStartNode() != null) {
            this.graph.getStartNode().setDistance(0f);
        }
        //this.priorityQ.add(this.graph.getAllNodes());
    }

    /*
     * public void go2() { while (this.priorityQ.hasMore()) {// nb iterr n-1
     * GraphNode n = this.priorityQ.remove(); for (Edge e :
     * n.getOutGoingEdges()) { GraphNode adjNode = e.getNodeTo(); Integer
     * newPossiblePathCost = e.getCost() + n.getDistance(); if
     * (newPossiblePathCost < adjNode.getDistance()) {
     * adjNode.setDistance(newPossiblePathCost);
     * this.priorityQ.updateGraphNodeDistance(adjNode); }
     *
     * System.out.println("=======================================================");
     * PrintStatusOfPriorityQ();
     * System.out.println("=======================================================");
     * } } }
     */
    public boolean selectStartNode(String val) {
        setStartNode(val);
        return selectStartNode();
    }

    public boolean selectStartNode() {
        //this.graph.setStartNode(this.graph.FindStartNode());
        if (this.graph.getStartNode() == null) {
            return false;
        }
        for (GraphNode g : graph.getAllNodes()) {
            if (g != graph.getStartNode()) {
                g.setDistance(Float.MAX_VALUE);
            }
        }
        this.graph.getStartNode().setDistance(0f);
        System.out.println("selectstartnode :: " + this.graph.getStartNode().getName() + " :: distance :: " + this.graph.getStartNode().getDistance());
        NodeXP.clear();
        EdgeXP.clear();
        return true;
    }

    public GraphNode getStartNode() {
        return this.graph.getStartNode();
    }

    private void setStartNode(String val) {
        this.graph.setStartNode(val);
    }

    public ArrayList<GraphNode> getAllStartNode() {
        ArrayList<GraphNode> sgn = new ArrayList<GraphNode>();

        ArrayList<GraphNode> ld = new ArrayList<GraphNode>();
        System.out.println(" * Les start noeuds assendants");
        for (GraphNode n : graph.getAllNodes()) {
            ld.clear();
            ArrayList<GraphNode> d = succseseur2(n, ld);
            System.out.println("ASS Start : (" + n.getName() + ") :: size=" + d.size());
            if (d.size() == graph.getAllNodes().size() - 1) {
                sgn.add(n);
            }
        }

        return sgn;
    }

    public ArrayList<GraphNode> succseseur2(GraphNode n, ArrayList<GraphNode> ld) {
        ArrayList<GraphNode> ass = new ArrayList<GraphNode>();
        ass.addAll(ld);

        if (n != null) {

            for (Edge e : n.getOutGoingEdges()) {
                //if (e.isDijkstra()){  // pour dijkestra
                GraphNode adjNode = e.getNodeTo();

                if (!ld.contains(adjNode)) {
                    ld.add(adjNode);
                    ArrayList<GraphNode> d = succseseur2(adjNode, ld);
                    ld.remove(adjNode);

                    if (d.size() > 0) {
                        for (GraphNode b : d) {
                            if (!ass.contains(b)) {
                                ass.add(b);
                            }
                        }
                    }

                    if (!ass.contains(adjNode)) {
                        ass.add(adjNode);
                    }
                }
                //}
            }

            if (ass.contains(n)) {
                ass.remove(n);
            }
        }
        return ass;
    }

    class GNE {

        public GraphNode gn = null;
        public Edge e = null;
        //public GNE() {}

        public GNE(GraphNode gn) {
            this(gn, null);
        }

        public GNE(GraphNode gn, Edge e) {
            this.gn = gn;
            this.e = e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    public void go() {
        //ArrayList<GraphNode> gn = new ArrayList<GraphNode>();
        NodeXP.add(getStartNode());
        ArrayList<GNE> gn = new ArrayList<GNE>();

        for (GraphNode b : graph.getAllNodes()) {
            gn.add(new GNE(b));
        }


        gn = trie(gn);
        while (!gn.isEmpty()) {
            int i = 0;
            GraphNode min = null;
            Edge gmin = null;
            GNE gne = gn.get(0);
            GraphNode s = gne.gn;
            gn.remove(gne);
            gn = trie(gn);
            for (Edge e : s.getOutGoingEdges()) {
                GraphNode nTo = e.getNodeTo();// to
                GraphNode nFrom = e.getNodeFrom();// from
                System.out.println(e);

                if (!NodeXP.contains(nTo)) {
                    Float newPossiblePathCost = nFrom.getDistance() + e.getValue();
                    if (newPossiblePathCost < nTo.getDistance()) {
                        nTo.setDistance(newPossiblePathCost);
                        for (GNE l : gn) {
                            if (l.gn == nTo) {
                                l.e = e;
                            }
                        }
                        gn = trie(gn);
                    }
                }
            }

            /*System.out.println(s.getName() + " %%%%%%%%%%%%%%%%%%% GN %%%%%%%%%%%%%%%%%%%%%%");
             for (GNE y : gn) {
             System.out.println("gn:" + y.gn.getName() + " e:" + y.e);
             }
             System.out.println("%%%%%%%%%%%%%%%%%%% %%%%%%%%%%%%%%%%%%%%%%");*/

            if (gn.size() > 0) {
                min = gn.get(0).gn;
                NodeXP.add(min);
                //gn = trie(gn);

                Edge em = gn.get(0).e;//min.getInCost(NodeXP,s);

                //System.err.println("sommet dep:" + s.getName() + " sommet arr:" + min.getName() + " arc:" + em);
                EdgeXP.add(em);
                em.setDijkstra(true);
            }
        }
    }

    public ArrayList<GNE> trie(ArrayList<GNE> g) {
        for (int i = 0; i < g.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < g.size(); j++) {
                if (g.get(j).gn.getDistance() < g.get(min).gn.getDistance()) {
                    min = j;
                }
            }

            if (i != min) {
                GNE x = g.get(i);
                g.set(i, g.get(min));
                g.set(min, x);
            }
        }
        return g;
    }

    public ArrayList<GraphNode> abs(GraphNode n, ArrayList<GraphNode> ld) {
        ArrayList<GraphNode> des = new ArrayList<GraphNode>();
        des.addAll(ld);
        if (n != null) {

            for (Edge e : n.getInGoingEdges()) {
                if (e.isImprovement()) {
                    GraphNode adjNode = e.getNodeFrom();

                    if (!ld.contains(adjNode)) {
                        //if (e.isImprovement()) {
                        ld.add(adjNode);
                        ArrayList<GraphNode> d = abs(adjNode, ld);
                        ld.remove(adjNode);

                        if (d.size() > 0) {
                            for (GraphNode b : d) {
                                if (!des.contains(b)) {
                                    des.add(b);
                                }
                            }
                        }

                        if (!des.contains(adjNode)) {
                            des.add(adjNode);
                        }
                        //}
                    }
                }
            }
            if (des.contains(n)) {
                des.remove(n);
            }
        }
        return des;
    }

    public ArrayList<GraphNode> succseseur(GraphNode n, ArrayList<GraphNode> ld) {
        ArrayList<GraphNode> ass = new ArrayList<GraphNode>();
        ass.addAll(ld);

        if (n != null) {

            for (Edge e : n.getOutGoingEdges()) {
                if (e.isDijkstra()) {  // pour dijkestra
                    GraphNode adjNode = e.getNodeTo();

                    if (!ld.contains(adjNode)) {
                        ld.add(adjNode);

                        ArrayList<GraphNode> d = succseseur(adjNode, ld);
                        ld.remove(adjNode);

                        if (d.size() > 0) {
                            for (GraphNode b : d) {
                                if (!ass.contains(b)) {
                                    ass.add(b);
                                }
                            }
                        }


                        if (!ass.contains(adjNode)) {
                            ass.add(adjNode);
                        }
                    }
                }
            }

            if (ass.contains(n)) {
                ass.remove(n);
            }
        }
        return ass;
    }

    public boolean improvement() {


        for (Edge e : graph.getDijEdges()) {
            e.setImprovement(true);
        }
        boolean trouve = true;
        while (trouve == true) {
            trouve = false;
            ArrayList<GraphNode> ld = new ArrayList<GraphNode>();

            ArrayList<Edge> other = graph.getOtherEdges();
            //Collections.sort(other);
            System.out.println("other");
            for (int k = 0; k < other.size() && trouve == false; k++) {
                Edge e = other.get(k);
                System.out.println("other = " + e);

                Float am = e.getNodeTo().getDistance() - e.getNodeFrom().getDistance() - e.getValue();//delta

                System.out.println("am1 : " + am);
                if (am.intValue() > 0) {
                    System.out.println("am2 : " + am);
                    ld.clear();
                    ArrayList<GraphNode> des = abs(e.getNodeFrom(), ld);
                    System.out.println("nbre des ::" + des.size());
                    for (GraphNode x : des) {
                        System.out.println("des ::" + x.getName());
                    }

                    if (des.contains(e.getNodeTo())) {// cycle abs
                        v = "";
                        boolean vb = false;
                        int xb = 0;
                        for (GraphNode x : des) {
                            if (e.getNodeTo() == x) {
                                vb = true;
                            }
                            if (vb == true) {
                                v += x.getName() + ", ";
                            }
                        }
                        v += e.getNodeFrom().getName();
                        System.out.println("cycle abs : " + e);
                        return false;
                    } else {

                        for (Edge v : e.getNodeTo().getInGoingEdges()) {
                            if (v.isDijkstra()) {
                                EdgeXP.remove(v);
                                v.setImprovement(false);
                                System.out.println("Edge a supprimer : " + v + " remplacer par : " + e);
                                break;
                            }
                        }

                        EdgeXP.add(e);
                        e.getNodeTo().setDistance(e.getNodeFrom().getDistance() + e.getValue());
                        e.setImprovement(true);
                        trouve = true;

                        ArrayList<GraphNode> sd = new ArrayList<GraphNode>();
                        //mise a jour des pi des nodes reliers a les arcs dijs
                        for (GraphNode g : succseseur(e.getNodeTo(), sd)) {
                            System.out.println(e.getNodeTo().getName() + ":node dij update:" + g.getName());
                            g.setDistance(g.getInGoingEdgeDij().getNodeFrom().getDistance() + g.getInGoingEdgeDij().getValue());
                        }

                    }
                }
            }
        }
        return true;
    }

    public void print() {
        //this.priorityQ.PrintContents();
        System.out.println(" - NodeXP");
        for (GraphNode n : NodeXP) {
            System.out.println(n.getName() + " distance=" + n.getDistance());
        }
        System.out.println(" - EdgeXP");
        for (Edge e : EdgeXP) {
            System.out.println(e);
        }


        System.out.println(" * Les noeuds dessendants");
        ArrayList<GraphNode> ld = new ArrayList<GraphNode>();
        for (GraphNode n : graph.getAllNodes()) {
            ArrayList<GraphNode> d = abs(n, ld);
            System.out.println("DES (" + n.getName() + ") :: size=" + d.size());
            for (GraphNode b : d) {
                System.out.println(b.getName() + " --> " + b.getDistance());
            }
        }

        System.out.println(" * Les noeuds assendants");
        ld.clear();
        for (GraphNode n : graph.getAllNodes()) {
            ArrayList<GraphNode> d = succseseur(n, ld);
            System.out.println("ASS (" + n.getName() + ") :: size=" + d.size());
            for (GraphNode b : d) {
                System.out.println(b.getName() + " --> " + b.getDistance());
            }
        }
    }
}
