package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.Optional;

import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualOperator;

/**
 * QueryFilter class represents class for filtering student records by given query.
 * 
 * <p>Its constructor accepts query string and parse it. From given parsed text
 * list of conditional expressions is created where between each expression is
 * used "and" logical operator.
 * 
 * <p>Query must be consisted of field getter {@link IFieldValueGetter}, 
 * comparison operator {@link IComparisonOperator} and literal to search for.
 * Each conditional expression must be separated with "and" as logical operator.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class QueryFilter implements IFilter {

	/** Given query */
	private String query;
	/** List of conditional expressions */
	private ConditionalExpression[] conditions;
	/** JMBAG container if requested by query */
	private Optional<String> jmbag = Optional.empty();

	/**
	 * Constructor of QueryFilter class. Creates filter based on given query.
	 * <p>Query must be consisted of field getter {@link IFieldValueGetter}, 
	 * comparison operator {@link IComparisonOperator} and literal to search for.
	 * Each conditional expression must be separated with "and" as logical operator.
	 * @param query String 
	 */
	public QueryFilter(String query) {
		this.query = query;
		parse();
	}

	/**
	 * Parse given query.
	 */
	private void parse() {
		//Checks if query contains valid expressions
		if(!checkQuery(query)) {
			throw new IllegalArgumentException("Unknown query statement!");
		}
		query = replaceAnd(query);
		String[] demands = query.split("&");
		conditions = new ConditionalExpression[demands.length];

		IFieldValueGetter getter = null;
		IComparisonOperator operator = null;

		for (int i = 0; i < demands.length; i++) {
			demands[i] = demands[i].replaceAll("\\s+", "");
			//Get field getter
			if (demands[i].startsWith("firstName")) {
				getter = new FirstNameFieldGetter();
				demands[i] = demands[i].replaceFirst("firstName", "");
			} else if (demands[i].startsWith("lastName")) {
				getter = new LastNameFieldGetter();
				demands[i] = demands[i].replaceFirst("lastName", "");
			} else if (demands[i].startsWith("jmbag")) {
				getter = new JmbagFieldGetter();
				demands[i] = demands[i].replaceFirst("jmbag", "");
			}
			//Get operator
			if (demands[i].charAt(0) == '=' && demands[i].charAt(1) == '"') {
				operator = new EqualOperator();
				demands[i] = demands[i].replaceFirst("=", "");
			} else if (demands[i].charAt(0) == '<'
					&& demands[i].charAt(1) == '='
					&& demands[i].charAt(2) == '"') {
				operator = new LessOrEqualOperator();
				demands[i] = demands[i].replaceFirst("<=", "");
			} else if (demands[i].charAt(0) == '<'
					&& demands[i].charAt(1) == '"') {
				operator = new LessOperator();
				demands[i] = demands[i].replaceFirst("<", "");
			} else if (demands[i].charAt(0) == '>'
					&& demands[i].charAt(1) == '='
					&& demands[i].charAt(2) == '"') {
				operator = new GreaterOrEqualOperator();
				demands[i] = demands[i].replaceFirst(">=", "");
			} else if (demands[i].charAt(0) == '>'
					&& demands[i].charAt(1) == '"') {
				operator = new GreaterOperator();
				demands[i] = demands[i].replaceFirst(">", "");
			} else if (demands[i].startsWith("!=")
					&& demands[i].charAt(2) == '"') {
				operator = new NotEqualOperator();
				demands[i] = demands[i].replaceFirst("!=", "");
			} else {
				throw new IllegalArgumentException("Unknown query operator: "
						+ demands[i] + "!");
			}
			
			//Get literal
			String literal = demands[i].replaceAll("\"", "");

			//Check if user specified student jmbag number
			if (!literal.contains("*") && getter instanceof JmbagFieldGetter
					&& operator instanceof EqualOperator) {
				jmbag = Optional.of(literal);
			}

			conditions[i] = new ConditionalExpression(getter, literal, operator);
		}
	}

	/**
	 * Checks if given query is valid.
	 * @param query Query to check.
	 * @return <code>true</code> if given query is valid, <code>false</code> otherwise.
	 */
	private boolean checkQuery(String query) {
		String temp = query.replaceAll("and", "").replaceAll("firstName", "")
				.replaceAll("lastName", "").replaceAll("jmbag", "");
		boolean asteriskOpened = false;

		for (int i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			if(c == '"' && !asteriskOpened) {
				asteriskOpened = true;
			} else if (c == '"' && asteriskOpened) {
				asteriskOpened = false;
			} else if (c != '=' && c != '>' && c != '<' && c != '!' && c != ' ' && !asteriskOpened) {
				return false;
			}
		}
		if (asteriskOpened == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * Replaces all "and" logical operators with {@literal "&"}.
	 * @param query Input query.
	 * @return Query with replaced "and".
	 */
	private String replaceAnd(String query) {
		String temp = query.replaceAll("and",	"&");
		StringBuilder sb = new StringBuilder();
		
		boolean asteriskOpened = false;

		for (int i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			if(c == '"' && !asteriskOpened) {
				asteriskOpened = true;
			} else if (c == '"' && asteriskOpened) {
				asteriskOpened = false;
			} else if (c == '&' && asteriskOpened) {
				sb.append("and");
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * Returns captured JMBAG number from query.
	 * @return JMBAG number.
	 */
	public Optional<String> getJMBAG() {
		return jmbag;

	}
	
	/**
	 * Checks if given student record satisfy conditional expressions from query.
	 * @param record Student record to check.
	 * @return <code>true</code> if given record satisfy all conditional expressions,
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expresion : conditions) {
			if (!expresion.getComparisonOperator().satisfied(
					expresion.getFieldGetter().get(record),
					expresion.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
