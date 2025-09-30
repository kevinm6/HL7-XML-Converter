package com.kevinm;

public interface HL7Converter {

  /**
   * Parse input message and convert HL7 ↔ XML
   *
   * @param mode  "hl7" to convert HL7 → XML, "xml" for XML → HL7
   * @param message the message string
   * @return parsed content
   * @throws Exception if parsing fails somehow
   */
  String parseMessage(String mode, String message) throws Exception;
}
