package mywebserver.plugins.navigation;

import java.util.*;

public class NavigationStore {

    private Map<String, List<String>> navStore = null;
    private static NavigationStore navStoreObj = null;

    private NavigationStore() {
        this.navStore = new HashMap<>();
    }

    public List<String> getStoreValue(final String storeKey) {
        if (this.navStore.containsKey(storeKey)) {
            return this.navStore.get(storeKey);
        }
        return new LinkedList<>();
    }

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

    public static NavigationStore getInstance() {
        if (navStoreObj == null) {
            navStoreObj = new NavigationStore();
        }
        return navStoreObj;
    }

    public static void setNavStore(NavigationStore navStore) {
        navStoreObj = navStore;
    }
}
