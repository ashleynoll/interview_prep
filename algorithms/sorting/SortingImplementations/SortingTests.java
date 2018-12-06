package SortingImplementations;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortingTests {
    private static final int TIMEOUT = 200;
    private static final double COMPARISON_BUFFER = 1.05; // 5% Buffer
    private Random random = new Random(226);

    // ------------------------------------------------------------------
    // Bubblesort Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bubbleExceptionNullArray() {
        try {
            Sorting.bubbleSort(null, new ComparatorPlus<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bubbleExceptionNullComparator() {
        try {
            Sorting.bubbleSort(new Integer[2], null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void bubbleLength0Array() {
        Integer[] expected = new Integer[0];
        Integer[] actual = new Integer[0];
        ComparatorPlus<Integer> comp = new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                incrementCount();
                return o1 - o2;
            }
        };

        Sorting.bubbleSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void bubbleSize1Array() {
        Integer[] expected = {11};
        Integer[] actual = {11};
        ComparatorPlus<Integer> comp = new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                incrementCount();
                return o1 - o2;
            }
        };

        Sorting.bubbleSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void bubbleAlreadySorted() {
        // [1, 2, 3, 4, 5] -> [1, 2, 3, 4, 5]
        Pokemon[] expected = {new Pokemon(1, "Bulbasaur"), new Pokemon(2, "Ivysaur"), new Pokemon(3, "Venusaur"), new Pokemon(4, "Charmander"),
                new Pokemon(5, "Charmeleon")};
        Pokemon[] actual = {new Pokemon(1, "Bulbasaur"), new Pokemon(2, "Ivysaur"), new Pokemon(3, "Venusaur"), new Pokemon(4, "Charmander"),
                new Pokemon(5, "Charmeleon")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.bubbleSort(actual, comp);
        checkAssertions(expected, actual, (int) (Math.ceil(4 * COMPARISON_BUFFER)), comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void bubbleReverseSorted() {
        // [151, 150, 100, 52, 26, 25, 9, 8] ->
        // [8, 9, 25, 26, 52, 100, 150, 151]
        Pokemon[] expected = {new Pokemon(8, "Wartortle"), new Pokemon(9, "Blastoise"), new Pokemon(25, "Pikachu"), new Pokemon(26, "Raichu"),
                new Pokemon(52, "Meowth"), new Pokemon(100, "Voltorb"), new Pokemon(150, "Mewtwo"), new Pokemon(151, "Mew")};
        Pokemon[] actual = {new Pokemon(151, "Mew"), new Pokemon(150, "Mewtwo"), new Pokemon(100, "Voltorb"), new Pokemon(52, "Meowth"),
                new Pokemon(26, "Raichu"), new Pokemon(25, "Pikachu"), new Pokemon(9, "Blastoise"), new Pokemon(8, "Wartortle")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.bubbleSort(actual, comp);
        checkAssertions(expected, actual, (int) (Math.ceil(28 * COMPARISON_BUFFER)), comp.getCount());
    }

    /**
     * Also relies on boolean isSorted flag to be working.
     */
    @Test(timeout = TIMEOUT)
    public void bubbleStability() {
        // [479-W, 493, 488, 405, 479-F, 479-H, 483, 491, 484, 487, 492] ->
        // [405, 479-W, 479-F, 479-H, 483, 484, 487, 488, 491, 492, 493]
        Pokemon[] expected = {new Pokemon(405, "Luxray"), new Pokemon(479, "Rotom-Wash"), new Pokemon(479, "Rotom-Fan"),
                new Pokemon(479, "Rotom-Heat"), new Pokemon(483, "Dialga"), new Pokemon(484, "Palkia"), new Pokemon(487, "Giratina-O"),
                new Pokemon(488, "Cresselia"), new Pokemon(491, "Darkrai"), new Pokemon(492, "Shaymin-S"), new Pokemon(493, "Arceus")};
        Pokemon[] actual = {new Pokemon(479, "Rotom-Wash"), new Pokemon(493, "Arceus"), new Pokemon(488, "Cresselia"), new Pokemon(405, "Luxray"),
                new Pokemon(479, "Rotom-Fan"), new Pokemon(479, "Rotom-Heat"), new Pokemon(483, "Dialga"), new Pokemon(491, "Darkrai"),
                new Pokemon(484, "Palkia"), new Pokemon(487, "Giratina-O"), new Pokemon(492, "Shaymin-S")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.bubbleSort(actual, comp);
        checkAssertions(expected, actual, (int) (Math.ceil(34 * COMPARISON_BUFFER)), comp.getCount());
    }

    // ------------------------------------------------------------------
    // Mergesort Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void mergeExceptionNullArray() {
        try {
            Sorting.mergeSort(null, new ComparatorPlus<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void mergeExceptionNullComparator() {
        try {
            Sorting.mergeSort(new String[2], null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void mergeLength0Array() {
        String[] expected = new String[0];
        String[] actual = new String[0];
        ComparatorPlus<String> comp = new ComparatorPlus<String>() {
            @Override
            public int compare(String o1, String o2) {
                incrementCount();
                return o1.compareTo(o2);
            }
        };

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void mergeSize1Array() {
        Pokemon[] expected = {new Pokemon(582, "Vanillite")};
        Pokemon[] actual = {new Pokemon(582, "Vanillite")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void mergePowerOf2ArrayNoCopy() {
        // [212, 155, 186, 160, 197, 169, 178, 171] ->
        // [155, 160, 169, 171, 178, 186, 197, 212]
        Pokemon[] expected = {new Pokemon(155, "Cyndaquil"), new Pokemon(160, "Feraligatr"), new Pokemon(169, "Crobat"), new Pokemon(171, "Lanturn"),
                new Pokemon(178, "Xatu"), new Pokemon(186, "Politoed"), new Pokemon(197, "Umbreon"), new Pokemon(212, "Scizor")};
        Pokemon[] actual = {new Pokemon(212, "Scizor"), new Pokemon(155, "Cyndaquil"), new Pokemon(186, "Politoed"), new Pokemon(160, "Feraligatr"),
                new Pokemon(197, "Umbreon"), new Pokemon(169, "Crobat"), new Pokemon(178, "Xatu"), new Pokemon(171, "Lanturn")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, (int) (17 * COMPARISON_BUFFER), comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void mergeOddLengthNoCopy() {
        // [376, 257, 306, 286, 272, 385, 303, 258, 334] ->
        // [257, 258, 272, 286, 303, 306, 334, 376, 385]
        Pokemon[] expected = {new Pokemon(257, "Blaziken"), new Pokemon(258, "Mudkip"), new Pokemon(272, "Ludicolo"), new Pokemon(286, "Breloom"),
                new Pokemon(303, "Mawile"), new Pokemon(306, "Aggron"), new Pokemon(334, "Altaria"), new Pokemon(376, "Metagross"),
                new Pokemon(385, "Jirachi")};
        Pokemon[] actual = {new Pokemon(376, "Metagross"), new Pokemon(257, "Blaziken"), new Pokemon(306, "Aggron"), new Pokemon(286, "Breloom"),
                new Pokemon(272, "Ludicolo"), new Pokemon(385, "Jirachi"), new Pokemon(303, "Mawile"), new Pokemon(258, "Mudkip"),
                new Pokemon(334, "Altaria")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, (int) (21 * COMPARISON_BUFFER), comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void mergeCopy() {
        // [598, 596, 553, 579, 494, 607, 547, 635, 582, 526] ->
        // [494, 526, 547, 553, 579, 582, 596, 598, 607, 635]
        Pokemon[] expected = {new Pokemon(494, "Victini"), new Pokemon(526, "Gigalith"), new Pokemon(547, "Whimsicott"),
                new Pokemon(553, "Krookodile"), new Pokemon(579, "Cinccino"), new Pokemon(582, "Vanillite"), new Pokemon(596, "Galvantula"),
                new Pokemon(598, "Ferrothorn"), new Pokemon(607, "Litwick"), new Pokemon(635, "Hydreigon")};
        Pokemon[] actual = {new Pokemon(598, "Ferrothorn"), new Pokemon(596, "Galvantula"), new Pokemon(553, "Krookodile"),
                new Pokemon(579, "Cinccino"), new Pokemon(494, "Victini"), new Pokemon(607, "Litwick"), new Pokemon(547, "Whimsicott"),
                new Pokemon(635, "Hydreigon"), new Pokemon(582, "Vanillite"), new Pokemon(526, "Gigalith")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, (int) (23 * COMPARISON_BUFFER), comp.getCount());
    }

    /**
     * Also relies on working with odd length arrays and copying the
     * remainder of an array without further comparisons.
     */
    @Test(timeout = TIMEOUT)
    public void mergeStability() {
        // [663, 704, 698, 681, 686, 718-10%, 716, 718-50%, 718-100%, 717, 658]
        // ->
        // [658, 663, 681, 686, 698, 704, 716, 717, 718-10%, 718-50%, 718-100%]
        Pokemon[] expected = {new Pokemon(658, "Greninja"), new Pokemon(663, "Talonflame"), new Pokemon(681, "Aegislash"), new Pokemon(686, "Inkay"),
                new Pokemon(698, "Amaura"), new Pokemon(704, "Goomy"), new Pokemon(716, "Xerneas"), new Pokemon(717, "Yvetal"),
                new Pokemon(718, "Zygarde-10%"), new Pokemon(718, "Zygarde-50%"), new Pokemon(718, "Zygarde-100%")};
        Pokemon[] actual = {new Pokemon(663, "Talonflame"), new Pokemon(704, "Goomy"), new Pokemon(698, "Amaura"), new Pokemon(681, "Aegislash"),
                new Pokemon(686, "Inkay"), new Pokemon(718, "Zygarde-10%"), new Pokemon(716, "Xerneas"), new Pokemon(718, "Zygarde-50%"),
                new Pokemon(718, "Zygarde-100%"), new Pokemon(717, "Yvetal"), new Pokemon(658, "Greninja")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.mergeSort(actual, comp);
        checkAssertions(expected, actual, 27, comp.getCount());
    }

    // ------------------------------------------------------------------
    // LSD Radix sort Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void lsdExceptionNullArray() {
        try {
            Sorting.lsdRadixSort(null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void lsdSize0Array() {
        int[] expected = new int[0];
        int[] actual = new int[0];

        actual = Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void lsdGeneralPositives() {
        int[] expected = {1, 13, 56, 597, 777, 935, 1242, 2260, 31000, 48104};
        int[] actual = {597, 48104, 1242, 56, 935, 777, 2260, 13, 1, 31000};

        actual = Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void lsdGeneralPosAndNeg() {
        int[] expected = {-77700, -597, -13, 0, 56, 93, 343, 1240, 3770, 22611};
        int[] actual = {-77700, 56, 343, 1240, 3770, 0, 93, 22611, -597, -13};

        actual = Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void lsdZeroBucketTermination() {
        int[] expected = {-40, 10, 50, 100, 700, 1100};
        int[] actual = {100, 10, 50, 700, -40, 1100};

        actual = Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void lsdMinValue() {
        int[] expected = {Integer.MIN_VALUE, -980997825, -486898317, 0, 320195863, 420195863, 523834390, 720195863, 781471215};
        int[] actual = {420195863, 720195863, 320195863, -486898317, 523834390, Integer.MIN_VALUE, 781471215, 0, -980997825};

        actual = Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void lsdMinMaxValue() {
        int[] expected = {Integer.MIN_VALUE, -582, -97, 11, 4930, 4930, 12345, 20000, Integer.MAX_VALUE};
        int[] actual = {Integer.MAX_VALUE, 4930, -582, -97, 4930, 20000, Integer.MIN_VALUE, 11, 12345};

        Sorting.lsdRadixSort(actual);
        assertArrayEquals("LSD Radix did not sort", expected, actual);
    }

    /*
     * INSERTION SORT TESTS
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void insertionExceptionNullArray() {
        try {
            Sorting.insertionSort(null, new ComparatorPlus<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    incrementCount();
                    return o1 - o2;
                }
            });
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void insertionExceptionNullComparator() {
        try {
            Sorting.insertionSort(new Integer[5], null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void insertionSize0Array() {
        Integer[] expected = new Integer[0];
        Integer[] actual = new Integer[0];
        ComparatorPlus<Integer> comp = new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                incrementCount();
                return o1 - o2;
            }
        };

        Sorting.insertionSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void insertionSize1Array() {
        Integer[] expected = {1337};
        Integer[] actual = {1337};
        ComparatorPlus<Integer> comp = new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                incrementCount();
                return o1 - o2;
            }
        };

        Sorting.insertionSort(actual, comp);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void insertionAlreadySorted() {
        Pokemon[] expected = {new Pokemon(1, "le nice poke guy"), new Pokemon(2, "squid kid"), new Pokemon(3, "lightning cat"),
                new Pokemon(4, "dark magician"), new Pokemon(5, "yu-gi-oh-e-mon")};
        Pokemon[] actual = {new Pokemon(1, "le nice poke guy"), new Pokemon(2, "squid kid"), new Pokemon(3, "lightning cat"),
                new Pokemon(4, "dark magician"), new Pokemon(5, "yu-gi-oh-e-mon")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.insertionSort(actual, comp);
        checkAssertions(expected, actual, 4, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void insertionReverseSorted() {
        Pokemon[] expected = {new Pokemon(7, "Light Magician"), new Pokemon(8, "Twin Princes"), new Pokemon(9, "Old Demon King"),
                new Pokemon(10, "Ysera"), new Pokemon(17, "EXODIA"), new Pokemon(19, "waddup"), new Pokemon(30, "its dat boi"),
                new Pokemon(33, "Gundyr"), new Pokemon(37, "Slifer"), new Pokemon(43, "Obelisk the Tormentor"), new Pokemon(49, "Dark Magician")};
        Pokemon[] actual = {new Pokemon(49, "Dark Magician"), new Pokemon(43, "Obelisk the Tormentor"), new Pokemon(37, "Slifer"),
                new Pokemon(33, "Gundyr"), new Pokemon(30, "its dat boi"), new Pokemon(19, "waddup"), new Pokemon(17, "EXODIA"),
                new Pokemon(10, "Ysera"), new Pokemon(9, "Old Demon King"), new Pokemon(8, "Twin Princes"), new Pokemon(7, "Light Magician")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.insertionSort(actual, comp);
        checkAssertions(expected, actual, 58, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void insertionStability() {
        Pokemon[] expected = {new Pokemon(1, "Zulrah"), new Pokemon(2, "Cleric Beast"), new Pokemon(7, "Champion Gundyr"),
                new Pokemon(9, "Kola Yhtapirt"), new Pokemon(12, "Alok Tripathy"), new Pokemon(17, "taurus demon"), new Pokemon(39, "cool guy-B"),
                new Pokemon(39, "cool guy-R"), new Pokemon(39, "cool guy-O"), new Pokemon(39, "cool guy-D"), new Pokemon(39, "cool guy-Y"),
                new Pokemon(49, "Corp Beast")};
        Pokemon[] actual = {new Pokemon(39, "cool guy-B"), new Pokemon(17, "taurus demon"), new Pokemon(39, "cool guy-R"), new Pokemon(1, "Zulrah"),
                new Pokemon(49, "Corp Beast"), new Pokemon(39, "cool guy-O"), new Pokemon(2, "Cleric Beast"), new Pokemon(7, "Champion Gundyr"),
                new Pokemon(39, "cool guy-D"), new Pokemon(12, "Alok Tripathy"), new Pokemon(9, "Kola Yhtapirt"), new Pokemon(39, "cool guy-Y")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.insertionSort(actual, comp);
        checkAssertions(expected, actual, 41, comp.getCount());
    }
    //--End of Insertion Sort Tests

    /*
     * QUICK SORT TESTS
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void quickExceptionNullArray() {
        try {
            Sorting.quickSort(null, new ComparatorPlus<String>() {
                @Override
                public int compare(String o1, String o2) {
                    incrementCount();
                    return o1.compareTo(o2);
                }
            }, random);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void quickExceptionNullComparatorOrRandom() {
        try {
            Sorting.quickSort(new Integer[5], null, random);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            try {
                Sorting.quickSort(new String[5], new ComparatorPlus<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        incrementCount();
                        return o1.compareTo(o2);
                    }
                }, null);
            } catch (IllegalArgumentException ex) {
                assertEquals(ex.getClass(), IllegalArgumentException.class);
                throw ex;
            }
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void quickSize0Array() {
        String[] expected = new String[0];
        String[] actual = new String[0];
        ComparatorPlus<String> comp = new ComparatorPlus<String>() {
            @Override
            public int compare(String o1, String o2) {
                incrementCount();
                return o1.compareTo(o2);
            }
        };

        Sorting.quickSort(actual, comp, random);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void quickSize1Array() {
        Pokemon[] expected = {new Pokemon(1337, "Blue Eyes White Dragon")};
        Pokemon[] actual = {new Pokemon(1337, "Blue Eyes White Dragon")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.quickSort(actual, comp, random);
        checkAssertions(expected, actual, 0, comp.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void quickAlreadySorted() {
        Pokemon[] expected = {new Pokemon(-25, "Dark Magician"), new Pokemon(-8, "Obelisk the Tormentor"), new Pokemon(0, "EXODIA"),
                new Pokemon(0, "Light Magician"), new Pokemon(5, "Slifer"), new Pokemon(12, "Ysera"), new Pokemon(16, "Skeleton guy"),
                new Pokemon(20, "Gundyr"), new Pokemon(30, "Old Demon King"), new Pokemon(70, "Twin Princes")};
        Pokemon[] actual = {new Pokemon(-25, "Dark Magician"), new Pokemon(-8, "Obelisk the Tormentor"), new Pokemon(0, "EXODIA"),
                new Pokemon(0, "Light Magician"), new Pokemon(5, "Slifer"), new Pokemon(12, "Ysera"), new Pokemon(16, "Skeleton guy"),
                new Pokemon(20, "Gundyr"), new Pokemon(30, "Old Demon King"), new Pokemon(70, "Twin Princes")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.quickSort(actual, comp, random);
        checkAssertionsUnstable(expected, actual, 30, comp.getCount(), comp);
    }

    @Test(timeout = TIMEOUT)
    public void quickGeneral() {
        Pokemon[] expected = {new Pokemon(-25, "Dark Magician"), new Pokemon(-8, "Obelisk the Tormentor"), new Pokemon(0, "EXODIA"),
                new Pokemon(0, "Light Magician"), new Pokemon(5, "Slifer"), new Pokemon(12, "Ysera"), new Pokemon(16, "Skeleton guy"),
                new Pokemon(20, "Gundyr"), new Pokemon(30, "Old Demon King"), new Pokemon(70, "Twin Princes")};
        Pokemon[] actual = {new Pokemon(5, "Slifer"), new Pokemon(30, "Old Demon King"), new Pokemon(16, "Skeleton guy"),
                new Pokemon(-8, "Obelisk the Tormentor"), new Pokemon(0, "EXODIA"), new Pokemon(70, "Twin Princes"), new Pokemon(0, "Light Magician"),
                new Pokemon(12, "Ysera"), new Pokemon(20, "Gundyr"), new Pokemon(-25, "Dark Magician")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.quickSort(actual, comp, random);
        checkAssertionsUnstable(expected, actual, 33, comp.getCount(), comp);
    }

    @Test(timeout = TIMEOUT)
    public void quickReversedArray() {
        Pokemon[] expected = {new Pokemon(1, "Light Magician"), new Pokemon(3, "Twin Princes"), new Pokemon(4, "Old Demon King"),
                new Pokemon(7, "Ysera"), new Pokemon(11, "EXODIA"), new Pokemon(15, "waddup"), new Pokemon(29, "its dat boi"),
                new Pokemon(33, "Gundyr"), new Pokemon(39, "Slifer"), new Pokemon(47, "Obelisk the Tormentor"), new Pokemon(50, "Dark Magician")};
        Pokemon[] actual = {new Pokemon(50, "Dark Magician"), new Pokemon(47, "Obelisk the Tormentor"), new Pokemon(39, "Slifer"),
                new Pokemon(33, "Gundyr"), new Pokemon(29, "its dat boi"), new Pokemon(15, "waddup"), new Pokemon(11, "EXODIA"),
                new Pokemon(7, "Ysera"), new Pokemon(4, "Old Demon King"), new Pokemon(3, "Twin Princes"), new Pokemon(1, "Light Magician")};
        ComparatorPlus<Pokemon> comp = Pokemon.getComparator();

        Sorting.quickSort(actual, comp, random);
        checkAssertionsUnstable(expected, actual, 58, comp.getCount(), comp);
    }
    //---End of Quick Sort Tests

    /*
     * MSD RADIX SORT TESTS
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void msdExceptionNullArray() {
        try {
            Sorting.msdRadixSort(null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void msdSize0Array() {
        int[] expected = new int[0];
        int[] actual = new int[0];

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdSmallArray() {
        int[] expected = {1, 13, 133, 1337};
        int[] actual = {1337, 133, 13, 1};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdGeneralPositives() {
        int[] expected = {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610};
        int[] actual = {89, 55, 3, 610, 1, 21, 8, 2, 233, 5, 13, 377, 34, 144};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdGeneralPosAndNeg() {
        int[] expected = {-1991, -1001, -565, -89, -11, 0, 12, 56, 320, 321, 457, 1899, 20000};
        int[] actual = {-89, 457, 20000, -1991, -11, 320, 56, -1001, 1899, 0, 12, 321, -565};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdZeroBucketTermination() {
        int[] expected = {-40, -20, 20, 40, 100, 1000};
        int[] actual = {40, -40, 1000, 20, -20, 100};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdMinValue() {
        int[] expected = {Integer.MIN_VALUE, -980997825, -486898317, 0, 320195863, 420195863, 523834390, 720195863, 781471215};
        int[] actual = {420195863, 720195863, 320195863, -486898317, 523834390, Integer.MIN_VALUE, 781471215, 0, -980997825};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void msdMinMaxValue() {
        int[] expected = {Integer.MIN_VALUE, -1211, -500, -251, -107, -51, 0, 5, 788, 6789, 10000, Integer.MAX_VALUE};
        int[] actual = {-107, 6789, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1211, 10000, 788, -500, -251, 5, -51};

        actual = Sorting.msdRadixSort(actual);
        assertArrayEquals("MSD Radix did not sort", expected, actual);
    }
    //--- End of All Tests


    // ------------------------------------------------------------------
    // Helper methods and classes

    public <T> void checkAssertions(T[] expected, T[] actual, int compExpected, int compActual) {
        assertArrayEquals("Did not sort array correctly", expected, actual);
        assertTrue("Made too many comparisons (" + compActual + ")", compActual <= compExpected);
    }

    public <T> void checkAssertionsUnstable(T[] expected, T[] actual, int compExpected, int compActual, Comparator<T> comparator) {
        Set<T> expectedContents = new HashSet<T>();
        Set<T> actualContents = new HashSet<T>();

        for (T element : expected) {
            expectedContents.add(element);
        }

        for (T element : actual) {
            actualContents.add(element);
        }

        // sanity check, making sure all elements are unique
        // for these junits, they should be
        assertEquals(expected.length, expectedContents.size());

        // assert exactly the right number of elements in the final array
        assertEquals(actual.length, actualContents.size());
        assertEquals(actual.length, expected.length);

        // assert all the elements present in one array are present in the other
        assertEquals(expectedContents, actualContents);

        // assert the actual array is sorted
        for (int i = 0; i < actual.length - 1; i++) {
            assertTrue(comparator.compare(actual[i], actual[i + 1]) <= 0);
        }

        assertTrue("Made too many comparisons (" + compActual + ")", compActual <= compExpected);
    }

    /**
     * Represents a pokemon
     */
    private static class Pokemon {
        private int dexNo;
        private String name;

        /**
         * Creates a pokemon
         *
         * @param dexNo the national dex number
         * @param name  the name of the pokemon
         */
        public Pokemon(int dexNo, String name) {
            this.dexNo = dexNo;
            this.name = name;
        }

        // So this is really bad practice, but equals() is based on dex number
        // and name, while compare() is based on only dex number.
        // It makes the stability checks a bit easier, though
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Pokemon)) {
                return false;
            }
            return this.dexNo == ((Pokemon) (obj)).dexNo && this.name.equals(((Pokemon) (obj)).name);
        }

        @Override
        public String toString() {
            return this.dexNo + ": " + this.name;
        }

        @Override
        public int hashCode() {
            return this.dexNo + this.name.hashCode() * 31;
        }

        public static ComparatorPlus<Pokemon> getComparator() {
            return new ComparatorPlus<Pokemon>() {
                @Override
                public int compare(Pokemon p1, Pokemon p2) {
                    incrementCount();
                    return p1.dexNo - p2.dexNo;
                }
            };
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}
