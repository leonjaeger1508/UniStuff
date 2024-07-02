package Datenmodell;

import java.awt.Image;
import turban.utils.IGuifiable;

public class GuifiableObject implements IGuifiable{

	private final String _strGuiString;
	
	public GuifiableObject(String strGuiString) {
		_strGuiString = strGuiString;
	}
	
	@Override
	public String toGuiString() {
		return _strGuiString;
	}

	@Override
	public Image getGuiIcon() {
		return null;
	}
}
