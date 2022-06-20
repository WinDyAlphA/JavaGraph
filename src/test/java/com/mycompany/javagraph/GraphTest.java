/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.javagraph;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author simp
 */
public class GraphTest {
    Node n1;
    Node n2;
    Graph g;
    Link l;
    
    public GraphTest() {
    }
    
    @BeforeEach
    public void setUp() {
        n1 = new Node("Ville1","V");
        n2 = new Node("Ville2","V");
        l = new Link("A",60,n1,n2);
        g = new Graph();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addNode method, of class Graph.
     */
    @Test
    public void testAddNode() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(n1);
        g.addNode(n1);
        assertEquals(g.getNodes(),nodes);
    }

    /**
     * Test of addLink method, of class Graph.
     */
    @Test
    public void testAddLink() {
        ArrayList<Link> links = new ArrayList<>();
        links.add(l);
        g.addLink("A",60,n1,n2);
        n1.addLink(l);
        n2.addLink(l);
        assertEquals(g.getLinks().get(0).getType(),links.get(0).getType());
        assertEquals(g.getLinks().get(0).getNodes()[0] ,links.get(0).getNodes()[0]);
        assertEquals(g.getLinks().get(0).getNodes()[1] ,links.get(0).getNodes()[1]);
        assertEquals(g.getLinks().get(0).getLength() ,links.get(0).getLength());
    }

    /**
     * Test of getNodes method, of class Graph.
     */
    @Test
    public void testGetNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(n1);
        g.addNode(n1);
        assertEquals(g.getNodes(),nodes);
    }

    /**
     * Test of getLinks method, of class Graph.
     */
    @Test
    public void testGetLinks() {
        ArrayList<Link> links = new ArrayList<>();
        links.add(l);
        g.addLink("A",60,n1,n2);
        n1.addLink(l);
        n2.addLink(l);
        assertEquals(g.getLinks().get(0).getType(),links.get(0).getType());
        assertEquals(g.getLinks().get(0).getNodes()[0] ,links.get(0).getNodes()[0]);
        assertEquals(g.getLinks().get(0).getNodes()[1] ,links.get(0).getNodes()[1]);
        assertEquals(g.getLinks().get(0).getLength() ,links.get(0).getLength());
    }

    /**
     * Test of findNode method, of class Graph.
     */
    @Test
    public void testFindNode() {
        g.addNode(n1);
        assertEquals(g.findNode("V,Ville1",g.getNodes()).getType(),n1.getType());
        assertEquals(g.findNode("V,Ville1",g.getNodes()).getName(),n1.getName());
    }

    /**
     * Test of findLink method, of class Graph.
     */
    @Test
    public void testFindLink_String_ArrayList() {
        g.addLink("A", 60, n1, n2);
        System.out.println();
        assertEquals(g.findLink("A-1",g.getLinks()).getType() , l.getType());
        assertEquals(g.findLink("A-1",g.getLinks()).getNodes()[0] , l.getNodes()[0]);
        assertEquals(g.findLink("A-1",g.getLinks()).getNodes()[1] , l.getNodes()[1]);
        assertEquals(g.findLink("A-1",g.getLinks()).getLength() , l.getLength());
        
    }

    /**
     * Test of findLinkHavingNodes method, of class Graph.
     */
    @Test
    public void testFindLinkHavingNodes_Node_Node() {
    }

    /**
     * Test of findLink method, of class Graph.
     */
    @Test
    public void testFindLink_3args() {
    }

    /**
     * Test of setAllInActive method, of class Graph.
     */
    @Test
    public void testSetAllInActive() {
    }

    /**
     * Test of getShortestLink method, of class Graph.
     */
    @Test
    public void testGetShortestLink() {
    }

    /**
     * Test of getShortestPathNodes method, of class Graph.
     */
    @Test
    public void testGetShortestPathNodes() {
    }

    /**
     * Test of findLinkHavingNodes method, of class Graph.
     */
    @Test
    public void testFindLinkHavingNodes_0args() {
    }

    /**
     * Test of draw method, of class Graph.
     */
    @Test
    public void testDraw() {
    }

    /**
     * Test of loadFromFile method, of class Graph.
     */
    @Test
    public void testLoadFromFile() throws Exception {
    }
    
}
