spring_mvc_base
===============

Base project used for my Pluralsight Spring JPA and Spring Security courses among others.

http://pluralsight.com/training/Courses/TableOfContents/maven-fundamentals

http://pluralsight.com/training/Courses/TableOfContents/springmvc-intro

Notes
-----
@PersistenceContext 
Injects the Entity Manager in our code

@Service
Spring service where the business logic is located

@Repository 
Spring DAO object, where database interaction occurs

@Transactional 
Used to start a transaction- EXTREMELY USEFUL, saves us having to start transactions, roll back etc


Need an EntityManger to allow persistence to database (see GoalRepositoryImpl)
need flush() to ensure that it is correctly written to database


For Join types, we want to associate many execises to a goal.
Do this by using the following code:

@OneToMany(mappedBy="goal", cascade=CascadeType.ALL)
private List<Exercise> exercises = new ArrayList<Exercise>();

2 Different types of fetch types
--------------------------------
Lazy - Queries the database when that property is called
Eager - Queries the database when the object is originally created

e.g 
@OneToMany(mappedBy="goal", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
private List<Exercise> exercises = ArrayList<Exercise>();

JPQL
-----
*	Java Persistence Query Langauge (JPQL) is not SQL
*	Centered around Objects
Query query = em.createQuery("Select g from Goal g");
// JPQL MUST be Case Sensitive


PROJECTION
-----------
* 	Great way to present objects to the UI
*	Objects added using JPQL using JPQL syntax
*	Projection Objects can be JPA Entities
*	Need a constructor for the projection

Example:
String jpql = "Select new com.pluralsight.model.GoalReport(g.minutes, e.minutes, e.activitiy) " +
"from Goal g, Exercise e where g.id = e.goal.id";

NamedQueries
------------
*	Cleaner than adhoc JPQL
*	Not required, but focuses on the domain (i.e put in Goal object)
*	Named parameters

@NamedQueries({@NamedQuery(name=Goal.FIND_GOAL_REPORTS,
query="Select new com.pluralsight.model.GoalReport(g.minutes, e.minutes, e.activitiy) " +
"from Goal g, Exercise e where g.id = e.goal.id")})

Spring Data JPA 
---------------
Framework that allows the removal of a lot of boiler plate code

persist() in JPA is used for creation.  
merge() is used for updates
save methods are usually overriden to handle BOTH saves and updates
Spring Data JPA avoids a lot of boiler plate code.

The method below both saves and updates, three points to note:
a) The save and update is determined depending on whether there is an id
b) flush() is only needed for saving (not updating)	 
c) updating will return the goal object, whereas saving won't (because the id won't be known at the time of creation, so can't be returned instantly).

public Goal save(Goal goal) {
	if(goal.getId() == null) {
		em.persist(goal);
		em.flush();
		}
		else{
		goal = em.merge(goal);
		}
		return goal;
}

*	Spring data jpa is a wrapper for Jpa (need to know jpa before using spring data jpa)
*	Replaces our repository tier
*	Extermely powerful and eliminates boiler plate code
*	Can be extended for additional complex functionality

Needs spring data jpa jar using maven (CATCH - NEED TO EXCLUDE a transitive dependencies, this ins't uncommon)
So, need to exclude spring-aop (see the Maven pom file for how this is done), e.g
<exclusion> tag

There is a specific namespace for spring data jpa
This needs to be added in the jpacontext file, together with schema location, and jpa:repositories.
This tells spring jpa where to look for our repository classes.

xmlns:jpa="http://www.springframework.org/schema/data/jpa"

http://www.springframework.org/schema/data/jpa
http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd

<jpa:repositories base-package="com.pluralsight.repository"/>

This allows us to delete the Impl classes (the implementation classes are only required if we want to override functionality).  The interface will extend JpaRepository, and all the functionality is bound up in the methods
provided by this extended interface.  Our main interface therefore becomes all the functionality we need!!

It just needs to know the Class it services and the ID type

@Repository("exerciseRepository")
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{

}














