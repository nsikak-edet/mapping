# Backend Coding Challenge: Mapping Challenge

### Solution Details
#### Service Interface: 
-	Redesign of service class to an interface. I observed that the service class
 was tightly coupled to the application, and this isn't a good software design
  practice. I extracted all service features to an interface so 
  that application will be loosely coupled with the service class. 
  On this regard, the Service interface `‘ArticleService’` and service 
  implementation class `‘ArticleServiceImpl’` were introduced. 
-	I implemented the Todos on `ArticleService#list()` and 
`ArticleServicce#articleForId(Long id)` respectively

#### Mapping Model to DTO
-	I added a dependency `ModelMapper` to gradle file for mapping of java
 classes (DTO & Models)
-	Autowired modelMapper object `ModelMapper` into `ArticleMapper` class for 
all mapping operations

#### Not Found Exception
-	I used controller advice `@RestControllerAdvice` for handling 404 exception
 because it helps in decoupling exception handler from the application. on this note
   `ErrorHandlerController` class was created.
-	`Controller#details` method was modified to throw 404 exception if requested domain isn’t available

#### Sorting: 
To sort `ArticleBlockDto` collection, I did the following:
*	To sort `ArticleBlockDto` collection, I created an interface `‘Sortable’`
 with a single method `‘sort’ `
*	Implements `‘Comparable<ArticleBlockDto>’` on `ArticleBlockDto` which 
will be used by `‘Collection.sort()’`
*	Implements `‘Sortable’` interface on `ArticleDto`, this will make
 it easier to sort `‘ArticleBlockDto’` Collections
*	On the service class, it was possible to call `‘ArticleDto#sort()’` operation 
on `ArticleDTO` object to sort block `Collections` (whenever sorting is needed)

#### Testing: 
A number of test cases has been implemented to check application functions.

## Thank You!  
