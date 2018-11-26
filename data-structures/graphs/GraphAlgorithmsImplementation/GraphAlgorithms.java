import java.util.*;

/**
 * Implementations of various graph algorithms.
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  Will return a List of the vertices in the order that
     * they are visited.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex to be started at
     * @param graph the Graph that is being traversed
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that they are visited
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Must give valid start vertex and graph.");
        } else if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("Start vertex must exist within the graph.");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> map = graph.getAdjacencyList();

        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.poll();

            if (!visited.contains(curr)) {
                visited.add(curr);
                list.add(curr);

                for (VertexDistancePair<T> child : map.get(curr)) {
                    if (!visited.contains(child.getVertex())) {
                        queue.add(child.getVertex());
                    }
                }
            }
        }

        return list;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  Will return a List of the vertices in the order that
     * they are visited.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex to start at
     * @param graph the Graph being traversed
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that they are visited
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot have null start or graph.");
        } else if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("Start vertex must exist within the graph.");
        }

        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        depthFirstSearch(start, graph, list, visited);
        return list;
    }

    private static <T> void depthFirstSearch(Vertex<T> curr, Graph<T> graph,
                                             List<Vertex<T>> list, Set<Vertex<T>> visited) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            list.add(curr);
            for (VertexDistancePair<T> child : graph.getAdjacencyList().get(curr)) {
                if (!visited.contains(child.getVertex())) {
                    depthFirstSearch(child.getVertex(), graph, list, visited);
                }
            }
        }
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. One may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex that is being started from
     * @param graph the Graph that is being searched
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Start vertex and graph must be valid.");
        } else if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("Start vertex must exist within the graph.");
        }

        Map<Vertex<T>, Integer> distances = new HashMap<>();
        PriorityQueue<VertexDistancePair<T>> pq = new PriorityQueue<>();

        for (Vertex<T> vertex : graph.getAdjacencyList().keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new VertexDistancePair<>(start, 0));

        while (!pq.isEmpty()) {
            VertexDistancePair<T> curr = pq.poll();

            for (VertexDistancePair<T> child : graph.getAdjacencyList().get(curr.getVertex())) {
                int newDistance = curr.getDistance() + child.getDistance();
                if (newDistance < distances.get(child.getVertex())) {
                    distances.put(child.getVertex(), newDistance);
                    pq.add(new VertexDistancePair<>(child.getVertex(), newDistance));
                }
            }
        }

        return distances;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex to be started at
     * @param graph the Graph the MST is being created for
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Start vertex and graph must be valid.");
        } else if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("Start vertex must exist within the graph.");
        }

        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();

        for (VertexDistancePair<T> vertex : graph.getAdjacencyList().get(start)) {
            pq.add(new Edge<>(start, vertex.getVertex(), vertex.getDistance(), graph.isDirected()));
        }

        while (!pq.isEmpty() && mst.size() < graph.getAdjacencyList().keySet().size() - 1) {
            Edge<T> currEdge = pq.poll();
            Vertex<T> currVertex = currEdge.getV();

            if (!visited.contains(currVertex)) {
                visited.add(currVertex);
                mst.add(currEdge);

                for (VertexDistancePair<T> vertex : graph.getAdjacencyList().get(currVertex)) {
                    pq.add(new Edge<>(currVertex, vertex.getVertex(), vertex.getDistance(), graph.isDirected()));
                }
            }
        }

        if (mst.size() != graph.getAdjacencyList().keySet().size() - 1) {
            return null;
        }

        return mst;
    }

}
