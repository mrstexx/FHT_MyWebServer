package mywebserver.plugins.navigation;

import java.util.*;

public class NavigationStore {

    private Map<String, List<String>> navStore = null;
    private static NavigationStore navStoreObj = null;

    private NavigationStore() {
        this.navStore = new HashMap<>();
    }

    /**
     * Get value from store with passed key
     *
     * @param storeKey Store key
     * @return List of all values with passed key
     */
    public List<String> getStoreValue(final String storeKey) {
        if (this.navStore.containsKey(storeKey)) {
            return this.navStore.get(storeKey);
        }
        return new LinkedList<>();
    }

    /**
     * Add new value to store with key. If key is already existing, it will be added to the current store list.
     *
     * @param storeKey   Store key
     * @param storeValue Store value
     */
    public void addStoreValue(final String storeKey, final String storeValue) {
        if (storeKey == null || storeValue == null) {
            return;
        }
        if (this.navStore.containsKey(storeKey)) {
            if (!this.navStore.get(storeKey).contains(storeValue)) {
                this.navStore.get(storeKey).add(storeValue);
            }
        } else {
            List<String> list = new LinkedList<>();
            list.add(storeValue);
            this.navStore.put(storeKey, list);
        }
    }

    /**
     * @return Get Navigation Store. Never returns null
     */
    public static synchronized NavigationStore getInstance() {
        if (navStoreObj == null) {
            navStoreObj = new NavigationStore();
        }
        return navStoreObj;
    }

    /**
     * Set navigation Store
     *
     * @param navStore Navigation Store
     */
    public static synchronized void setNavStore(NavigationStore navStore) {
        navStoreObj = navStore;
    }
}
