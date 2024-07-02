package Datenmodell;

import java.util.List;

public class TestClass extends GuifiableObject {

	private TestStatus _status;
	private Class<?> _class;
	private List<TestMethod> _lstMethods;
	
	public TestClass(String name, TestStatus status, Class<?> clazz, List<TestMethod> lstMethods) {
		super(name);
		_status = status;
		_class = clazz;
		_lstMethods = lstMethods;
	}
	
	public void setStatus(TestStatus status) {
		_status = status;
	}
	
	public List<TestMethod> getTestMethods(){
		return _lstMethods;
	}
	
	public Class<?> getClassToRun(){
		return _class;
	}
	public TestStatus getTestStatus() {
		return _status;
	}
	
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Klassenname:\n" + this.toGuiString() + "\nStatus der Klasse:\n" + _status + "\n\nMethoden der Klasse:\n" );
		
		for(TestMethod method : _lstMethods) {
			if(method != _lstMethods.get(0)) {
				toReturn.append(",\n");
			}
			toReturn.append(method.toGuiString());
			
		}
		return toReturn.toString();
	}
}
