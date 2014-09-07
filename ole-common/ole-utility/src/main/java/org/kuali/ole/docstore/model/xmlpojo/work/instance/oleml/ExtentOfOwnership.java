package org.kuali.ole.docstore.model.xmlpojo.work.instance.oleml;


import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Pranitha
 * Date: 8/17/12
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * <p>Java class for extentOfOwnership complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="extentOfOwnership">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="textualHoldings" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="note" type="{http://ole.kuali.org/standards/ole-instance}note" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extentOfOwnership", namespace = "http://ole.kuali.org/standards/ole-instance", propOrder = {
        "textualHoldings",
        "note",
        "type"
})
public class ExtentOfOwnership {

    @XmlElement(namespace = "http://ole.kuali.org/standards/ole-instance", required = true)
    protected String textualHoldings;
    @XmlElement(namespace = "http://ole.kuali.org/standards/ole-instance")
    @XStreamImplicit(itemFieldName = "note")
    protected List<Note> note;
    @XmlElement(namespace = "http://ole.kuali.org/standards/ole-instance", required = true)
    protected String type;

    /**
     * Gets the value of the textualHoldings property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTextualHoldings() {
        return textualHoldings;
    }

    /**
     * Sets the value of the textualHoldings property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTextualHoldings(String value) {
        this.textualHoldings = value;
    }

    /**
     * Gets the value of the note property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Note }
     */
    public List<Note> getNote() {
        if (note == null) {
            note = new ArrayList<Note>();
        }
        return this.note;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setType(String value) {
        this.type = value;
    }

}
