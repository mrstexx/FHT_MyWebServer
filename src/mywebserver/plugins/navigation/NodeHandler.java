package mywebserver.plugins.navigation;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NodeHandler extends DefaultHandler {

    private boolean bStreet = false;
    private boolean bCity = false;
    private String streetValue = null;
    private String cityValue = null;
    private NodeDetail nodeDetail = null;
    private NavigationStore navStore = null;
    private StringBuilder data = null;

    public NodeHandler() {
        navStore = NavigationStore.getInstance();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("node")) {
            nodeDetail = new NodeDetail();
        } else if (qName.equalsIgnoreCase("tag")) {
            if (attributes.getValue("k").equalsIgnoreCase("addr:street")) {
                bStreet = true;
                streetValue = attributes.getValue("v");
            } else if (attributes.getValue("k").equalsIgnoreCase("addr:city")) {
                bCity = true;
                cityValue = attributes.getValue("v");
            }
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bStreet) {
            nodeDetail.setStreet(streetValue);
            bStreet = false;
        } else if (bCity) {
            nodeDetail.setCity(cityValue);
            bCity = false;
        }

        if (qName.equalsIgnoreCase("node")) {
            navStore.addStoreValue(nodeDetail.getStreet(), nodeDetail.getCity());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    public NavigationStore getNavStore() {
        return this.navStore;
    }

}
