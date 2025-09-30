package com.kevinm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ca.uhn.hl7v2.HL7Exception;

public class App {

  public static void main(String[] args) throws IOException, HL7Exception {

    String mode = "hl7";
    String filePath = "";
    if (args.length == 1) {
      filePath = args[0];
    } else if (args.length == 2) {
      mode = args[0].toLowerCase();
      filePath = args[1];
    } else {
      System.err.println("Usage:\tjava -jar hl7toxml-1.0-jar-with-deps.jar [hl7|xml] <file>");
      System.exit(1);
    }

    Path path = Paths.get(filePath);

    if (!Files.exists(path) || !Files.isRegularFile(path)) {
      System.err.println("File not found: " + filePath);
      System.exit(1);
    }

    String filename = filePath.toLowerCase();

    if (mode == "hl7" && !filename.endsWith(".hl7")) 
      printErrorAndExit("Expected an .hl7 file for hl7 mode, got: " + filename, 1);
    else if (mode == "xml" && !filename.endsWith(".xml")) 
      printErrorAndExit("Expected an .xml file for xml mode, got: " + filename, 1);

    String inFile = new String(Files.readString(path));

    HL7_XML_Converter converter = new HL7_XML_Converter();
    String outMsg = converter.parseMessage(mode, inFile);

    System.out.println(outMsg);
  }


  public static void printErrorAndExit(String msgToPrint, int errorStatus) {
    System.err.println(msgToPrint);
    System.exit(errorStatus);
  }

}
