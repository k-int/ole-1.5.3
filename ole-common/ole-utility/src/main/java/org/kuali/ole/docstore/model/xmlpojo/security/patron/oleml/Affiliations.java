////
//// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
//// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
//// Any modifications to this file will be lost upon recompilation of the source schema.
//// Generated on: 2012.03.15 at 02:03:46 PM IST
////
//
//
//package org.kuali.ole.docstore.model.xmlpojo.security.patron.oleml;
//
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamImplicit;
//
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlType;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * <p>Java class for affiliations complex type.
// *
// * <p>The following schema fragment specifies the expected content contained within this class.
// *
// * <pre>
// * &lt;complexType name="affiliations">
// *   &lt;complexContent>
// *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
// *       &lt;sequence>
// *         &lt;element name="affiliation" type="{http://ole.kuali.org/standards/ole-patron}affiliation" maxOccurs="unbounded"/>
// *       &lt;/sequence>
// *     &lt;/restriction>
// *   &lt;/complexContent>
// * &lt;/complexType>
// * </pre>
// *
// *
// * @author Rajesh Chowdary K
// * @created Mar 15, 2012
// */
//
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "affiliations", propOrder = {
//    "affiliation"
//})
//@XStreamAlias("affiliations")
//public class Affiliations {
//
//    @XmlElement(required = true)
//    @XStreamImplicit
//    protected List<Affiliation> affiliation;
//
//    /**
//     * Gets the value of the affiliation property.
//     *
//     * <p>
//     * This accessor method returns a reference to the live list,
//     * not a snapshot. Therefore any modification you make to the
//     * returned list will be present inside the JAXB object.
//     * This is why there is not a <CODE>set</CODE> method for the affiliation property.
//     *
//     * <p>
//     * For example, to add a new item, do as follows:
//     * <pre>
//     *    getAffiliation().add(newItem);
//     * </pre>
//     *
//     *
//     * <p>
//     * Objects of the following type(s) are allowed in the list
//     * {@link Affiliation }
//     *
//     *
//     */
//    public List<Affiliation> getAffiliation() {
//        if (affiliation == null) {
//            affiliation = new ArrayList<Affiliation>();
//        }
//        return this.affiliation;
//    }
//
//}
