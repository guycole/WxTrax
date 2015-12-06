package com.digiburo.wxtraxlib.utility;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Perform weather collection for a single station
 */
public class WxCallable implements Callable<ObservationHashMap> {
  public static final String NWS_BASE_URL = "http://www.weather.gov";
  public static final String NWS_OBSERVATION_URL = NWS_BASE_URL + "/xml/current_obs/XXXX.xml";

  private final URL url;

  /**
   * ctor
   * @param target KLAX, KPDX, KSEA, etc
   */
  public WxCallable(String target) throws MalformedURLException {
    String rawUrl = NWS_OBSERVATION_URL.replace("XXXX", target);
    url = new URL(rawUrl);
  }

  /**
   * Collect current weather
   * @return current weather or empty HashMap
   */
  @Override
  public ObservationHashMap call() throws IOException, SAXException, ParserConfigurationException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser sp = spf.newSAXParser();
    XMLReader xr = sp.getXMLReader();

    WxXmlHandler handler = new WxXmlHandler();
    xr.setContentHandler(handler);
    xr.parse(new InputSource(url.openStream()));
    return handler.getTokens();
  }
}
