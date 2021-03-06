import java.util.*;

public class Graph {

    private class Node{
        private String label;
        private List<Edge> edges = new ArrayList<>();

        public Node(String label) {
            this.label = label;
        }

        public void addEdge(Node to, int weight){
            this.edges.add(new Edge(this, to, weight));
        }

        public List<Edge> getEdges() {
            return edges;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    private class Edge{
        private Node from;
        private Node to;
        private int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return from+ "->"+ to; //A->B
        }
    }

    private Map<String, Node> nodes = new HashMap<>();

    public void addNode(String label){
        nodes.putIfAbsent(label, new Node(label));
    }

    public void addEdge(String from, String to, int weight){
        Node fromNode = this.nodes.get(from);
        if(fromNode == null)
            throw new IllegalArgumentException("From Node:"+from+" not found");

        Node toNode = this.nodes.get(to);
        if(toNode == null)
            throw  new IllegalArgumentException("To Node:"+to+" not found");

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

    public void print(){
        for (Node source: this.nodes.values()){
            List<Edge> targets = source.getEdges();
            if(!targets.isEmpty()){
                System.out.println(source+ " is connected to "+ targets);
            }
        }
    }

    private class NodeEntry{
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }
    public Path getShortestPath(String from, String to){
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        Map<Node, Integer> distances = new HashMap<>();
        for (Node node: this.nodes.values())
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(fromNode, 0);

        Map<Node, Node> previousNodes = new HashMap<>();


        Set<Node> visited = new HashSet<>();


        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(
                Comparator.comparingInt(ne->ne.priority)
        );
        queue.add(new NodeEntry(fromNode, 0));

        while (!queue.isEmpty()){
            Node current = queue.remove().node;

            visited.add(current);

            for (Edge edge: current.getEdges()){
                if(visited.contains(edge.to))
                    continue;
                int newDistance = distances.get(current)+ edge.weight;
                if(newDistance < distances.get(edge.to)){
                    distances.replace(edge.to, newDistance);
                    previousNodes.put(edge.to, current);
                    queue.add(new NodeEntry(edge.to, newDistance));
                }
            }
        }

        return buildPath(previousNodes, toNode);
    }

    private Path buildPath(Map<Node, Node> previousNodes, Node toNode) {
        Stack<Node> stack = new Stack<>();
        stack.push(toNode);
        Node previous = previousNodes.get(toNode);

        while (previous !=null){
            stack.push(previous);
            previous = previousNodes.get(previous);
        }

        Path path = new Path();
        while (!stack.isEmpty())
            path.add(stack.pop().label);
        return path;
    }
}
