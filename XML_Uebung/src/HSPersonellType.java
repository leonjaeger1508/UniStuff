
import java.awt.Image;

import turban.utils.*;

public enum HSPersonellType implements IGuifiable {
	Student("Student", true , false , false , false),
	Professor("Professor",false , true, false , false),
	Tutor("Tutor",false, true, false , false),
	President("Präsident",false, true , true , false),
	Dean("Dekan" , false, true, true , false) ,
	ITAdmin("IT Admin", false , false , false , true),
	Secretary("Sekretär", false , false , false , true);
	
	private String _guiString;
	private boolean _receiveLessons;
	private boolean _givesLessons;
	private boolean _hasOrgResp;
	private boolean _isAdminStaff;
	
	private HSPersonellType(String guiString, boolean receiveLessons, boolean givesLessons, boolean hasOrgResp, boolean isAdminStaff) {
		_guiString = guiString;
		_receiveLessons = receiveLessons;
		_givesLessons = givesLessons;
		_hasOrgResp = hasOrgResp;
		_isAdminStaff = isAdminStaff;
		
		}

	public boolean receiveLessons() {
		return _receiveLessons;
	}
	
	public boolean givesLessons() {
		return _givesLessons;
	}
	
	public boolean hasOrgResp() {
		return _hasOrgResp;
	}
	
	public boolean isAdminStaff() {
		return _isAdminStaff;
	}
	
	
	

	@Override
	public String toGuiString() {return _guiString;}

	@Override
	public Image getGuiIcon() {return null;}

}
