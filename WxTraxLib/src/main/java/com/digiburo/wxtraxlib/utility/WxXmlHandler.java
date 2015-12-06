package com.digiburo.wxtraxlib.utility;

import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.utility.ObservationHashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler for NWS datum
 *
 * @author gsc
 */
public class WxXmlHandler extends DefaultHandler {

  //
  private boolean flag = false;

  //
  private String token;

  //
  private ObservationHashMap tokenz = new ObservationHashMap();

  /**
   * Return collected key/value pairs
   * @return collected key/value pairs, might be empty but never null
   */
  public ObservationHashMap getTokens() {
    return(tokenz);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    flag = true;
    token = Constant.BAD_VALUE;
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    tokenz.put(localName, token);

    flag = false;
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (flag) {
      token = new String(ch, start, length);
    }
  }
}