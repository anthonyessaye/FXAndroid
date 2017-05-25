package com.ndusenior.fix.XMLRelated;

import android.os.Environment;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

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
 * Created by antho on 5/5/2017.
 */

public class SettingsQueries {


    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    String SettingsXML= rootFolder.getAbsolutePath() + "/SettingsData.xml";

    // XML node keys
    private String KEY_SETTINGS= "settings";
    private String KEY_WEATHER= "weather"; // parent node
    private String KEY_CITY = "city";
    private String KEY_UNIT = "unit";
    private String KEY_USER= "user";
    private String KEY_FIRSTNAME = "firstname";
    private String KEY_LASTNAME = "lastname";
    private String KEY_USERNAME= "username";
    private String KEY_ASSISTANT = "assistant";
    private String KEY_ASSISTANTNAME= "name";
    private String KEY_ASSISTANTSTATUS = "alwayson";
    private String KEY_ASSISTANTGENDER= "gender";

    private String Email;
    private String Password;


    public SettingsQueries(String EmailToLogin, String UserPassword){

        Email = EmailToLogin;
        Password = UserPassword;

    }

    public void LoadOutputXml( EditText[] editTextArray , Switch theButton, TextView theTempText){
        boolean isOn = true;

        XMLparsing parser = new XMLparsing();
        Document doc = parser.getDomElement(SettingsXML); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SETTINGS);
        NodeList NodeList2 = doc.getElementsByTagName(KEY_USERNAME);

        // looping through all item nodes <item>

            Element e = (Element) nl.item(0);
        Element ElementsForUsername = (Element) NodeList2.item(0);

            String city = parser.getValue(e,KEY_CITY);
            String firstname = parser.getValue(e, KEY_FIRSTNAME);
            String lastname = parser.getValue(e, KEY_LASTNAME);
        String username = parser.getValue(ElementsForUsername,KEY_USER);
        String assistantname = parser.getValue(e,KEY_ASSISTANTNAME);
        String assistantgender = parser.getValue(e,KEY_ASSISTANTGENDER);
        String alwaysOn = parser.getValue(e,KEY_ASSISTANTSTATUS);
        String unit = parser.getValue(e,KEY_UNIT);


        if(unit.contains("C")) {
            theButton.setChecked(true);
            theTempText.setText("C");

        }
        else {
            theButton.setChecked(false);
theTempText.setText("F");
        }


        editTextArray[0].setText(username);
        editTextArray[1].setText(firstname + " " + lastname);
        editTextArray[2].setText(city);
        editTextArray[3].setText(assistantname);




    }

    public void SaveSettings( EditText[] theSettings, Switch theUnit) {
        File xmlFile = new File(SettingsXML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            //update Element value
            updateElementValue(doc,KEY_WEATHER,KEY_CITY,0,theSettings[2].getText().toString());

            if(theUnit.isChecked()) {
                updateElementValue(doc,KEY_WEATHER,KEY_UNIT,0,theUnit.getTextOn().toString());

            }
            else {
                updateElementValue(doc,KEY_WEATHER,KEY_UNIT,0,theUnit.getTextOff().toString());

            }

            updateElementValue(doc,KEY_ASSISTANT,KEY_ASSISTANTNAME,0,theSettings[3].getText().toString());




            //write the updated document to file or console
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SettingsXML));
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








}
