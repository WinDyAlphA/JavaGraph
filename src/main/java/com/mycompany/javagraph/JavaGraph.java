/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.javagraph;
/**
 * main
 */
public class JavaGraph {

    public static void main(String[] args) {
        Graph graph = new Graph();
        

        Node a = new Node("A", "");
        Node b = new Node("B", "");
        Node c = new Node("C", "");
        Node d = new Node("D", "");
        Node e = new Node("E", "");
        
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addNode(e);
        
        graph.addLink("D", 6, a, b);
        graph.addLink("D", 5, b, c);
        graph.addLink("D", 5, c, e);
        graph.addLink("D", 1, e, d);
        graph.addLink("D", 2, e, b);
        graph.addLink("D", 1, d, a);
        graph.addLink("D", 2, d, b);
        


        try {
            graph = Graph.loadFromFile("GraphText.txt");

            HomeScreen hs = new HomeScreen(graph);
            
        } catch (Exception ex) {
            System.out.println("Error while reading file");
            System.err.print(ex);
        }
    }
}
