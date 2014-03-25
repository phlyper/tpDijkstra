package tpgraphe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Graph {

    private GraphNode startNode = null;
    private ArrayList<GraphNode> allNodes = new ArrayList<GraphNode>();
    private ArrayList<Edge> allEdges = new ArrayList<Edge>();
    private ArrayList<Integer> visitedNodes = new ArrayList<Integer>();
    private int n = 5;
    private Float[][] matrice = new Float[n][n];
    private Float[] vecteur = new Float[n];

    public void gerateGraph() {
        DecimalFormat df = new DecimalFormat("##.##");
                
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    matrice[i][j] = -1.0F;
                } else {
                    matrice[i][j] = Float.parseFloat(df.format(Math.random()).replace(',', '.'));
                }
            }
            vecteur[i] = Float.parseFloat(df.format(Math.random()).replace(',', '.'));
        }
    }

    public Float[][] getmatrice() {
        return matrice;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Float[] getvecteur() {
        return vecteur;
    }

    public String printMV() {
        String m = "", v = "";
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                m += matrice[i][j] + " |";
            }
            m += "\n";
            v += vecteur[i] + " |";
        }
        return m + "\n" + v;
    }

    public Graph() {
    }

    public Graph(String val) {
        this.startNode = AddNode(val);
        this.discoverGraph();
    }

    public Graph(GraphNode startNode) {
        this.startNode = startNode;
        this.discoverGraph();
    }

    public void clear() {
        allNodes.clear();
        allEdges.clear();
        startNode = null;
    }

    /*public GraphNode FindStartNode() { //pour la recherche d'un noeud qui a des arcs sortants et pas des arcs entrants
     for(GraphNode gn : allNodes){
     if(gn.getInGoingEdges().isEmpty()){
     return gn;
     }
     }
     return null;
     }*/
    public GraphNode getStartNode() {
        return this.startNode;
    }

    public void setStartNode(String valStartNode) {
        this.startNode = AddNode(valStartNode);
        this.discoverGraph();
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
        this.discoverGraph();
    }

    public GraphNode AddNode(String val) {
        GraphNode g = existeNode(val);
        if (g == null) {
            g = new GraphNode(val);
            allNodes.add(g);
        }

        return g;
    }
    
    public void AddNode(GraphNode gn) {
        if (gn == null) {
            allNodes.add(gn);
        }
    }

    public Edge AddEdge(String valFrom, String valTo, Float cost) {
        Edge e = existeEdge(valFrom, valTo);
        if (e == null) {
            GraphNode gFrom = existeNode(valFrom);
            GraphNode gTo = existeNode(valTo);
            if (gFrom == null) {
                gFrom = new GraphNode(valTo);
                allNodes.add(gFrom);
            }
            if (gTo == null) {
                gTo = new GraphNode(valTo);
                allNodes.add(gTo);
            }
            e = new Edge(gFrom, gTo, cost);
            gFrom.AddOutgoingEdge(gTo, e.getValue());
            allEdges.add(e);
        }

        return e;
    }
    
    public void removeNode(GraphNode g) {
        this.allNodes.remove(g);
    }
    
    public void removeEdge(Edge e) {
        this.allEdges.remove(e);
    }

    public ArrayList<GraphNode> getAllNodes() {
        return this.allNodes;
    }

    public ArrayList<Edge> getAllEdges() {
        return this.allEdges;
    }

    public ArrayList<Edge> getDijEdges() {
        ArrayList<Edge> dij = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            if (e.isDijkstra()) {
                dij.add(e);
            }
        }
        return dij;
    }

    public void resetDij() {
        for (Edge e : allEdges) {
            if (e.isDijkstra()) {
                e.setDijkstra(false);
            }
            if (e.isImprovement()) {
                e.setImprovement(false);
            }
        }
    }

    public ArrayList<Edge> getImpEdges() {
        ArrayList<Edge> imp = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            if (e.isImprovement()) {
                imp.add(e);
            }
        }
        return imp;
    }

    public void resetImp() {
        for (Edge e : allEdges) {
            if (e.isImprovement()) {
                e.setImprovement(false);
            }
        }
    }

    public ArrayList<Edge> getOtherEdges() {
        ArrayList<Edge> other = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            if (!e.isDijkstra()) {
                other.add(e);
            }
        }
        return other;
    }

    private void discoverGraph() {
        visitedNodes.clear();
        allEdges.clear();
        allNodes.clear();

        allNodes.add(this.startNode);
        visit(startNode);
        for (Edge e : this.startNode.getOutGoingEdges()) {
            if (!isVisited(e.getNodeTo())) {
                bfs(e.getNodeTo());
            }
            if (!allEdges.contains(e)) {
                allEdges.add(e);
            }
        }
    }

    private void bfs(GraphNode n) {
        visit(n);
        this.allNodes.add(n);
        for (Edge e : n.getOutGoingEdges()) {
            if (!isVisited(e.getNodeTo())) {
                bfs(e.getNodeTo());
            }

            if (!allEdges.contains(e)) {
                allEdges.add(e);
            }
        }
    }

    private GraphNode existeNode(String val) {
        for (GraphNode g : allNodes) {
            if (g.getName().equals(val)) {
                return g;
            }
        }
        return null;
    }

    private Edge existeEdge(String valFrom, String valTo) {
        for (Edge e : allEdges) {
            if (e.getNodeFrom().getName().equals(valFrom) && e.getNodeTo().getName().equals(valTo)) {
                return e;
            }
        }
        return null;
    }

    private boolean isVisited(GraphNode n) {
        return visitedNodes.contains(n.getID());
    }

    private void visit(GraphNode n) {
        this.visitedNodes.add(n.getID());
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String s = "**** Print Graph **************************\n";
        s += "nbre des sommets=" + GraphNode.getNodeCount() + "\n";
        s += "nbre des arcs=" + Edge.getEdgeCount() + "\n";
        s += "allNodes=" + allNodes.size() + ", allEdges=" + allEdges.size() + "\n";
        Collections.sort(allNodes);
        Collections.sort(allEdges);
        for (Edge e : this.allEdges) {
            s += e + "\n";
        }
        s += "----------------------------------------------\n";
        for (GraphNode n : this.allNodes) {
            s += n + "\n";
            /*s += n.getName() + " --> " + n.getDistance()+"\n";
             s += "INarcs:" + n.getInGoingEdges().size()+"\n";

             for (Edge e : n.getInGoingEdges()) {
             s += e+"\n";
             }
             s += "OUTarcs:" + n.getOutGoingEdges().size()+"\n";
             for (Edge e : n.getOutGoingEdges()) {
             s += e+"\n";
             }*/
            s += "----------------------------------------------\n";
        }
        s += "**************************************************\n";
        return s;
    }
}
