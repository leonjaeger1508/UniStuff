
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import turban.utils.*;
import java.awt.*;
 
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

 
public class DomParser extends JFrame
{
	String _strXmlFileName;
	
	FlexibleTreeNode<MyGuifiable> _tnRoot;
	JTree _jtree;
	DefaultTreeModel _treeModel;
	
	boolean _blnRootVisible = true;
	
	private void domParser_buildContentPane()
	{
        _tnRoot = new FlexibleTreeNode<MyGuifiable>(new MyGuifiable ("Content of XML-Document " + _strXmlFileName + ":"));
	      
		_treeModel=new DefaultTreeModel(_tnRoot);
		_jtree = new JTree(_treeModel);
		
		//_jtree.setCellRenderer(new MyTreeCellRenderer());
		_jtree.setRootVisible(_blnRootVisible);
		
		JScrollPane treeView = new JScrollPane(_jtree);
		treeView.setPreferredSize(new Dimension(450, 250));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(treeView,BorderLayout.CENTER);
		
	}
	
	public DomParser(String strXmlFileName)throws UserMsgException{
		
		super("DOM-Baum von " + strXmlFileName);
		
		_strXmlFileName = strXmlFileName;


		domParser_buildContentPane();
		
		parseXMLAndFillJTreeWithNodes();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//this.setSize(300, 200);
		this.pack();
		this.setLocation(50, 50);
		
		this.setVisible(true);
		
	} 
	
	
	private void parseXMLAndFillJTreeWithNodes()throws UserMsgException {
		Document doc = parseXMLtoDOM(_strXmlFileName);
		
		traverseXmlNodesToFillInJTree(doc, _tnRoot); // doc == DOM-Baum, _tnRoot = JTree-Baum
		
		_treeModel.reload();
	}
	
	
	
	
	public Document parseXMLtoDOM(String strFileName)throws UserMsgException {
		
		  try{
			  DocumentBuilderFactory domFact = DocumentBuilderFactory.newInstance();
			  
			  domFact.setValidating(VALIDATING);
			  /* Moegl. Parser-Konfigurationen:
			  domFact.setValidating(true); domFact.setNameSpaceAware(true);
			  domFact.setIgnoringComments(true);domFact.setIgnoringElementContentWhitespace(true);
			  */
			  
			  DocumentBuilder domBuilder = domFact.newDocumentBuilder();
			  domBuilder.setErrorHandler(new MyErrorHandler());
			  Document domDoc = domBuilder.parse(strFileName);
			  return domDoc;
		  }
		  catch (SAXException sxe){ //Fehler bei Analyse des XML
			  ErrorHandler.logException(sxe, false, DomParser.class, "Exception in parseXMLtoDOM");
		  }
		  catch (ParserConfigurationException pce){ //Fehler bei Parser Config
			  ErrorHandler.logException(pce, false, DomParser.class, "Exception in parseXMLtoDOM");
		  }
		  catch (java.io.IOException ioex){ //Fehler bei Parser Config
			  ErrorHandler.logException(ioex, false, DomParser.class, "Exception in parseXMLtoDOM");
		  }
		  throw new UserMsgException("Error parsing file {0}", "Error parsing file {0}.\nPlease, consult logfile for detailed error message.", strFileName ); //Do in case of Exception!
	}
	
	
	
	 private void traverseXmlNodesToFillInJTree_doChildren(Node nd, FlexibleTreeNode <MyGuifiable> tnThisNode){
		 
		 NodeList nl = nd.getChildNodes();
		 for (int i = 0; i < nl.getLength(); i++){
			 traverseXmlNodesToFillInJTree(nl.item(i), tnThisNode);
		 }
	 }
	 
	 
	 private String traverseXmlNodesToFillInJTree_doAttributes(Element elem) {
		 
		if (elem.hasAttributes() == false){
			return "";
		}
		
		String strToRet = "";
		
	    NamedNodeMap nmp = elem.getAttributes();
		for (int i = 0; i < nmp.getLength(); i++){
			Attr attrib = (Attr)nmp.item(i);
			if (strToRet.length() > 0){
				strToRet += ", "; // strToRet>0 add a comma because we already have added a value before!
			}
			strToRet += attrib.getNodeName() + ": " + attrib.getNodeValue() ;	 
		}
		
	    return "  (Attributes(" + strToRet + "))";
    }
	 
