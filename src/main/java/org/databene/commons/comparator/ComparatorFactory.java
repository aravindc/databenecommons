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

package org.databene.commons.comparator;

import org.databene.commons.BeanUtil;
import org.databene.commons.ComparableComparator;
import org.databene.commons.ConfigurationError;
import org.databene.commons.IOUtil;
import org.databene.commons.NullSafeComparator;
import org.databene.commons.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.Collator;

/**
 * Creates comparators by the type of the objects to be compared.<br/>
 * <br/>
 * Created: 22.10.2005 21:29:08
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComparatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(ComparatorFactory.class);
    private static final String CONFIG_FILE_URI = "org/databene/commons/comparator/comparators.txt";
    
    private static Map<Class<?>, Comparator<?>> comparators;

    static {
        comparators = new HashMap<Class<?>, Comparator<?>>();
        addComparator(String.class, Collator.getInstance());
        readConfigFileIfExists(CONFIG_FILE_URI);
        
        // this is the fallback if no specific Comparator was found
        addComparator(Comparable.class, new ComparableComparator());
    }

    public static void addComparator(Class comparedClass, Comparator comparator) {
        comparators.put(comparedClass, comparator);
    }

    private static void readConfigFileIfExists(String uri) {
        if (!IOUtil.isURIAvailable(uri)) {
            logger.info("No custom Comparator setup defined, (" + uri + "), using defaults");
            return;
        }
        BufferedReader reader = null;
        try {
            reader = IOUtil.getReaderForURI(uri);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!StringUtil.isEmpty(line))
                    createComparator(line);
            }
        } catch (IOException e) {
            throw new ConfigurationError(e);
        } finally {
            IOUtil.close(reader);
        }
    }

    private static <T> Comparator<T> createComparator(String className) {
        Class<Comparator<T>> cls = BeanUtil.forName(className);
        Comparator<T> comparator = BeanUtil.newInstance(cls);
        Type[] genTypes = BeanUtil.getGenericInterfaceParams(cls, Comparator.class);
        addComparator((Class<T>) genTypes[0], comparator);
        return comparator;
    }
    
    public static <T> Comparator<T> getComparator(Class<T> type) {
        Comparator<T> comparator = (Comparator<T>) comparators.get(type);
        if (comparator == null && Comparable.class.isAssignableFrom(type))
            comparator = new ComparableComparator();
        if (comparator == null)
            throw new RuntimeException("No Comparator defined for " + type.getName());
        return new NullSafeComparator<T>(comparator);
    }
}
