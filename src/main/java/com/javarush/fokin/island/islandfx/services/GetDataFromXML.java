package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.constants.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class GetDataFromXML {
    public HashMap<String, String> createPropertiesMap(String type) {
        HashMap<String, String> entityData = new HashMap<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(Configuration.XML_DATA_FILE_NAME);
            Node root = document.getDocumentElement();
            NodeList entities = root.getChildNodes();
            for (int i = 0; i < entities.getLength(); i++) {
                Node entity = entities.item(i);
                if (entity.getNodeType() != Node.TEXT_NODE) {
                    NodeList entityProperties = entity.getChildNodes();
                    for (int j = 0; j < entityProperties.getLength(); j++) {
                        Node entityProperty = entityProperties.item(j);
                        if (entityProperty.getNodeType() != Node.TEXT_NODE && entity.getNodeName().equals(type)) {
                            entityData.put(entityProperty.getNodeName(), entityProperty.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return entityData;
    }

    public HashMap<String, String> createProbabilityMap(String type) {
        HashMap<String, String> probabilityData = new HashMap<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(Configuration.XML_PROBABILITY_FILE_NAME);
            Node root = document.getDocumentElement();
            NodeList entities = root.getChildNodes();
            for (int i = 0; i < entities.getLength(); i++) {
                Node entity = entities.item(i);
                if (entity.getNodeType() != Node.TEXT_NODE) {
                    NodeList entityProperties = entity.getChildNodes();
                    for (int j = 0; j < entityProperties.getLength(); j++) {
                        Node entityProperty = entityProperties.item(j);
                        if (entityProperty.getNodeType() != Node.TEXT_NODE && entity.getNodeName().equals(type)) {
                            probabilityData.put(entityProperty.getNodeName(), entityProperty.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return probabilityData;
    }
}
