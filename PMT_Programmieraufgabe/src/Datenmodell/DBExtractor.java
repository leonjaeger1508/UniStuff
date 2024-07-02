package Datenmodell;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.SpinnerListModel;

import turban.utils.ErrorHandler;

public class DBExtractor {

	private static  String _urlString;
	private static String _usernameString;
	private static String _passwordString;
	private static TestSet _testSet;
	public DBExtractor(String url, String username, String password) {
		_passwordString = password;
		_urlString = url;
		_usernameString = username;
		
		//password = "LeonsDB"
		//User : "Leon"
		//connection : "jdbc:mysql://sql.razeserver.net:3306/UniDB"
	}
	
	public void writeTODB(TestSet testSet, Timestamp startTimestamp, Timestamp endTimestamp) {
	    _testSet = testSet;

	    String start = startTimestamp.toString();
	    String end = endTimestamp.toString();
	    //TODO -> TimeStamp in DB speichern, in DB einrichten!
	    int testedClasses = 0;
	    int testedMethods = 0;
	    int passedClasses = 0;
	    int failedClasses = 0;
	    int passedMethods = 0;
	    int failedMethods = 0;
	    int ignoredMethods = 0;
	    int undefinedClasses = 0;
	    int undefinedMethods = 0;

	    for(TestClass testClass: _testSet.getTestClasses()) {
	        
	        testedClasses++;
	        
	        switch(testClass.getTestStatus()) {
	            
	        case Passed:
	        {
	            passedClasses++;
	            break;
	        }
	        case Failed:
	        {
	            failedClasses++;
	            break;
	        }
	        default:
	        {
	            undefinedClasses++;
	        }
	        }
	        
	        for(TestMethod testMethod: testClass.getTestMethods()) {
	            
	            testedMethods++;
	            
	            switch(testMethod.getTestStatus()) {
	            case Passed:
	            {
	                passedMethods++;
	                break;
	            }
	            case Failed:
	            {
	                failedMethods++;
	                break;
	            }
	            case Ignored:
	            {
	                ignoredMethods++;
	                break;
	            }
	            default:
	            {
	                undefinedMethods++;
	            }
	            }
	        }
	        
	        
	    }
	    
	    
	    try(Connection con = DriverManager.getConnection(_urlString, _usernameString, _passwordString)) {
	        
	        Statement statement = con.createStatement();

	        try{
	            
	            int count = statement.executeUpdate("INSERT INTO TestSet (GetesteteKlassen, GetesteteMethoden, FailedMethoden, PassedMethoden, IgnoredMethoden, UndefinedMethoden, PassedKlassen, FailedKlassen, UndefinedKlassen, Startzeit, Endzeit) " 
	            + "VALUES (" + testedClasses + ", " + testedMethods + ", " + failedMethods +", " + passedMethods +", " + ignoredMethods +", " +undefinedMethods +", " + passedClasses +", " +failedClasses +", " +undefinedClasses + ", '" + start +"', '" + end + "')");
	            
	            ErrorHandler.Assert(count == 1, true, DBExtractor.class, "Falscher Count {0} -> Fehlerhaftes Statement", count);
	        } catch (Exception e) {
	            ErrorHandler.logException(e, true, DBExtractor.class, "Fehler beim Statement übergeben");
	        }
	    } catch (Exception e) {
	        ErrorHandler.logException(e, DBExtractor.class, "Exception");
	    }
	}

}
