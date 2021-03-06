

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
 
[Maven][MVNINST] is used for dependency management in this project and there are two options to exercise the behavior of the classes. 
 
###Testing: 
There are some test cases in [./src/test][TESTDIR] which can be run as follows
 
```
     mvn test
```     
     
###Example usage:  
There is a [library runner class][LRCLASS] which creates some members and a fake timer. The members interact with the library on a loop as follows:

- Ask which items are available for them to borrow
- Borrow one
- Enjoy the book or DVD for a random number of days
- check for any overdue items (we would pay the fine here too if that was supported)
- Return the item      
 
```
     mvn exec:java 
```


Core classes
------------

###Library
Responsible for maintaining a record of which items were loaned to which member. 

There are other services we expect a library to provide but they are covered by 'micro-service' classes. 

###Member
A very naïve member class with a collection of roles. The roles indicate which items the member can borrow from the library but provides and extension point for other services we need to add in the future, for example a children's library or a rare books reading room. 

###Item
This class represents a specific physical book, DVD or VHS Cassette which we can loan to a member. 

###LoanRecord
Provides a relationship between a member and the checkout of a specific item.  We log the checkout date and the expected return date on creation of the object. 


###FrontDesk
The front desk class provides a way for members to interact with the library. The Simple implementation currently in place provides some abstraction for the  clients wrapping the service calls in convenience methods. The next phase of development will add the ability to separate the Library instance from the members by providing a restful API for the FrontDesk to call. 

Further improvements and features
---------------------------------

See the [github issues log][ALLISSUES] for details of outstanding features and improvements. 



   
   [ITEM1]: https://github.com/codingSteve/library/issues/1
   [ITEM2]: https://github.com/codingSteve/library/issues/2
   [ITEM3]: https://github.com/codingSteve/library/issues/3
   [ITEM4]: https://github.com/codingSteve/library/issues/4
   [TESTDIR]: https://github.com/codingSteve/library/tree/master/src/test
   [LRCLASS]: https://github.com/codingSteve/library/blob/master/src/main/java/bestcoders/library/world/LibraryRunner.java
   [ALLISSUES]: https://github.com/codingSteve/library/issues
   [MVNINST]: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html