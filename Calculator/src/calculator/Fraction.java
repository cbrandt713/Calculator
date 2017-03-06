package calculator;

public class Fraction 
{
	private int m_numerator;
	private int m_denominator;
	
	public Fraction()
	{
		m_numerator = 0;
		m_denominator = 1;
	}
	
	public Fraction(int a_wholeNumber)
	{
		m_numerator = a_wholeNumber;
		m_denominator = 1;
	}
	
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
	
	public Fraction(Fraction a_other)
	{
		m_numerator = a_other.getNumerator();
		m_denominator = a_other.getDenominator();
	}
	
	public int getNumerator()
	{
		return m_numerator;
	}
	
	public int getDenominator()
	{
		return m_denominator;
	}
	
	public void setNumerator(int a_numerator)
	{
		m_numerator = a_numerator;
		reduce();
	}
	
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
	
	public Fraction add(Fraction a_other)
	{
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		int lhsNum = this.m_numerator * a_other.m_denominator;
		int rhsNum = a_other.m_numerator * this.m_denominator;
		
		return new Fraction(lhsNum + rhsNum, newDenominator);
	}
	
	public Fraction add(int a_wholeNumber)
	{
		return add(new Fraction(a_wholeNumber));
	}
	
	public Fraction subtract(Fraction a_other)
	{
		return add(a_other.multiply(-1));
	}
	
	public Fraction subtract(int a_wholeNumber)
	{
		return add(new Fraction(-a_wholeNumber));
	}
	
	public Fraction multiply(Fraction a_other)
	{
		int newNumerator = this.m_numerator * a_other.m_numerator;
		int newDenominator = this.m_denominator * a_other.m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction multiply(int a_wholeNumber)
	{
		return multiply(new Fraction(a_wholeNumber));
	}
	
	public Fraction divide(Fraction a_other)
	{
		return multiply(a_other.reciprocal());
	}
	
	public Fraction divide(int a_wholeNumber)
	{
		return multiply(new Fraction(1, a_wholeNumber));
	}
	
	public boolean equals(Fraction a_other)
	{
		if (m_numerator == a_other.m_numerator && m_denominator == a_other.m_denominator) return true;
		else return false;
	}
	
	public boolean equals(int a_wholeNumber)
	{
		return equals(new Fraction(a_wholeNumber));
	}
	
	public Fraction reciprocal()
	{
		int newDenominator = m_numerator;
		int newNumerator = m_denominator;
		
		return new Fraction(newNumerator, newDenominator);
	}
	
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
	
	public double toDouble()
	{
		return ((double) m_numerator) / ((double) m_denominator);
	}
	
}
