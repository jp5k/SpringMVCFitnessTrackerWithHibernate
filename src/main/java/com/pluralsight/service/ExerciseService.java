package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Activity;
import com.pluralsight.model.Exercise;

public interface ExerciseService {

	List<Activity> findAllActivities();

	// Default for the return type will be null, but best to have Exercise returned so that 
	// can use the object after you've dealt with it.
	// Then need to implement the method in the implementation class.
	Exercise save(Exercise exercise);

}