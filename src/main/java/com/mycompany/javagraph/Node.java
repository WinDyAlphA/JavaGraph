package com.mycompany.javagraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Villes
 */
public class Node extends Ellipse2D.Double {

    private String name;
    private String type; // V, L, R
    private boolean isActive;
    private boolean isSelected;
    private final ArrayList<Link> links = new ArrayList<>();
    /**return selected*/
    public boolean isSelected() {
        return isSelected;
    }
    /**set selected*/
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    /**get name*/
    public String getName() {
        return name;
    }
    /**set name*/
    public void setName(String name) {
        this.name = name;
    }
    /**get type*/
    public String getType() {
        return type;
    }
    /**set type*/
    public void setType(String type) {
        this.type = type;
    }
    /**retourné les routes auxqeuls il est relié*/
    public ArrayList<Link> getLinks() {
        return links;
    }
    /**ajouter routes dans ses voisins*/
    public void addLink(Link lnk) {
        this.links.add(lnk);
    }
    /**crée ville*/
    public Node(String name, String type) {
        super(0, 0, 40, 40);
        this.name = type+","+name;
        this.type = type;
    }
    /**changer activation ville*/
    public void toggleActive() {
        isActive = !isActive;
    }
    /**set inactive*/
    public void setInActive() {
        isActive = false;
    }
    /**set ville activé*/
    public void setActive() {
        isActive = true;
    }
    /**get ville activé*/
    public boolean isActive() {
        return isActive;
    }
    /**true si 2-distance*/
    public boolean isAt2Distance(Node n) {
        for (Node neighbour : this.getNeighbours()) {
            if (neighbour.getNeighbours().contains(n) && !this.getNeighbours().contains(n)) {
                return true;
            }
        }
        return false;
    }
    /**ville a 2-distance*/
    public int totalCitiesAt2Distance() {
        int count = 0;
        for (Node neighbour : this.getNeighbours()) {
            for (Node n : neighbour.getNeighbours()) {
                if (n != this && !this.getNeighbours().contains(n)) {
                    if (n.getType().equalsIgnoreCase("V")) count++;
                }
            }
        }
        return count;
    }
    /**Restaurant a 2-distance*/
    public int totalRestaurantsAt2Distance() {
        int count = 0;
        for (Node neighbour : this.getNeighbours()) {
            for (Node n : neighbour.getNeighbours()) {
                if (n != this && !this.getNeighbours().contains(n)) {
                    if (n.getType().equalsIgnoreCase("R")) count++;
                }
            }
        }
        return count;
    }
    /**Loisir a 2-distance*/
    public int totalLeisuresAt2Distance() {
        int count = 0;
        for (Node neighbour : this.getNeighbours()) {
            for (Node n : neighbour.getNeighbours()) {
                if (n != this && !this.getNeighbours().contains(n)) {
                    if (n.getType().equalsIgnoreCase("L")) count++;
                }
            }
        }
        return count;
    }
    /**Voisin de la ville*/
    public ArrayList<Node> getNeighbours() {
        ArrayList<Node> neighbours = new ArrayList<>();
        for (Link lnk : this.links) {
            if (lnk.getNodes()[0] == this) {
                // if (!neighbours.contains(lnk.getNodes()[1]))
                    neighbours.add(lnk.getNodes()[1]);
            } else {
                // if (!neighbours.contains(lnk.getNodes()[0]))
                    neighbours.add(lnk.getNodes()[0]);
            }
        }
        return neighbours;
    }
    /**Ville qui se chevauchent*/
    public boolean intersects(Node n) {
        double n1x1 = this.getX();
        double n1x2 = n1x1 + this.getWidth();
        double n1y1 = this.getY();
        double n1y2 = n1y1 + this.getHeight();

        double n2x1 = n.getX();
        double n2x2 = n2x1 + n.getWidth();
        double n2y1 = n.getY();
        double n2y2 = n2y1 + n.getHeight();

        if (n1x1 > n2x2 || n1x2 < n2x1) {
            return false;
        }
        else if (n1y1 > n2y2 || n1y2 < n2y1) {
            return false;
        }
        return true;
    }
    /**dessiner*/
    public void draw(Graphics2D g2d) {
        if (this.isActive) {
            if (this.isSelected) g2d.setColor(Color.blue);
            else g2d.setColor(Color.red);
            g2d.fill(this);
            g2d.drawString(name, (float) x, (float) y);
            g2d.setColor(Color.black);
        }
        else if (this.isSelected) {
            g2d.setColor(Color.blue);
            g2d.fill(this);
            g2d.drawString(name, (float) x, (float) y);
            g2d.setColor(Color.black);
        }
        else {
            g2d.setColor(Color.black);
            g2d.drawString(name, (float) x, (float) y);
            g2d.clearRect((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
            g2d.draw(this);
        }
    }

}
