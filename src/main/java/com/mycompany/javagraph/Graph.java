package com.mycompany.javagraph;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
/**
 * Méthodes principales, avec les tableau de villes et de routes
 */
public class Graph {
    private final ArrayList<Link> links = new ArrayList<>();
    private final ArrayList<Node> nodes = new ArrayList<>();
    
    private boolean isDrawn = false;
    
    /**ajouter ville*/
    public void addNode(Node n) { // Returns index of added node
        nodes.add(n);
    }
    
    /**ajouter un liens au graph */
    public void addLink(String type, double length, Node n1, Node n2) {
        // check if link is already is created
        for (Link lnk : n1.getLinks()) {
            if (lnk.getNodes()[0] == n2 || lnk.getNodes()[1] == n2) {
                System.out.println("Link " + n1.getName()+"-"+n2.getName()+ " is already created");
                return; // do nothing
            }
        }
        Link lnk = new Link(type, length, n1, n2);
        links.add(lnk);
        n1.addLink(lnk);
        n2.addLink(lnk);
    }
    /**return tableau ville*/
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    /**return tableau routes*/
    public ArrayList<Link> getLinks() {
        return links;
    }
    /**trouver ville par nom*/
    public static Node findNode(String name, ArrayList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equalsIgnoreCase(name)) {
                return nodes.get(i);
            }
        }
        return null;
    }
    
    /**trouver route par nom*/
    public static Link findLink(String name, ArrayList<Link> links) {
        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getName().equalsIgnoreCase(name)) {
                return links.get(i);
            }
        }
        return null;
    }
    /**trouver route par les deux villes*/
    public Link findLinkHavingNodes(Node n1, Node n2) {
        if (n1 == n2) {
            return new Link("", 0.0, n1, n1);
        }
        for (Link lnk : this.links) {
            if (lnk.getNodes()[0] == n1 || lnk.getNodes()[1] == n1) {
                if (lnk.getNodes()[0] == n2 || lnk.getNodes()[1] == n2) {
                    return lnk;
                }
            }
        }
        return null;
    }
    /**trouver route par type et longueur*/
    public static Link findLink(String type, int length, ArrayList<Link> links) {
        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getType().equalsIgnoreCase(type) && links.get(i).getLength() == length) {
                return links.get(i);
            }
        }
        return null;
    }
    /**Graph sans couleurs*/
    public void setAllInActive() {
        // setting inactive all nodes
        for (int i=0; i<this.nodes.size(); i++) {
            this.nodes.get(i).setInActive();
            this.nodes.get(i).setIsSelected(false);
        }
        // setting inactive all links
        for (Link lnk : links) {
            lnk.setInActive();
            lnk.setNotSelected();
        }
    }
    /**trouver la route la plus courte*/
    public Link getShortestLink(Map<String, Double> links) {
        Link sl = null;
        for (String ln : links.keySet()) {
            Link lnk = Graph.findLink(ln, this.getLinks());
            if (lnk == null) lnk = new Link("", 0.0);
            if (sl == null) sl = lnk;
            else if (sl.getLength() > (lnk.getLength() + links.get(lnk))) sl = lnk;
        }
        return sl;
    }
    /**plsu court chemin entre deux villes*/
    public ArrayList<Node> getShortestPathNodes(Node src, Node dest) {
        ArrayList<NodeDistance> nodesList = getShortestPaths(src); // this list contains shortest paths from given source to every node
        ArrayList<Node> path = new ArrayList<>();
        NodeDistance temp = NodeDistance.find(dest, nodesList);
        while(true) {
            path.add(temp.node);
            if (temp.node == src) break;
            temp = NodeDistance.find(temp.previous, nodesList);
        }
        // printing the shortest path nodes
        /* System.out.println("From "+src.getName()+" to "+dest.getName());
        for (int i=path.size()-1; i >= 0; i--) {
            System.out.print(path.get(i).getName()+"\t");
        }
        System.out.println(); */
        return path;
    }
    /**Not supported yet.*/
    Link findLinkHavingNodes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**NodeDistance*/
    private class NodeDistance {
        public Node node;
        public Node previous;
        public double distance;
        
        public NodeDistance(Node n) {
            node = n;
            distance = Double.MAX_VALUE;
            previous = null;
        }
        /**trouver la ville*/
        public static NodeDistance find(Node n, ArrayList<NodeDistance> list) {
            for (NodeDistance nd : list) {
                if (nd.node == n) return nd;
            }
            return null;
        }
        /**trouver avec la ville precedente*/
        public static NodeDistance findWithPrevious(Node prev, ArrayList<NodeDistance> list) {
            for (NodeDistance nd : list) {
                if (nd.previous == prev) return nd;
            }
            return null;
        }
    }
    /**trouver la route la plus courte*/
    private ArrayList<NodeDistance> getShortestPaths(Node src) {
        ArrayList<NodeDistance> nodesList = new ArrayList<>();
        // setting infinit value for all nodes
        for (Node n : this.nodes) {
            nodesList.add(new NodeDistance(n));
        }
        // setting zero for src
        NodeDistance.find(src, nodesList).distance = 0.0;
        
        Set<NodeDistance> settledNodes = new HashSet<>();
        Set<NodeDistance> unsettledNodes = new HashSet<>();
        
        unsettledNodes.add(NodeDistance.find(src, nodesList));
        
        while(!unsettledNodes.isEmpty()) {
            NodeDistance current = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(current);
            for (Node neighbour : current.node.getNeighbours()) {
                if (settledNodes.contains(NodeDistance.find(neighbour, nodesList))) continue;
                NodeDistance neighbourDistance = calculateMinDistance(neighbour, current.node, nodesList);
                unsettledNodes.add(neighbourDistance);
            }
            settledNodes.add(current);
        }
        
        return nodesList;
    }
    /**distance mini entre deux villes*/
    private NodeDistance calculateMinDistance(Node dst, Node src, ArrayList<NodeDistance> list) {
        Link lnk = this.findLinkHavingNodes(dst, src);
        NodeDistance nd = NodeDistance.find(dst, list);
        NodeDistance srcDistance = NodeDistance.find(src, list);
        if (nd.distance == Double.MAX_VALUE) {
            nd.distance = lnk.getLength() + srcDistance.distance;
            nd.previous = src;
        }
        else {
            Double newDist = lnk.getLength() + srcDistance.distance;
            if (nd.distance > newDist) {
                nd.distance = newDist;
                nd.previous = src;
            }
        }
        return nd;
    }
    /**tropuver la ville la plus proche*/
    private NodeDistance getLowestDistanceNode(Set<NodeDistance> list) {
        NodeDistance nd = null;
        for (NodeDistance n : list) {
            if (nd == null) nd = n;
            else if (n.distance < nd.distance) nd = n;
        }
        return nd;
    }
    /**afficher distance ville*/
    private void printNodeDistances(ArrayList<NodeDistance> list) {
        for (NodeDistance nd : list) {
            System.out.println(nd.node.getName()+": "+nd.distance);
        }
    }    
    /**dessiner graph*/
    public void draw(Graphics2D g2d, int width, int height) {
        final int NW = 40;
        final int NH = 40;
        Random rand = new Random();
        
        double x, y;
              
        for (int i=0; i<nodes.size(); i++) {
            Node n = this.nodes.get(i);
            if (!isDrawn) {
                do {
                    x = rand.nextInt(width);
                    y = rand.nextInt(height);
                    n.setFrame(x, y, NW, NH);
                } while(isNodeIntersecting(n));
            }
            n.draw(g2d);
        }
        
        for (int i = 0; i < this.links.size(); i++) {
            Link lnk = this.links.get(i);
            lnk.draw(g2d);
        }
        
        isDrawn = true; // No need for drawing again and again
    }
    /**les villes se superpose*/
    private boolean isNodeIntersecting(Node n) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if (nodes.get(i) != n && n.intersects(nodes.get(i))) {
                return true;
            }
        }
        return false;
    }
    /**lecture fichier*/
    public static Graph loadFromFile(String path) throws FileNotFoundException, IOException {
        Graph g = new Graph();
        
        int lineCount = 0;
        BufferedReader br = new BufferedReader(new java.io.FileReader(path));
        String line = "";
        
        while ((line = br.readLine()) != null) { //read each line of the csv doc
            lineCount++;
            String[] firstTreatment = line.split(":", 2); //seperate the line in two: the city and all his neighbors
            if (firstTreatment.length == 1) {
                System.out.println("Error on line "+lineCount);
                continue;
            };
            String[] city = firstTreatment[0].split(",");
            Node currentNode = new Node(city[1], city[0].charAt(0)+"");
            
            Node foundNode = Graph.findNode(currentNode.getName(), g.getNodes());
            if (foundNode == null) g.addNode(currentNode);
            else currentNode = foundNode;
            
            String[] secondTreatment = firstTreatment[1].split(";", 0); //seperate all the neighbors
            for (String neigborLink: secondTreatment) {
                String[] thirdTreatment = neigborLink.split("::"); //seperate edge and neighbor
                String[] link = thirdTreatment[0].split(",");
                String[] neighbour = thirdTreatment[1].split(",");
                
                Node n = new Node(neighbour[1], neighbour[0].charAt(0)+"");
                Node fn = Graph.findNode(n.getName(), g.getNodes());
                if (fn == null) g.addNode(n);
                else n = fn;
                
                g.addLink(link[0].charAt(0)+"", Double.parseDouble(link[1]), currentNode, n);
            }
        }
        
        return g;
    }
}
