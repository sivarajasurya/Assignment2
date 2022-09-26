import org.w3c.dom.Document;
import java.io.*;
import java.io.FileOutputStream;
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




@XmlRootElement
public class Person{





//    @XmlAttribute
    private String Name;

    private int Age;

    private char Sex;

    public Person(){

    }

    public Person(String name, int age, char sex) {
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

    //Serilize the object as XML and write to the file.
    //And print the .xml file
    public String write(String xmlFileName) throws Exception{


        try {

            JAXBContext contextobj = JAXBContext.newInstance(Person.class);
            Marshaller marshelling = contextobj.createMarshaller();
            marshelling.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshelling.marshal(this, new FileOutputStream(xmlFileName));
            System.out.println("employee.xml is created successfully");


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

            Logger logger = Logger.getAnonymousLogger();
            //Exception e1 = new Exception(e);
            logger.log(Level.SEVERE, "an exception was thrown", e);

            System.out.println(e);
        }


        return "successfully saved to filename";
    }



    //De-serilize the XML and return the Person object.
    public void personRead(String xmlFilePath) throws Exception{



       try {
            File file = new File(xmlFilePath);
          JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);

          Unmarshaller jabUnmarshaller = jaxbContext.createUnmarshaller();
          Person p2 = (Person) jabUnmarshaller.unmarshal(file);
           System.out.println("Name = "+p2.getName()+" Age = "+ p2.getAge()+" Sex = "+p2.getSex());
       } catch (Exception e){

           //Log and Throw appropriate exception on error

           Logger logger = Logger.getAnonymousLogger();
           Exception e1 = new Exception(e);
           logger.log(Level.SEVERE, "an exception was thrown", e1);
           System.out.println(e1);

       }

    }
}
