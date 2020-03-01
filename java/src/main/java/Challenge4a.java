import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Challenge4a {

    // Already implemented FordFulkerson for Algorithm Design course :)
    public static int solution(int[] entrances, int[] exits, int[][] path) {
        Node[] nodes = new Node[path.length];

        Node s = new Node();
        for (int i = 0; i < path.length; i++) {
            Node room = new Node();
            nodes[i] = room;
        }
        Node t = new Node();

        for (int roomIndex : entrances) {
            s.addEdge(nodes[roomIndex], Integer.MAX_VALUE);
        }

        for (int roomIndex : exits) {
            nodes[roomIndex].addEdge(t, Integer.MAX_VALUE);
        }

        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                int pathCapacity = path[i][j];
                if (pathCapacity > 0) {
                    nodes[i].addEdge(nodes[j], pathCapacity);
                }
            }
        }

        Graph graph = new Graph(s, t);
        return FordFulkerson.maxFlow(graph);
    }

    private static class FordFulkerson {

        static int maxFlow(Graph g) {
            Node s = g.source;
            Node t = g.sink;

            while (true) {
                final List<Edge> path = findPath(s, t);
                if (path == null || path.isEmpty()) break;

                int bottleNeckValue = path.stream().map(edge -> edge.capacity - edge.flow).min(Integer::compareTo).get();

                for (Edge edge : path) {
                    edge.flow += bottleNeckValue;
                    edge.backwards.flow = edge.capacity - edge.flow;
                }
            }

            return s.edges.stream().map(edge -> edge.flow).mapToInt(Integer::intValue).sum();
        }

        private static List<Edge> findPath(Node s, Node t) {

            Map<Node, Edge> nodeReachedByEdgeMap = new HashMap<>();
            Queue<Node> toBeCheckedNodes = new LinkedList<>();

            Node currNode = s;
            toBeCheckedNodes.add(s);

            while (!toBeCheckedNodes.isEmpty() && currNode != t) {
                currNode = toBeCheckedNodes.remove();

                for (Edge edge : currNode.edges) {
                    Node toNode = edge.to;

                    if (toNode != s && !nodeReachedByEdgeMap.containsKey(toNode) && edge.capacity > edge.flow) {
                        toBeCheckedNodes.add(toNode);
                        nodeReachedByEdgeMap.put(toNode, edge);
                    }
                }
            }

            if (currNode != t) return null;

            List<Edge> path = new ArrayList<>();
            while (currNode != s) {
                final Edge currEdge = nodeReachedByEdgeMap.get(currNode);
                path.add(currEdge);
                currNode = currEdge.from;
            }

            Collections.reverse(path);

            return path;
        }
    }

    private static class Graph {

        public final Node source;
        public final Node sink;

        public Graph(Node source, Node sink) {
            this.source = source;
            this.sink = sink;
        }
    }

    private static class Node {

        public final Collection<Edge> edges;

        public Node() {
            this.edges = new ArrayList<>();
        }

        public void addEdge(Node to, int capacity) {
            Edge e = new Edge(capacity, this, to);
            edges.add(e);
            to.edges.add(e.backwards);
        }
    }

    private static class Edge {

        public final int capacity;
        public final Node from;
        public final Node to;
        public final Edge backwards;

        public int flow;

        private Edge(Edge e) {
            this.flow = e.capacity;
            this.capacity = e.capacity;
            this.from = e.to;
            this.to = e.from;
            this.backwards = e;
        }

        public Edge(int capacity, Node from, Node to) {
            this.capacity = capacity;
            this.from = from;
            this.to = to;
            this.flow = 0;
            this.backwards = new Edge(this);
        }
    }
}
