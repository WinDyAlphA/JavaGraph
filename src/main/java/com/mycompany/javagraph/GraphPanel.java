/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.javagraph;

import java.awt.Graphics;
import java.awt.Graphics2D;


//Class qui definié l'écran du graphe
/**
 * création graphique du graph
 */
public class GraphPanel extends javax.swing.JPanel {
    
    public Graph graph = new Graph();

    /**
     * @param graph
     */
    public GraphPanel(Graph graph) {
        this.graph = graph;
        initComponents();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.setSize(950, 800);
        g2d.clearRect(0, 0, 950, 800);
        graph.draw(g2d, this.getWidth() - 50, this.getHeight() - 50);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(700, 500));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // Verifie sur quel type on clique (noeuds ou lien) puis indique à qui il est relié ou qui il relie
        graph.setAllInActive();
        int x = evt.getX();
        int y = evt.getY();
        boolean isFoundAny = false;
        //verifie si on clique dans un noeud
        for (int i=0; i<graph.getNodes().size(); i++) {
            Node n = graph.getNodes().get(i);
            if (n.contains(x, y)) {
                n.setIsSelected(!n.isSelected());
                isFoundAny = true;
                
                if (HomeScreen.hs != null) HomeScreen.hs.setActiveNodeCategory(n.getType());
                if (Screen1.sc != null) Screen1.sc.setSelectedNode(n);
                
                break;
            }
        }
        // verifie si on clique dans un lien
        if (!isFoundAny) 
            for (Link lnk : graph.getLinks()) {
                if (lnk.intersects(x, y, 5, 5)) {
                    lnk.setSelected();
                    isFoundAny = true;

                    if (HomeScreen.hs != null) HomeScreen.hs.setActiveLinkCategory(lnk.getType());
                    if (Screen1.sc != null) Screen1.sc.setSelectedLink(lnk);

                    break;
                }
            }
        if (!isFoundAny) {
            graph.setAllInActive();
        }
        this.repaint();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
