package com.mcrudyy;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

/**
 * Smart XML Analyzer
 *
 * @author michaelrudyy
 * @version 1.2
 */

public class SmartXMLAnalyzer {

    private static final Logger LOG = Logger.getAnonymousLogger();

    public static void main(String[] args) {


        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: Main <input_origin_file_path> <input_other_sample_file_path>");
        }

        LOG.info("Create Files");
        File originFile = new File(args[0]);
        File file = new File(args[1]);

        LOG.info("Getting target element");
        Optional<Element> targetElement = HtmlScanner.getElementById(
                originFile,
                "make-everything-ok-button");

        System.out.println(generateOutput(getSimularElement(file, targetElement.get())));
    }

    /**
     * Return founded element by attributes
     *
     * @param anotherFile
     * @param targetElement
     * @return
     */
    private static Element getSimularElement(File anotherFile, Element targetElement) {
        LOG.info("Searching for Element");
        return getSimularElementByAttributes(anotherFile, targetElement);
    }

    /**
     * Searching for element by attr by getting more suitable
     *
     * @param anotherFile
     * @param targetElement
     * @return
     */
    private static Element getSimularElementByAttributes(File anotherFile, Element targetElement) {
        Elements elements = HtmlScanner.getElementsByTag(anotherFile, targetElement.tagName());
        Element element = elements.stream()
                .max(
                        Comparator.comparing(currentElement -> compareMatches(targetElement, currentElement))
                ).get();
        return element;

    }

    /**
     * Compare element with targetElement
     * @param targetElement
     * @param currentElement
     * @return
     */
    private static int compareMatches(Element targetElement, Element currentElement) {
        int count = 0;
        if (targetElement.tagName() == currentElement.tagName()) count++;
        count += compareAttributes(targetElement, currentElement);
        return count;
    }

    /**
     * Compare Attributes between element and targetElement
     * @param targetElement
     * @param currentElement
     * @return
     */
    private static int compareAttributes(Element targetElement, Element currentElement) {
        int count = 0;
        for (Attribute attribute : currentElement.attributes()) {
            count += checkAttribute(targetElement, attribute) ? 1 : 0;
        }
        return count;
    }

    /**
     * Checking if attributes is avalible in targetElement
     * @param targetElement
     * @param attribute
     * @return
     */
    private static boolean checkAttribute(Element targetElement, Attribute attribute) {
        for (Attribute attr : targetElement.attributes()) {
            if (attr.equals(attribute)) return true;
        }
        return false;
    }

    /**
     * Generate Output
     * @param element
     * @return
     */
    private static String generateOutput(Element element) {
        Element currentElement = element;
        List<String> tagList = new ArrayList<>();
        String output = element.tagName();

        while (currentElement.parent() != null) {
            tagList.add(currentElement.parent().tagName());

            output =
                    currentElement.parent().tagName() +
                            "[" + currentElement.parent().elementSiblingIndex() +
                            "] > " + output;

            currentElement = currentElement.parent();
        }

        return output;
    }

}
