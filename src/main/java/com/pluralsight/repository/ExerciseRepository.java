package com.pluralsight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Exercise;

// Just extend the JpaRepository to get all the save() etc functionality from 
// spring data jpa.  The Long parameter passed in is the Long Id associated with the 
// exercise.  This omits the need for any implementation class.  This interface handles 
// all the functionality for saving, updating etc for us.  

@Repository("exerciseRepositor")
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
	
	
}
