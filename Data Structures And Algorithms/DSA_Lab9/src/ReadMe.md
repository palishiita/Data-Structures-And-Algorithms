   - findSet(int item): returns the representative of the set to which item belongs to
     two items belong to the same group if they have the same representative
   - union(int itemA, int itemB): joins the group that itemA belongs to with the group that itemB belongs to
   - makeSet(int item): - a set of one-item set

   Disjoint set LINKED LIST implementation: one-way linked list implemented on an array
- The items (integer) are indexes of the array
- In the array every item stores information about index of the next item (-1 if there are no next item),
  index of head (representative), the size of the list (but only for the head this value is updated) and.
  index of the tail element (also updated only in the head).
- The size is used during union operation to connect a smaller list to a longer list (because in the first list we
  have to update the reference to the head of the second list) and the index of tail element to not spend time
  searching for the tail.
- To String(): linked list as a sequence of number from a head to a tail, separated by comma and one space.
  Lists have to be ordered depending of the value of the first element.

https://www.geeksforgeeks.org/disjoint-set-data-structures/
   Disjoint set FOREST implementation: (many trees create forest - tree implementation)
- array of elements
- stores: index of the parent element and rank.
- node has info about where is the parent
- root of each tree contains the representative and is its own parent
- ToString(): For DisjointSetForest the first line has to be equal “Disjoint sets as forest:”
  and every next line will present the connection from every item to its parent in the format: “x -> y”,
  where x is an item and y is its parent. Values of x have to be from 0 to size()

Another implementation is with use of disjoint set forest. Like in previous one, we start from
an array of elements. In one cell it is stored only two data: index of the parent element and a
rank. In graphical representation it can be viewed as a set of trees. In this type of tree every
node has only information where is the parent, and a root has itself as the parent.
But in this implementation during FindSet operation it has to be done a path compression.
The representant is the root of the tree, so the procedure to find it goes from an element to its
parent, than to the parent of previous parent and so on, till an node which has itself as a parent.
This can be done in a recursive way. During come backing from recursion, the nodes on the
path to a root update the field with parent with the root value. So, the next call of findSet for
many argument will need many less steps to find the root (exactly 2 steps).
The union operation simple connect one representant to another reprentant as a parent. Which
of the representant will be a root of a new tree depend on the rank. The node with smaller rank
is connected to the one with bigger rank. If two considered ranks are equal, after connection we
have to increase by one the rank of the root.