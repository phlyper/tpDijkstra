package tpgraphe;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ZoneDess extends JPanel {

    public ArrayList<GraphNode> sommets = new ArrayList<GraphNode>();
    public ArrayList<Edge> arcs = new ArrayList<Edge>();
    private Graph mygraph = null;
    protected GraphNode mo = null;
    protected char num;
    protected Boolean desArcs;
    protected Boolean desSommets;
    protected GraphNode sommet1 = null;
    protected GraphNode sommet2 = null;

    public ZoneDess() {
        num = 'A';
        mo = null;
        desArcs = false;
        desSommets = true;
        sommet1 = null;
        sommet2 = null;
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        //setBounds(10, 100, 500, 500);
        setBorder(javax.swing.BorderFactory.createEtchedBorder(1));
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked(evt);
            }
        });
        this.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Mousemove(evt);
            }
        });

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Mousepres(evt);
            }
        });

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Mouserelea(evt);
            }
        });
    }

    public ZoneDess(Graph g) {
        this();
        mygraph = g;
        //arcs = g.getAllEdges();
        //sommets = g.getAllNodes();
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {

        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Iterator iter = arcs.iterator(); iter.hasNext();) {
            Edge ar = (Edge) iter.next();
            ar.draw(g);
        }

        for (Iterator iter = sommets.iterator(); iter.hasNext();) {
            GraphNode s = (GraphNode) iter.next();
            s.draw(g);
        }
    }

    public void clear() {
        sommets.clear();
        arcs.clear();
        this.removeAll();
        this.repaint();
        num = 'A';
    }

    public void ajouteGraphNode(GraphNode s) {

        sommets.add(s);
        repaint();
    }

    public void supprimesommet(GraphNode s) {

        sommets.remove(s);
    }

    public void supprimearcs(GraphNode s) {
        ArrayList la = new ArrayList();
        for (Iterator iter = arcs.iterator(); iter.hasNext();) {
            Edge a = (Edge) iter.next();
            if (a.getNodeFrom() == s || a.getNodeTo() == s) {
                la.add(a);
            }
        }

        for (Iterator iter = la.iterator(); iter.hasNext();) {
            Edge a = (Edge) iter.next();
            supprimearcs(a);
        }
        la.clear();
    }

    public void supprimearcs(Edge a) {
        a.getNodeFrom().removeEdge(a);
        a.getNodeTo().removeEdge(a);
        arcs.remove(a);
    }

    public void nettoyer() {
        sommets.clear();
        arcs.clear();
        repaint();
    }

    private void Mousepres(java.awt.event.MouseEvent evt) {
        List sel = this.findsommet(evt.getPoint());
        if (sel.isEmpty()) {
            return;
        }
        mo = (GraphNode) sel.get(0);
    }

    private void Mouserelea(java.awt.event.MouseEvent evt) {
        mo = null;
    }

    private void MouseClicked(java.awt.event.MouseEvent evt) {
        if (SwingUtilities.isLeftMouseButton(evt)) {
            leftClickAction(evt);
        } else {
            rightClickAction(evt);
        }
    }

    private void Mousemove(java.awt.event.MouseEvent evt) {

        if (mo != null) {
            Point p = evt.getPoint();
            p.x -= mo.getLoc().width / 2;
            p.y -= mo.getLoc().height / 2;
            mo.setPosition(p);
            this.repaint();
        }
    }

    protected void rightClickAction(MouseEvent e) {
        List selectedDrawables = this.findsommet(e.getPoint());

        if (!selectedDrawables.isEmpty()) {
            GraphNode s = (GraphNode) selectedDrawables.get(0);
            this.supprimearcs(s);
            this.supprimesommet(s);
        }

        selectedDrawables.clear();

        selectedDrawables = this.findarc(e.getPoint());

        if (!selectedDrawables.isEmpty()) {
            Edge a = (Edge) selectedDrawables.get(0);
            this.supprimearcs(a);
        }
        selectedDrawables.clear();

        repaint();
    }

    protected void leftClickAction(MouseEvent e) {
        if (desSommets == true) {

            Point p = e.getPoint();
            GraphNode rect = createsommet(e);
            if (this.isFree(rect.getLoc())) {
                this.ajouteGraphNode(rect);
                num += 1;
            }
        } else {

            List sel = this.findsommet(e.getPoint());
            if (sel.isEmpty()) {
                return;
            }
            if (sommet1 == null) {
                sommet1 = (GraphNode) sel.get(0);
            } else {
                sommet2 = (GraphNode) sel.get(0);
                if (!sommet1.getName().equals(sommet2.getName()) && existe(sommet1, sommet2) == false) {
                    Edge v = new Edge(sommet1, sommet2);
                    String valarc = JOptionPane.showInputDialog(this, "Le cout de l\'arc :", "");
                    try {
                        //v.setVal(Integer.parseInt(valarc));
                        v.setValue(Float.parseFloat(valarc));
                    } catch (NumberFormatException ex) {
                        //v.setVal(0);
                        v.setValue(0f);
                    }

                    this.arcs.add(v);
                    /*sommet1.addEdge(v);
                     sommet2.addEdge(v);*/
                    sommet1.setDegre(sommet1.getDegre() + 1);
                    sommet2.setDegre(sommet2.getDegre() + 1);
                    sommet1 = null;
                    sommet2 = null;

                    this.repaint();
                } else {
                    sommet2 = null;
                }
            }
        }
    }

    public ArrayList findsommet(Point p) {
        ArrayList l = new ArrayList();
        for (Iterator iter = sommets.iterator(); iter.hasNext();) {
            GraphNode element = (GraphNode) iter.next();
            if (element.getLoc().contains(p)) {
                l.add(element);
            }
        }
        return l;
    }

    public ArrayList findarc(Point p) {
        ArrayList l = new ArrayList();
        for (Iterator iter = arcs.iterator(); iter.hasNext();) {
            Edge element = (Edge) iter.next();
            if (element.getloc().contains(p)) {
                l.add(element);
            }
        }
        return l;
    }

    public boolean isFree(Rectangle rect) {
        for (Iterator iter = sommets.iterator(); iter.hasNext();) {
            GraphNode element = (GraphNode) iter.next();
            if (element.getLoc().intersects(rect)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAlone(GraphNode s) {
        Rectangle rect = s.getLoc();
        for (Iterator iter = sommets.iterator(); iter.hasNext();) {
            GraphNode element = (GraphNode) iter.next();
            if (!element.equals(s) & element.getLoc().intersects(rect)) {
                return false;
            }
        }
        return true;
    }

    private GraphNode createsommet(MouseEvent e) {
        Point p = e.getPoint();
        return new GraphNode(p, String.valueOf(num));
    }

    public boolean existe(GraphNode a, GraphNode b) {

        for (Iterator iter = arcs.iterator(); iter.hasNext();) {
            Edge element = (Edge) iter.next();
            if ((element.getNodeFrom() == a && element.getNodeTo() == b) || (element.getNodeFrom() == a && element.getNodeTo() == b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "GraphNodes :\n";
        for (Iterator iter = sommets.iterator(); iter.hasNext();) {
            GraphNode e = (GraphNode) iter.next();
            s += e.toString() + "\n";
        }

        s += "\nEdges :\n";
        for (Iterator iter = arcs.iterator(); iter.hasNext();) {
            Edge e = (Edge) iter.next();
            s += e.toString() + "\n";
        }

        return s;
    }
}