	 private void traverseXmlNodesToFillInJTree(Node nd, FlexibleTreeNode <MyGuifiable> tnParent)
	  {
		  String strToShowInGui = "";
		  
		  switch(nd.getNodeType()) {  // alternativ: mit instanceof pruefen.
			  case Node.DOCUMENT_NODE:
				 
				 strToShowInGui = nd.getNodeName();
				 //Document doc = (Document)nd;
				 break;
			  case Node.ELEMENT_NODE:
				  Element elem = (Element)nd;
				  strToShowInGui = "#element: " + nd.getNodeName(); //+":"+nd.getNodeValue();
				  strToShowInGui += traverseXmlNodesToFillInJTree_doAttributes(elem);
			  break;
			  case Node.TEXT_NODE:
				  //Text txt=(Text)nd;
				  strToShowInGui = nd.getNodeName() + ": " + nd.getNodeValue().trim(); //Do trim to remove beginning and ending white spaces!
				  // Work on Text
				  break;
			  default:
			      //All Other Node-Types
				  String strValue = nd.getNodeValue();
    			  strToShowInGui = nd.getNodeName() +   ((strValue!=null) ? ": " +strValue.trim() : "");
		  }
		  
		  FlexibleTreeNode <MyGuifiable> tnThisNode = new FlexibleTreeNode<>(new MyGuifiable(strToShowInGui));
		  tnParent.add(tnThisNode);
		  
		  traverseXmlNodesToFillInJTree_doChildren(nd, tnThisNode); // We can also call this at the end because this method terminates anyway, if the node has no children!
	  }
	 
	 
	 private static void traverseXmlNodesForOutputOnCommandline_doChildren(Node nd)
	 {
		 NodeList nl = nd.getChildNodes();
		 for (int i = 0; i < nl.getLength(); i++){
			 traverseXmlNodesForOutputOnCommandline(nl.item(i));
		 }
	 }
	
	 private static void traverseXmlNodesForOutputOnCommandline(Node nd)
	  {
		  switch(nd.getNodeType()){
			  case Node.DOCUMENT_NODE:
				 Document doc = (Document)nd;
				 // Work on Document
				 System.out.println(nd.getNodeName());
				 traverseXmlNodesForOutputOnCommandline_doChildren(nd);
				 break;
			  case Node.ELEMENT_NODE:
				  Element elem = (Element)nd;
				  System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
				  // Analyze Attributes
				  traverseXmlNodesForOutputOnCommandline_doChildren( nd);
			  break;
			  case Node.TEXT_NODE:
				  Text txt = (Text)nd;
				  System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
				  // Work on Text
				  break;
			  default:
    			  //Further Node Types - we just print them out, if present:
				  System.out.println(nd.getNodeName() + ":" + nd.getNodeValue());
		  }
	  }
	
	
	
	public static void main(String [] args){
		try{
			
//     		if (args.length < 1){
//				//Schoene Beispiele wofuer man UserMsgException einfach verwenden kann:
//				throw new UserMsgException("Error: Wrong arguments","Error: File name not provided!\nPlease, call application with argument:\n"
//				                                                    +"   java {0} <FileName>", DomParser.class);
//			}
//		
//			String strFileName = args[0];
			
			
			//Falls es nicht mit Kommandozeilenparameter klappt:
			//	 1. Die darueberliegenden Zeilen ab "if (args.length < 1){" auskommentieren		
			//   2. Die folgende Zeile einkommentieren und dann den Pfad fest im String setzen.
			String strFileName = "/Users/leonjaeger/Desktop/HSBib.xml";
			
			
			if( (new java.io.File(strFileName)).exists() == false){
				//Schoene Beispiele wofuer man UserMsgException einfach verwenden kann:
				throw new UserMsgException("XML-File does not exist","Specified XML-File [{0}] not found!",strFileName);
			}
		
		    DomParser domParser = new DomParser(strFileName);
		
		}
		catch(Throwable th)
		{
			 ErrorHandler.logException(th, true, DomParser.class, "Exception in main");
		}
	}
	
	private final boolean VALIDATING = false;
	
	/**
	 * Hier ein Beispiel fuer einen ErrorHandler fuer DOM (oder SAX) als innere Klasse
	 */
	private class MyErrorHandler implements org.xml.sax.ErrorHandler{
		
		private final boolean _blnThrowEx = false;//true; // Hier kann man ausprobieren, wie sich der ErrorHandler verhaelt, wenn Ausnahmen geworfen werden, oder nicht.
		
		
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
			if (_blnThrowEx == true){
				throw sx;
			}
		}
		
	}
	
	
	
	/**
	 * Hier eine Datenmodell-Hilfsklasse fuer FlexibleTreeNode als innere Klasse
	 */
	private class MyGuifiable implements IGuifiable
	{
		String _strGui;
		
		
		public MyGuifiable(String strGui){
			_strGui = strGui;
		}
		
		public void setGuiString(String str){
			_strGui = str;
		}
		
		public String toGuiString() {
			return _strGui;
		}
		
		public Image getGuiIcon() {
			return null;
		}
	}
	
	
}