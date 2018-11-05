/**
 * Problem:
 *
 * Write a method to replace all spaces in a string with '%20'. You many assume
 * that the string has sufficient space at the end to hold the additional characters,
 * and that you are given the "true" length of the string. Note: if implementing
 * in Java, please use a character array so that you can perform this operation
 * in place.
 *
 * Solution Explanation:
 *
 * Since there needs to be more characters inserted into the array, you'll need
 * to start from the end and shift characters to make room. Place a pointer at the
 * last available index and start a loop to iterate backwards over the array.
 * Copy the character to the end pointer and decrement the end pointer.
 * Whenever a space is encountered, use the end pointer to add
 * '0', '2', and '%' and continue on.
 *
 * Optional optimization - Use the difference between truelength and the length
 * of the character array to find the number of spaces and end after it is reached.
 */
public class URLify {
    public static void URLify(char[] characters, int trueLength) {
        if (characters.length == trueLength) {
            return;
        }
        int numSpacesRemaining = (characters.length - trueLength) / 2,
            endPointer = characters.length - 1;
        for (int i = trueLength - 1; i >= 0; i--) {
            char c = characters[i];
            if (c != ' ') {
                characters[endPointer--] = c;
            } else {
                characters[endPointer--] = '0';
                characters[endPointer--] = '2';
                characters[endPointer--] = '%';
                numSpacesRemaining--;
                if (numSpacesRemaining == 0) {
                    return;
                }
            }
        }
    }

    private static void printArray(char[] chars) {
        for (char c : chars) {
            System.out.print(c);
        }
    }

    public static void main(String[] args) {
        char[] ans = new char[]{'m', 'r', ' ', 'j', 'o', 'h', 'n', ' ', 's', 'm', 'i', 't', 'h', ' ', ' ', ' ', ' '};
        URLify(ans, 13);

        System.out.println("Given answer for 'mr john smith ' is: ");
        printArray(ans);
        System.out.println();

        ans = "nospace".toCharArray();
        URLify(ans, 7);

        System.out.println("Given answer for 'nospace' is: ");
        printArray(ans);
        System.out.println();

        System.out.println("All tests complete!");
    }
}
