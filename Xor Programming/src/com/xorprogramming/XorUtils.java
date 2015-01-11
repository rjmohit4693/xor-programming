package com.xorprogramming;

// -------------------------------------------------------------------------
/**
 * A utility class for commonly used helper methods.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class XorUtils
{
    private XorUtils()
    {
        assertConstructNoninstantiability();
    }


    // ----------------------------------------------------------
    /**
     * Call inside a constructor of a class that is designed to not be instantiated, e.g. utility class.
     *
     * @throws AssertionError
     *             whenever the method is called
     */
    public static void assertConstructNoninstantiability()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }


    // ----------------------------------------------------------
    /**
     * Insures that an Object is not null. This method is useful for concisely validating parameters.
     *
     * @param t
     *            The object to check
     * @param message
     *            The message of the {@link NullPointerException} that is throw if the object is null
     * @return The object
     * @throws NullPointerException
     *             if the object is null
     */
    public static <T> T assertNotNull(T t, String message)
    {
        if (t == null)
        {
            throw new NullPointerException(message);
        }
        return t;
    }
}
