package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;

public class FileUtility {

    public static File getFile(String fileName) throws IOException {
        if (FileUtility.class.getClassLoader().getResourceAsStream(fileName) != null) {
            InputStream resourceAsStream = FileUtility.class.getClassLoader().getResourceAsStream(fileName);
            File file = new File(fileName, "");
            FileUtils.copyInputStreamToFile(resourceAsStream, file);
            return file;
        } else {
            return new File(fileName);
        }
    }

}
