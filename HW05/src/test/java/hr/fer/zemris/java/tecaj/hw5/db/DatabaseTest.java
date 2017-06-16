package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class DatabaseTest {
	
	@Rule 
	public ExpectedException thrown= ExpectedException.none();
	
	private StudentDatabase database;
	private QueryFilter filter;
	List<StudentRecord> studentRecords;
	StudentRecord student;

	@Before
	public void fillTable() throws IOException  {
			List<String> lines = Files.readAllLines(
					Paths.get("studenti.txt"),
					StandardCharsets.UTF_8
					);
			
			database = new StudentDatabase(lines);
	}

	@Test
	public void forJMBAGMethodTest()  {
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		assertEquals(student, database.forJMBAG("0000000016"));
	}
	
	@Test
	public void filteringWithEqualOperator() {
		filter = new QueryFilter("lastName=\"Glumac\" and firstName=\"Milan\"");
		studentRecords = database.filter(filter);
		
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		assertEquals(true, studentRecords.contains(student));
	}
	
	@Test
	public void filteringWithGreaterOrEqualOperator() {
		filter = new QueryFilter("lastName>=\"Glumac\" and firstName>=\"Milan\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(true, studentRecords.contains(student));
		assertEquals(5, student.getFinalGrade());
	}
	
	@Test
	public void filteringWithLessOrEqualOperator() {
		filter = new QueryFilter("lastName<=\"Glumac\" and firstName<=\"Milan\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(true, studentRecords.contains(student));
		assertEquals(5, student.getFinalGrade());
	}
	
	
	@Test
	public void filteringWithNotEqualOperator() {
		filter = new QueryFilter("lastName!=\"Glumac\" and firstName!=\"Milan\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(false, studentRecords.contains(student));
	}
	
	@Test
	public void filteringWithGettingRecordInO1() {
		filter = new QueryFilter("lastName=\"Glumac\" and firstName=\"Milan\" and jmbag=\"0000000016\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(true, studentRecords.contains(student));
	}
	
	@Test
	public void filteringWithGreaterAndLessOperators() {
		filter = new QueryFilter("lastName<\"Glumac\" and firstName>\"Milan\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(false, studentRecords.contains(student));
	}
	
	@Test
	public void invalidFieldGetter() {
	    thrown.expect( IllegalArgumentException.class );

	    filter = new QueryFilter("lastName<\"Glumac\" and firistName>\"Milan\"");
	}
	
	@Test
	public void invalidLiteralQuery() {
	    thrown.expect( IllegalArgumentException.class );

	    filter = new QueryFilter("lastName<\"Glumac\" and firstName>\"Milan");
	}
	@Test
	public void invalidOperatorQuery() {
	    thrown.expect( IllegalArgumentException.class );

	    filter = new QueryFilter("lastName=!\"Glumac\" and firstName>\"Milan\"");
	}
	@Test
	public void invalidLiteral() {
	    thrown.expect( IllegalArgumentException.class );

	    filter = new QueryFilter("lastName=!\"Glum\"ac\" and firstName>\"Milan\"");
	}
	
	@Test
	public void queryWithWildcard() {
		filter = new QueryFilter("lastName=\"*ić\" and firstName=\"M*\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000013", "Gagić", "Mateja", 2);
		StudentDB.printDatabase(studentRecords);
		assertEquals(true, studentRecords.contains(student));
	}
	
	@Test
	public void queryWithWildcardInMiddle() {
		filter = new QueryFilter("lastName=\"G*ić\" and firstName=\"M*\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000013", "Gagić", "Mateja", 2);
		StudentDB.printDatabase(studentRecords);
		assertEquals(true, studentRecords.contains(student));
	}

	@Test
	public void invalidNumberOFWildcards() {
	    thrown.expect( IllegalArgumentException.class );
	    
	    filter = new QueryFilter("lastName=\"G*u*ac\" and firstName>\"Milan\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidGreaterOperatorWithWildcard() {
	    thrown.expect(IllegalArgumentException.class);
	    
	    filter = new QueryFilter("lastName>\"G*u\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidGreateOrEqualOperatorWithWildcard() {
	    thrown.expect(IllegalArgumentException.class);
	    
	    filter = new QueryFilter("lastName>=\"G*u\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidLessOperatorWithWildcard() {
	    thrown.expect(IllegalArgumentException.class);
	    
	    filter = new QueryFilter("lastName<\"G*u\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidLessOrEqualOperatorWithWildcard() {
	    thrown.expect(IllegalArgumentException.class);
	    
	    filter = new QueryFilter("lastName<=\"G*u\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidNotEqualOperatorWithWildcard() {
	    thrown.expect(IllegalArgumentException.class);
	    
	    filter = new QueryFilter("lastName!=\"G*u\"");
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void invalidRecord() {
	    thrown.expect(IllegalArgumentException.class);
	    List<String> inputList = new ArrayList<String>(1);
	    inputList.add("0036479428\tGulan\tFilip");
	    StudentDatabase database = new StudentDatabase(inputList);
	    studentRecords = database.filter(filter);
	}
	
	@Test
	public void getNonexistingJMBAG() {
		filter = new QueryFilter("lastName=\"Glumac and glumica\" and firstName=\"Milan\" and jmbag=\"0000000099\"");
		studentRecords = database.filter(filter);
		student = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(false, studentRecords.contains(student));
	}
	
	@Test
	public void compareJMBAGs() {
		StudentRecord student1 = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		StudentRecord student2 = new StudentRecord("0000000017", "Glumac", "Ivan", 5);
		StudentRecord student3 = new StudentRecord("0000000016", "Glumac", "Milan", 5);
		
		assertEquals(false, student1.equals(student2));
		assertEquals(false, student1.equals(new String("0000000016")));
		assertEquals(true, student1.equals(student3));
		assertEquals(false, student1.hashCode() == student2.hashCode());
		assertEquals(true,  student1.hashCode() == student3.hashCode());
	}

}
