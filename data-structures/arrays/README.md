# Arrays & Array Lists

## Arrays

### Strengths
* Lower memory usage
* O(1) access
* Fast insertion/removal from end

### Weaknesses
* Static Size
* Contiguous memory allocation
* Adding and deleting are O(n)

## ArrayLists

### Strengths
* Resizes for amortized O(n) operations
* Lower memory usage
* O(1) access
* Fast insertion/removal from end

### Weaknesses
* Contiguous memory allocation
* Inconsistent runtimes due to resizing
* Adding and deleting are O(n)


| Operation             | Description                                                                               | Time Complexity  |
| -------------------- |:--------------------------------------------------------------------:| --------------------:|
| addAtIndex           | add data into array at index, shifting existing elements          | amortized O(n)     |
| addToBack           | add data to last index of array                                                 | amortized O(1)     |
| addToFront           | add data to first index of array, shifting all elements               | amortized O(n)     |
| removeAtIndex     | remove data at index from array, shifting elements to fill gap |  O(n)                     |
| removeFromFront | remove data from first index, shifting elements to fill gap       |  O(n)                     |
| removeFromBack | remove data from the end of the array                                     |  O(1)                     |
| access                  | get data from given index                                                         | O(1)                      |
