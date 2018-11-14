# Linked Lists

Implementations of both singly and doubly linked lists. Below are the associated time complexities of each.

Recursion is generally a good approach to linked list problems.

## Singly Linked Lists

Typically includes a head pointer (required), tail pointer, and size variable.

### Strengths
- Good for fast adding to the front or back
- Less space needed than doubly linked lists
- Dyanamic regrow, no amortized times like arrays
- Does not require contiguous memory allocation; nodes can be anywhere

### Weaknesses
- More space needed than arrays as data and pointer are stored
- Slow access time

| Operation             | Description                         | Time Complexity  |
| -------------------- |:----------------------------------:| --------------------:|
| addAtIndex           | add data into list at index         | O(n)                     |
| addToBack           | add data to back of list             | O(1) with tail pointer     |
| addToFront           | add data to first of list           | O(1)                      |
| removeAtIndex       | remove data at index                 | O(n)                  |
| removeFromFront     | remove data from first of list       | O(1)                     |
| removeFromBack     | remove data from the end of the list  | O(n)                     |
| access             | get data from given index             | O(n)                      |


## Doubly Linked Lists

### Strengths
- Fast adding and removal from the ends
- Can optimize traversal by traveling forwards or backwards to node

### Weaknesses
- Requires more space than singly linked lists as there are twice as many pointers
- Pointer manipulation on add/removal is more complex

| Operation             | Description                         | Time Complexity  |
| -------------------- |:----------------------------------:| --------------------:|
| addAtIndex           | add data into list at index         | O(n)                     |
| addToBack           | add data to back of list             | O(1)                     |
| addToFront           | add data to first of list           | O(1)                      |
| removeAtIndex       | remove data at index                 | O(n)                  |
| removeFromFront     | remove data from first of list       | O(1)                     |
| removeFromBack     | remove data from the end of the list  | O(1)                     |
| access             | get data from given index             | O(n)                      |
