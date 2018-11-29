# Trees

Trees are acyclic, connected graphs. They consist of nodes that contain data and pointers to child nodes. Though trees may
have any number of children, this directory will focus on two popular binary trees (two children max). Trees are useful for
demonstrating relationships between data in a heirarchical fashion. Common traversals of trees include pre-order, in-order,
post-order, and level order. The first three are much like depth first search on graphs whereas the latter is like breadth first
search.

This directory includes implementation for both Binary Search Trees and AVL trees which are self-balancing BSTs.

## BSTs (Binary Search Trees)

Binary Search Trees are special trees that can have at max two children and also hold the special property that all children
to the left of a node must be less than the node and all children to the right must be greater than the node. Depending on
the implementation, duplicates may or may not be allowed and may fall on different sides of the tree. BSTs are useful for
data lookup as they have an average runtime of O(logn) for lookup. However, BSTs can become degenerate which means that nodes
go to one side more than another and worst case runtime can be O(n) in this case.

### Strengths
* Simpler to code
* Maintains order of insertion
* O(logn) average lookup

### Weaknesses
* Can become degenerate and have O(n) runtimes

| Operation        | Description                                     | Time Complexity       |
| ---------------- |:-----------------------------------------------:| ---------------------:|
| add              | add data into the correct position in the BST   | O(logn) (worst O(n))  |
| remove           | remove data from the BST                        | O(logn) (worst O(n))  |
| access           | find the given data in the BST                  | O(logn) (worst O(n))  |

## AVLs (Adelson-Velsky and Landis)

AVLs are a type of self-balancing BSTs. These trees maintain balance by having all nodes store their respective heights. Then,
upon addition or deletion, every node that was traversed through on the path will have their heights updated. If the heights
differ by more than one, a series of rotations might occur with the nodes to ensure that the heights never differ by more than
one. This ensures that all runtimes will be O(logn), however this requires more complex code and more memory to store the heights.

### Strengths
* Guarenteed O(logn) runtime

### Weaknesses
* Requires more memory
* More complex code


| Operation        | Description                                     | Time Complexity    |
| ---------------- |:-----------------------------------------------:| ------------------:|
| add              | add data into the correct position in the AVL   | O(logn)            |
| remove           | remove data from the AVL                        | O(logn)            |
| access           | find the given data in the AVL                  | O(logn)            |
