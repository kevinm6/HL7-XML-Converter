package com.kevinm;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.parser.XMLParser;

public class HL7_XML_Converter implements HL7Converter {

  @Override
  public String parseMessage(String mode, String message) throws HL7Exception {
    // Set version to 2.5
    CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.5");
    PipeParser pipeParser = new PipeParser(mcf);
    GenericParser parser = new GenericParser(mcf);
    XMLParser xmlParser = new DefaultXMLParser(mcf);

    Message msg;
    String outMsg = "";
    if (mode.equalsIgnoreCase("hl7")) { // HL7 -> XML
      try {
        msg = parser.parse(message);
        outMsg = parser.encode(msg, "XML");
      } catch (HL7Exception e) {
        throw new HL7Exception("Error parsing HL7 message to XML.\n" + e);
      }
    } else if (mode.equalsIgnoreCase("xml")) { // XML -> HL7
      try {
        msg = xmlParser.parse(message);
        outMsg = pipeParser.encode(msg);
      } catch (HL7Exception e) {
        throw new HL7Exception("Error parsing XML message to HL7.\n" + e);
      }
    } else {
      throw new IllegalArgumentException("<mode> must be 'hl7' (default) or 'xml', got '"+mode+"'");
    }

    return outMsg;
  }

}
