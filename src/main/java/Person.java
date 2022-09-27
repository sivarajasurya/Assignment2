import org.w3c.dom.Document;
import java.io.*;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.LoggerFactory;

@XmlRootElement
public class Person{

//    @XmlAttribute
    private String Name;
    private int Age;
    private char Sex;
    public Person() throws JAXBException {
    }

    public Person(String name, int age, char sex) throws JAXBException {
        super();
        this.Name = name;
        this.Age = age;
        this.Sex = sex;
    }

    @XmlElement
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    @XmlElement
    public int getAge() {
        return Age;
    }
    public void setAge(int age) {
        this.Age = age;
    }

    @XmlElement
    public char getSex() {
        return Sex;
    }
    public void setSex(char sex) {
        this.Sex = sex;
    }

    @Override
    public String toString(){
        return "Person [Name = "+ this.Name+ " , Age = "+ this.Age +" , sex = " +this.Sex+"]";
    }
    static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Person.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    static Scanner get = new Scanner(System.in);
    static org.slf4j.Logger logger = LoggerFactory.getLogger(Person.class);

    //Serilize the object as XML and write to the file.
    //And print the .xml file
    public String write(String xmlFileName) throws Exception{

        try {

            Marshaller marshelling = jaxbContext.createMarshaller();
            marshelling.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshelling.marshal(this, new FileOutputStream(xmlFileName));
            System.out.println(xmlFileName+" is created successfully");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new FileInputStream(xmlFileName));
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            Writer out = new StringWriter();
            tf.transform(new DOMSource(doc), new StreamResult(out));
            System.out.println(out.toString());

        }
        catch (Exception e){
            //Log and Throw appropriate exception on error
            logger.error("An exception occurred!", new Exception("Custom exception"));

        }
        return "successfully saved to filename";
    }

    //De-serilize the XML and return the Person object.
    public static Person personRead(String xmlFilePath) {

      try {
            File file = new File(xmlFilePath);
          Unmarshaller jabUnmarshaller = jaxbContext.createUnmarshaller();
          Person p2 = (Person) jabUnmarshaller.unmarshal(file);
        return p2;
       } catch (Exception e){
           //Log and Throw appropriate exception on error
          logger.error("An exception occurred! ", new Exception("Custom exception"));
       }
    return null;
    }

    public void edit(String xmlFilePath)  {

        try {
            File file = new File(xmlFilePath);
            Unmarshaller jabUnmarshaller = jaxbContext.createUnmarshaller();
            Person p3 = (Person) jabUnmarshaller.unmarshal(file);
            System.out.println("Enter the name to be changed");
            p3.setName(get.next());
            this.Name = p3.getName();
            this.Age = p3.getAge();
            this.Sex = p3.getSex();

            Marshaller marshelling = jaxbContext.createMarshaller();
            marshelling.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshelling.marshal(this, new FileOutputStream(xmlFilePath));
            System.out.println(p3.getName()+p3.getAge()+".xml is created successfully");

            File rename = new File(p3.getName()+p3.getAge()+".xml");
            file.renameTo(rename);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new FileInputStream(rename));
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            Writer out = new StringWriter();
            tf.transform(new DOMSource(doc), new StreamResult(out));
            System.out.println(out.toString());
        }catch (Exception e){
            logger.error("An exception occurred! ", new Exception("Custom exception"));
        }

    }

}
