package com.xorprogramming.string;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * ObjectParser is a recursive decent parser capable of converting a String with Java-like constructor and array syntax
 * into objects. Syntax examples:
 * <ol>
 * <li><code> my.package.ClassName("hi", 1.4f, my.other.package.AnotherClassName())</code></li>
 * <li><code> java.awt.Rectangle(java.awt.Point(), java.awt.Dimension(10, 92))</code></li>
 * <li><code>java.lang.Boolean{true, false, false} //This is an array</code></li>
 * <li><code> my.package.ClassName("This constructor",</code><br/>
 * <code> "uses two lines!")</code></li>
 * </ol>
 * Notice that the new keyword is not used and arrays do not require square brackets. Also, comments can be appended to
 * the end of the string with a double slash and will be ignored by the parser.
 *
 * @author Steven Roberts
 * @version 1.2.0
 */
public class ObjectParser
{
    private final String text;


    // ----------------------------------------------------------
    /**
     * Create a new ObjectParser object.
     *
     * @param text
     */
    public ObjectParser(String text)
    {
        if (text == null)
        {
            throw new IllegalArgumentException("The text must be non-null");
        }
        this.text = text;
    }


    // ----------------------------------------------------------
    /**
     * Parses the given text into the corresponding object
     *
     * @return The object parsed from the text
     * @throws ParseException
     */
    public Object parse()
        throws ParseException
    {
        return parseObject(new StringChopper(text, "{}(),\n"));
    }


    private Object parseObject(StringChopper sc)
        throws ParseException
    {
        if (!sc.hasNext())
        {
            throw new ParseException("No object to parse");
        }

        String initial = sc.next().trim();
        if (initial.length() == 0) // Skip a newline or any other empty token
        {
            return parseObject(sc);
        }
        else if (initial.startsWith("//"))
        {
            // Remove all the tokens inside the commented area
            while (sc.hasNext() && !sc.next().equals("\n"))
                ;
            return parseObject(sc);
        }
        else if (initial.equals("null"))
        {
            return null;
        }
        else if (initial.startsWith("\"") && initial.endsWith("\""))
        {
            // Remove the quotes at the beginning and end
            return initial.substring(1, initial.length() - 1);
        }
        else if (initial.equals("true"))
        {
            return true;
        }
        else if (initial.equals("false"))
        {
            return false;
        }

        try
        {
            return Integer.parseInt(initial);
        }
        catch (Exception ex)
        {
            // Ignore and continue
        }
        try
        {
            if (initial.endsWith("f"))
            {
                return Float.parseFloat(initial);
            }
            else
            {
                return Double.parseDouble(initial);
            }
        }
        catch (Exception ex)
        {
            // Ignore and continue
        }

        if (!sc.hasNext())
        {
            throw new ParseException("No object to parse");
        }

        ArrayList<Object> parameters = new ArrayList<Object>();

        String next = sc.next().trim();

        // If it is not an object, it is an array
        boolean isObject = true;
        if (next.equals("("))
        {
            isObject = true;
        }
        else if (next.equals("{"))
        {
            isObject = false;
        }
        else
        {
            throw new ParseException("Invalid object: " + next);
        }

        boolean done = false;

        do
        {
            if (!sc.hasNext())
            {
                throw new ParseException("Missing " + (isObject ? ")" : "}"));
            }
            next = sc.peek().trim();
            if (next.equals(")"))
            {
                if (!isObject)
                {
                    throw new ParseException("} expected");
                }
                sc.next();
                done = true;
            }
            else if (next.equals("}"))
            {
                if (isObject)
                {
                    throw new ParseException(") expected");
                }
                sc.next();
                done = true;
            }
            else if (next.equals(","))
            {
                sc.next();
            }
            else
            {
                Object o = parseObject(sc);
                parameters.add(o);
            }
        }
        while (!done);
        try
        {
            if (isObject)
            {
                // Parse a class
                Class<?> classType = Class.forName(initial);
                for (Constructor<?> constructor : classType.getConstructors())
                {
                    Object[] newActualParameters = matchConstructor(constructor, parameters);
                    if (newActualParameters != null)
                    {
                        return constructor.newInstance(newActualParameters);
                    }
                }
                throw new ParseException("Parameters do not match any constructors for " + classType.getSimpleName());
            }
            else
            {
                // Parse an array

                Class<?> c = Class.forName(initial);
                Object o = Array.newInstance(c, parameters.size());
                for (int i = 0; i < parameters.size(); i++)
                {
                    Array.set(o, i, parameters.get(i));
                }

                return o;

            }
        }
        catch (Exception e)
        {
            throw new ParseException(e.getMessage());
        }
    }


    private Object[] matchConstructor(Constructor<?> constructor, ArrayList<Object> actualParameters)
    {
        Class<?>[] formalParameters = constructor.getParameterTypes();
        int size = formalParameters.length;
        Object[] returnParameters = new Object[size];
        if (size != actualParameters.size())
        {
            return null;
        }
        for (int i = 0; i < formalParameters.length; i++)
        {
            Class<?> fp = formalParameters[i];
            Object ap = actualParameters.get(i);
            if (fp == convertToWrapper(ap.getClass()))
            {
                returnParameters[i] = ap;
            }
            else
            {
                try
                {
                    returnParameters[i] = fp.cast(ap);
                }
                catch (Exception ex)
                {
                    return null;
                }
            }
        }
        return returnParameters;
    }


    private Class<?> convertToWrapper(Class<?> c)
    {
        if (c == Boolean.class)
        {
            return boolean.class;
        }
        else if (c == Byte.class)
        {
            return byte.class;
        }
        else if (c == Short.class)
        {
            return short.class;
        }
        else if (c == Character.class)
        {
            return char.class;
        }
        else if (c == Integer.class)
        {
            return int.class;
        }
        else if (c == Long.class)
        {
            return long.class;
        }
        else if (c == Float.class)
        {
            return float.class;
        }
        else if (c == Double.class)
        {
            return double.class;
        }
        else
        {
            return c;
        }
    }
}
