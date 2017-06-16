package hr.fer.zemris.java.tecaj.hw3;

/**
 * The CString class represent character strings. It can be created from array of character, 
 * String object or from the CString object. It includes methods for extracting substring,
 * replacing characters or substrings, for examining individual characters of the sequence,
 * converting CString object to array of characters or to a String object, for returning the length and
 * returning the index of specified character.
 *  
 * <p>CString class implements functionality of a String class which have been before Java 7 update 6.
 * CString object is immutable, so once created CString cannot be changed.
 * 
 * <p> The methods substring(), left() and right() are executed in O(1) complexity because they 
 * return a new CString with a reference to initial character array and only change offset and
 * length parameters.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CString {

	private final char[] data;
	private final int offset;
	private final int count;
	
	/**
	 * Creates a new CString object from a subarray of the input character array.
	 * Subarray starts at position of <code>offset</code> and takes <code>length</code>
	 * number of characters of input array. The contents of the character array are copied,
	 * so any modification of the character does not affect on created CString.
	 * 
	 * @param data Input character array.
	 * @param offset Start index of subarray.
	 * @param length Number of characters in subarray.
	 */
	public CString(char[] data, int offset, int length) {
		this(data, offset, length, true);	
	}
	
	/**
	 * Creates a new CString object from input character array.
	 * The contents of the character array are copied, so any modification
	 * of the character does not affect on created CString.
	 * @param data Input character array.
	 * @throws CStringException If input data is null.
	 */
	public CString(char[] data) {
		if (data == null) {
			throw new CStringException("Null pointer exception! Input array must be different from null!");
		}
		this.data = new char[data.length];
		this.offset = 0;
		this.count = data.length;
		System.arraycopy(data, 0, this.data, 0, data.length);
	}
	
	/**
	 * Creates a new CString object from input CString so that it represents the 
	 * same sequence of characters as the input argument.
	 * Unless an explicit copy of original is needed, use of this
	 * constructor is unnecessary since CStrings are immutable.
	 * @param original Input CString
	 * @throws CStringException If input data is null.
	 */
	public CString(CString original) {
		if (original == null) {
			throw new CStringException("Null pointer exception! Input CString must be different from null!");
		}
		if(original.data.length > original.count) {
			//If input CString has more space than it need aka offset != 0
			//Copy only reachable part of it
			this.data = original.toCharArray();
		} else {
			//If input CString has offset == 0 copy only reference to it
			this.data = original.data;
		}
		this.offset = 0;
		this.count = data.length;
	}
	
	/**
	 * Creates a new CString object from input String so that it represents the 
	 * same sequence of characters as the input argument.
	 * @param s Input String
	 * @throws CStringException If input data is null.
	 */
	public CString(String s) {
		if (s == null) {
			throw new CStringException("Null pointer exception! Input string must be different from null!");
		}
		count = s.length();
		data = new char[count];
		offset = 0;
		System.arraycopy(s.toCharArray(), 0, data, 0, count);
	}
	
	/**
	 * Private constructor used for copying only reference to character array if <code>copy</code> is false,
	 * otherwise if <code>copy</code> is true then new character array is created and input array is copied to it.
	 * New array starts at position of <code>offset</code> and takes <code>length</code> number of characters of input array.
	 * @param data Input character array.
	 * @param offset Start index of subarray.
	 * @param length Number of characters in subarray.
	 * @param copy If <code>true</code> it creates a new array and input array is copied to it,
	 * otherwise it retain only a reference to input array.
	 * @throws CStringException If input data is null or length or offset are out of boundaries.
	 */
	private CString(char[] data, int offset, int length, boolean copy) {
		if (data == null) {
			throw new CStringException("Null pointer exception! Input array must be different from null!");
		}
		if (offset < 0) {
			throw new CStringException("Invalid input argument! Offset must be greater or equal to 0.");
		}
		if (length < 0) {
			throw new CStringException("Invalid input argument! Length must be greater or equal to 0.");
		}
		if (offset > data.length-length) {
			throw new CStringException("Invalid input argument! Offset is out of range of array dimension and given length!");
		}
		
		if(copy) {
			//Execute this if user called public constructor
			this.count = length;
			this.data = new char[this.count];
			this.offset = 0;
			System.arraycopy(data, offset, this.data, 0, this.count);
		} else {
			//Execute this if methods substring, left or right are called it
			this.data = data;
			this.offset = offset;
			this.count = length;
		}
	}
	
	/**
	 * Returns the length of this CString. The length is equal 
	 * to the number of characters in the CString.
	 * @return The number of characters in this CString object.
	 */
	public int length() {
		return count;
	}
	
	/**
	 * Returns the char value at the specified index. 
	 * An index ranges from 0 to length() - 1.
	 * @param index The index of character to get.
	 * @return Character at the specified index of this CString
	 * @throws CStringException If index is out of range.
	 */
	public char charAt(int index) {
		if(index < 0 || index >= count) {
			throw new CStringException("Unable to get character at specified index! An index ranges from 0 to length() - 1.");
		} else {
			return data[index+offset];
		}
	}
	
	/**
	 * Converts this CString object to a new character array.
	 * @return A newly allocated character array.
	 */
	public char[] toCharArray() {
		char[] newArray = new char[count];
		System.arraycopy(data, offset, newArray, 0, count);
		return newArray;
	}
	
	@Override
	public String toString() {
		return new String(data, offset, count);
	}
	
	/**
	 * Returns the index within this string of the first occurrence 
	 * of the specified character. If no such character as specified
	 * occurs in this string, then -1 is returned.
	 * @param c A character to check.
	 * @return The index of the first occurrence of the character, or -1 if the character does not occur.
	 */
	public int indexOf(char c) {
		for (int i = 0; i < count; i++) {
			if(data[i+offset] == c) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if this CString starts with the specified prefix.
	 * @param s The CString prefix.
	 * @return <code>true</code> if the input CString is a prefix of this CString, <code>false</code> otherwise. 
	 */
	public boolean startsWith(CString s) {
		if (s.count > count) {
			return false;
		} else {
			for (int i = 0; i < s.count; i++) {
				if(data[i+offset] != s.charAt(i)) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Checks if this CString starts with the specified suffix.
	 * @param s The CString suffix.
	 * @return <code>true</code> if the input CString is a suffix of this CString, <code>false</code> otherwise. 
	 */
	public boolean endsWith(CString s) {
		if (s.count > count) {
			return false;
		} else {
			for(int i = 0; i < s.count; i++) {
				if (s.charAt(i) != data[count-s.count+i]) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Checks if this CString contains input CString as a substring.
	 * @param s The CString sequence to search for-
	 * @return <code>true</code> if this CSring contains input CString, <code>false</code> otherwise
	 */
	public boolean contains(CString s) {
		if (s.count > count) {
			return false;
		} else {
			
			for (int i = 0; i < count; i++) {
				if(data[i+offset] != s.charAt(0)) {
					continue;
				}
				for (int j = 0; j < s.count && i+j < count ; j++) {
					if (data[i+offset+j] != s.charAt(j)) {
						break;
					}
					if(j == s.count-1 && data[i+offset+j] == s.charAt(j)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * Returns a new instance of CString that is a substring of this CString.
	 * The substring begins at the specified startIndex and extends to the
	 * character at index endIndex - 1. 
	 * 
	 * <p>The method is executed in O(1) 
	 * complexity because the new CString object retain reference
	 * to initial CString initial array of characters.
	 * 
	 * @param startIndex The beginning index (inclusive).
	 * @param endIndex The ending index (exclusive).
	 * @return The specified substring as a new instance of CString.
	 * @throws CStringException If start index or end index are out of range.
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new CStringException("Invalid input argument! Start index must be greater or equal to 0 and less than length()!");
		}
		if (endIndex > count) {
			throw new CStringException("Invalid input argument! End index must be less than length()!");
		}
		if (startIndex > endIndex) {
			throw new CStringException("Invalid input argument! Start index must be less than end index!");
		}
		
		if (startIndex == 0 && endIndex == count) {
			return this;
		}
		return new CString(data, offset + startIndex, endIndex-startIndex, false);
	}

	/**
	 * Returns a new instance of CString that is a substring of first n
	 * characters of this CString. The substring begins at index 0 and 
	 * extends to index n - 1.
	 * 
	 * <p>The method is executed in O(1) complexity
	 * because the new CString object retain reference
	 * to initial CString initial array of characters.
	 * 
	 * @param n Number of characters.
	 * @return The specified substring as a new instance of CString.
	 * @throws CStringException If input argument is greater than length().
	 */
	public CString left(int n) {
		if (n > count) {
			throw new CStringException("Invalid input argument! Input argument must be equal or less than length()!");
		}
		return new CString(data, offset,n, false);
	}

	/**
	 * Returns a new instance of CString that is a substring of last n
	 * characters of this CString. The substring begins at index length() - n and 
	 * extends to index length() - 1.
	 * 
	 * <p>The method is executed in O(1) complexity
	 * because the new CString object retain reference
	 * to initial CString initial array of characters. 
	 * 
	 * @param n Number of characters.
	 * @return The specified substring as a new instance of CString.
	 * @throws CStringException If input argument is greater than length().
	 */
	public CString right(int n) {
		if (n > count) {
			throw new CStringException("Invalid input argument! Input argument must be equal or less than length()!");
		}
		return new CString(data, offset+count-n, n, false);
	}
	
	/**
	 * Concatenates the specified CString to the end of this CString.
	 * If the length of the argument CString is 0, then this CString object is returned. 
	 * Otherwise, a new CString object is created
	 * @param s CString to concatenate at the end of this CString.
	 * @return A concatenated CString instance.
	 * @throws CStringException If input data is null.
	 */
	public CString add(CString s) {
		if(s == null) {
			throw new CStringException("Null pointer exception! Input CString must be different from null!"); 
		}
		if(s.count == 0) {
			return this;
		}
		char[] newArray = new char[count + s.count];
		System.arraycopy(data, offset, newArray, 0, count);
		
		for(int i = 0; i < s.count; i++) {
			newArray[count+i] = s.charAt(i);
		}
		return new CString(newArray);
	}

	/**
	 * Returns a new CString resulting from replacing all occurrences of oldChar with newChar.
	 * If the character oldChar does not occur in this CString, then a reference to this CString
	 * object is returned. Otherwise, a new CString object is created.
	 * @param oldChar Character to replace.
	 * @param newChar Character to replace with.
	 * @return A new CString where oldChar character is replaced with newChar character.
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if(oldChar == newChar) {
			return this;
		}
		
		int i = 0;
		for(; i < count; ++i) {
			if (data[i+offset] == oldChar) {
				break;
			}
		}
		
		if (i < count) {
			char[] newArray = new char[count];
			System.arraycopy(data, offset, newArray, 0, count);
			
			for(i = 0; i < count; ++i) {
				if (data[i+offset] == oldChar) {
					newArray[i] = newChar;
					continue;
				} 				
				newArray[i] = data[i+offset];
			}
			return new CString(newArray);
		}
		return this;
	}

	/**
	 * Private method which counts the number of occurrences of input CString in this CString.
	 * @param s Input substring.
	 * @return The number of occurrences of input CString in this CString.
	 */
	private int numberOfSubstrings(CString s) {
		int num = 0;
		for (int i = 0; i < count; i++) {
			if(data[i+offset] != s.charAt(0)) {
				continue;
			}
			for (int j = 0; j < s.count && i+j < count; j++) {
				if (data[i+offset+j] != s.charAt(j)) {
					break;
				}
				if(j == s.count-1 && data[i+offset+j] == s.charAt(j)) {
					num++;
					i +=offset+j;
				}
			}
		}
		return num;
	}
	
	/**
	 * Private method which finds the first occurrence of input CString in this CString after newOffset index.
	 * @param s Input substring.
	 * @param newOffset The index from which the search starts.
	 * @return index of the first occurrence of input CString, or -1 if this CString does not contains specified substring.
	 */
	private int positionOfSubstring(CString s, int newOffset) {
		for (int i = newOffset; i < count; i++) {
			if(data[i+offset] != s.charAt(0)) {
				continue;
			}
			for (int j = 0; j < s.count && i+j < count ; j++) {
				if (data[i+offset+j] != s.charAt(j)) {
					break;
				}
				if(j == s.count-1 && data[i+offset+j] == s.charAt(j)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	//TODO
	/**
	 * Returns a new CString resulting from replacing all occurrences of oldStr with newStr.
	 * If this CString does not contains oldStr as a substring, then a reference to this 
	 * CString object is returned.
	 * @param oldStr CString to replace.
	 * @param newStr CString to replace with.
	 * @return A new CString where oldStr substring is replaced with newStr character.
	 * @throws CStringException If input data is null or old string length is equal to 0.
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr == null || newStr == null) {
			throw new CStringException("Null pointer exception! Input CString must be different from null!");
		}
		if(oldStr.count == 0) {
			throw new CStringException("Invalid input argument! Old substring lenght must be greater than 0!");
		}
		
		int num = numberOfSubstrings(oldStr);
		if(num == 0) {
			return this;
		}
		
		//Count a length for a new character array
		int newLength = data.length + (newStr.count-oldStr.count)*num;
		char[] array = new char[newLength];
		
		for (int i = 0, k = 0; i < count && k < newLength ; i++, k++) {
			//Checks if this index is a index of a substring occurrence
			if(positionOfSubstring(oldStr, i) == i) {
				//Replace oldStr with newStr
				for(int j = 0; j < newStr.count; ++j) {
					array[k+j] = newStr.charAt(j);
				}
				//Compute new indexes
				i += oldStr.count-1;
				k += newStr.count-1;
			} else {
				array[k] = data[i+offset];			
			}
		}
		return new CString(array);
	}
}
