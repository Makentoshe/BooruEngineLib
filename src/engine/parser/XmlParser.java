package engine.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import engine.BooruEngineException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser posts from xml from url or stream,
 * which have basic structure - post with data in attributes.
 *
 * Instance of this class can be reused by option in constructor.
 * As default - instance will be reused.
 * If instance not reused - results will be collect in getResult() method.
 * You will can get all results by one call.
 */
public class XmlParser extends DefaultHandler {

    private final List<HashMap<String, Object>> result = new ArrayList<>();
    private boolean reusable = true;

    public XmlParser(boolean reusable){
        this.reusable = reusable;
    }

    public XmlParser(){
        reusable = true;
    }

    /**
     * Parses an XML file from url into memory
     */
    public void startParse(String url) throws BooruEngineException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            factory.newSAXParser().parse(url, this);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new BooruEngineException(e.getMessage());
        }
    }

    /**
     * Parses an XML file from stream into memory
     */
    public void startParse(InputStream stream) throws BooruEngineException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            factory.newSAXParser().parse(stream, this);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new BooruEngineException(e);
        }
    }

    /**
     * Event: Parser starts reading an element
     */
    @Override
    public void startElement(String s1, String s2, String elementName, Attributes attributes) throws SAXException {
        if (!"post".equals(elementName)) return;
        //all data about one post
        HashMap<String, Object> post = new HashMap<>();
        //for all attributes in this post
        for(int i = 0; i < attributes.getLength(); i++) {
            //we put it in hash map
            post.put(attributes.getQName(i), attributes.getValue(i));
        }
        //add post in result
        result.add(post);
    }


    /**
     * @return list of posts
     * If reusable flag enable we can get result only once.
     * After, the data will be clear.
     */
    public List getResult() {
        if (reusable) {
            List list = new ArrayList(result);
            result.clear();
            return list;
        }
        return result;
    }

    public boolean isReusable(){
        return reusable;
    }
}
