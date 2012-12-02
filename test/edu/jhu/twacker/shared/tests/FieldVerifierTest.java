/**
 * OOSE Project - Group 4
 * {@link FieldVerifierTest}.java
 */

package edu.jhu.twacker.shared.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.jhu.twacker.shared.FieldVerifier;

/**
 * Class to test the {@link FieldVerifier} class for valid functionality
 * 
 * @author Disa Mhembere
 */
public class FieldVerifierTest
{

	@Test
	public void test()
	{
		testEmail();
		testIsValidName();
		testIsValidPassword();
		testIsValidSearch();
		testIsValidUsername();
	}

	/**
	 * Test if the {@link FieldVerifier} <code>isValidEmail</code> method returns
	 * the correct result when called
	 */
	private void testEmail()
	{
		String goodEmail1 = "disa@jhu.edu";
		String goodEmail2 = "Jimbo@yahoo.com";
		String goodEmail3 = "homy@somewhere.co.za";
		String goodEmail4 = "1234@microsoft.com";

		String badEmail1 = "disa jhu.edu";
		String badEmail2 = "@disa@jhu.edu";
		String badEmail3 = "disa_yahoo.com";
		String badEmail4 = "disa@gmail.com ";
		String badEmail5 = null;

		if (!(FieldVerifier.isValidEmail(goodEmail1)
				&& FieldVerifier.isValidEmail(goodEmail2)
				&& FieldVerifier.isValidEmail(goodEmail3)
				&& FieldVerifier.isValidEmail(goodEmail4)))
		{
			fail("Failure on good email addresses : testing FieldVerifier.isValidEmail");
		}

		if (FieldVerifier.isValidEmail(badEmail1)
				|| FieldVerifier.isValidEmail(badEmail2)
				|| FieldVerifier.isValidEmail(badEmail3)
				|| FieldVerifier.isValidEmail(badEmail4)
				|| FieldVerifier.isValidEmail(badEmail5))
		{
			fail("Failure on bad email addresses : testing FieldVerifier.isValidEmail");
		}
	}

	/**
	 * Test whether the {@link FieldVerifier} <code> isValidUsername </code>
	 * method is functional
	 */
	private void testIsValidUsername()
	{
		String goodName1 = "DisaM";
		String goodName2 = "ricky69James";
		String goodName3 = "drizzle185";
		String goodName4 = "100kurwa0";

		String badName1 = "Disa M";
		String badName2 = "@goodguy1";
		String badName3 = null;
		String badName4 = "goodie ";

		if (!(FieldVerifier.isValidUsername(goodName1)
				&& FieldVerifier.isValidUsername(goodName2)
				&& FieldVerifier.isValidUsername(goodName3) && FieldVerifier
					.isValidUsername(goodName4)))
		{
			fail("Failure on good username : testing FieldVerifier.isValidUsername");
		}

		if (FieldVerifier.isValidUsername(badName1)
				|| FieldVerifier.isValidUsername(badName2)
				|| FieldVerifier.isValidUsername(badName3)
				|| FieldVerifier.isValidUsername(badName4))
		{
			fail("Failure on bad username : testing FieldVerifier.isValidUsername");
		}

	}

	/**
	 * Test whether the {@link FieldVerifier} <code> isValidPassword </code>
	 * method is functional
	 */
	private void testIsValidPassword()
	{
		String goodPassword1 = "6523jhsdf764";
		String goodPassword2 = "^%#86fdhj386+{}";
		String goodPassword3 = "simple";
		String goodPassword4 = "iuafboiuef";
		String badPassword1 = "jhsgf jsgf";
		String badPassword2 = "1234";
		String badPassword3 = " jhssgf";
		String badPassword4 = null;

		if (!(FieldVerifier.isValidPassword(goodPassword1)
				&& FieldVerifier.isValidPassword(goodPassword2)
				&& FieldVerifier.isValidPassword(goodPassword3)
				&& FieldVerifier.isValidPassword(goodPassword4)))
		{
			fail("Failure on good password : testing FieldVerifier.isValidPassword");
		}

		if (FieldVerifier.isValidPassword(badPassword1)
				|| FieldVerifier.isValidPassword(badPassword2)
				|| FieldVerifier.isValidPassword(badPassword3)
				|| FieldVerifier.isValidPassword(badPassword4)
		)
		{
			fail("Failure on bad password : testing FieldVerifier.isValidPassword");
		}

	}

	/**
	 * Test whether the {@link FieldVerifier} <code> isValidName </code> method
	 * is functional
	 */
	private void testIsValidName()
	{
		String goodName1 = "Disa";
		String goodName2 = "Ma";

		String badName1 = "Disa M";
		String badName2 = "@goodguy";
		String badName3 = null;
		String badName4 = "dave ";
		String badName5 = "ricky";
		
		if (!(FieldVerifier.isValidName(goodName1)
				&& FieldVerifier.isValidName(goodName2)))
		{
			fail("Failure on good name : testing FieldVerifier.isValidName");
		}

		if (FieldVerifier.isValidName(badName1)
				|| FieldVerifier.isValidName(badName2)
				|| FieldVerifier.isValidName(badName3)
				|| FieldVerifier.isValidName(badName4)
				|| FieldVerifier.isValidName(badName5)
				)
		{
			fail("Failure on bad name : testing FieldVerifier.isValidName");
		}

	}

	/**
	 * TODO: AL
	 */
	private void testIsValidSearch()
	{

	}
}
