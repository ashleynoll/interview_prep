import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GraphAlgorithmsTests {
    private static final int TIMEOUT = 200;


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bfsExceptions() {
        Graph<String> graph = makeSingleVertexGraph();
        try {
            GraphAlgorithms.breadthFirstSearch(null, graph);
        } catch (IllegalArgumentException e) {
            assertEquals("BFS should throw IllegalArgumentException on " + "a null start vertex.", e.getClass(), IllegalArgumentException.class);
            try {
                GraphAlgorithms.breadthFirstSearch(new Vertex<>("Jaina"), null);
            } catch (IllegalArgumentException ex) {
                assertEquals("BFS should throw IllegalArgumentException on " + "a null graph.", ex.getClass(), IllegalArgumentException.class);
                try {
                    GraphAlgorithms.breadthFirstSearch(new Vertex<>("RNJesus"), graph);
                } catch (IllegalArgumentException exc) {
                    assertEquals("BFS should throw IllegalArgumentException on " + "a start vertex that doesn't " + "exist in the graph.",
                            exc.getClass(), IllegalArgumentException.class);
                    throw exc;
                }
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void bfsSingleVertex() {
        Graph<String> graph = makeSingleVertexGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Jaina"), graph);
        assertEquals("BFS failed single vertex test", Arrays.asList(new Vertex<String>("Jaina")), actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsConnectedUndirectedAcyclic() {
        Graph<String> graph = makeAcyclicConnectedUndirectedGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Alexstraza"), graph);
        assertEquals("BFS failed in a connected, undirected acyclic " + "graph",
                Arrays.asList(new Vertex<>("Alexstraza"), new Vertex<>("Archmage Antonidas"), new Vertex<>("Baron Geddon"),
                        new Vertex<>("Bloodmage Thalnos"), new Vertex<>("Cairne Bloodhoof"), new Vertex<>("Gruul"), new Vertex<>("Harrison Jones"),
                        new Vertex<>("Captain Greenskin"), new Vertex<>("Cenarius"), new Vertex<>("Deathwing"), new Vertex<>("Grommash Hellscream"),
                        new Vertex<>("Edwin VanCleef")), actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsConnectedUndirectedGeneral() {
        Graph<String> graph = makeCyclicConnectedUndirectedGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Hogger"), graph);
        assertEquals("BFS failed in a connected, undirected " + "graph",
                Arrays.asList(new Vertex<>("Hogger"), new Vertex<>("Illidan Stormrager"), new Vertex<>("King Krush"), new Vertex<>("Malygos"),
                        new Vertex<>("Lorewalker Cho"), new Vertex<>("King Mukla"), new Vertex<>("Nozdormu"), new Vertex<>("Leeroy Jenkins"),
                        new Vertex<>("Lord Jaraxxus"), new Vertex<>("Millhouse Manastorm"), new Vertex<>("Nat Pagle")), actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsConnectedDirectedReachableTwoVertices() {
        Graph<String> graph = makeTwoVertexDirectedConnectedGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Prophet Velen"), graph);
        assertEquals("BFS failed in a connected, directed, reachable " + "graph with two vertices",
                Arrays.asList(new Vertex<String>("Prophet Velen"), new Vertex<>("The Beast")), actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsConnectedDirectedGeneral() {
        Graph<String> graph = makeConnectedDirectedReachableGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("The Black Knight"), graph);
        assertEquals("BFS failed in a connected, directed " + "graph",
                Arrays.asList(new Vertex<>("The Black Knight"), new Vertex<>("Baron Rivendare"), new Vertex<>("Tinkmaster Overspark"),
                        new Vertex<>("Tirion Fordring"), new Vertex<>("Feugen"), new Vertex<>("Kel'Thuzad"), new Vertex<>("Ysera")), actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsConnectedDirectedUnreachableGeneral() {
        Graph<String> graph = makeConnectedDirectedUnreachableGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Loatheb"), graph);
        assertEquals("BFS failed in a connected, directed, unreachable " + "graph",
                Arrays.asList(new Vertex<>("Loatheb"), new Vertex<>("Stalagg"), new Vertex<>("Thaddius"), new Vertex<>("Maexxna"),
                        new Vertex<>("Nerubian"), new Vertex<>("Deathlord"), new Vertex<>("Shade of Naxxramas"), new Vertex<>("Mimiron's Head"),
                        new Vertex<>("Echoing Ooze"), new Vertex<>("Blingtron 3000"), new Vertex<>("Hemet Nesingway")), actual);
    }


    @Test(timeout = TIMEOUT)
    public void bfsUnconnectedGraph() {
        Graph<String> graph = makeUnconnectedGraph();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Gazlowe"), graph);
        assertEquals("BFS failed in an unconnected graph",
                Arrays.asList(new Vertex<>("Gazlowe"), new Vertex<>("Foe Reaper 4000"), new Vertex<>("V-07-TR-ON"), new Vertex<>("Boom Bot")),
                actual);
    }

    @Test(timeout = TIMEOUT)
    public void bfsUnconnectedGraphIsolatedStart() {
        Graph<String> graph = makeUnconnectedGraphIsolatedStart();
        List<Vertex<String>> actual = GraphAlgorithms.breadthFirstSearch(new Vertex<String>("Malorne"), graph);
        assertEquals("BFS failed in an unconnected graph with an isolated " + "start vertex", Arrays.asList(new Vertex<>("Malorne")), actual);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dijkstrasExceptions() {
        Graph<String> graph = makeUnconnectedGraph();
        try {
            GraphAlgorithms.dijkstras(null, graph);
        } catch (IllegalArgumentException e) {
            assertEquals("Dijkstras should throw IllegalArgumentException on " + "a null start vertex.", e.getClass(),
                    IllegalArgumentException.class);
            try {
                GraphAlgorithms.dijkstras(new Vertex<>("Gazlowe"), null);
            } catch (IllegalArgumentException ex) {
                assertEquals("Dijkstras should throw IllegalArgumentException" + " on a null graph.", ex.getClass(), IllegalArgumentException.class);
                try {
                    GraphAlgorithms.dijkstras(new Vertex<>("RNJesus"), graph);
                } catch (IllegalArgumentException exc) {
                    assertEquals("Dijkstras should throw " + "IllegalArgumentException on a start " + "vertex that doesn't exist in the " + "graph.",
                            exc.getClass(), IllegalArgumentException.class);
                    throw exc;
                }
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasSingleVertex() {
        Graph<String> graph = makeSingleVertexGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Jaina"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Jaina"), 0);
        assertEquals("Dijkstras failed in a graph with a single vertex", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasAcyclicConnectedUndirected() {
        Graph<String> graph = makeAcyclicConnectedUndirectedGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Alexstraza"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Grommash Hellscream"), 19);
        expected.put(new Vertex<>("Harrison Jones"), 67);
        expected.put(new Vertex<>("Edwin VanCleef"), 21);
        expected.put(new Vertex<>("Archmage Antonidas"), 41);
        expected.put(new Vertex<>("Bloodmage Thalnos"), 6);
        expected.put(new Vertex<>("Captain Greenskin"), 18);
        expected.put(new Vertex<>("Cairne Bloodhoof"), 43);
        expected.put(new Vertex<>("Baron Geddon"), 4);
        expected.put(new Vertex<>("Alexstraza"), 0);
        expected.put(new Vertex<>("Deathwing"), 19);
        expected.put(new Vertex<>("Gruul"), 54);
        expected.put(new Vertex<>("Cenarius"), 10);
        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedUndirectedGeneral() {
        Graph<String> graph = makeCyclicConnectedUndirectedGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Hogger"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("King Krush"), 1);
        expected.put(new Vertex<>("Leeroy Jenkins"), 3);
        expected.put(new Vertex<>("Lord Jaraxxus"), 4);
        expected.put(new Vertex<>("Illidan Stormrager"), 2);
        expected.put(new Vertex<>("Millhouse Manastorm"), 22539);
        expected.put(new Vertex<>("Nozdormu"), 3);
        expected.put(new Vertex<>("King Mukla"), 5);
        expected.put(new Vertex<>("Malygos"), 5);
        expected.put(new Vertex<>("Hogger"), 0);
        expected.put(new Vertex<>("Lorewalker Cho"), 6);
        expected.put(new Vertex<>("Nat Pagle"), 5145971);
        assertEquals("Dijkstras failed in a connected, undirected graph", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedReachableAcyclic() {
        Graph<String> graph = makeConnectedDirectedReachableAcyclicGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Spiritsinger Umbra"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("The Voraxx"), 4);
        expected.put(new Vertex<>("Elder Longneck"), 14);
        expected.put(new Vertex<>("Elise the Trailblazer"), 4);
        expected.put(new Vertex<>("Tortollan Forager"), 10);
        expected.put(new Vertex<>("Verdant Longneck"), 3);
        expected.put(new Vertex<>("Hemet Jungle Hunter"), 1);
        expected.put(new Vertex<>("Spiritsinger Umbra"), 0);
        expected.put(new Vertex<>("Ozruk"), 2);
        assertEquals("Dijkstras failed in a connected, directed, reachable, " + "acyclic graph", expected, actual);
    }

    // The shortest distance for each point is from the vertex to that point.
    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedReachableBasic() {
        Graph<String> graph = makeConnectedDirectedReachableGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("The Black Knight"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("The Black Knight"), 0);
        expected.put(new Vertex<>("Tinkmaster Overspark"), 5);
        expected.put(new Vertex<>("Tirion Fordring"), 3);
        expected.put(new Vertex<>("Ysera"), 4);
        expected.put(new Vertex<>("Baron Rivendare"), 1);
        expected.put(new Vertex<>("Feugen"), 3);
        expected.put(new Vertex<>("Kel'Thuzad"), 5000);
        assertEquals("Dijkstras failed in a connected, directed, reachable, " + "basic graph", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedReachableGeneral() {
        Graph<String> graph = makeConnectedDirectedReachableCyclicGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Spiritsinger Umbra"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("The Voraxx"), 4);
        expected.put(new Vertex<>("Elder Longneck"), 14);
        expected.put(new Vertex<>("Elise the Trailblazer"), 4);
        expected.put(new Vertex<>("Tortollan Forager"), 10);
        expected.put(new Vertex<>("Verdant Longneck"), 3);
        expected.put(new Vertex<>("Hemet Jungle Hunter"), 1);
        expected.put(new Vertex<>("Spiritsinger Umbra"), 0);
        expected.put(new Vertex<>("Ozruk"), 2);
        assertEquals("Dijkstras failed in a connected, directed, reachable, " + "graph", expected, actual);
    }


    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedUnreachableAcyclic() {
        Graph<String> graph = makeAcyclicConnectedDirectedUnreachableGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Emerald Hive Queen"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Primordial Drake"), 9002);
        expected.put(new Vertex<>("Charged Devilsaur"), 9005);
        expected.put(new Vertex<>("Blazecaller"), 9001);
        expected.put(new Vertex<>("Bittertide Hydra"), 9000);
        expected.put(new Vertex<>("Gentle Megasaur"), 8001);
        expected.put(new Vertex<>("Bright-Eyed Scout"), 2);
        expected.put(new Vertex<>("Gluttonous Ooze"), 8000);
        expected.put(new Vertex<>("Emerald Hive Queen"), 0);
        assertEquals("Dijkstras failed on a connected, directed" + ", unreachable acyclic graph.", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedUnreachableEqualWeight() {
        Graph<String> graph = makeConnectedDirectedUnreachableGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Loatheb"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Mimiron's Head"), 6);
        expected.put(new Vertex<>("Stalagg"), 1);
        expected.put(new Vertex<>("Deathlord"), 5);
        expected.put(new Vertex<>("Nerubian"), 4);
        expected.put(new Vertex<>("Blingtron 3000"), 7);
        expected.put(new Vertex<>("Hemet Nesingway"), 8);
        expected.put(new Vertex<>("Echoing Ooze"), 7);
        expected.put(new Vertex<>("Shade of Naxxramas"), 6);
        expected.put(new Vertex<>("Maexxna"), 3);
        expected.put(new Vertex<>("Thaddius"), 2);
        expected.put(new Vertex<>("Loatheb"), 0);
        assertEquals("Dijkstras failed in a connected, directed, unreachable," + " graph with equal weights", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasConnectedDirectedUnreachableGeneral() {
        Graph<String> graph = makeConnectedDirectedUnreachableGraph2();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("One Night in Karazhan"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Whispers of the Old Gods"), 3);
        expected.put(new Vertex<>("Blackrock Mountain"), 8);
        expected.put(new Vertex<>("Mean Streets of Gadgetzan"), 1);
        expected.put(new Vertex<>("Journey to Un'goro"), 6);
        expected.put(new Vertex<>("One Night in Karazhan"), 0);
        expected.put(new Vertex<>("Curse of Naxxramas"), 5);
        assertEquals("Dijkstras failed in a connected, directed, unreachable, " + "graph", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasUnconnectedIsolatedStart() {
        Graph<String> graph = makeUnconnectedGraphIsolatedStart();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Malorne"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Malorne"), 0);
        expected.put(new Vertex<>("Bolvar Fordragon"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Flame Leviathan"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Gahz'rilla"), Integer.MAX_VALUE);
        assertEquals("Dijkstras failed in an unconnected graph with an isolated" + "starting vertex", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasUnconnectedUndirected() {
        Graph<String> graph = makeUnconnectedUndirectedGraph();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Battlecry"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Deathrattle"), 3);
        expected.put(new Vertex<>("Freeze"), 4);
        expected.put(new Vertex<>("Windfury"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Quest"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Battlecry"), 0);
        expected.put(new Vertex<>("Inspire"), 1);
        assertEquals("Dijkstras failed in an unconnected and undirected " + "graph", expected, actual);
    }


    @Test(timeout = TIMEOUT)
    public void dijkstrasUnconnectedGeneral() {
        Graph<String> graph = makeUnconnectedGraph2();
        Map<Vertex<String>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<>("Anduin"), graph);
        Map<Vertex<String>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>("Tyrande"), 8);
        expected.put(new Vertex<>("Malfurion"), 3);
        expected.put(new Vertex<>("Rexxar"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Uther"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Magni"), 1);
        expected.put(new Vertex<>("Garrosh"), 5);
        expected.put(new Vertex<>("Thrall"), 6);
        expected.put(new Vertex<>("Anduin"), 0);
        expected.put(new Vertex<>("Gul'dan"), Integer.MAX_VALUE);
        expected.put(new Vertex<>("Valeera"), Integer.MAX_VALUE);
        assertEquals("Dijkstras failed in an unconnected " + "graph", expected, actual);
    }


    //Graph creating methods:

    /**
     * Makes a graph with a single vertex
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeSingleVertexGraph() {
        Vertex<String> jaina = new Vertex<>("Jaina");
        Set<Vertex<String>> vertices = new HashSet<>();
        vertices.add(jaina);
        Graph<String> g = new Graph<>(vertices, new LinkedHashSet<Edge<String>>());
        return g;
    }

    /**
     * Makes an acyclic connected undirected graph.
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeAcyclicConnectedUndirectedGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Alexstraza"), new Vertex<>("Archmage Antonidas"),
                new Vertex<>("Baron Geddon"), new Vertex<>("Bloodmage Thalnos"), new Vertex<>("Cairne Bloodhoof"), new Vertex<>("Gruul"),
                new Vertex<>("Harrison Jones"), new Vertex<>("Captain Greenskin"), new Vertex<>("Cenarius"), new Vertex<>("Deathwing"),
                new Vertex<>("Edwin VanCleef"), new Vertex<>("Grommash Hellscream")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 41, false),
                new Edge<>(vertices[1], vertices[4], 2, false), new Edge<>(vertices[1], vertices[5], 13, false),
                new Edge<>(vertices[0], vertices[2], 4, false), new Edge<>(vertices[0], vertices[3], 6, false),
                new Edge<>(vertices[3], vertices[6], 61, false), new Edge<>(vertices[3], vertices[7], 12, false),
                new Edge<>(vertices[3], vertices[8], 4, false), new Edge<>(vertices[7], vertices[9], 1, false),
                new Edge<>(vertices[9], vertices[11], 0, false), new Edge<>(vertices[9], vertices[10], 2, false)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a cyclic connected undirected graph
     *
     * @return the created graph
     */
    public Graph<String> makeCyclicConnectedUndirectedGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Hogger"), new Vertex<>("Illidan Stormrager"),
                new Vertex<>("King Krush"), new Vertex<>("King Mukla"), new Vertex<>("Leeroy Jenkins"), new Vertex<>("Lord Jaraxxus"),
                new Vertex<>("Lorewalker Cho"), new Vertex<>("Malygos"), new Vertex<>("Millhouse Manastorm"), new Vertex<>("Nat Pagle"),
                new Vertex<>("Nozdormu")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 2, false),
                new Edge<>(vertices[0], vertices[2], 1, false), new Edge<>(vertices[0], vertices[0], 5, false),
                new Edge<>(vertices[0], vertices[7], 7, false), new Edge<>(vertices[0], vertices[6], 6, false),
                new Edge<>(vertices[1], vertices[3], 7, false), new Edge<>(vertices[1], vertices[10], 1, false),
                new Edge<>(vertices[2], vertices[4], 2, false), new Edge<>(vertices[3], vertices[7], 9, false),
                new Edge<>(vertices[3], vertices[8], 22534, false), new Edge<>(vertices[3], vertices[5], 1, false),
                new Edge<>(vertices[4], vertices[5], 1, false), new Edge<>(vertices[4], vertices[6], 7, false),
                new Edge<>(vertices[4], vertices[4], 7, false), new Edge<>(vertices[4], vertices[10], 5, false),
                new Edge<>(vertices[5], vertices[7], 1, false), new Edge<>(vertices[8], vertices[9], 5123432, false),};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a two-vertex directed connected graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeTwoVertexDirectedConnectedGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Prophet Velen"), new Vertex<>("The Beast")};
        Edge<String>[] edges = new Edge[]{new Edge<>(vertices[0], vertices[1], 0, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a connected directed reachable graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeConnectedDirectedReachableGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("The Black Knight"), new Vertex<>("Tinkmaster Overspark"),
                new Vertex<>("Tirion Fordring"), new Vertex<>("Ysera"), new Vertex<>("Baron Rivendare"), new Vertex<>("Feugen"),
                new Vertex<>("Kel'Thuzad")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[4], 1, true),
                new Edge<>(vertices[0], vertices[1], 5, true), new Edge<>(vertices[0], vertices[2], 3, true),
                //                new Edge<>(vertices[0], vertices[5], 3, false),
                new Edge<>(vertices[0], vertices[5], 3, true), new Edge<>(vertices[5], vertices[0], 3, true),
                //                new Edge<>(vertices[0], vertices[6], 5000, false),
                new Edge<>(vertices[0], vertices[6], 5000, true), new Edge<>(vertices[6], vertices[0], 5000, true),
                //                new Edge<>(vertices[1], vertices[4], 5, false),
                new Edge<>(vertices[1], vertices[4], 5, true), new Edge<>(vertices[4], vertices[1], 5, true),
                new Edge<>(vertices[0], vertices[3], 4, true), new Edge<>(vertices[5], vertices[2], 3, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a cyclic connected directed unreachable graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeConnectedDirectedUnreachableGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Loatheb"), new Vertex<>("Maexxna"), new Vertex<>("Stalagg"),
                new Vertex<>("Thaddius"), new Vertex<>("Echoing Ooze"), new Vertex<>("Shade of Naxxramas"), new Vertex<>("Deathlord"),
                new Vertex<>("Nerubian"), new Vertex<>("Blingtron 3000"), new Vertex<>("Hemet Nesingway"), new Vertex<>("Mimiron's Head")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[2], 1, true),
                //                new Edge<>(vertices[0], vertices[0], 1, false),
                new Edge<>(vertices[0], vertices[0], 1, true),
                //                new Edge<>(vertices[0], vertices[0], 1, true),
                new Edge<>(vertices[0], vertices[0], 1, true),
                //                new Edge<>(vertices[2], vertices[3], 1, false),
                new Edge<>(vertices[2], vertices[3], 1, true), new Edge<>(vertices[3], vertices[2], 1, true),
                new Edge<>(vertices[3], vertices[1], 1, true), new Edge<>(vertices[1], vertices[2], 1, true),
                new Edge<>(vertices[1], vertices[0], 1, true), new Edge<>(vertices[1], vertices[7], 1, true),
                //                new Edge<>(vertices[6], vertices[7], 1, false),
                new Edge<>(vertices[6], vertices[7], 1, true), new Edge<>(vertices[7], vertices[6], 1, true),
                //                new Edge<>(vertices[5], vertices[6], 1, false),
                new Edge<>(vertices[5], vertices[6], 1, true), new Edge<>(vertices[6], vertices[5], 1, true),
                new Edge<>(vertices[6], vertices[0], 1, true),
                //                new Edge<>(vertices[10], vertices[6], 1, false),
                new Edge<>(vertices[10], vertices[6], 1, true), new Edge<>(vertices[6], vertices[10], 1, true),
                new Edge<>(vertices[10], vertices[10], 1, true), new Edge<>(vertices[8], vertices[5], 1, true),
                new Edge<>(vertices[9], vertices[5], 1, true), new Edge<>(vertices[5], vertices[0], 1, true),
                //                new Edge<>(vertices[9], vertices[8], 1, false),
                new Edge<>(vertices[9], vertices[8], 1, true), new Edge<>(vertices[8], vertices[9], 1, true),
                new Edge<>(vertices[9], vertices[10], 1, true), new Edge<>(vertices[10], vertices[8], 1, true),
                new Edge<>(vertices[5], vertices[4], 1, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes an unconnected graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeUnconnectedGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Gazlowe"), new Vertex<>("Mogor the Ogre"), new Vertex<>("Toshley"),
                new Vertex<>("Dr. Boom"), new Vertex<>("Troggzor the Earthinator"), new Vertex<>("Foe Reaper 4000"),
                new Vertex<>("Sneed's Old Shredder"), new Vertex<>("Mekgineer Thermaplugg"), new Vertex<>("Chicken"), new Vertex<>("Boom Bot"),
                new Vertex<>("V-07-TR-ON")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{
                //                new Edge<>(vertices[1], vertices[3], 0, false),
                new Edge<>(vertices[1], vertices[3], 0, true), new Edge<>(vertices[3], vertices[1], 0, true),
                new Edge<>(vertices[3], vertices[2], 0, true), new Edge<>(vertices[1], vertices[2], 0, true),
                new Edge<>(vertices[1], vertices[0], 0, true),
                //                new Edge<>(vertices[0], vertices[5], 0, false),
                new Edge<>(vertices[0], vertices[5], 0, true), new Edge<>(vertices[5], vertices[0], 0, true),
                new Edge<>(vertices[6], vertices[5], 0, true),
                //                new Edge<>(vertices[10], vertices[0], 0, false),
                new Edge<>(vertices[10], vertices[0], 0, true), new Edge<>(vertices[0], vertices[10], 0, true),
                //                new Edge<>(vertices[10], vertices[9], 0, false),
                new Edge<>(vertices[10], vertices[9], 0, true), new Edge<>(vertices[9], vertices[10], 0, true),
                new Edge<>(vertices[0], vertices[9], 0, true),
                //                new Edge<>(vertices[4], vertices[7], 0, false),
                new Edge<>(vertices[4], vertices[7], 0, true), new Edge<>(vertices[7], vertices[4], 0, true),
                new Edge<>(vertices[8], vertices[7], 0, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes an unconnected graph with an isolated starting vertex.
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeUnconnectedGraphIsolatedStart() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Malorne"), new Vertex<>("Gahz'rilla"),
                new Vertex<>("Flame Leviathan"), new Vertex<>("Bolvar Fordragon")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[1], vertices[2], 3, true),
                //                new Edge<>(vertices[1], vertices[3], 2, false),
                new Edge<>(vertices[1], vertices[3], 2, true), new Edge<>(vertices[3], vertices[1], 2, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes an acyclic, connected, directed, unreachable graph.
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeAcyclicConnectedDirectedUnreachableGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Emerald Hive Queen"), new Vertex<>("Gluttonous Ooze"),
                new Vertex<>("Bright-Eyed Scout"), new Vertex<>("Gentle Megasaur"), new Vertex<>("Bittertide Hydra"), new Vertex<>("Blazecaller"),
                new Vertex<>("Charged Devilsaur"), new Vertex<>("Primordial Drake")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 8000, true),
                new Edge<>(vertices[0], vertices[2], 2, true), new Edge<>(vertices[1], vertices[3], 1, true),
                new Edge<>(vertices[1], vertices[4], 1000, true), new Edge<>(vertices[4], vertices[5], 1, true),
                new Edge<>(vertices[4], vertices[6], 5, true), new Edge<>(vertices[4], vertices[7], 2, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a connected, directed, reachable, acyclic graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeConnectedDirectedReachableAcyclicGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Spiritsinger Umbra"), new Vertex<>("The Voraxx"),
                new Vertex<>("Elise the Trailblazer"), new Vertex<>("Hemet Jungle Hunter"), new Vertex<>("Ozruk"), new Vertex<>("Tortollan Forager"),
                new Vertex<>("Elder Longneck"), new Vertex<>("Verdant Longneck")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 4, true),
                new Edge<>(vertices[0], vertices[2], 8, true), new Edge<>(vertices[0], vertices[3], 1, true),
                new Edge<>(vertices[0], vertices[4], 2, true), new Edge<>(vertices[0], vertices[5], 12, true),
                new Edge<>(vertices[0], vertices[6], 14, true), new Edge<>(vertices[0], vertices[7], 3, true),
                new Edge<>(vertices[3], vertices[4], 2, true), new Edge<>(vertices[7], vertices[2], 2, true),
                new Edge<>(vertices[3], vertices[2], 3, true), new Edge<>(vertices[4], vertices[5], 8, true),
                new Edge<>(vertices[6], vertices[5], 1, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a connected directed reachable cyclic graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeConnectedDirectedReachableCyclicGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Spiritsinger Umbra"), new Vertex<>("The Voraxx"),
                new Vertex<>("Elise the Trailblazer"), new Vertex<>("Hemet Jungle Hunter"), new Vertex<>("Ozruk"), new Vertex<>("Tortollan Forager"),
                new Vertex<>("Elder Longneck"), new Vertex<>("Verdant Longneck")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 4, true),
                new Edge<>(vertices[0], vertices[2], 8, true), new Edge<>(vertices[0], vertices[3], 1, true),
                new Edge<>(vertices[0], vertices[4], 2, true), new Edge<>(vertices[0], vertices[5], 12, true),
                new Edge<>(vertices[0], vertices[6], 14, true), new Edge<>(vertices[0], vertices[7], 3, true),
                new Edge<>(vertices[3], vertices[4], 2, true), new Edge<>(vertices[7], vertices[2], 2, true),
                new Edge<>(vertices[3], vertices[2], 3, true), new Edge<>(vertices[4], vertices[5], 8, true),
                new Edge<>(vertices[6], vertices[5], 1, true), new Edge<>(vertices[4], vertices[4], 1, true),
                new Edge<>(vertices[4], vertices[0], 1, true), new Edge<>(vertices[5], vertices[4], 1, true),
                new Edge<>(vertices[5], vertices[6], 10, true), new Edge<>(vertices[6], vertices[7], 1, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes an unconnected graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeUnconnectedGraph2() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Anduin"), new Vertex<>("Tyrande"), new Vertex<>("Garrosh"),
                new Vertex<>("Magni"), new Vertex<>("Malfurion"), new Vertex<>("Thrall"), new Vertex<>("Uther"), new Vertex<>("Rexxar"),
                new Vertex<>("Gul'dan"), new Vertex<>("Valeera")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 9, true),
                new Edge<>(vertices[0], vertices[2], 6, true), new Edge<>(vertices[0], vertices[3], 1, true),
                new Edge<>(vertices[2], vertices[3], 1, true), new Edge<>(vertices[2], vertices[5], 1, true),
                new Edge<>(vertices[3], vertices[4], 2, true), new Edge<>(vertices[4], vertices[2], 2, true),
                new Edge<>(vertices[4], vertices[5], 5, true), new Edge<>(vertices[5], vertices[1], 2, true),
                new Edge<>(vertices[5], vertices[4], 2, true), new Edge<>(vertices[5], vertices[0], 1, true),
                new Edge<>(vertices[6], vertices[8], 5, true), new Edge<>(vertices[7], vertices[8], 3, true),
                new Edge<>(vertices[8], vertices[7], 2, true), new Edge<>(vertices[5], vertices[5], 1, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes a connected directed unreachable graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeConnectedDirectedUnreachableGraph2() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("One Night in Karazhan"), new Vertex<>("Blackrock Mountain"),
                new Vertex<>("Curse of Naxxramas"), new Vertex<>("Mean Streets of Gadgetzan"), new Vertex<>("Whispers of the Old Gods"),
                new Vertex<>("Journey to Un'goro")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 9, true),
                new Edge<>(vertices[0], vertices[2], 6, true), new Edge<>(vertices[0], vertices[3], 1, true),
                new Edge<>(vertices[2], vertices[3], 1, true), new Edge<>(vertices[2], vertices[5], 1, true),
                new Edge<>(vertices[3], vertices[4], 2, true), new Edge<>(vertices[4], vertices[2], 2, true),
                new Edge<>(vertices[4], vertices[5], 5, true), new Edge<>(vertices[5], vertices[1], 2, true),
                new Edge<>(vertices[5], vertices[4], 2, true), new Edge<>(vertices[5], vertices[0], 1, true)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    /**
     * Makes an unconnected undirected graph
     * Refer to images if you would like to visualize this graph.
     *
     * @return the created graph
     */
    public Graph<String> makeUnconnectedUndirectedGraph() {
        Vertex<String>[] vertices = (Vertex<String>[]) new Vertex[]{new Vertex<>("Battlecry"), new Vertex<>("Deathrattle"), new Vertex<>("Freeze"),
                new Vertex<>("Inspire"), new Vertex<>("Windfury"), new Vertex<>("Quest")};


        Edge<String>[] edges = (Edge<String>[]) new Edge[]{new Edge<>(vertices[0], vertices[1], 4, false),
                new Edge<>(vertices[0], vertices[2], 5, false), new Edge<>(vertices[0], vertices[3], 1, false),
                new Edge<>(vertices[1], vertices[3], 2, false), new Edge<>(vertices[2], vertices[3], 3, false),
                new Edge<>(vertices[4], vertices[5], 1, false)};

        Set<Vertex<String>> verticesSet = new HashSet<>();
        for (Vertex<String> current : vertices) {
            verticesSet.add(current);
        }

        LinkedHashSet<Edge<String>> edgesSet = new LinkedHashSet<>();
        for (Edge<String> current : edges) {
            edgesSet.add(current);
        }
        return new Graph<>(verticesSet, edgesSet);
    }

    @Test(timeout = TIMEOUT)
    public void dfsExceptions() {
        try {
            GraphAlgorithms.depthFirstSearch(null, new Graph<String>(new LinkedHashSet<Edge<String>>()));
            Assert.fail("Calling DFS with a null starting vertex should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling DFS with a null starting vertex threw " + "the wrong exception", IllegalArgumentException.class, ex.getClass());
        }

        try {
            GraphAlgorithms.depthFirstSearch(new Vertex<String>(""), null);
            Assert.fail("Calling DFS with a null graph should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling DFS with a null graph threw the wrong exception", IllegalArgumentException.class, ex.getClass());
        }

        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");

        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Vertex<String> e = new Vertex<String>("e");

        Edge<String> ab = new Edge<String>(a, b, 34, true);
        Edge<String> cd = new Edge<String>(c, d, 4, true);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(cd);

        Graph<String> graph = new Graph<String>(edgeSet);

        try {
            GraphAlgorithms.depthFirstSearch(e, graph);
            Assert.fail("Calling DFS with a vertex not in the graph should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling DFS with a vertex not in the graph threw " + "the wrong exception", IllegalArgumentException.class, ex.getClass());
        }

    }

    /*
     * A -- B -- C -- D
     */
    @Test(timeout = TIMEOUT)
    public void dfsUndirectedNoCycles() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> ab = new Edge<String>(a, b, 10, false);
        Edge<String> bc = new Edge<String>(b, c, 3, false);
        Edge<String> cd = new Edge<String>(c, d, -4, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(cd);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expectedStartAtA = new LinkedList<Vertex<String>>();
        expectedStartAtA.add(a);
        expectedStartAtA.add(b);
        expectedStartAtA.add(c);
        expectedStartAtA.add(d);

        List<Vertex<String>> actualStartAtA = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed in an undirected straight-line graph", expectedStartAtA, actualStartAtA);

        List<Vertex<String>> expectedStartAtB = new LinkedList<Vertex<String>>();
        expectedStartAtB.add(b);
        expectedStartAtB.add(a);
        expectedStartAtB.add(c);
        expectedStartAtB.add(d);

        List<Vertex<String>> actualStartAtB = GraphAlgorithms.depthFirstSearch(b, graph);
        assertEquals("DFS failed in an undirected straight-line graph", expectedStartAtB, actualStartAtB);

        List<Vertex<String>> expectedStartAtC = new LinkedList<Vertex<String>>();
        expectedStartAtC.add(c);
        expectedStartAtC.add(b);
        expectedStartAtC.add(a);
        expectedStartAtC.add(d);

        List<Vertex<String>> actualStartAtC = GraphAlgorithms.depthFirstSearch(c, graph);
        assertEquals("DFS failed in an undirected straight-line graph", expectedStartAtC, actualStartAtC);

        List<Vertex<String>> expectedStartAtD = new LinkedList<Vertex<String>>();
        expectedStartAtD.add(d);
        expectedStartAtD.add(c);
        expectedStartAtD.add(b);
        expectedStartAtD.add(a);

        List<Vertex<String>> actualStartAtD = GraphAlgorithms.depthFirstSearch(d, graph);
        assertEquals("DFS failed in an undirected straight-line graph", expectedStartAtD, actualStartAtD);

    }

    /*
     *   A -- B
     *   |    |
     *   |    |
     *   D -- C
     */
    @Test(timeout = TIMEOUT)
    public void dfsUndirectedWithCycle() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> ab = new Edge<String>(a, b, 3, false);
        Edge<String> bc = new Edge<String>(b, c, 6, false);
        Edge<String> cd = new Edge<String>(c, d, 8, false);
        Edge<String> ad = new Edge<String>(a, d, -14, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(cd);
        edgeSet.add(ad);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expectedStartAtA = new LinkedList<Vertex<String>>();
        expectedStartAtA.add(a);
        expectedStartAtA.add(b);
        expectedStartAtA.add(c);
        expectedStartAtA.add(d);

        List<Vertex<String>> actualStartAtA = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed in an undirected graph with a cycle", expectedStartAtA, actualStartAtA);

        List<Vertex<String>> expectedStartAtB = new LinkedList<Vertex<String>>();
        expectedStartAtB.add(b);
        expectedStartAtB.add(a);
        expectedStartAtB.add(d);
        expectedStartAtB.add(c);

        List<Vertex<String>> actualStartAtB = GraphAlgorithms.depthFirstSearch(b, graph);
        assertEquals("DFS failed in an undirected graph with a cycle", expectedStartAtB, actualStartAtB);

        List<Vertex<String>> expectedStartAtC = new LinkedList<Vertex<String>>();
        expectedStartAtC.add(c);
        expectedStartAtC.add(b);
        expectedStartAtC.add(a);
        expectedStartAtC.add(d);

        List<Vertex<String>> actualStartAtC = GraphAlgorithms.depthFirstSearch(c, graph);
        assertEquals("DFS failed in an undirected graph with a cycle", expectedStartAtC, actualStartAtC);

        List<Vertex<String>> expectedStartAtD = new LinkedList<Vertex<String>>();
        expectedStartAtD.add(d);
        expectedStartAtD.add(c);
        expectedStartAtD.add(b);
        expectedStartAtD.add(a);

        List<Vertex<String>> actualStartAtD = GraphAlgorithms.depthFirstSearch(d, graph);
        assertEquals("DFS failed in an undirected graph with a cycle", expectedStartAtD, actualStartAtD);

    }

    /*
     * A -- B
     *
     * C -- D
     */
    @Test(timeout = TIMEOUT)
    public void dfsDisconnected() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> ab = new Edge<String>(a, b, 10, false);
        Edge<String> cd = new Edge<String>(c, d, 3, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(cd);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expected = new LinkedList<Vertex<String>>();
        expected.add(a);
        expected.add(b);

        List<Vertex<String>> actual = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed when the graph was not connected", expected, actual);
    }

    /*
     * A    B
     *    / |
     *  /   |
     * C -- D
     */
    @Test(timeout = TIMEOUT)
    public void dfsLonelyVertex() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> bc = new Edge<String>(b, c, 1, false);
        Edge<String> cd = new Edge<String>(c, d, 3, false);
        Edge<String> bd = new Edge<String>(b, d, 5, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(bc);
        edgeSet.add(cd);
        edgeSet.add(bd);

        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);

        Graph<String> graph = new Graph<String>(vertices, edgeSet);

        List<Vertex<String>> expected = new LinkedList<Vertex<String>>();
        expected.add(a);

        List<Vertex<String>> actual = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed when the graph was not connected", expected, actual);

    }

    /*
     *    -----------
     *   |           |
     *   V           |
     *   A --> B --> C
     *         |     |
     *         V     V
     *   F <-- D <-- E
     *   |           ^
     *   |           |
     *    -----------
     */
    @Test(timeout = TIMEOUT)
    public void dfsDirectedAllReachableFromStart() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");
        Vertex<String> f = new Vertex<String>("f");

        Edge<String> ab = new Edge<String>(a, b, -5, true);
        Edge<String> bc = new Edge<String>(b, c, 1, true);
        Edge<String> ca = new Edge<String>(c, a, 6, true);

        Edge<String> df = new Edge<String>(d, f, 6, true);
        Edge<String> fe = new Edge<String>(f, e, 1, true);
        Edge<String> ed = new Edge<String>(e, d, 2, true);

        Edge<String> bd = new Edge<String>(b, d, 4, true);
        Edge<String> ce = new Edge<String>(c, e, 10, true);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(ca);
        edgeSet.add(df);
        edgeSet.add(fe);
        edgeSet.add(ed);
        edgeSet.add(bd);
        edgeSet.add(ce);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expectedStartAtA = new LinkedList<Vertex<String>>();
        expectedStartAtA.add(a);
        expectedStartAtA.add(b);
        expectedStartAtA.add(c);
        expectedStartAtA.add(e);
        expectedStartAtA.add(d);
        expectedStartAtA.add(f);

        List<Vertex<String>> actualStartAtA = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed on a directed graph", expectedStartAtA, actualStartAtA);

    }

    /*
     *   A --> B --> C
     *   |
     *   V
     *   F <-- D <-- E
     *   |           ^
     *   |           |
     *    -----------
     */
    @Test(timeout = TIMEOUT)
    public void dfsDirectedAllReachableFromStartWithBacktracking() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");
        Vertex<String> f = new Vertex<String>("f");

        Edge<String> ab = new Edge<String>(a, b, -5, true);
        Edge<String> bc = new Edge<String>(b, c, 1, true);

        Edge<String> df = new Edge<String>(d, f, 6, true);
        Edge<String> fe = new Edge<String>(f, e, 1, true);
        Edge<String> ed = new Edge<String>(e, d, 2, true);

        Edge<String> af = new Edge<String>(a, f, 4, true);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(df);
        edgeSet.add(fe);
        edgeSet.add(ed);
        edgeSet.add(af);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expectedStartAtA = new LinkedList<Vertex<String>>();
        expectedStartAtA.add(a);
        expectedStartAtA.add(b);
        expectedStartAtA.add(c);
        expectedStartAtA.add(f);
        expectedStartAtA.add(e);
        expectedStartAtA.add(d);

        List<Vertex<String>> actualStartAtA = GraphAlgorithms.depthFirstSearch(a, graph);
        assertEquals("DFS failed on a directed graph with backtracking", expectedStartAtA, actualStartAtA);

    }

    /*
     *    -----------
     *   |           |
     *   V           |
     *   A --> B --> C
     *         |     |
     *         V     V
     *   F <-- D <-- E
     *   |           ^
     *   |           |
     *    -----------
     */
    @Test(timeout = TIMEOUT)
    public void dfsDirectedNotAllReachableFromStart() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");
        Vertex<String> f = new Vertex<String>("f");

        Edge<String> ab = new Edge<String>(a, b, -5, true);
        Edge<String> bc = new Edge<String>(b, c, 1, true);
        Edge<String> ca = new Edge<String>(c, a, 6, true);

        Edge<String> df = new Edge<String>(d, f, 6, true);
        Edge<String> fe = new Edge<String>(f, e, 1, true);
        Edge<String> ed = new Edge<String>(e, d, 2, true);

        Edge<String> bd = new Edge<String>(b, d, 4, true);
        Edge<String> ce = new Edge<String>(c, e, 10, true);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(ca);
        edgeSet.add(df);
        edgeSet.add(fe);
        edgeSet.add(ed);
        edgeSet.add(bd);
        edgeSet.add(ce);

        Graph<String> graph = new Graph<String>(edgeSet);

        List<Vertex<String>> expectedStartAtF = new LinkedList<Vertex<String>>();
        expectedStartAtF.add(f);
        expectedStartAtF.add(e);
        expectedStartAtF.add(d);

        List<Vertex<String>> actualStartAtF = GraphAlgorithms.depthFirstSearch(f, graph);
        assertEquals("DFS failed on a directed graph when not all " + "vertices were reachable from the start", expectedStartAtF, actualStartAtF);

    }

    @Test(timeout = TIMEOUT)
    public void primsExceptions() {
        try {
            GraphAlgorithms.prims(null, new Graph<String>(new LinkedHashSet<Edge<String>>()));
            Assert.fail("Calling Prim's with a null starting vertex should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling Prim's with a null starting vertex threw " + "the wrong exception", IllegalArgumentException.class, ex.getClass());
        }

        try {
            GraphAlgorithms.prims(new Vertex<String>(""), null);
            Assert.fail("Calling Prim's with a null graph should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling Prim's with a null graph threw " + "the wrong exception", IllegalArgumentException.class, ex.getClass());
        }

        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");

        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Vertex<String> e = new Vertex<String>("e");

        Edge<String> ab = new Edge<String>(a, b, 4, false);
        Edge<String> bc = new Edge<String>(c, d, 4, false);
        Edge<String> cd = new Edge<String>(c, d, 4, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(bc);
        edgeSet.add(cd);

        Graph<String> graph = new Graph<String>(edgeSet);

        try {
            GraphAlgorithms.prims(e, graph);
            Assert.fail("Calling Prim's with a vertex not in the graph should " + "throw an IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals("Calling Prim's with a vertex not in the graph threw " + "the wrong exception", IllegalArgumentException.class,
                    ex.getClass());
        }

    }

    /*
     * A -- B
     *
     *
     * C -- D
     *
     * start at A
     */
    @Test(timeout = TIMEOUT)
    public void noMSTTwoSegments() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");

        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> ab = new Edge<String>(a, b, 4, false);
        Edge<String> cd = new Edge<String>(c, d, 4, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(cd);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertNull("Prim's algorithm failed to return null when the graph " + "had two connected components", mst);

    }

    /*
     * A
     *
     * BCDE = K4
     *
     * start on A
     */
    @Test(timeout = TIMEOUT)
    public void noMSTStartOnIsland() {
        Vertex<String> a = new Vertex<String>("a");

        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");

        Edge<String> bc = new Edge<String>(b, c, 1, false);
        Edge<String> bd = new Edge<String>(b, d, 2, false);
        Edge<String> be = new Edge<String>(b, e, 3, false);
        Edge<String> cd = new Edge<String>(c, d, 4, false);
        Edge<String> ce = new Edge<String>(c, e, 5, false);
        Edge<String> de = new Edge<String>(d, e, 6, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(bc);
        edgeSet.add(bd);
        edgeSet.add(be);
        edgeSet.add(cd);
        edgeSet.add(ce);
        edgeSet.add(de);

        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        vertices.add(e);

        Graph<String> graph = new Graph<String>(vertices, edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertNull("Prim's algorithm failed to return null when the search " + "started on an island", mst);

    }

    /*
     * A
     *
     * BCDE = K4
     *
     * start on D
     */
    @Test(timeout = TIMEOUT)
    public void noMSTOneInaccessibleVertex() {
        Vertex<String> a = new Vertex<String>("a");

        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");

        Edge<String> bc = new Edge<String>(b, c, 1, false);
        Edge<String> bd = new Edge<String>(b, d, 2, false);
        Edge<String> be = new Edge<String>(b, e, 3, false);
        Edge<String> cd = new Edge<String>(c, d, 4, false);
        Edge<String> ce = new Edge<String>(c, e, 5, false);
        Edge<String> de = new Edge<String>(d, e, 6, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(bc);
        edgeSet.add(bd);
        edgeSet.add(be);
        edgeSet.add(cd);
        edgeSet.add(ce);
        edgeSet.add(de);

        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        vertices.add(e);

        Graph<String> graph = new Graph<String>(vertices, edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(d, graph);

        assertNull("Prim's algorithm failed to return null when one vertex " + "was inaccessible", mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstK5StartAtA() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");

        Edge<String> ab = new Edge<String>(a, b, 1, false);
        Edge<String> ac = new Edge<String>(a, c, 5, false);
        Edge<String> ad = new Edge<String>(a, d, 7, false);
        Edge<String> ae = new Edge<String>(a, e, 3, false);

        Edge<String> bc = new Edge<String>(b, c, 9, false);
        Edge<String> bd = new Edge<String>(b, d, 4, false);
        Edge<String> be = new Edge<String>(b, e, 2, false);

        Edge<String> cd = new Edge<String>(c, d, 6, false);
        Edge<String> ce = new Edge<String>(c, e, 10, false);

        Edge<String> de = new Edge<String>(d, e, 8, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ac);
        edgeSet.add(ad);
        edgeSet.add(ae);

        edgeSet.add(bc);
        edgeSet.add(bd);
        edgeSet.add(be);

        edgeSet.add(cd);
        edgeSet.add(ce);

        edgeSet.add(de);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();
        expected.add(ab);
        expected.add(be);
        expected.add(bd);
        expected.add(ac);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed on a fully-connected graph " + "of size 5", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstSparseGraphStartAtAllVertices() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");
        Vertex<String> f = new Vertex<String>("f");

        Edge<String> ab = new Edge<String>(a, b, 7, false);
        Edge<String> ad = new Edge<String>(a, d, 6, false);
        Edge<String> ae = new Edge<String>(a, e, 10, false);

        Edge<String> bd = new Edge<String>(b, d, 1, false);

        Edge<String> cd = new Edge<String>(c, d, 4, false);
        Edge<String> ce = new Edge<String>(c, e, 3, false);

        Edge<String> df = new Edge<String>(d, f, 63, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ad);
        edgeSet.add(ae);
        edgeSet.add(bd);
        edgeSet.add(cd);
        edgeSet.add(ce);
        edgeSet.add(df);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();
        expected.add(bd);
        expected.add(ce);
        expected.add(cd);
        expected.add(ad);
        expected.add(df);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

        mst = GraphAlgorithms.prims(b, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

        mst = GraphAlgorithms.prims(c, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

        mst = GraphAlgorithms.prims(d, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

        mst = GraphAlgorithms.prims(e, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

        mst = GraphAlgorithms.prims(f, graph);

        assertEquals("Prim's algorithm failed on a sparsely-connected graph", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstK5NegativeEdgeWeight() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");

        Edge<String> ab = new Edge<String>(a, b, -10, false);
        Edge<String> ac = new Edge<String>(a, c, 5, false);
        Edge<String> ad = new Edge<String>(a, d, 7, false);
        Edge<String> ae = new Edge<String>(a, e, 3, false);

        Edge<String> bc = new Edge<String>(b, c, 9, false);
        Edge<String> bd = new Edge<String>(b, d, 4, false);
        Edge<String> be = new Edge<String>(b, e, 2, false);

        Edge<String> cd = new Edge<String>(c, d, 6, false);
        Edge<String> ce = new Edge<String>(c, e, 10, false);

        Edge<String> de = new Edge<String>(d, e, 8, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ac);
        edgeSet.add(ad);
        edgeSet.add(ae);

        edgeSet.add(bc);
        edgeSet.add(bd);
        edgeSet.add(be);

        edgeSet.add(cd);
        edgeSet.add(ce);

        edgeSet.add(de);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();
        expected.add(ab);
        expected.add(be);
        expected.add(bd);
        expected.add(ac);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed on a fully-connected graph " + "of size 5 " + "when an edge had negative weight", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstUniqueButDuplicateWeights() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");

        Edge<String> ab = new Edge<String>(a, b, 1, false);
        Edge<String> ac = new Edge<String>(a, c, 1, false);
        Edge<String> ad = new Edge<String>(a, d, 4, false);

        Edge<String> bc = new Edge<String>(b, c, 2, false);
        Edge<String> bd = new Edge<String>(b, d, 3, false);

        Edge<String> cd = new Edge<String>(c, d, 5, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ac);
        edgeSet.add(ad);

        edgeSet.add(bc);
        edgeSet.add(bd);

        edgeSet.add(cd);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();
        expected.add(ab);
        expected.add(ac);
        expected.add(bd);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed when duplicate edge weights " + "were present", expected, mst);

        mst = GraphAlgorithms.prims(b, graph);

        assertEquals("Prim's algorithm failed when duplicate edge weights " + "were present", expected, mst);

        mst = GraphAlgorithms.prims(c, graph);

        assertEquals("Prim's algorithm failed when duplicate edge weights " + "were present", expected, mst);

        mst = GraphAlgorithms.prims(d, graph);

        assertEquals("Prim's algorithm failed when duplicate edge weights " + "were present", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstK5NegativeCycles() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");

        Edge<String> ab = new Edge<String>(a, b, -4, false);
        Edge<String> ac = new Edge<String>(a, c, 0, false);
        Edge<String> ad = new Edge<String>(a, d, 2, false);
        Edge<String> ae = new Edge<String>(a, e, -2, false);

        Edge<String> bc = new Edge<String>(b, c, 4, false);
        Edge<String> bd = new Edge<String>(b, d, -1, false);
        Edge<String> be = new Edge<String>(b, e, -3, false);

        Edge<String> cd = new Edge<String>(c, d, 1, false);
        Edge<String> ce = new Edge<String>(c, e, 5, false);

        Edge<String> de = new Edge<String>(d, e, 3, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ac);
        edgeSet.add(ad);
        edgeSet.add(ae);

        edgeSet.add(bc);
        edgeSet.add(bd);
        edgeSet.add(be);

        edgeSet.add(cd);
        edgeSet.add(ce);

        edgeSet.add(de);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();
        expected.add(ab);
        expected.add(be);
        expected.add(bd);
        expected.add(ac);

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed on a fully-connected graph of size 5 " + "when negative cycles were present", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstK5SingleVertex() {
        Vertex<String> a = new Vertex<String>("a");
        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        vertices.add(a);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        Set<Edge<String>> expected = new HashSet<Edge<String>>();

        Graph<String> graph = new Graph<String>(vertices, edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed to return an empty set when " + "only one vertex was present", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstK5SingleVertexWithLoop() {
        Vertex<String> a = new Vertex<String>("a");
        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        vertices.add(a);

        Edge<String> aa = new Edge<String>(a, a, 4, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();

        edgeSet.add(aa);

        Set<Edge<String>> expected = new HashSet<Edge<String>>();

        Graph<String> graph = new Graph<String>(vertices, edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed to return an empty set when " + "only one vertex was present and had a self-loop", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstAlreadyTreeSimple() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");

        Edge<String> ab = new Edge<String>(a, b, 4, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);

        Set<Edge<String>> expected = edgeSet;

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was a two-vertex tree", expected, mst);

        mst = GraphAlgorithms.prims(b, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was a two-vertex tree", expected, mst);

    }

    @Test(timeout = TIMEOUT)
    public void mstAlreadyTreeBigger() {
        Vertex<String> a = new Vertex<String>("a");
        Vertex<String> b = new Vertex<String>("b");
        Vertex<String> c = new Vertex<String>("c");
        Vertex<String> d = new Vertex<String>("d");
        Vertex<String> e = new Vertex<String>("e");
        Vertex<String> f = new Vertex<String>("f");
        Vertex<String> g = new Vertex<String>("g");

        Edge<String> ab = new Edge<String>(a, b, 10, false);
        Edge<String> ac = new Edge<String>(a, c, 1000, false);

        Edge<String> bd = new Edge<String>(b, d, 10, false);
        Edge<String> be = new Edge<String>(b, e, 20, false);
        Edge<String> cf = new Edge<String>(c, f, 30, false);
        Edge<String> cg = new Edge<String>(c, g, 40, false);

        LinkedHashSet<Edge<String>> edgeSet = new LinkedHashSet<Edge<String>>();
        edgeSet.add(ab);
        edgeSet.add(ac);
        edgeSet.add(bd);
        edgeSet.add(be);
        edgeSet.add(cf);
        edgeSet.add(cg);

        Set<Edge<String>> expected = edgeSet;

        Graph<String> graph = new Graph<String>(edgeSet);

        Set<Edge<String>> mst = GraphAlgorithms.prims(a, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(b, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(c, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(d, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(e, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(f, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

        mst = GraphAlgorithms.prims(g, graph);

        assertEquals("Prim's algorithm failed to return the correct MST when " + "the graph was already a tree", expected, mst);

    }
}

