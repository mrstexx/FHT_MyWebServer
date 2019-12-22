package mywebserver.manager;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IServiceManager {

    /**
     * Load services from file
     *
     * @param fileName File name from where should services be loaded.
     * @throws java.io.IOException When service file location is not found.
     */
    void loadServices(final String fileName) throws IOException;
}
