package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * StudentDatabase class represents a database of student records. Database is
 * populated through the constructor as the list of String object, where each
 * string represents each student record.
 * 
 * <p>In order to parse correctly, each student must have jmbag number, last name,
 * first name and final grade where each column is separated with tab (\t).
 * For example: "003612345\tHorvat\tIvan\t5".
 * 
 * <p>List can be filtered using user defined query as filter. Also, reach of a student
 * record can be made in O(1) complexity using student's jmbag.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class StudentDatabase {

	/** List of student records */
	private List<StudentRecord> studentRecords;
	/** Indexed student records using jmbag */
	private Map<String,Integer> jmbagMap = new HashMap<>();

	/**
	 * Constructor of StudentDatabase class. It accepts the list of strings
	 * each string containing record for each student.
	 * @param input List of student records.
	 */
	public StudentDatabase(List<String> input) {
		int index = 0;
		studentRecords = new ArrayList<>(input.size());
		
		for (String entry : input) {
			StudentRecord student = parseEntry(entry);
			studentRecords.add(student);
			jmbagMap.put(student.getJmbag(), index);
			index++;
		}
	}
	
	/**
	 * Parse given input on student's jmbag number, last name,
	 * first name and final grade.
	 * @param input String containing data for student.
	 * @return Student record.
	 */
	private StudentRecord parseEntry(String input) {
		String[] entry = input.split("\\t");
		if(entry.length == 4) {
			return new StudentRecord(entry[0], entry[1], entry[2], Integer.parseInt(entry[3]));
		} else {
			throw new IllegalArgumentException("Unable to create a database! Invalid input data: "+ input);
		}
	}
	
	/**
	 * Get student record in O(1) complexity using given jmbag number.
	 * @param jmbag Student's jmbag number.
	 * @return Student record with given jmbag number.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(!jmbagMap.containsKey(jmbag)) {
			return null;
		}
		int index = jmbagMap.get(jmbag);
		return studentRecords.get(index);
	}
	
	/**
	 * Returns the list of student records of those students who 
	 * meet given filter conditions.
	 * @param filter Filter used for processing student records.
	 * @return List of student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredStudents =  new LinkedList<>();
	
		if(((QueryFilter) filter).getJMBAG().isPresent()) {
			String jmbag = ((QueryFilter) filter).getJMBAG().get();
			StudentRecord record = forJMBAG(jmbag);
			if(record != null && filter.accepts(record)) {
				filteredStudents.add(record);
				System.out.println("Using index for record retrieval.");
			}
		} else {	
			for (StudentRecord record : studentRecords) {
				if(filter.accepts(record)) {
					filteredStudents.add(record);
				}
			}
		}
		return filteredStudents;
	}
}
