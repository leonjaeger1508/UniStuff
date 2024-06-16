package Datenmodell;

import Tests.ExceptionTests;
import Tests.FailingTests;
import Tests.IgnoredTests;
import Tests.SuccessfulTests;

public class mainframe {

	public mainframe() {
		Class<?>[] testSetClasses = {
				ExceptionTests.class,
				FailingTests.class,
				IgnoredTests.class,
				IgnoredTests.class,
				SuccessfulTests.class,
		};
		
		//TODO: Liste an TreeModel geben, Baum darauf aufbauen und den dann ans JTreeGUI geben
	}
	
}
