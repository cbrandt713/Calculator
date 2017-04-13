package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Class Fraction.
 */
public class Fraction 
{
	
	/** The m numerator. */
	private int m_numerator;
	
	/** The m denominator. */
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
	 * @param a_wholeNumber the a whole number
	 */
	public Fraction(int a_wholeNumber)
	{
		m_numerator = a_wholeNumber;
		m_denominator = 1;
	}
	
	/**
	 * Instantiates a new fraction.
	 *
	 * @param a_numerator the a numerator
	 * @param a_denominator the a denominator
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
	 * Instantiates a new fraction.
	 *
	 * @param a_other the a other
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
	 * Reduce.
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
	 * Adds the.
	 *
	 * @param a_other the a other
	 * @return the fraction
	 */
	public Fraction add(Fraction a_other)
	{
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		int lhsNum = this.m_numerator * a_other.m_denominator;
		int rhsNum = a_other.m_numerator * this.m_denominator;
		
		return new Fraction(lhsNum + rhsNum, newDenominator);
	}
	
	/**
	 * Adds the.
	 *
	 * @param a_wholeNumber the a whole number
	 * @return the fraction
	 */
	public Fraction add(int a_wholeNumber)
	{
		return add(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Subtract.
	 *
	 * @param a_other the a other
	 * @return the fraction
	 */
	public Fraction subtract(Fraction a_other)
	{
		return add(a_other.multiply(-1));
	}
	
	/**
	 * Subtract.
	 *
	 * @param a_wholeNumber the a whole number
	 * @return the fraction
	 */
	public Fraction subtract(int a_wholeNumber)
	{
		return add(new Fraction(-a_wholeNumber));
	}
	
	/**
	 * Multiply.
	 *
	 * @param a_other the a other
	 * @return the fraction
	 */
	public Fraction multiply(Fraction a_other)
	{
		int newNumerator = this.m_numerator * a_other.m_numerator;
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
	/**
	 * Multiply.
	 *
	 * @param a_wholeNumber the a whole number
	 * @return the fraction
	 */
	public Fraction multiply(int a_wholeNumber)
	{
		return multiply(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Divide.
	 *
	 * @param a_other the a other
	 * @return the fraction
	 */
	public Fraction divide(Fraction a_other)
	{
		return multiply(a_other.reciprocal());
	}
	
	/**
	 * Divide.
	 *
	 * @param a_wholeNumber the a whole number
	 * @return the fraction
	 */
	public Fraction divide(int a_wholeNumber)
	{
		return multiply(new Fraction(1, a_wholeNumber));
	}
	
	/**
	 * Equals.
	 *
	 * @param a_other the a other
	 * @return true, if successful
	 */
	public boolean equals(Fraction a_other)
	{
		if (m_numerator == a_other.m_numerator && m_denominator == a_other.m_denominator) return true;
		else return false;
	}
	
	/**
	 * Equals.
	 *
	 * @param a_wholeNumber the a whole number
	 * @return true, if successful
	 */
	public boolean equals(int a_wholeNumber)
	{
		return equals(new Fraction(a_wholeNumber));
	}
	
	/**
	 * Reciprocal.
	 *
	 * @return the fraction
	 */
	public Fraction reciprocal()
	{
		int newDenominator = m_numerator;
		int newNumerator = m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
	/**
	 * Parses the fraction.
	 *
	 * @param a_input the a input
	 * @return the fraction
	 * @throws NumberFormatException the number format exception
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
	
	/* (non-Javadoc)
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
	 * To double.
	 *
	 * @return the double
	 */
	public double toDouble()
	{
		return ((double) m_numerator) / ((double) m_denominator);
	}
	
}
