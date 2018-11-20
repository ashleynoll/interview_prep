# Stacks and Queues

These data structures can be implemented two ways: with a linked list or with an array. 
These methods have their tradeoffs, but both options will be discussed.

## Stacks

The stack is a LIFO (last-in-first-out) data structure. Just like in real life with a stack of books or plates, the item on the
top of the stack is the next item to be taken off. This is an incredibly powerful data structure as it is used in such cases as
recursion (pushing method calls into a stack and popping them back off on returns) and can also be used for depth-first search.

Elements are put onto the stack through `push`, removed through `pop`, and the first element can be viewed through `peek`.

### Array Stacks

When implemented with an array, elements are `push`ed to the next available index starting from the front and then `pop`ped from the end.

#### Strengths
- Less memory as arrays do not need a next pointer

#### Weaknesses
- Array must regrow if more elements are added than its capacity

| Operation        | Description                             | Time Complexity    |
| --------------- |:----------------------------------------:| ------------------:|
| push            | add data to the end of the stack         | amortized O(1)     |
| pop             | remove and return top data of the stack  | O(1)               |
| peek            | return the value on top of the stack     | O(1)               |

### Linked List Stacks

When implemented with a linked list, stacks are both `push`ed and `pop`ped to the front of the list to maintain O(1) time complexities.


#### Strengths
- All time complexities are O(1) as there is dynamic resizing

#### Weaknesses
- Requires more memory to store next pointer

| Operation        | Description                             | Time Complexity    |
| --------------- |:----------------------------------------:| ------------------:|
| push            | add data to the end of the stack         | O(1)               |
| pop             | remove and return top data of the stack  | O(1)               |
| peek            | return the value on top of the stack     | O(1)               |

## Queues

The queue data structure can be thought of as a line of people. The first person to join the line will be the next person who is
served from the line. Thus, this is FIFO (first-in, first-out). Queues can be used in scheduling algorithms or breadth-first search.

Elements are added to the queue through `enqueue`, removed through `dequeue`, and the top element is accessed through `peek`.

### Array Queues

Array-backed queues can be tricky to implement. Since elements are added and removed from opposite ends, there is not a static
beginning to the queue. Therefore, when creating this data structure efficiently, you'll need to maintian a variable that
tracks the starting index. Instead of regrowing when data is added to the last index of the array, more elements can be potentially
added to the front of the queue since the start can move further into the list upon dequeue. However, when the array is completely
filled, the elements will need to "unravel" so that the start of the queue is once more at index 0.

#### Strengths
- Less space needed as there are no next pointers

#### Weaknesses
- Must resize after n `enqueue`s
- More complex code

| Operation        | Description                             | Time Complexity    |
| --------------- |:----------------------------------------:| ------------------:|
| enqueue         | add data to the end of the queue         | amortized O(1)     |
| dequeue         | remove and return first data of the queue| O(1)               |
| peek            | return the first value of the queue      | O(1)               |

### Linked List Queues

Linked Lists allow queues to be more simplistically implemented. Elements are added to the end of the list and removed from the
front for optimal time complexities.

#### Strengths
- Less complex code
- Dynamically resizes

#### Weaknesses
- More memory used for pointers

| Operation        | Description                             | Time Complexity    |
| --------------- |:----------------------------------------:| ------------------:|
| enqueue         | add data to the end of the queue         | O(1)               |
| dequeue         | remove and return first data of the queue| O(1)               |
| peek            | return the first value of the queue      | O(1)               |
