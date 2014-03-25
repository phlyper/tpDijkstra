package tpgraphe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Graphe extends JFrame implements ActionListener {

    protected Graph mygraph = new Graph();
    protected ZoneDess zone = new ZoneDess(mygraph);
    protected Dijkstra dijAlgo = new Dijkstra(mygraph);
    private JButton dijkstra = new JButton("Dijkstra");
    private JButton amelioration = new JButton("Amelioration");
    private JButton startn = new JButton("Select Node");
    private JComboBox sommets = new JComboBox();
    //
    private JTextArea degre = new JTextArea();
    private JScrollPane zoneScrolable = new JScrollPane(degre);

    public Graphe() {

        setTitle("..:: TP GRAPHE ::..");
        JButton desAr = new JButton("Dessiner les Arcs (Edges)");
        JButton desSo = new JButton("Dessiner les Sommets (GraphNodes)");
        JButton clear = new JButton("Vider le Graphe");

        JButton afichdeg = new JButton("Afficher les degres");
        JButton colore = new JButton("Colore le graphe");
        JButton rotationd = new JButton("Rotation D");
        JButton rotationg = new JButton("Rotation G");
        JButton generate = new JButton("Genere MV");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(950, 650);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.add(desAr);
        this.add(desSo);
        this.add(clear);
        this.add(startn);
        this.add(sommets);
        this.add(dijkstra);
        this.add(amelioration);

        this.add(zoneScrolable);
        this.add(afichdeg);
        this.add(colore);
        this.add(rotationd);
        this.add(rotationg);
        this.add(generate);


        this.add(zone);
        dijkstra.setEnabled(false);
        amelioration.setEnabled(false);

        zone.setBounds(10, 50, 600, 500);

        desSo.setBounds(40, 10, 150, 30);
        desAr.setBounds(230, 10, 150, 30);
        clear.setBounds(430, 10, 150, 30);

        startn.setBounds(10, 560, 100, 40);
        sommets.setBounds(120, 560, 100, 40);
        dijkstra.setBounds(240, 560, 180, 40);
        amelioration.setBounds(430, 560, 180, 40);

        zoneScrolable.setBounds(620, 10, 300, 500);
        afichdeg.setBounds(620, 515, 150, 30);
        colore.setBounds(770, 515, 150, 30);
        rotationd.setBounds(620, 550, 150, 30);
        rotationg.setBounds(770, 550, 150, 30);
        generate.setBounds(620, 585, 150, 30);

        degre.setLineWrap(true);
        degre.setWrapStyleWord(true);
        degre.setFont(new Font("Arial", Font.PLAIN, 12));

        sommets.setFont(new Font("Arial", Font.PLAIN, 16));

        //// Listener ////////////////////////////////////////////////////////////////////////////////////
        desAr.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked(evt);
            }
        });
        desSo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked1(evt);
            }
        });
        clear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zone.clear();
            }
        });

        rotationd.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rotationd(evt);
            }
        });
        rotationg.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rotationg(evt);
            }
        });
        colore.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked3(evt);
            }
        });
        afichdeg.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked2(evt);
            }
        });

        generate.addActionListener(this);

        ///////////////////////////////////////////////////////////////////////////////
        startn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dijkstra.setEnabled(false);
                sommets.removeAllItems();
                generategraph();
                //dijAlgo.setStartNode(mygraph.getAllNodes().get(0).getName());
                if (dijAlgo.getAllStartNode().size() > 0) {
                    for (GraphNode n : dijAlgo.getAllStartNode()) {
                        sommets.addItem(n.getName());
                        //System.out.println("le sommet du graphe est :" + n.getName());
                    }

                    if (sommets.getSelectedIndex() != -1) {
                        //JOptionPane.showMessageDialog(zone, "Le sommet du graphe par defaut est 0 :" + dijAlgo.getStartNode().getName(), "Graphe", JOptionPane.INFORMATION_MESSAGE);
                        //dijAlgo.setStartNode((String) sommets.getSelectedItem());
                        dijAlgo.selectStartNode((String) sommets.getSelectedItem());
                        dijkstra.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(zone, "Racine -1 Graphe invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(zone, "Noeud GraphNode invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                }

                //dijAlgo.selectStartNode();
                updateSTRzone();

                //dijAlgo.print();
            }
        });

        sommets.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sommets.getSelectedIndex() != -1) {
                    //JOptionPane.showMessageDialog(zone, "Le sommet du graphe est 1 : " + sommets.getSelectedItem().toString(), "Graphe", JOptionPane.INFORMATION_MESSAGE);
                    //dijAlgo.setStartNode((String) sommets.getSelectedItem());
                    dijAlgo.selectStartNode((String) sommets.getSelectedItem());
                    //dijAlgo.selectStartNode();
                    //updateSTRzone();
                }
                updateSTRzone();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////

        dijkstra.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                //if (generategraph() == true) {
                if (dijAlgo.getStartNode() != null) {
                    if (dijAlgo.selectStartNode() == true) {
                        updateSTRzone();

                        mygraph.resetDij();
                        dijAlgo.go();
                        amelioration.setEnabled(true);
                        updateDIJzone();
                    } else {
                        JOptionPane.showMessageDialog(zone, "Graphe invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(zone, "GraphNode invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        amelioration.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (dijAlgo.getStartNode() != null) {
                    //if (dijAlgo.selectStartNode() == true) {
                    //mygraph.resetImp();
                    //System.out.println(" *** Avant amelioraation ***");
                    //mygraph.print();
                    boolean b = dijAlgo.improvement();
                    if (b == false) {
                        JOptionPane.showMessageDialog(zone, "Le graphe contient un cycle absorbant ( " + dijAlgo.v + " )", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                    } else {
                        amelioration.setEnabled(false);
                    }
                    //System.out.println(" *** Apres amelioraation ***");
                    updateIMPzone();
                    //} else {
                    //  JOptionPane.showMessageDialog(zone, "GraphNode invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                    //}
                } else {
                    JOptionPane.showMessageDialog(zone, "Graphe invalide", "Erreur Graphe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean generategraph() {
        mygraph.clear();
        if (zone.sommets.isEmpty() || zone.arcs.isEmpty()) {
            return false;
        } else if (!zone.sommets.isEmpty() && !zone.arcs.isEmpty()) {
            for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
                GraphNode s = (GraphNode) iter.next();
                //if(s.arcs)
                mygraph.AddNode(String.valueOf(s.getName()));
            }

            for (Iterator iter = zone.arcs.iterator(); iter.hasNext();) {
                Edge a = (Edge) iter.next();
                a.setColor(Color.BLACK);
                //if(s.arcs)
                mygraph.AddEdge(String.valueOf(a.getNodeFrom().getName()), String.valueOf(a.getNodeTo().getName()), a.getValue());
            }
        }

        //mygraph.print();
        return true;
    }

    public void updateDIJzone() {
        for (GraphNode gn : mygraph.getAllNodes()) {
            for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
                GraphNode s = (GraphNode) iter.next();
                if (s.getName().equals(gn.getName())) {
                    s.setDistance(gn.getDistance());
                }
            }
        }

        for (Edge e : mygraph.getDijEdges()) {
            for (Iterator iter = zone.arcs.iterator(); iter.hasNext();) {
                Edge a = (Edge) iter.next();
                if (a.getNodeFrom().getName().equals(e.getNodeFrom().getName()) && a.getNodeTo().getName().equals(e.getNodeTo().getName()) && a.getValue() == e.getValue()) {
                    a.setColor(Color.ORANGE);
                }
            }
        }
        //mygraph.print();
        //dijAlgo.print();
        zone.repaint();
    }

    public void updateIMPzone() {
        for (GraphNode gn : mygraph.getAllNodes()) {
            for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
                GraphNode s = (GraphNode) iter.next();
                if (s.getName().equals(gn.getName())) {
                    s.setDistance(gn.getDistance());
                }
            }
        }

        for (Edge e : mygraph.getImpEdges()) {
            for (Iterator iter = zone.arcs.iterator(); iter.hasNext();) {
                Edge a = (Edge) iter.next();
                if (a.getNodeFrom().getName().equals(e.getNodeFrom().getName()) && a.getNodeTo().getName().equals(e.getNodeTo().getName()) && a.getValue() == e.getValue()) {
                    a.setColor(Color.GREEN);
                }
            }
        }
        //mygraph.print();
        //dijAlgo.print();
        zone.repaint();
    }

    public void updateSTRzone() {
        for (GraphNode gn : mygraph.getAllNodes()) {
            for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
                GraphNode s = (GraphNode) iter.next();
                if (s.getName().equals(gn.getName())) {
                    s.setDistance(gn.getDistance() == Integer.MAX_VALUE ? 0 : gn.getDistance());
                }
            }
        }

        for (Edge e : mygraph.getAllEdges()) {
            for (Iterator iter = zone.arcs.iterator(); iter.hasNext();) {
                Edge a = (Edge) iter.next();
                if (a.getNodeFrom().getName().equals(e.getNodeFrom().getName()) && a.getNodeTo().getName().equals(e.getNodeTo().getName()) && a.getValue() == e.getValue()) {
                    a.setColor(Color.BLACK);
                }
            }
        }
        //mygraph.print();
        //dijAlgo.print();
        zone.repaint();
    }

    private void MouseClicked(java.awt.event.MouseEvent evt) {
        zone.desArcs = true;
        zone.desSommets = false;
    }

    private void MouseClicked1(java.awt.event.MouseEvent evt) {
        zone.desSommets = true;
        zone.desArcs = false;
        zone.sommet1 = null;
        zone.sommet2 = null;
    }

    private void rotationd(java.awt.event.MouseEvent evt) {
        int x = 0, dx = zone.getWidth() / 2;
        int y = 0, dy = zone.getHeight() / 2;
        int c;
        for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
            GraphNode ar = (GraphNode) iter.next();
            x = ar.getPosition().x - dx;
            y = dy - ar.getPosition().y;

            c = x;
            x = (int) (x * Math.cos(-Math.PI / 18) - y * Math.sin(-Math.PI / 18));
            y = (int) (c * Math.sin(-Math.PI / 18) + y * Math.cos(-Math.PI / 18));
            x += dx - 16;
            y = dy - y - 11;

            ar.setPosition(new Point(x, y));

        }
        zone.repaint();
    }

    private void rotationg(java.awt.event.MouseEvent evt) {
        int x = 0, dx = zone.getWidth() / 2;
        int y = 0, dy = zone.getHeight() / 2;
        int c;
        for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
            GraphNode ar = (GraphNode) iter.next();
            x = ar.getPosition().x - dx;
            y = dy - ar.getPosition().y;
            c = x;
            x = (int) (x * Math.cos(Math.PI / 18) - y * Math.sin(Math.PI / 18));
            y = (int) (c * Math.sin(Math.PI / 18) + y * Math.cos(Math.PI / 18));
            x += dx - 11;
            y = dy - y - 16;

            ar.setPosition(new Point(x, y));

        }
        zone.repaint();
    }

    private void MouseClicked2(java.awt.event.MouseEvent evt) {
        String msgdegre = "";

        Collections.sort(zone.sommets);
        for (Iterator iter = zone.sommets.iterator(); iter.hasNext();) {
            GraphNode gn = (GraphNode) iter.next();

            msgdegre = msgdegre + "le degree de sommet " + gn.getName() + " est :" + gn.getDegre() + ".\n";
            this.degre.setText(msgdegre);
        }
        this.degre.append("\n" + zone.toString());
        this.degre.append("\n" + mygraph.toString());
    }

    private void MouseClicked3(java.awt.event.MouseEvent evt) {
        Collections.sort(zone.sommets);
        ArrayList<GraphNode> inter = (ArrayList<GraphNode>) zone.sommets.clone();
        ArrayList dejacolor = new ArrayList();
        for (Iterator iter1 = inter.iterator(); iter1.hasNext();) {
            GraphNode q = (GraphNode) iter1.next();
            q.setIscolor(false);
        }
        Colors palet = new Colors();
        int i = 0;
        int h = inter.size();
        GraphNode a = null;
        Iterator iter;
        while (h != 0) {
            for (iter = inter.iterator(); iter.hasNext();) {
                a = (GraphNode) iter.next();
                if (a.isColor() == false) {
                    break;
                }
            }

            a.setColor(palet.palet[i]);
            a.setIscolor(true);
            h--;
            zone.repaint();
            dejacolor.add(a);
            while (iter.hasNext()) {
                GraphNode s = (GraphNode) iter.next();

                if (s.isColor() == false && isAdjoint(dejacolor, s) == false) {
                    s.setColor(palet.palet[i]);
                    System.out.println(s.getName() + "  " + s.getColor());
                    s.setIscolor(true);
                    h--;
                    dejacolor.add(s);
                }
            }
            i += 1;
            dejacolor.clear();
        }

    }

    public Boolean isAdjoint(ArrayList l, GraphNode b) {
        Boolean resultat = false;
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            GraphNode q = (GraphNode) iter.next();
            for (Iterator iter1 = zone.arcs.iterator(); iter1.hasNext();) {
                Edge ar = (Edge) iter1.next();
                if ((ar.getNodeFrom().getName().equals(q.getName()) && ar.getNodeTo().getName().equals(b.getName())) || (ar.getNodeFrom().getName().equals(b.getName()) && ar.getNodeTo().getName().equals(q.getName()))) {
                    resultat = true;
                    break;
                }
            }
        }
        return resultat;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mygraph.gerateGraph();
        degre.append(mygraph.printMV());
    }
}
