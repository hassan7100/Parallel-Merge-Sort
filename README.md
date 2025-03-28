# Parallel-Merge-Sort
Includes an implementation for merge sort using work stealing mechanism

50_000_000 elements array sorting, 12 threads, 1_000_000 elements threshold, we get these results:

Parallel Time: 797 ms, 

Serial Time: 5498 ms

Which is 86% faster sorting, in other words, ~6.9 times faster than serial sorting.

I noticed that the threshold must be choosed carefully, because if you keep assigning small tasks to threads,
this would mean some more time as there is an overhead for cpu scheduling, context switiching etc.
