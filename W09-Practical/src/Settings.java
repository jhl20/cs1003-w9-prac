import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;

/**
 * Settings.java.
 */
public class Settings {
    private String[] args;
    private Document doc;
    private URL url;

    /**
     * Builds a document and cache file.
     * @param args                              this takes a string of arguments
     * @param search                            this takes an integer value for the search argument
     * @param query                             this takes an integer value for the query argument
     * @param cache                             this takes a Path
     * @throws IOException                      throws possible IOException
     * @throws TransformerException             throws possible TransformerException
     * @throws ParserConfigurationException     throws possible ParserConfigurationException
     * @throws SAXException                     throws possible SAXException
     */
    public void buildDoc(String[] args, int search, int query, Path cache) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        args[query] = args[query].replaceAll("\\s", "%20");
        url = new URL("http://dblp.org/search/" + args[search] + "/api?q=" + args[query] + "&h=40&c=0");
        String encodeURL = URLEncoder.encode(url.toString(), "UTF-8");
        File apiCall = new File(cache + "/" + encodeURL + ".xml");
        if (apiCall.exists()) {
            buildDoc(apiCall);
            return;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.doc = db.parse(url.openConnection().getInputStream());
        makeCache(apiCall);
    }

    /**
     * Overloading the buildDoc method.
     * @param apiCall                           takes a file
     * @throws ParserConfigurationException     throws possible ParserConfigurationException
     * @throws IOException                      throws possible IOException
     * @throws SAXException                     throws possible SAXException
     */
    public void buildDoc(File apiCall) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.doc = db.parse(apiCall);
    }

    /**
     * Creates a cache file.
     * @param apiCall                   takes a file
     * @throws IOException              throws possible IOException
     * @throws TransformerException     throws possible TransformerException
     */
    public void makeCache(File apiCall) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);
        FileWriter file = new FileWriter(apiCall);
        StreamResult result = new StreamResult(file);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.transform(source, result);
    }

    /**
     * Returns the doc.
     * @return doc      returns the document
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * Returns the url.
     * @return url      returns the url
     */
    public URL getUrl() {
        return url;
    }
}
