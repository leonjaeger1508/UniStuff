import java.awt.*;
import java.util.*;
import turban.utils.*;


public class Person implements IGuifiable
{
	private String _strId="";
	private String _strName="";
	private String _strEmail="";
	private HSPersonellType _personelltype=null;
	
	public Person(String strId,String strName, HSPersonellType personelltype, String strEmail)
	{
		_strId=strId;
		_strName=strName;
		_strEmail=strEmail;
		_personelltype=personelltype;
	}
	
	public String toGuiString()
	{
		return _personelltype.toGuiString()+" "+ _strName;
	}
	
	public Image getGuiIcon()
	{
		return null;
	}
	
	public String getId()
	{
		return _strId;
	}
	
	public String getName()
	{
		return _strName;
	}
	
	public String getEmail()
	{
		return _strEmail;
	}
	
	public HSPersonellType getPersonellType()
	{
		return _personelltype;
	}
}