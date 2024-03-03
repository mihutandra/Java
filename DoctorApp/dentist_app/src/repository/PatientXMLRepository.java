package repository;

import domain.Patient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientXMLRepository extends FileRepository<Patient, Integer> {

    public PatientXMLRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try {
            File inputFile = new File(filename); // Create a File object with the specified filename
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // Create a DocumentBuilderFactory to obtain a DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); // Create a DocumentBuilder to parse the XML file
            Document doc = dBuilder.parse(inputFile); // Parse the XML file and obtain the Document object
            doc.getDocumentElement().normalize();

            NodeList patientList = doc.getElementsByTagName("patient");  // Get a NodeList of all elements with the tag name "patient"

            for (int temp = 0; temp < patientList.getLength(); temp++) // Iterate through the NodeList of patients
            {
                Node patientNode = patientList.item(temp); // Get the current patient Node

                if (patientNode.getNodeType() == Node.ELEMENT_NODE)  // Check if the Node is of type ELEMENT_NODE
                //If the node is an element, the getNodeType method will return the value ELEMENT_NODE
                {
                    Element patientElement = (Element) patientNode; // Convert the Node to an Element
                    String idString = patientElement.getElementsByTagName("id").item(0).getTextContent(); // Get the text content of the "id" element within the patientElement

                    int id = Integer.parseInt(idString); // Parse the "id" attribute to an integer
                    // Get the text content of the "name" and "disease" elements within the patientElement
                    String name = patientElement.getElementsByTagName("name").item(0).getTextContent();
                    String disease = patientElement.getElementsByTagName("disease").item(0).getTextContent();

                    //// Create a Patient object with the parsed data and put it into the data map
                    Patient patient = new Patient(id, name, disease);
                    //data.put(id, patient);
                    repo.put(id, patient);

                }


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write("<patients>\n");
            for (Patient patient : repo.values()) {
                fileWriter.write("  <patient>\n");
                fileWriter.write("    <id>" + patient.getId() + "</id>\n");
               // fileWriter.write("  <patient id=\"" + patient.getId() + "\">\n");
                fileWriter.write("    <name>" + patient.getName() + "</name>\n");
                fileWriter.write("    <disease>" + patient.getDisease() + "</disease>\n");
                fileWriter.write("  </patient>\n");
            }
            fileWriter.write("</patients>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
   DocumentBuilderFactory:
        DocumentBuilderFactory is an abstract class that provides a factory for obtaining DocumentBuilder instances.
        It follows the factory design pattern, allowing us to obtain instances of DocumentBuilder without directly creating them.
        The newInstance method is typically used to get an instance of DocumentBuilderFactory.

    DocumentBuilder:
        DocumentBuilder is an interface in the Java API for processing XML documents.
        It provides methods to parse XML content and create a Document object.
        Instances of DocumentBuilder are obtained from a DocumentBuilderFactory.

    Document:
        Document is an interface representing the entire XML document.
        It provides methods to navigate and manipulate the structure of the document, such as getting elements, attributes, and text content.
        The parse method of DocumentBuilder returns a Document object.

    Node:
        A Node is a fundamental interface in the DOM API.
        It represents a node in the XML tree structure, such as an element, attribute, or text node.
        Common types of nodes include Element, Attribute, Text, Comment, etc.
        Nodes can have child nodes, attributes, and other properties.

    NodeList:
        A NodeList is an ordered collection of nodes.
        It is often used to represent a list of child nodes of an element or the result of a query.
        Methods like getElementsByTagName return a NodeList containing nodes that match a specific tag name.
        It provides an ordered sequence of nodes that can be iterated over.

 */

}