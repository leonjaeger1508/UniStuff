package Datenmodell; 
import Tests.ExceptionTests;
import Tests.FailingTests;
import Tests.IgnoredTests;
import Tests.SuccessfulTests;

public class main {

	public static void main(String[] args) {
		Class<?>[] testSetClasses = {
				ExceptionTests.class,
				FailingTests.class,
				IgnoredTests.class,
				IgnoredTests.class,
				SuccessfulTests.class,
		};
		
		TreeModel tree = new TreeModel(testSetClasses);
	}
}
