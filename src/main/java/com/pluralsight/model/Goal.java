package com.pluralsight.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

// @Entity to define the class to the database
// Can have several @NamedQuery within @NamedQueries 
@Entity
@Table(name = "goals")
// The below named queries were eventually moved out to the GoalRespoitory interface (Best practice)
//@NamedQueries({
//		@NamedQuery(name = Goal.FIND_GOAL_REPORTS, query = "Select new com.pluralsight.model.GoalReport(g.minutes, e.minutes, e.activity) "
//				+ "from Goal g, Exercise e where g.id = e.goal.id"),
//		@NamedQuery(name = Goal.FIND_ALL_GOALS, query = "Select g from Goal g") 
//		})
public class Goal {

	// Named query (just take final String, and convert to camel case for the
	// variable on right hand side
	public static final String FIND_ALL_GOALS = "findAllGoals";
	public static final String FIND_GOAL_REPORTS = "findGoalReports";

	// ID to mark field as an ID field
	// Generated value to auto increment a field
	@Id
	@GeneratedValue
	@Column(name = "GOAL_ID")
	private Long id;

	// @Range to define validation
	@Range(min = 1, max = 120)
	@Column(name = "MINUTES")
	private int minutes;

	// This maps one goal to many exercises (OneToMany)
	// Goals have a list of exercises associated with a goal/
	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Exercise> exercises = new ArrayList<Exercise>();

	public List<Exercise> getExercises() {
		return exercises;
	}

	public Long getId() {
		return id;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

}
