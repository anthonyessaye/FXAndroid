package com.ndusenior.fix;


import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ndusenior.fix.XMLRelated.XMLparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by antho on 3/28/2017.
 */

public class ControlsMenu extends Fragment  {

    EditText[] editTextArray;
    ToggleButton[] toggleButtons;


    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    String OutputXML= rootFolder.getAbsolutePath() + "/OutputNames.xml";


    private Button saveBtn;
    private MyFTPClientFunctions theFTP = null;

    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    // XML node keys
    private String KEY_OUTPUT= "Output"; // parent node
    private String KEY_NAME = "name";
    private String KEY_ID= "id";
    private String KEY_STATUS = "status";

    private String Email;
    private String Password;

    private TextView statusText;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Main Device");

        editTextArray = new EditText[] {
                (EditText) getView().findViewById(R.id.OutputOneName),
                (EditText) getView().findViewById(R.id.OutputTwoName),
                (EditText) getView().findViewById(R.id.OutputThreeName),
                (EditText) getView().findViewById(R.id.OutputFourName)};
        toggleButtons = new ToggleButton[] {
                (ToggleButton) getView().findViewById(R.id.OutputOneToggle),
                (ToggleButton) getView().findViewById(R.id.OutputTwoToggle),
                (ToggleButton) getView().findViewById(R.id.OutputThreeToggle),
                (ToggleButton) getView().findViewById(R.id.OutputFourToggle)};


        statusText = (TextView) getView().findViewById(R.id.statusText);
        statusText.setText("Getting Latest Files");


        LoadXML();





    }


    private void testWriting(String fileToWrite, String ElementChosen,String RootElement,
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
            //loop for each employee
            for(int i=0; i < node.getLength();i++){
                nodes = (Element) node.item(ElementPosition);
                Node name = nodes.getElementsByTagName(ElementChosen).item(0).getFirstChild();
                name.setNodeValue(UpdatedValue);
            }
        }


 public void SaveButtonListener(){
     saveBtn.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {


             for(int i = 0; i< editTextArray.length; i++) {
                 testWriting(OutputXML, KEY_NAME, KEY_OUTPUT, i, editTextArray[i].getText().toString());
                 testWriting(OutputXML, KEY_STATUS, KEY_OUTPUT, i, toggleButtons[i].getText().toString());
             }
             uploadIt();

             //Toast.makeText(getContext(),"Files Uplaoded",Toast.LENGTH_LONG).show();




         }

     });
}

    public void uploadIt(){

        // MyFTPClientFunctions is an open source class created by Tejas Jasani
        // available for public use and sale/resale of any product developed using such work
        // with licensing available for free with credits

        new Thread(new Runnable() {
            public void run() {
                boolean status = false;

                boolean uploadStatus;

                uploadStatus = theFTP.ftpConnect("ftp.bodirectors.com", Email + "@bodirectors.com",
                        Password, 21);

                if (uploadStatus == true) {
                    theFTP.ftpUpload(OutputXML, "OutputNames.xml", "/", getContext());
                    theFTP.ftpDisconnect();
                   // Toast.makeText(getContext(),"Files Updated", Toast.LENGTH_LONG).show();
                }

            }
        }).start();

    }

    public void LoadXML()
    {
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


            if (status == "On")
                toggleButtons[i].setChecked(true);
            else
                toggleButtons[i].setChecked(false);

            editTextArray[i].setText(name);
            statusText.setText("Files Up-To-Date\nStatus: Idle");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.menu_controlsfragment,container,false);
        theFTP = new MyFTPClientFunctions();
        Bundle bundle = getArguments();

        Email = bundle.getString(EMAIL_KEY);
        Password = bundle.getString(PASSWORD_KEY);


        return theView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        saveBtn = (Button) getView().findViewById(R.id.SaveButton);

        SaveButtonListener();
    }



}
