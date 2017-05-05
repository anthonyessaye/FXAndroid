package com.ndusenior.fix.XMLRelated;

import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by antho on 4/24/2017.
 */

public class OutputQueries {


    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    String OutputXML= rootFolder.getAbsolutePath() + "/OutputNames.xml";

    // XML node keys
    private String KEY_OUTPUT= "Output"; // parent node
    private String KEY_NAME = "name";
    private String KEY_ID= "id";
    private String KEY_STATUS = "status";

    private String Email;
    private String Password;

    public boolean isOutputNameAvailable = false;
    public boolean OutputStatus = false;
    public String aNameIChose = null;
    public String itsStatus = null;
    public int itsPosition = -1;

    public OutputQueries(String EmailToLogin, String UserPassword){

        Email = EmailToLogin;
        Password = UserPassword;

    }


    public void LoadOutputXml(ToggleButton[] toggleButtons, EditText[] editTextArray,TextView statusText){
        boolean isOn = true;

        XMLparsing parser = new XMLparsing();
        Document doc = parser.getDomElement(OutputXML); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_OUTPUT);

        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String name = parser.getValue(e,KEY_NAME); // name child value
            String id = parser.getValue(e, KEY_ID); // cost child value
            String status = parser.getValue(e, KEY_STATUS); // description child value

            toggleButtons[i].setTextOff("Off");
            toggleButtons[i].setTextOn("On");




            if (status.contains("On")){
                toggleButtons[i].setChecked(true);

            }
            else
                toggleButtons[i].setChecked(false);

            editTextArray[i].setText(name);
            statusText.setText("Files Up-To-Date\nStatus: Idle");
        }


    }


    public void SetOutputs(String fileToWrite, String ElementChosen,String RootElement,
                           int ElementPosition, String UpdateElementValue) {
        File xmlFile = new File(fileToWrite);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            //update Element value
            updateElementValue(doc,RootElement,ElementChosen,ElementPosition,UpdateElementValue);


            //write the updated document to file or console
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileToWrite));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");

        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    }


    private static void updateElementValue(Document doc, String RootElement, String ElementChosen,
                                           int ElementPosition, String UpdatedValue) {
        NodeList node = doc.getElementsByTagName(RootElement);
        Element nodes = null;
        //loop for each output

        for(int i=0; i < node.getLength();i++){
            nodes = (Element) node.item(ElementPosition);
            Node name = nodes.getElementsByTagName(ElementChosen).item(0).getFirstChild();
            name.setNodeValue(UpdatedValue);
        }
    }

    public void resetBooleanValues(){
        isOutputNameAvailable =false;
        OutputStatus = false;
        aNameIChose = null;
        itsPosition = -1;
    }


    public void LoadOutputXmlWithNoGui(String GiveMeAnOutput, String GiveMetheStatusYouWant){


        XMLparsing parser = new XMLparsing();
        Document doc = parser.getDomElement(OutputXML); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_OUTPUT);

        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String name = parser.getValue(e,KEY_NAME); // name child value
            String id = parser.getValue(e, KEY_ID); // cost child value
            String status = parser.getValue(e, KEY_STATUS); // description child value

            if(GiveMeAnOutput.contains(name)) {
                isOutputNameAvailable = true;
                if (status.contains(GiveMetheStatusYouWant))
                    OutputStatus = true;
                else {
                    aNameIChose = name;
                    itsPosition = i;


                }
                break;
            }


        }


    }



}
