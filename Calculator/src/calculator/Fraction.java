package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Class Fraction.
 */
public class Fraction 
{
	
	/** The numerator. */
	private int m_numerator;
	
	/** The denominator. */
	private int m_denominator;
	
	/**
	 * Instantiates a new fraction.
	 */
	public Fraction()
	{
		m_numerator = 0;
		m_denominator = 1;
	}
	
	/**
	 * Instantiates a new fraction.
	 *
	 * @param a_wholeNumber the whole number to set
	 */
	public Fraction(int a_wholeNumber)
	{
		m_numerator = a_wholeNumber;
		m_denominator = 1;
	}
	
	/**
	 * Instantiates a new fraction.
	 *
	 * @param a_numerator the numerator
	 * @param a_denominator the denominator
	 */
	public Fraction(int a_numerator, int a_denominator)
	{
		m_numerator = a_numerator;
		m_denominator = (a_denominator != 0) ? a_denominator : 1;
		
		//Switch the negative (if it exists) from the denominator to the numerator
		if (a_denominator < 0)
		{
			m_denominator = -m_denominator;
			m_numerator = -m_numerator;
		}
		
		reduce();
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param a_other the other fraction
	 */
	public Fraction(Fraction a_other)
	{
		m_numerator = a_other.getNumerator();
		m_denominator = a_other.getDenominator();
	}
	
	/**
	 * Gets the numerator.
	 *
	 * @return the numerator
	 */
	public int getNumerator()
	{
		return m_numerator;
	}
	
	/**
	 * Gets the denominator.
	 *
	 * @return the denominator
	 */
	public int getDenominator()
	{
		return m_denominator;
	}
	
	/**
	 * Sets the numerator.
	 *
	 * @param a_numerator the new numerator
	 */
	public void setNumerator(int a_numerator)
	{
		m_numerator = a_numerator;
		reduce();
	}
	
	/**
	 * Sets the denominator.
	 *
	 * @param a_denominator the new denominator
	 */
	public void setDenominator(int a_denominator)
	{
		if (a_denominator != 0) m_denominator = a_denominator;
		
		//Shift the negative to the numerator:
		if (a_denominator < 0) 
		{
			m_numerator = -m_numerator;
			m_denominator = -m_denominator;
		}
		reduce();
	}
	
	/**
	 * Reduce the fraction to lowest terms.
	 */
	public void reduce()
	{
		int abs_num = Math.abs(m_numerator);
		int largest;
		
		
		if (abs_num > m_denominator) largest = abs_num;
		else largest = m_denominator;
		
		int gcd = 1;
		
		for (int i = largest; i > 1; i--)
		{
			if (abs_num % i == 0 && m_denominator % i == 0)
			{
				gcd = i;
				break;
			}
		}
		
		m_numerator /= gcd;
		m_denominator /= gcd;
	}
	
	/**
	 * Adds the fraction to another fraction.
	 *
	 * @param a_other the other fraction
	 * @return the sum
	 */
	public Fraction add(Fraction a_other)
	{
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		int lhsNum = this.m_numerator * a_other.m_denominator;
		int rhsNum = a_other.m_numerator * this.m_denominator;
		
		return new Fraction(lhsNum + rhsNum, newDenominator);
	}
	
	/**
	 * Adds the fraction to a whole number.
	 *
	 * @param a_wholeNumber the whole number
	 * @return the sum
	 */
	public Fraction add(int a_wholeNumber)
	{
		return add(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Subtracts this fraction from another.
	 *
	 * @param a_other the other fraction
	 * @return the difference
	 */
	public Fraction subtract(Fraction a_other)
	{
		return add(a_other.multiply(-1));
	}
	
	/**
	 * Subtracts this fraction from a whole number.
	 *
	 * @param a_wholeNumber the whole number
	 * @return the difference
	 */
	public Fraction subtract(int a_wholeNumber)
	{
		return add(new Fraction(-a_wholeNumber));
	}
	
	/**
	 * Multiply this fraction with another.
	 *
	 * @param a_other the other fraction
	 * @return the product
	 */
	public Fraction multiply(Fraction a_other)
	{
		int newNumerator = this.m_numerator * a_other.m_numerator;
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
	/**
	 * Multiply this fraction with a whole number.
	 *
	 * @param a_wholeNumber the whole number
	 * @return the product
	 */
	public Fraction multiply(int a_wholeNumber)
	{
		return multiply(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Divide this fraction from another.
	 *
	 * @param a_other the other fraction
	 * @return the quotient
	 */
	public Fraction divide(Fraction a_other)
	{
		return multiply(a_other.reciprocal());
	}
	
	/**
	 * Divide this fraction from a whole number.
	 *
	 * @param a_wholeNumber the whole number
	 * @return the quotient
	 */
	public Fraction divide(int a_wholeNumber)
	{
		return multiply(new Fraction(1, a_wholeNumber));
	}
	
	/**
	 * Compare if two fractions are equal.
	 *
	 * @param a_other the other fraction
	 * @return true if equal, false if not
	 */
	public boolean equals(Fraction a_other)
	{
		if (m_numerator == a_other.m_numerator && m_denominator == a_other.m_denominator) return true;
		else return false;
	}
	
	/**
	 * Compare if a fraction equals a whole number.
	 *
	 * @param a_wholeNumber the whole number
	 * @return true if equal, false if not.
	 */
	public boolean equals(int a_wholeNumber)
	{
		return equals(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Get the reciprocal of this fraction.
	 *
	 * @return the reciprocal of this fraction
	 */
	public Fraction reciprocal()
	{
		int newDenominator = m_numerator;
		int newNumerator = m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
	/**
	 * Parses a given string and returns a Fraction if it can be parsed from the text.
	 *
	 * @param a_input the string
	 * @return the Fraction in the string
	 * @throws NumberFormatException the string can't be parsed
	 */
	public static Fraction parseFraction(String a_input) throws NumberFormatException
	{
		//Check for the '/' character
		if (a_input.indexOf('/') == -1)
		{
			int value = Integer.parseInt(a_input);
			return new Fraction(value);
		}
		
		int split = a_input.indexOf('/');
		
		int numerator = Integer.parseInt(a_input.substring(0, split));
		int denominator = Integer.parseInt(a_input.substring(split + 1));
		
		return new Fraction(numerator, denominator);
		
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if (m_denominator != 1)
		{
			return m_numerator + "/" + m_denominator;
		}
		else
		{
			return new Integer(m_numerator).toString();
		}
	}
	
	/**
	 * Returns a double representation of the Fraction.
	 *
	 * @return the double representation.
	 */
	public double toDouble()
	{
		return ((double) m_numerator) / ((double) m_denominator);
	}
	
}
