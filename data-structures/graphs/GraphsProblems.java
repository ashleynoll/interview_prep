import java.util.*;

public class GraphsProblems {
    public class Graph {
        public Map<String, Vertex> mappedVertices;
        public List<Vertex> vertices;

        public Graph() {
            mappedVertices = new HashMap<>();
            vertices = new ArrayList<>();
        }

        private Vertex addVertex(String s) {
            if (!mappedVertices.containsKey(s)) {
                Vertex vertex = new Vertex(s);
                mappedVertices.put(s, vertex);
                vertices.add(vertex);
            }
            return mappedVertices.get(s);
        }

        public void addEdge(String s, String t) {
            addVertex(s);
            Vertex child = addVertex(t);

            mappedVertices.get(s).addChild(child);
        }

        public List<Vertex> getVertices() {
            return vertices;
        }
    }
    public class Vertex {
        public String id;
        public List<Vertex> adjacencyList;

        public Vertex(String id) {
            this.id = id;
            adjacencyList = new ArrayList<>();
        }

        public void addChild(Vertex v) {
            adjacencyList.add(v);
        }

        public List<Vertex> getAdjacencyList() {
            return this.adjacencyList;
        }
    }
    /**
     * Problem:
     *
     * You are given a list of projects and a list of dependencies (which is a list of pairs of
     * projects, where the second project is dependent on the first project). All of
     * a project's dependencies must be built before the project is. Find a build order that
     * will allow the projects to be built. If there is no valid order, return null.
     *
     * Solution Explanation:
     *
     * The idea for this solution is that all projects will be placed in a directed graph
     * and will be connected to all nodes that depend on them to complete. Then, we'll
     * run through the nodes performing dfs multiple times. The last node reached on the
     * dfs call can obviously be the last node in the build order as it has no nodes depending
     * on it. After all of the children have been explored, then the current node can be
     * added to the stack. We keep track of nodes we've visited during this process to make
     * sure there's no duplicate entries as well as nodes we've touched within a particular
     * dfs call. If nodes are touched twice then there must be a cycle and therefore there
     * is no possible build order.
     */
    public Stack<String> buildOrder(String[] projects, String[][] dependencies) {
        if (projects == null || projects.length == 0 || dependencies == null) {
            return null;
        }

        /* Create graph and all known dependencies to make a directed graph */
        Graph graph = new Graph();

        for (String project : projects) {
            graph.addVertex(project);
        }

        for (String[] dependency : dependencies) {
            // Assuming index 0 is parent and 1 is dependent child
            graph.addEdge(dependency[0], dependency[1]);
        }

        /* Perform DFS on the graph and return stack. */

        Stack<String> stack = new Stack<>();
        Set<Vertex> visited = new HashSet<>(),
                    touched = new HashSet<>();
        for (Vertex vertex : graph.getVertices()) {
            if (!dfs(vertex, stack, visited, touched)) {
                return null;
            } else if (visited.size() >= graph.getVertices().size()) {
                return stack;
            }
            touched = new HashSet<>();
        }

        return stack;
    }

    /**
     * Helper method for recursive DFS
     * @param vertex the current vertex being explored
     * @param stack the return stack
     * @param visited all previously visited nodes
     * @param touched nodes visited on this dfs (cycle detection)
     * @return if there is a valid order for this dfs
     */
    private boolean dfs(Vertex vertex, Stack<String> stack, Set<Vertex> visited, Set<Vertex> touched) {
        if (visited.contains(vertex)) {
            return true;
        } else if (touched.contains(vertex)) {
            // Loop
            return false;
        }
        touched.add(vertex);

        for (Vertex child : vertex.getAdjacencyList()) {
            if (!dfs(child, stack, visited, touched)) {
                return false;
            }
        }

        visited.add(vertex);
        stack.push(vertex.id);

        return true;
    }
}
