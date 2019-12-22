package mywebserver.util;

public class Constants {

    /**
     * Default system file separator
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Default project directory
     */
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * Static directory folder path
     */
    public static final String STATIC_FOLDER_PATH = USER_DIR + FILE_SEPARATOR + "deploy" + FILE_SEPARATOR + "tmp-static-files";

    /**
     * Resources directory path
     */
    public static final String RESOURCES_PATH = USER_DIR + FILE_SEPARATOR + "src" + FILE_SEPARATOR + "resources";

    /**
     * Plugin services path
     */
    public static final String PLUGIN_SERVICE_PATH = RESOURCES_PATH + FILE_SEPARATOR + "services" + FILE_SEPARATOR + "plugin_service.txt";

}
