import java.awt.Image;

import turban.utils.IGuifiable;

public class MyGuifiableObject implements IGuifiable{

	
	private String _strGuiString;
	
	public MyGuifiableObject(String strGuiString) {
		_strGuiString = strGuiString;
	}
	@Override
	public Image getGuiIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toGuiString() {
		// TODO Auto-generated method stub
		return _strGuiString;
	}

}
