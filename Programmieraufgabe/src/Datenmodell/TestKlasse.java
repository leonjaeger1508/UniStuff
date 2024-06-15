package Datenmodell;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import turban.utils.ErrorHandler;

	public class TestKlasse{
		
	private volatile boolean stop = false;
	
	
	public TestKlasse(JUnitGUI gui) {
		this._gui = gui;
	}
	
	public void runTests(Class<?> [] testClasses) {
		stop = false;
		
		new Thread(()-> {
			for( Class<?> testClass : testClasses) {
				if(stop) {
                    _gui.appendResult("Testlauf wurde gestoppt.");
					break;
				}
				Result result = JUnitCore.runClasses(testClass);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                processResult(result);
				
			}
		}).start();
	}
	
	public void stopTests() {
		stop = true;
	}
	
	 private void processResult(Result result) {
	        _gui.appendResult("Testklasse: " + result.getRunCount());
	        _gui.appendResult("Erfolgreiche Tests: " + result.wasSuccessful());
	        for (Failure failure : result.getFailures()) {
	            _gui.appendResult("Fehler: " + failure.toString());
	        }
	    }
}
