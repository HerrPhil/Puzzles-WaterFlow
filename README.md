# Puzzles-WaterFlow

Find the positions that can flow to both the Pacific and the Atlantic.

## Notes from Greg Hogg video

The input to this problem is an M x N grid of integers.

The outer boundaries represent the Pacific and the Atlantic.

All "shoreline" positions from (0, 0) to (m - 1, 0) and (0,0) to (n - 1, 0) are the Pacific Coast.

All "shoreline" positions from (m - 1, 0) to (m - 1, n - 1) and (0, n - 1) to (m - 1, n - 1) are the Atlantic Coast.

The numbers on the grid are representing an elevation map.

When rain falls on a position, then it flows out to all adjacent positions at the same or lower elevation.

Return all the positions (i, j) that can reach both the Pacific and the Atlantic.

We use the same initial step for the Atlantic and Pacific.

The "shoreline" positions all flow into an ocean.

We queue up these "shoreline" positions to start a BFS (more on that below).

Their neighbours can get to ocean as long as they are greater than or equal to the current position.

That will give us two sets of positions, P and A.

Set P is all the positions that can reach the Pacific.

Set A is all the positions that can reach the Atlantic.

We take the intersection of those two sets to find the positions that flow to both oceans.

## Bread-First Search (BFS)

The breadh-first search is accomplished by using two data structures.

The queue are the positions to analyze whether neighbouring positions match the business rule.

The set are the positions that have been "seen", or analyzed.

When next positions match the business rule, then they are added to the back of the queue.

Also, when next positions match the business rule, then they are added to the set of seen positions.

That allows the BFS to always move forward.

The BFS stop condition is when the queue is empty, and the final (i, j) position
popped from the queue has no more unseen neighbouring positions.

See
[this article](https://www.programiz.com/dsa/graph-bfs)
and
[this article](https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/)
for more explanations and examples of BFS.
