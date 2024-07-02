import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import turban.utils.ErrorHandler;

public class PersonalParser {

	
	private String _strFileName;
    private List<Person> _lstPersonal;

    public PersonalParser(String strFileName) {
    	ErrorHandler.Assert(strFileName != null && new File(strFileName).exists(), true, PersonalParser.class, "Fehler: File  {0}  existiert nicht", strFileName);
        _strFileName = strFileName;
        
        _lstPersonal = new ArrayList<>();
        }
    
    public void parsePersonal() {
    	

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);

            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
            domBuilder.setErrorHandler(new org.xml.sax.ErrorHandler() {
            	private final boolean _blnThrowEx = true;
        		
        		
        		public void warning(SAXParseException sx) throws SAXException{
        			handle("Warning: ", sx);
        		}
        		
        		public void error(SAXParseException sx) throws SAXException{
        			handle("Normal error: ", sx);
        		}
        		
        		public void fatalError(SAXParseException sx) throws SAXException{
        			handle("Fatal: ",  sx);
        		}
        		
        		private void handle(String errType, SAXParseException sx) throws SAXException{
        			System.out.println(errType+": "+ sx.getMessage()+ "(Line: "+ sx.getLineNumber()
        								+", Col:"+ sx.getColumnNumber()+")");
        			if (_blnThrowEx){
        				throw sx;
        			}
        		}
            });
            
            
            Document domDoc = domBuilder.parse(_strFileName);
            traverseNodes(domDoc);

        } catch (Exception e) {
            ErrorHandler.logException(e, PersonalParser.class, _strFileName, "Fehler in parsePersonal");
        }
    }

    public void traverseNodes(Node nd) {
        switch (nd.getNodeType()) {
            case Node.DOCUMENT_NODE:
            	Document document = (Document) nd;
                traverseNodes_doChildren(document);
                break;

            case Node.ELEMENT_NODE:
            	Element element = (Element) nd;
            	if (element.getNodeName().equals("Person")) {
                    handlePersonElement(element);
                }
                traverseNodes_doChildren(element);
                break;

            default:
                break;
        }
    }

    private void traverseNodes_doChildren(Node nd) {
        NodeList nList = nd.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            traverseNodes(nList.item(i));
        }
    }

    private void handlePersonElement(Element element) {
        String id = element.getAttribute("ID");
        String typeStr = element.getAttribute("Type");
        String email = element.getAttribute("Email");
        String name = element.getTextContent().trim();

        HSPersonellType type = HSPersonellType.valueOf(typeStr);

        Person person = new Person(id, name, type, email);
        _lstPersonal.add(person);
    }
    
    public List<Person> getPersonen(){
    	return this._lstPersonal;
    }

    public static void main(String[] args) {
        try {
        	
        	String fileName = "/Users/leonjaeger/Desktop/Personal.xml";
            PersonalParser parser = new PersonalParser(fileName);
            parser.parsePersonal();

            for (Person person : parser._lstPersonal) {
                System.out.println(person.toGuiString());
            }
        } catch (Throwable e) {
            ErrorHandler.logException(e, true, PersonalParser.class, "Exception in main");
        }
    }


}

