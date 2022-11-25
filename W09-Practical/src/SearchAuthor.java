import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;

/**
 * SearchAuthor.java.
 */
public class SearchAuthor {

    /**
     * Reads a url, constructs a xml document, processes the xml document, repeat for url nodes and prints out author, no. of publications and no. of co-authors.
     * @param args      this takes a string of arguments
     * @param search    this takes an integer value for the search argument
     * @param query     this takes an integer value for the query argument
     * @param cache     this takes a Path
     */
    public void search(String[] args, int search, int query, Path cache) {
        URL url2;
        File apiCall2;
        Document doc2;
        try {
            /**
            args[query] = args[query].replaceAll("\\s", "%20");
            url = new URL("http://dblp.org/search/" + args[search] + "/api?q=" + args[query] + "&h=40&c=0");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openConnection().getInputStream());
            Document doc2;

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
                url2 = new URL(element.getElementsByTagName("url").item(0).getTextContent() + ".xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                doc2 = dbf.newDocumentBuilder().parse(url2.openConnection().getInputStream());
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t2 = tf.newTransformer();
                apiCall2 = new File(cache + "/" + URLEncoder.encode(url2.toString(), "UTF-8"));
                DOMSource source2 = new DOMSource(doc2);
                StreamResult result2 = new StreamResult(apiCall2);
                t2.transform(source2, result2);
                NodeList person = doc2.getElementsByTagName("dblpperson");
                for (int j = 0; j < person.getLength(); j++) {
                    Node node2 = person.item(j);
                    Element element2 = (Element) node2;
                    System.out.println(element.getElementsByTagName("author").item(0).getTextContent() + " - " + element2.getElementsByTagName("title").getLength() + " publications with " + element2.getElementsByTagName("co").getLength() + " co-authors.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
