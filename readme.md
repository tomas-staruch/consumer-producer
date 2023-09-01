# Processing of commands from FIFO queue using producer - consumer pattern

Demonstration of processing of DB commands from FIFO queue using producer - consumer pattern. 

The sequence of commands:

```
Add (1, a1, Robert)
Add (2, a2, Martin)
PrintAll
DeleteAll
```

The commands are processed in order as long as there is just a single consumer:

```
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Add user User {1, a1, Robert} to DB
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Add user User {2, a2, Martin} to DB
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Print all users from DB:
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - user: User {1, a1, Robert}
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - user: User {2, a2, Martin}
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Delete all users from DB
```

When there are two or more consumer, the order is not guaranteed. Thread Scheduler can execute threads - consumers in unexpected order. It can result into following sequence:

```
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Add user User {1, a1, Robert} to DB
[pool-1-thread-1] INFO org.example.datasources.DatabaseRepository - Delete all users from DB
[pool-1-thread-3] INFO org.example.datasources.DatabaseRepository - Print all users from DB:
[pool-1-thread-2] INFO org.example.datasources.DatabaseRepository - Add user User {2, a2, Martin} to DB
```