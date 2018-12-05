package com.mcrudyy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Simple class that work with HTML files
 * @author michaelrudyy
 * @version 1.0
 */

public class HtmlScanner {

    private static final Logger LOG = Logger.getAnonymousLogger();

    private static String ENCODING = "utf8";

    /**
     * Return element by ID
     * @param file
     * @param id
     * @return
     */

    public static Optional<Element> getElementById(File file, String id) {
        try {

            Document document = getJsoupDocumentByFile(file);
            return Optional.of(document.getElementById(id));

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();

        }
    }

    /**
     * Return Elements by Tag
     * @param file
     * @param tag
     * @return
     */
    public static Elements getElementsByTag(File file, String tag) {
        try {

            Document document = getJsoupDocumentByFile(file);
            return document.getElementsByTag(tag);

        } catch (IOException e) {

            e.printStackTrace();
            return new Elements();

        }
    }

    /**
     * Helps to create Document for getElement** functions
     * @param file
     * @return
     * @throws IOException
     */
    private static Document getJsoupDocumentByFile(File file) throws IOException {
        LOG.info("Parse File to Jsoup Document");
        return Jsoup.parse(file, ENCODING);
    }
}
