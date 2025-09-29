package com.kevinm;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.parser.XMLParser;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.GenericParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HL7_XML_Converter {

  public static void main(String[] args) throws IOException, HL7Exception {

    String mode = "hl7";
    String filePath = "";
    if (args.length == 1) {
      filePath = args[0];
    } else if (args.length == 2) {
      mode = args[0];
      filePath = args[1];
    } else {
      System.err.println("Usage:\tjava -jar hl7toxml-1.0-jar-with-deps.jar [hl7|xml] <file>");
      System.exit(1);
    }

    String inFile = new String(Files.readAllBytes(Paths.get(filePath)));

    // Set version to 2.5
    CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.5");
    PipeParser pipeParser = new PipeParser(mcf);
    GenericParser parser = new GenericParser(mcf);
    XMLParser xmlParser = new DefaultXMLParser(mcf);

    Message msg;
    String outMsg = "";
    if (mode.equalsIgnoreCase("hl7")) { // HL7 -> XML
      // msg = pipeParser.parse(inFile);
      msg = parser.parse(inFile);
      outMsg = parser.encode(msg, "XML");
    } else if (mode.equalsIgnoreCase("xml")) { // XML -> HL7
      msg = xmlParser.parse(inFile);
      outMsg = pipeParser.encode(msg);
    } else {
      System.err.println("Passing more arguments require first to be 'hl7 or 'xml', got '"+mode+"'");
      System.exit(1);
    }

    System.out.println(outMsg);
  }
}
