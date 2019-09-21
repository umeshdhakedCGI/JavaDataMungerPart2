package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for
 * each aggregate function
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class AggregateFunction {

	// Write logic for constructor

	String field;
	String function;

	public AggregateFunction(String field, String function) {
		this.field=field;
		this.function=function;
	}


	public String toString() {
		return field+function;
	}
}