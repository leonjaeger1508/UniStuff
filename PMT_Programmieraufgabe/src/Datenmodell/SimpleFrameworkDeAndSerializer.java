package Datenmodell;

import java.io.*;
import turban.utils.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

public class SimpleFrameworkDeAndSerializer
{
		/**
		 * Starts deserialization from a file (reading from a file and building the object structure).
		 *
		 * @param <T> the generic type of the start object in the data model object structure.
		 * @param classTarget IN: Classfile defining the T-Parameter as start object in the data model structure.
		 * @param strFilePath IN: The path and file name of the file to write
		 * @return the created start object in the data model structure (-> the complete data structure should be created by this).
		 * @throws UserMsgException Signals that an usage error has occurred through the user.
		 * NOTE: There will automatically be displayed a message box to the user. Thus, this exception
		 * should just be caught but no further actions to handle it should be necessary.
		 * NOTE: The function Errorhandler#logException will ignore this exception and not perform a logging.
		 */
		public static <T extends Object> T deserialize(Class<T> classTarget
		                      , String strFileWithPath)throws UserMsgException 
		{	
		    File source = new File(strFileWithPath);
			ErrorHandler.Assert(source.exists(),true
			     ,SimpleFrameworkDeAndSerializer.class,"File [{0}] does not exist!"
				 ,strFileWithPath);
			try {
				Serializer serializer = new Persister();
				
				return (T) serializer.read(classTarget, source);
			}
		    catch (Exception ex) {
				ErrorHandler.logException(ex, SimpleFrameworkDeAndSerializer.class
				         ,"Exception encountered at deserialization of file [{0}]."
						 , strFileWithPath);
				   
			    throw new UserMsgException("Error reading XML-file {0}"
				            , "Error reading XML-file {0}.\nError: {1}"
				            ,strFileWithPath,  ex.getMessage());
			}
		}
		
	private static void tryDeleteFileIfPresent(String strFileNamePath)
	{
		try {
			File file = new File(strFileNamePath);
			if (file.exists() == true) {
				file.delete();
			}
		}
		catch (Throwable ex) {
			//Beispiel fÃ¼r eine "Nichtkriegsentscheidender Ort"-Zwischenfangleine
			ErrorHandler.logException(ex, false
			                    , SimpleFrameworkDeAndSerializer.class, "Error deleting file [{0}]", strFileNamePath);
		}
	}
		
	/**
	 * Starts serialization to a file (writing to a file).
	 *
	 * @param <T> the generic type of the start object in the data model.
	 * @param serializeTarget IN: The serialize target
	 * @param strFilePath IN: The path and file name of the file to read.
	 * @throws UserMsgException Signals that an usage error has occurred through the user.
	 * NOTE: There will automatically be displayed a message box to the user. Thus, this exception
	 * should just be caught but no further actions to handle it should be necessary.
	 * NOTE: The function Errorhandler#logException will ignore this exception and not perform a logging.
	 */
	public static <T extends Object> void serializeToFile(T serializeTarget
	                           , String strFilePath) throws UserMsgException
	{
		try {
			Format format = new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>"); //Erzeugt die XML-Deklaration
            Serializer serializer = new Persister(format);
			//Serializer serializer = new Persister();
			File targetFile = new File(strFilePath);
			serializer.write(serializeTarget, targetFile);
		}
		catch (Throwable ex)
		{
			ErrorHandler.logException(ex, true
			                    , SimpleFrameworkDeAndSerializer.class, "Error serializing object [{0}]", serializeTarget);
			//Delete the file because it will be corrupt anyway.
			tryDeleteFileIfPresent(strFilePath);
		}
	}
	
	
	
	
	/**
	 * Start serialization to a String/writing to a string.
	 *
	 * @param <T> the generic type
	 * @param serializeTarget IN: The serialize target
	 * @return String containing the complete content as XML.
	 */
	public static <T extends Object> String serializeToString(T serializeTarget)
															throws UserMsgException
	{
		try{
			Serializer serializer = new Persister();
						
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			
			serializer.write(serializeTarget, byteOutputStream);
			
			byteOutputStream.flush();
			
			return new String(byteOutputStream.toByteArray(), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			ErrorHandler.logException(e, true, SimpleFrameworkDeAndSerializer.class, "");
			throw new UserMsgException("Unable to serialize object"
							,"Unable to serialize object [{0}] to string! "
							+"- Unable to convert byte array to String with encoding UTF-8!", serializeTarget);
		}
		catch (Throwable ex) {
			ErrorHandler.logException(ex, true, SimpleFrameworkDeAndSerializer.class, "Error serializing object [{0}]", serializeTarget);
			throw new UserMsgException("Unable to serialize object"
			                      ,"Unable to serialize object [{0}] to string!", serializeTarget);
		}
	}
		
		
}	