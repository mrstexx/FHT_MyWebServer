package mywebserver.util;

public class Constants {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String USER_DIR = System.getProperty("user.dir");

    public static final String STATIC_FOLDER_PATH = USER_DIR + FILE_SEPARATOR + "deploy" + FILE_SEPARATOR + "tmp-static-files";

    public static final String CONFIG_PATH = USER_DIR + FILE_SEPARATOR + "resources";

}
