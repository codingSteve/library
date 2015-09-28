

Best coders: Library
===================

Simple project showing a set of public library classes and behaviors

http://codingsteve.github.io/docs/2015-09-26-library-project/

Basic functionality
-------------------

 - [Borrow][ITEM1] and [return][ITEM2] items - 
   items are loaned out for a period of one week. 
   For example, a customer can borrow WarGames on 21st February and they will be expected to return it by 28th February.
   

 - [Determine current inventory][ITEM3] - show the current items that are available for loan. We provide support for multiple copies of the same item (i.e. there can be multiple copies of the same book/movie).  
   
 - [Determine overdue items][ITEM4] - show all items that should have been returned before today. 
   For example, if a book was due on 12th February and today is 15th February, that book should be flagged as overdue. 

 
Running the application
-----------------------
 
Maven is used for dependency management in this project and there are two options to exercise the behavior of the classes
 
 - Testing: There are some test cases in [./src/test][TESTDIR] which can be run as follows
 
```
     mvn test
```     
     
- Example usage:  There is a library runner class which creates some members and a fake timer. The members interact with the library on a loop as follows:

- Ask which items are available for them to borrow
- Borrow one
- Enjoy the book or DVD for a random number of days
- check for any overdue items (we would pay the fine here too if that was supported)
- Return the item      
 
```
     mvn exec:java 
```




   
   [ITEM1]: https://github.com/codingSteve/library/issues/1
   [ITEM2]: https://github.com/codingSteve/library/issues/2
   [ITEM3]: https://github.com/codingSteve/library/issues/3
   [ITEM4]: https://github.com/codingSteve/library/issues/4
   [TESTDIR]: https://github.com/codingSteve/library/tree/master/src/test