package Datenmodell;

import java.awt.Image;

import turban.utils.IGuifiable;

public enum TestStatus implements IGuifiable{
	
	Ignored("ignored"),
	Failed("failed"),
	Passed("passed"),
	Undefined("undefined")
	;
	
	private String _statusString;
	
	private TestStatus(String statusString) {
		_statusString = statusString;
	}
	
	@Override
	public Image getGuiIcon() {
		return null;
	}

	@Override
	public String toGuiString() {
		return _statusString;
	}

}
