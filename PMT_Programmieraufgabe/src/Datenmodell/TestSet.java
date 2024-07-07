package Datenmodell;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Ignore;

public class TestSet {

	private List<TestClass> _lstClasses;
	
	public TestSet(List<TestClass> lstClasses) {
		_lstClasses = lstClasses;
	}
	
	public List<TestClass> getTestClasses(){
		return _lstClasses;
	}
	
	public static TestSet getTestSet(List<Class<?>> classesToTest) {
		List<TestClass> lstClasses = new ArrayList<>();
		
		for(Class<?> clazz : classesToTest) {
			List<TestMethod> lstMethods = extractTestMethods(clazz);
			
			TestClass classToTest = new TestClass(clazz.getName(), TestStatus.Undefined, clazz, lstMethods);
			
			boolean classPassed = true;
			
			for(TestMethod method: classToTest.getTestMethods()) {
				if(method.getTestStatus() == TestStatus.Failed) {
					//classPassed = false;
				}
			}
			
			if(!classPassed) {
				//classToTest.setStatus(TestStatus.Failed);
			}
			if(classPassed) {
				//classToTest.setStatus(TestStatus.Passed);
			}
			
			lstClasses.add(classToTest);
		}
		
		return new TestSet(lstClasses);
	}
	
	public static List<TestMethod> extractTestMethods(Class<?> clazz){
		List<TestMethod> lstMethods = new ArrayList<TestMethod>();
		
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method : methods) {
			transferToTestMethodObj(clazz, lstMethods, method);
		}
		return lstMethods;
		
	}
	
	private static void transferToTestMethodObj(Class<?> clazz, List<TestMethod> lstMethods, Method method) {
		if(method.isAnnotationPresent(Test.class)) {
			
			TestMethod objToTest = new TestMethod(method.getName(), TestStatus.Undefined, "");
			
			try {
				Object instance = clazz.getDeclaredConstructor().newInstance();
				method.invoke(instance);
				//objToTest.setStatus(TestStatus.Passed);
				
			} catch (Throwable th) {
				//objToTest.setStatus(TestStatus.Failed);
				//objToTest.setFehlermeldung(th.getCause().getMessage());
			}
			lstMethods.add(objToTest);
			
		}
		if(method.isAnnotationPresent(Ignore.class)) {
			TestMethod objToIgnore = new TestMethod(method.getName(), TestStatus.Ignored, "");
			lstMethods.add(objToIgnore);
		}
		
		
	}
	

}
