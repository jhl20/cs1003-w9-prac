import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * SearchPublication.java.
 */
public class SearchPublication {

    /**
     * Reads a url, constructs a xml document, processes the xml document and prints out titles and no. of authors.
     * @param args      this takes a string of arguments
     * @param search    this takes an integer value for the search argument
     * @param query     this takes an integer value for the query argument
     * @param cache     this takes a Path
     */
    public void search(String[] args, int search, int query, Path cache) {
        try {
            /**
            args[query] = args[query].replaceAll("\\s", "%20");
            url = new URL("http://dblp.org/search/" + args[search] + "/api?q=" + args[query] + "&h=40&c=0");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openConnection().getInputStream());

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            String encodeURL = URLEncoder.encode(url.toString(), "UTF-8");
            apiCall = new File(cache + "/" + encodeURL + ".xml");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(apiCall);

            t.transform(source, result);
             */
            Settings settings = new Settings();
            settings.buildDoc(args, search, query, cache);
            NodeList info = settings.getDoc().getElementsByTagName("info");
            for (int i = 0; i < info.getLength(); i++) {
                Node node = info.item(i);
                Element element = (Element) node;
                System.out.println(element.getElementsByTagName("title").item(0).getTextContent() + " (number of authors: " + element.getElementsByTagName("author").getLength() + ")");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
