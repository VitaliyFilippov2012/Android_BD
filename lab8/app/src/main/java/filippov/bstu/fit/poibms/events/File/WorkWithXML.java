package filippov.bstu.fit.poibms.events.File;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import filippov.bstu.fit.poibms.events.Events.CalendarEvents;
import filippov.bstu.fit.poibms.events.Events.Category.Category;
import filippov.bstu.fit.poibms.events.Events.Event;

public class WorkWithXML {
    private WorkWithFile wf;

    public WorkWithXML(WorkWithFile workWithFile) {
        this.wf = workWithFile;
    }

    private Document getXMLDocumentFromEvent(ArrayList<String> categoriesName, ArrayList<Event> events) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element categories = doc.createElement("Categories");
            for (String item : categoriesName) {
                Element category = doc.createElement("Category");
                category.setAttribute("Name", item);

                for (Event e : events) {
                    if (e.getCategory().equals(item)) {
                        Element eventElement = doc.createElement("Event");
                        eventElement.setAttribute("Name", e.getName());
                        eventElement.setAttribute("Date", e.getDate());
                        eventElement.setAttribute("Info", e.getInfo());
                        category.appendChild(eventElement);
                    }

                }

                categories.appendChild(category);
            }
            doc.appendChild(categories);

            return doc;

        } catch (ParserConfigurationException e) {
            Log.d("MyEvent", e.getMessage());
        }
        return null;
    }

    private void parseXMLDocumetToEvent(Document doc) {
        NodeList elements = doc.getElementsByTagName("Category");
        for (int i = 0; i < elements.getLength(); i++) {
            Node categoryNode = elements.item(i);
            String category = categoryNode.getAttributes().getNamedItem("Name").getTextContent();
            Category.addCategoryNames(category);

            NodeList eventsList = categoryNode.getChildNodes();
            for (int a = 0; a < eventsList.getLength(); a++) {
                Node taskNode = eventsList.item(a);
                Event task = new Event(taskNode.getAttributes().getNamedItem("Name").getTextContent(),
                        taskNode.getAttributes().getNamedItem("Info").getTextContent(), taskNode.getAttributes().getNamedItem("Date").getTextContent(),
                        category);
                CalendarEvents.addEvent(task);
            }
        }
    }

    private String convertDocumentToString(Document document) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.getBuffer().toString();

        } catch (TransformerException e) {
            Log.d("MyEvent", e.getMessage());
        }

        return "";
    }

    private Document getDocumentFromFile() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.parse(wf.file);
        } catch (ParserConfigurationException e) {
            Log.d("Xml.parseFromFile", e.getMessage());
        } catch (SAXException e) {
            Log.d("Xml.parseFromFile", e.getMessage());
        } catch (IOException e) {
            Log.d("Xml.parseFromFile", e.getMessage());
        }
        return null;
    }

    public void serializeToXMLDocument(ArrayList<String> categoriesName, ArrayList<Event> events) {
        wf.createFile(true);
        String xml = convertDocumentToString(getXMLDocumentFromEvent(categoriesName, events));
        wf.writeFile(xml);
    }

    public void deserialize() {
        Document d;
        if ((d = getDocumentFromFile()) != null) {
            parseXMLDocumetToEvent(d);
        }
    }

    public ArrayList<String> compileXPath(String category) {
        ArrayList<String> list = new ArrayList<>();

        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        Log.d("MyEvent","1");

        try {
            XPathExpression expression1 = xpath.compile("/Categories/Category[@Name='"+category+"']/Event/@Name");
            XPathExpression expression2 = xpath.compile("/Categories/Category[@Name='"+category+"']/Event/@Date");


            NodeList nodesName = (NodeList) expression1.evaluate(getDocumentFromFile(), XPathConstants.NODESET);
            NodeList nodesDate = (NodeList) expression2.evaluate(getDocumentFromFile(), XPathConstants.NODESET);

            for (int i = 0; i < nodesName.getLength(); i++) {
                list.add(nodesName.item(i).getNodeValue()+": "+nodesDate.item(i).getNodeValue());
            }
            return list;

        } catch (XPathExpressionException e) {
            Log.d("MyEvent","2");

            return null;
        }

    }
}
