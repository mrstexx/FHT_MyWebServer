package mywebserver.util;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.util.Map;

public class PluginUtil {

    private static final int PLUGIN_NAME_LENGTH = 6;
    private static final float DEFAULT_PLUGIN_PROBABILITY = 0.4f;

    private static final String PLUGIN_SUFFIX = "_plugin";

    /**
     * Method used to calculate default plugin probability based on plugin name and request.
     * If the url segment is same as plugin name, it will add 0.4 to return value.
     * Additionally if url contains param pluginname_plugin=true (example tolower_plugin=true)
     * it will add again 0.4 to return value.
     *
     * @param pluginClass Plugin class for which we need to calculate default probability
     * @param request     Plugin http request
     * @return Plugin probability. Value between 0 and 1.
     */
    public static float getDefaultPluginProbability(Class pluginClass, Request request) {
        float pluginProbability = 0f;
        String className = pluginClass.getSimpleName().toLowerCase();
        String pluginName = className.substring(0, className.length() - PLUGIN_NAME_LENGTH);
        Url url = request.getUrl();
        String[] urlSegments = url.getSegments();

        for (String segment : urlSegments) {
            if (segment.equals(pluginName)) {
                pluginProbability += DEFAULT_PLUGIN_PROBABILITY;
            }
        }

        // check for case pluginname_plugin=true
        if (url.getParameterCount() > 0) {
            Map<String, String> parameters = url.getParameter();
            String targetParameterName = pluginName + PLUGIN_SUFFIX;
            String targetParameterValue = parameters.get(targetParameterName);
            if (targetParameterValue != null && targetParameterValue.equals("true")) {
                pluginProbability += DEFAULT_PLUGIN_PROBABILITY;
            }
        }
        return pluginProbability > 1 ? 1f : pluginProbability;
    }

}
