package tpgraphe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphNode implements Comparable<GraphNode> {

    private static int nodeCount = 0;
    private ArrayList<Edge> inGoingEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outGoingEdges = new ArrayList<Edge>();
    private String name;
    private Float value = new Float(0);
    private Integer ID = null;
    private boolean visited = false;
    private Float distance = Float.MAX_VALUE;
    //private LinkedList<Arc> arcs = new LinkedList<Arc>();
    private Color color = Color.BLUE;
    private final Dimension dim = new Dimension(30, 30);
    private Rectangle rec = new Rectangle(dim);
    //private char num = 'A';//val
    private Boolean iscolor;
    private int degre;
    //private int pi = 0;//distance

    public GraphNode() {
        this(null, "");
    }

    public GraphNode(String value) {
        this.init(null, value);
    }

    public GraphNode(Point pos, String value) {
        this.init(pos, value);
    }

    private void init(Point pos, String nodeName) {
        this.name = nodeName;
        this.ID = GraphNode.nodeCount++;
        this.visited = false;
        if (pos != null) {
            this.rec.setLocation(pos);
        }
        this.degre = 0;
        this.iscolor = false;
    }

    /*public char getNum() {
     return num;
     }*/

    /*public void addArc(Arc a) {
     arcs.add(a);
     }*/
    public void removeEdge(Edge e) {
        //arcs.remove(a);
        inGoingEdges.remove(e);
        outGoingEdges.remove(e);
    }

    /*public int getPi() {
     return pi;
     }

     public void setPi(int pi) {
     this.pi = pi;
     }*/
    public Rectangle getLoc() {
        return (Rectangle) this.rec.clone();
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public int getDegre() {
        return degre;
    }

    public void setDegre(int degre) {
        this.degre = degre;
    }

    /*@Override
     public void setText(char a) {

     this.num = a;
     }*/
    public void setPosition(Point p) {
        this.rec.x = p.x;
        this.rec.y = p.y;
    }

    public Point getPosition() {
        Point p = rec.getLocation();
        p.x = (p.x + rec.width / 2);
        p.y = (p.y + rec.height / 2);

        return p;
    }

    public int getPositionX() {
        return rec.x + rec.width / 2;
    }

    public int getPositionY() {
        return rec.y + rec.height / 2;
    }

    public Boolean isColor() {
        return this.iscolor;
    }

    public void setIscolor(Boolean iscolor) {
        this.iscolor = iscolor;
    }

    public Color getColor() {
        return color;
    }

    public void draw(Graphics a) {
        Graphics2D g = (Graphics2D) a;
        //GradientPaint gp = new GradientPaint(rec.x - 3, rec.y + 13, Color.white, rec.x + 30, rec.y + 13, this.color, true);
        GradientPaint gp = new GradientPaint(rec.x - 10, rec.y - 10, Color.white, rec.x + 2, rec.y + 2, this.color, true);
        Color c = g.getColor();
        g.setColor(color);
        g.setPaint(gp);
        //g.fillRect(rec.x, rec.y, rec.width, rec.height);
        g.fillRoundRect(rec.x, rec.y, rec.width, rec.height, 6, 6);
        g.setColor(Color.BLACK);
        g.drawString(name + ":" + String.valueOf((distance == Integer.MAX_VALUE ? "0" : distance)), rec.x + 8, rec.y + 19);

        g.setColor(c);
    }

    @Override
    public String toString() {
        String s = "";
        s += "GraphNode{ name=" + name + ", id=" + ID + ", color=" + color + ", degre=" + degre + /*", pi=" + pi +*/ ", distance=" + distance;
        s += ", IN(" + inGoingEdges.size() + ")=[";
        for (Iterator iter = inGoingEdges.iterator(); iter.hasNext();) {
            Edge e = (Edge) iter.next();
            s += e.toString() + ", ";
        }
        s += "], OUT(" + outGoingEdges.size() + ")=[";
        for (Iterator iter = outGoingEdges.iterator(); iter.hasNext();) {
            Edge e = (Edge) iter.next();
            s += e.toString() + ", ";
        }
        s += "] }";
        return s;
    }

    public static int getNodeCount() {
        return GraphNode.nodeCount;
    }

    public void setVisited(boolean visited) {

        System.out.println("Visited" + this.name);
        this.visited = visited;
    }

    public void AddIngoingEdge(GraphNode node, Float cost) {
        Edge e = new Edge(node, this, cost);
        node.outGoingEdges.add(e);
        this.inGoingEdges.add(e);
    }

    public ArrayList<Edge> getInGoingEdges() {
        return inGoingEdges;
    }

    public void AddOutgoingEdge(GraphNode node, Float cost) {
        Edge e = new Edge(this, node, cost);
        this.outGoingEdges.add(e);
        node.inGoingEdges.add(e);
    }

    public ArrayList<Edge> getOutGoingEdges() {
        return outGoingEdges;
    }

    public Edge getInGoingEdgeDij() {
        for (Edge e : inGoingEdges) {
            if (e.isDijkstra()) {
                return e;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Edge getInCost(ArrayList<GraphNode> gxp, GraphNode s) {
        Edge min = inGoingEdges.get(0);
        //Integer dm = inGoingEdges.get(0).getValue() + inGoingEdges.get(0).getNodeFrom().getDistance();
        for (int i = 1; i < inGoingEdges.size(); i++) {
            if (inGoingEdges.get(i).getValue() < min.getValue() /*&& inGoingEdges.get(i).getNodeFrom() == s*/ && gxp.contains(inGoingEdges.get(i).getNodeFrom())) {
                //Integer dmi = inGoingEdges.get(i).getValue() + inGoingEdges.get(i).getNodeFrom().getDistance();
                //if(dmi < dm) {
                min = inGoingEdges.get(i);
                //dm = dmi;
            }
        }
        return min;
    }

    @Override
    public int compareTo(GraphNode g) {
        if (name.charAt(0) > g.name.charAt(0)) {
            return 1;
        } else if (name.charAt(0) < g.name.charAt(0)) {
            return -1;
        }
        return 0;

        /*  int resultat = 0;
         if (this.degre < s.degre) {
         resultat = 1;
         }
         if (this.degre > s.degre) {
         resultat = -1;
         }
         if (this.degre == s.degre) {
         resultat = 0;
         }
         return resultat;*/
    }
}
