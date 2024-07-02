package Datenmodell;

public class TestMethod extends GuifiableObject{

	private TestStatus _status;
	private String _name;
	private String _fehlermeldung;
	
	public TestMethod(String name, TestStatus status, String fehlermeldung) {
		super(name);
		_name = name;
		_status = status;
		_fehlermeldung = fehlermeldung;
	}
	
	public void setStatus(TestStatus status) {
		_status = status;
	}
	
	public TestStatus getTestStatus() {
		return _status;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getFehlermeldung() {
		return _fehlermeldung;
	}
	
	public void setFehlermeldung(String fehlermeldung) {
		_fehlermeldung = fehlermeldung;
	}
	
	
	public String printFehlermeldung() {
		return super.toGuiString() +"\n\nStatus:\n" + this._status.toGuiString() +"\nAufgetretene Fehler in der Methode:\n" + getFehlermeldung();
	}
	@Override
	public String toGuiString() {
		return "Methode:" + super.toGuiString() + ", Status der Methode: " + this._status.toGuiString();
	}

}
