import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;


public class GraphsTests {
    private static final long TIMEOUT = 200;
    private GraphsProblems problems;

    @Before
    public void setUp() {
        problems = new GraphsProblems();
    }

    @Test(timeout = TIMEOUT)
    public void testBuildOrderNoOrder() {
        Stack<String> actual = problems.buildOrder(new String[]{"a", "b", "c"}, new String[][]{ {"a", "b"}, {"b", "c"}, {"c", "a"}});

        assertNull("Simple cycle should return null", actual);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildOrderValid() {
        Stack<String> actual = problems.buildOrder(new String[]{"a", "b", "c"}, new String[][]{ {"a", "b"}, {"c", "b"}}),
                expected = new Stack<>();
        String[] orders = { "c", "a", "b" };

        for (int i = orders.length - 1; i >= 0; i--) {
            expected.push(orders[i]);
        }

        assertArrayEquals("Should return appropriate build order for projects without dependencies", expected.toArray(), actual.toArray());

        String[][] dependencies = {{"a", "d"}, {"f", "b"}, {"b", "d"}, {"f", "a"}, {"d", "c"}};
        actual = problems.buildOrder(new String[]{"a", "b", "c", "d", "e", "f"}, dependencies);

        expected = new Stack<>();
        orders = new String[]{"f", "e", "b", "a", "d", "c"};

        for (int i = orders.length - 1; i >= 0; i--) {
            expected.push(orders[i]);
        }

        assertArrayEquals("Should return appropriate build order for projects without dependencies", expected.toArray(), actual.toArray());
    }
}
