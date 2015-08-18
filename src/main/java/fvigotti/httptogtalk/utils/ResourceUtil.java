package fvigotti.httptogtalk.utils;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

/**
 * load resources starting from current class location
 */
public class ResourceUtil {
    private Logger log = Logger.getLogger(ResourceUtil.class);


    public static String getLocalResource(String theName) {
        try {
            InputStream input;
            input = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream(theName);
            if (input == null) {
                throw new RuntimeException("Can not find " + theName);
            }
            BufferedInputStream is = new BufferedInputStream(input);
            StringBuilder buf = new StringBuilder(3000);
            int i;
            try {
                while ((i = is.read()) != -1) {
                    buf.append((char) i);
                }
            } finally {
                is.close();
            }
            String resource = buf.toString();
            // convert EOLs
            String[] lines = resource.split("\\r?\\n");
            StringBuilder buffer = new StringBuilder();
            for (int j = 0; j < lines.length; j++) {
                buffer.append(lines[j]);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getLocalResourceasStream(String theName) {
        InputStream input;
        input = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream(theName);
        if (input == null) {
            throw new RuntimeException("Can not find " + theName);
        }
        return input;
    }

}
