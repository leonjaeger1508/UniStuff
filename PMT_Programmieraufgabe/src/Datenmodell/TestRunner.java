package Datenmodell;

import java.sql.Timestamp;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import turban.utils.ErrorHandler;

public class TestRunner {

	
	public void runTests(TestClass testClass) {
        Class<?> clazz = testClass.getClassToRun();
        
        try {
            Thread.sleep(1000);
            Result result = JUnitCore.runClasses(clazz);
            
            for (Failure failure : result.getFailures()) {
                String methodName = failure.getDescription().getMethodName();
                
                for (TestMethod method : testClass.getTestMethods()) {
                    if (method.getName().equals(methodName)) {
                        method.setFehlermeldung(failure.getTrimmedTrace());
                        method.setStatus(TestStatus.Failed);
                    }
                }
            }
            
            for (TestMethod method : testClass.getTestMethods()) {
                if (method.getTestStatus() == TestStatus.Undefined) {
                    method.setStatus(TestStatus.Passed);
                }
            }
            
        } catch (Throwable th) {
            ErrorHandler.logException(th, true, TestRunner.class, "Fehler in TestRunner!");
        }
    }
	
	public Timestamp runTests1(TestClass testClass) {
		
		
        Class<?> clazz = testClass.getClassToRun();
        
        try {
            Thread.sleep(1000);
            Result result = JUnitCore.runClasses(clazz);
            
            for (Failure failure : result.getFailures()) {
                String methodName = failure.getDescription().getMethodName();
                
                for (TestMethod method : testClass.getTestMethods()) {
                    if (method.getName().equals(methodName)) {
                        method.setFehlermeldung(failure.getTrimmedTrace());
                        method.setStatus(TestStatus.Failed);
                    }
                }
            }
            
            for (TestMethod method : testClass.getTestMethods()) {
                if (method.getTestStatus() == TestStatus.Undefined) {
                    method.setStatus(TestStatus.Passed);
                }
            }
            
        } catch (Throwable th) {
            ErrorHandler.logException(th, true, TestRunner.class, "Fehler in TestRunner!");
        }
        return new Timestamp(System.currentTimeMillis());
        
    }
	
}
