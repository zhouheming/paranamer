package com.thoughtworks.paranamer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Implementation of Paranamer which delegate to another Paranamer implementation, adding checked exception
 * handling.
 * 
 * @author Paul Hammant
 * @author Mauro Talevi
 */
public class CheckedParanamer {
    private Paranamer delegate;

    public CheckedParanamer() {
        this(new DefaultParanamer());
    }

    public CheckedParanamer(Paranamer delegate) {
        this.delegate = delegate;
    }

    public Method checkedMethodLookup(ClassLoader classLoader, String className, String methodName, String paramNames) throws ParanamerException {
        Method method = delegate.lookupMethod(classLoader, className, methodName, paramNames);
        if (method == null) {
            throw new ParanamerException("Paranamer could not find method signature");
        }
        return method;
    }

    public Constructor checkedConstructorLookup(ClassLoader classLoader, String className, String paramNames) throws ParanamerException {
        Constructor constructor = delegate.lookupConstructor(classLoader, className, paramNames);
        if (constructor == null) {
            throw new ParanamerException("Paranamer could not find constructor signature");
        }
        return constructor;
    }

    public String toString() {
        return new StringBuffer("[CheckedParanamer delegate=")
                .append(delegate)
                .append("]")
                .toString();
    }
}
