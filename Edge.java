package tpgraphe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Edge implements Comparable<Edge> {

    private static int edgeCount = 0;
    private GraphNode nodeFrom = null;
    private GraphNode nodeTo = null;
    private Float value = new Float(0);
    private Integer ID = null;
    private boolean dijkstra = false;
    private boolean improvement = false;
    //private Sommet sommer1 = null;//From
    //private Sommet sommet2 = null;//To
    private Point ps1 = new Point();
    private Point ps2 = new Point();
    private Rectangle rec = new Rectangle(24, 24);
    //private int val = 0;//Value
    private Color color = Color.BLACK;

    /*public Arc(Sommet a, Sommet b, int val) {
     this(a, b);
     this.val = val;
     }

     public Arc(Sommet a, Sommet b) {
     this.nodeFrom = a;
     this.nodeTo = b;
     updateLoc();
     }*/
    public Edge(GraphNode nodeFrom, GraphNode nodeTo) {
        this.nodeTo = nodeTo;
        this.nodeFrom = nodeFrom;
        ID = Edge.edgeCount++;
    }

    public Edge(GraphNode nodeFrom, GraphNode nodeTo, Float value) {
        this(nodeFrom, nodeTo);
        this.value = value;
    }

    public static int getEdgeCount() {
        return edgeCount;
    }

    public GraphNode getNodeTo() {
        return nodeTo;
    }

    public GraphNode getNodeFrom() {
        return nodeFrom;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getID() {
        return ID;
    }

    public boolean isDijkstra() {
        return dijkstra;
    }

    public void setDijkstra(boolean dijkstra) {
        this.dijkstra = dijkstra;
    }

    public boolean isImprovement() {
        return improvement;
    }

    public void setImprovement(boolean improvement) {
        this.improvement = improvement;
    }

    /*public int getName() {
     return val;
     }

     public void setVal(int val) {
     this.val = val;
     }*/
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle getloc() {
        Rectangle rec = this.rec;
        rec.x = rec.x - rec.width / 2;
        rec.y = rec.y - rec.height / 2;
        return rec;
    }

    private void updateLoc() {
        int dx = nodeTo.getPosition().x - nodeFrom.getPosition().x, dy = nodeTo.getPosition().y - nodeFrom.getPosition().y;
        rec.x = nodeFrom.getPosition().x + dx / 2;
        rec.y = nodeFrom.getPosition().y + dy / 2;
    }

    public void draw(Graphics a) {
        if (nodeFrom != null && nodeTo != null) {
            calculateCoordinate();
            //System.out.print("so1=" + nodeFrom.rec.getLocation() + ", ps1=" + ps1.getLocation() + ", ");
            //System.out.print("so2=" + nodeTo.rec.getLocation() + ", ps2=" + ps2.getLocation() + "\n");
        }
        Graphics2D g = (Graphics2D) a;
        Color c = g.getColor();
        g.setColor(this.getColor());
        g.setStroke(new BasicStroke(2));
        //g.drawLine(nodeFrom.getPosition().x, nodeFrom.getPosition().y, nodeTo.getPosition().x, nodeTo.getPosition().y);

        /*
         * int dx = nodeTo.getPosition().x - nodeFrom.getPosition().x, dy =
         * nodeTo.getPosition().y - nodeFrom.getPosition().y; int h = 18; double
         * alpha = Math.atan2(dy, dx); double dcos = h * Math.cos(alpha), dsin =
         * h * Math.sin(alpha); Point p = new Point((int)
         * (nodeTo.getPosition().x - dcos - dsin), (int)
         * (nodeTo.getPosition().y + dcos - dsin)); Point q = new Point((int)
         * (nodeTo.getPosition().x - dcos + dsin), (int)
         * (nodeTo.getPosition().y - dcos - dsin));
         * g.drawLine(nodeTo.getPosition().x, nodeTo.getPosition().y, p.x,
         * p.y); g.drawLine(nodeTo.getPosition().x, nodeTo.getPosition().y,
         * q.x, q.y);
         */

        g.drawLine(ps1.x, ps1.y, ps2.x, ps2.y);

        int dx = ps2.x - ps1.x, dy = ps2.y - ps1.y;
        int h = 8;
        double alpha = Math.atan2(dy, dx);
        double dcos = h * Math.cos(alpha), dsin = h * Math.sin(alpha);
        Point p = new Point((int) (ps2.x - dcos - dsin), (int) (ps2.y + dcos - dsin));
        Point q = new Point((int) (ps2.x - dcos + dsin), (int) (ps2.y - dcos - dsin));

        g.drawLine(ps2.x, ps2.y, p.x, p.y);
        g.drawLine(ps2.x, ps2.y, q.x, q.y);

        ////////////////////////////////////////////////////////////////////////
        updateLoc();
        g.setColor(Color.GRAY);
        //g.fillRect(rec.x - rec.width / 2, rec.y - rec.height / 2, rec.width, rec.height);
        g.fillRoundRect(rec.x - rec.width / 2, rec.y - rec.height / 2, rec.width, rec.height, 6, 6);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(value), rec.x - 2, rec.y + 4);

        g.setColor(c);
    }

    public String getDrawDirection() {
        String drawDirection = "";
        int boxFromX2 = nodeFrom.getLoc().x + nodeFrom.getLoc().width;
        int boxFromY2 = nodeFrom.getLoc().y + nodeFrom.getLoc().height;
        int boxToX2 = nodeTo.getLoc().x + nodeTo.getLoc().width;
        int boxToY2 = nodeTo.getLoc().y + nodeTo.getLoc().height;

        if (nodeFrom.getLoc().x > boxToX2 && boxFromY2 < nodeTo.getLoc().y) {
            drawDirection = "RIGHT_TOP";
        } else if (nodeFrom.getLoc().x > boxToX2 && nodeFrom.getLoc().y > boxToY2) {
            drawDirection = "RIGHT_BOTTOM";
        } else if (boxFromX2 < nodeTo.getLoc().x && nodeFrom.getLoc().y > boxToY2) {
            drawDirection = "LEFT_BOTTOM";
        } else if (boxFromX2 < nodeTo.getLoc().x && boxFromY2 < nodeTo.getLoc().y) {
            drawDirection = "LEFT_TOP";
        } else if (nodeFrom.getLoc().x > boxToX2) {
            drawDirection = "RIGHT";
        } else if (boxFromY2 < nodeTo.getLoc().y) {
            drawDirection = "TOP";
        } else if (nodeFrom.getLoc().y > boxToY2) {
            drawDirection = "BOTTOM";
        } else if (boxFromX2 < nodeTo.getLoc().x) {
            drawDirection = "LEFT";
        }
        return drawDirection;
    }

    private void calculateCoordinate() {
        String drawDirection = getDrawDirection();
        int boxFromX2 = nodeFrom.getLoc().x + nodeFrom.getLoc().width;
        int boxFromY2 = nodeFrom.getLoc().y + nodeFrom.getLoc().height;
        int boxToX2 = nodeTo.getLoc().x + nodeTo.getLoc().width;
        int boxToY2 = nodeTo.getLoc().y + nodeTo.getLoc().height;
        int diffY = 0;
        int diffX = 0;

        if (drawDirection == "BOTTOM" || drawDirection == "TOP") {
            if (nodeFrom.getLoc().x <= nodeTo.getLoc().x && boxFromX2 >= boxToX2) {
                diffX = (nodeTo.getLoc().width) / 2;
                diffX = nodeTo.getLoc().x + diffX;
            } else if (nodeFrom.getLoc().x > nodeTo.getLoc().x && boxFromX2 > boxToX2) {
                diffX = (boxToX2 - nodeFrom.getLoc().x) / 2;
                diffX = nodeFrom.getLoc().x + diffX;
            } else if (nodeFrom.getLoc().x < nodeTo.getLoc().x && boxFromX2 < boxToX2) {
                diffX = (boxFromX2 - nodeTo.getLoc().x) / 2;
                diffX = nodeTo.getLoc().x + diffX;
            } else if (nodeFrom.getLoc().x > nodeTo.getLoc().x && boxFromX2 < boxToX2) {
                diffX = (nodeFrom.getLoc().width) / 2;
                diffX = nodeFrom.getLoc().x + diffX;
            }
            if (diffX == 0) {
                diffX = nodeFrom.getLoc().x + (nodeFrom.getLoc().width / 2);
            }
        }
        if (drawDirection == "RIGHT" || drawDirection == "LEFT") {
            if (nodeFrom.getLoc().y <= nodeTo.getLoc().y && boxFromY2 >= boxToY2) {
                diffY = (boxToY2 - nodeTo.getLoc().y) / 2;
                diffY = nodeTo.getLoc().y + diffY;
            } else if (nodeFrom.getLoc().y > nodeTo.getLoc().y && boxFromY2 > boxToY2) {
                diffY = (boxToY2 - nodeFrom.getLoc().y) / 2;
                diffY = nodeFrom.getLoc().y + diffY;
            } else if (nodeFrom.getLoc().y < nodeTo.getLoc().y && boxFromY2 < boxToY2) {
                diffY = (boxFromY2 - nodeTo.getLoc().y) / 2;
                diffY = nodeTo.getLoc().y + diffY;
            } else if (nodeFrom.getLoc().y > nodeTo.getLoc().y && boxFromY2 < boxToY2) {
                diffY = (nodeFrom.getLoc().height) / 2;
                diffY = nodeFrom.getLoc().y + diffY;
            }
        }

        if (drawDirection == "RIGHT_BOTTOM") {
            this.ps1.x = nodeFrom.getLoc().x + diffX;
            this.ps1.y = nodeFrom.getLoc().y;
            this.ps2.x = boxToX2;
            this.ps2.y = boxToY2 - diffY;
        } else if (drawDirection == "RIGHT_TOP") {
            this.ps1.x = nodeFrom.getLoc().x;
            this.ps1.y = boxFromY2 - diffY;
            this.ps2.x = boxToX2 - diffX;
            this.ps2.y = nodeTo.getLoc().y;
        } else if (drawDirection == "LEFT_BOTTOM") {
            this.ps1.x = boxFromX2 - diffX;
            this.ps1.y = nodeFrom.getLoc().y;
            this.ps2.x = nodeTo.getLoc().x;
            this.ps2.y = boxToY2 - diffY;
        } else if (drawDirection == "LEFT_TOP") {
            this.ps1.x = boxFromX2;
            this.ps1.y = boxFromY2 - diffY;
            this.ps2.x = nodeTo.getLoc().x + diffX;
            this.ps2.y = nodeTo.getLoc().y;
        } else if (drawDirection == "RIGHT") {
            this.ps1.x = nodeFrom.getLoc().x;
            this.ps1.y = diffY;
            this.ps2.x = boxToX2;
            this.ps2.y = diffY;
        } else if (drawDirection == "TOP") {
            this.ps1.x = diffX;
            this.ps1.y = boxFromY2;
            this.ps2.x = diffX;
            this.ps2.y = nodeTo.getLoc().y;
        } else if (drawDirection == "BOTTOM") {
            this.ps1.x = diffX;
            this.ps1.y = nodeFrom.getLoc().y;
            this.ps2.x = diffX;
            this.ps2.y = boxToY2;
        } else if (drawDirection == "LEFT") {
            this.ps1.x = boxFromX2;
            this.ps1.y = diffY;
            this.ps2.x = nodeTo.getLoc().x;
            this.ps2.y = diffY;
        }
    }

    public String toString() {
        return "Edge{ id=" + ID + ", nodeFrom=" + (nodeFrom == null ? "null" : nodeFrom.getName()) + ", nodeTo=" +
                (nodeTo == null ? "null" : nodeTo.getName()) + ", value=" + (value == null ? "null" : value) +
                ", color=" + color + ", dij=" + dijkstra + ", imp=" + improvement + "}";
    }

    @Override
    public int compareTo(Edge e) {
        if (value.intValue() > e.value.intValue()) {
            return 1;
        } else if (value.intValue() < e.value.intValue()) {
            return -1;
        }
        return 0;
    }
}