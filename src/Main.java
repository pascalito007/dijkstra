public class Main {
    public static void main(String... args){
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addEdge("A", "B", 3);
        graph.addEdge("A", "D", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "E", 1);
        graph.addEdge("B", "D", 6);
        graph.addEdge("D", "C", 1);
        graph.addEdge("D", "E", 5);

        System.out.println("*********** LE CHEMIN LE PLUS COURT DE A à E EST **********");

        System.out.println(graph.getShortestPath("A", "E"));
    }
}
