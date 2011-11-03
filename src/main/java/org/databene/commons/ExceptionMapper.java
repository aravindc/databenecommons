/*
 * (c) Copyright 2007-2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.beans.IntrospectionException;

/**
 * Wraps checked exceptions as unchecked exceptions of type ConfigurationError.<br/>
 * <br/>
 * Created: 02.07.2006 07:44:23
 * @author Volker Bergmann
 */
public final class ExceptionMapper {

    /**
     * Maps method reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param constructor the constructor that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Constructor<?> constructor) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to constructor: " + constructor;
        else if (cause instanceof InvocationTargetException)
            message = "Internal exception in constructor: " + constructor;
        else if (cause instanceof InstantiationException)
            message = "Error in instantiation by constructor: " + constructor;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps method reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param method the method that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Method method) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to method: " + method;
        else if (cause instanceof InvocationTargetException)
            message = "Internal exception in method: " + method;
        else if (cause instanceof IntrospectionException)
            message = "Internal exception in method: " + method;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps class reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param type the class that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Class<?> type) {
        String message;
        if (cause instanceof IntrospectionException)
            message = "Introspection failed for class " + type;
        else if (cause instanceof InstantiationException)
            message = "Instantiation failed for class '" + type + "' Possibly it is abstract, the constructor not public, or no appropriate constructor is provided.";
        else if (cause instanceof IllegalAccessException)
            message = "Constructor not accessible for class " + type;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps attribute reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param field the field that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Field field) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to field: " + field;
        else if (cause instanceof NoSuchFieldException)
            message = "No such field found: " + field; 
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param name a characteristic identifier that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, String name) {
        String message;
        if (cause instanceof ClassNotFoundException)
            message = "Class not found: '" + name + "'";
        else if (cause instanceof NoSuchFieldException)
            message = "Field not found: '" + name + "'";
        else if (cause instanceof NoSuchMethodException)
            message = "Method not found: '" + name + "'";
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }
}
