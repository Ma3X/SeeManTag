// http://thinkandroid.wordpress.com/2010/01/05/using-xpath-and-html-cleaner-to-parse-html-xml/
// http://htmlcleaner.sourceforge.net/javause.php

package com.xpyct.ondatra;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.xml.sax.SAXException;

public class OptionScraper {
 
    // example XPATH queries in the form of strings - will be used later
    private static final String NAME_XPATH = "//div[@class='yfi_quote']/div[@class='hd']/h2";
 
    private static final String TIME_XPATH = "//table[@id='time_table']/tbody/tr/td[@class='yfnc_tabledata1']";
 
    private static final String PRICE_XPATH = "//table[@id='price_table']//tr//span";
 
    // TagNode object, its use will come in later
    private static TagNode node;

    public class Option {

		public void setPremium(double price) {
			// TODO Auto-generated method stub
			
		}
    }
    
    // a method that helps me retrieve the stock option's data based off the name (i.e. GOUAA is one of Google's stock options)
    public static Option getOptionFromName(String name) throws XPatherException, ParserConfigurationException,SAXException, IOException, XPatherException {
    	Option o = null;
 
        // the URL whose HTML I want to retrieve and parse
        String option_url = "http://finance.yahoo.com/q?s=" + name.toUpperCase();
 
        // this is where the HtmlCleaner comes in, I initialize it here
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
 
        // open a connection to the desired URL
        URL url = new URL(option_url);
        URLConnection conn = url.openConnection();
 
        //use the cleaner to "clean" the HTML and return it as a TagNode object
        node = cleaner.clean(new InputStreamReader(conn.getInputStream()));
 
        // once the HTML is cleaned, then you can run your XPATH expressions on the node, which will then return an array of TagNode objects (these are returned as Objects but get casted below)
        Object[] info_nodes = node.evaluateXPath(NAME_XPATH);
        Object[] time_nodes = node.evaluateXPath(TIME_XPATH);
        Object[] price_nodes = node.evaluateXPath(PRICE_XPATH);
 
        // here I just do a simple check to make sure that my XPATH was correct and that an actual node(s) was returned
        if (info_nodes.length > 0) {
            // casted to a TagNode
            TagNode info_node = (TagNode) info_nodes[0];
            // how to retrieve the contents as a string
            String info = info_node.getChildren().iterator().next().toString().trim();
 
            // some method that processes the string of information (in my case, this was the stock quote, etc)
            processInfoNode(o, info);
        }
 
        if (time_nodes.length > 0) {
            TagNode time_node = (TagNode) time_nodes[0];
            String date = time_node.getChildren().iterator().next().toString().trim();
 
            // date returned in 15-Jan-10 format, so this is some method I wrote to just parse that string into the format that I use
            processDateNode(o, date);
        }
 
        if (price_nodes.length > 0) {
            TagNode price_node = (TagNode) price_nodes[0];
            double price = Double.parseDouble(price_node.getChildren().iterator().next().toString().trim());
            o.setPremium(price);
        }
 
        return o;
    }

	private static void processDateNode(Option o, String date) {
		// TODO Auto-generated method stub
		
	}

	private static void processInfoNode(Option o, String info) {
		// TODO Auto-generated method stub
		
	}
}
