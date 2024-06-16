package Datenmodell;

import javax.swing.JTextArea;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import javax.swing.SwingUtilities;

public class TestRunner {

    public static void runTests(Class<?>[] testClasses, JTextArea textArea) {
        for (Class<?> testClass : testClasses) {
            runTestForClass(testClass, textArea);
        }
    }

    private static void runTestForClass(Class<?> testClass, JTextArea textArea) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(testClass);

        SwingUtilities.invokeLater(() -> {
            textArea.append("Running tests for: " + testClass.getSimpleName() + "\n");
            for (Failure failure : result.getFailures()) {
                textArea.append("Test failed: " + failure.getTestHeader() + "\n");
                textArea.append("Reason: " + failure.getMessage() + "\n");
            }
            textArea.append("Tests run: " + result.getRunCount() + ", Failures: " + result.getFailureCount() + "\n");
            textArea.append("-------------------------------------------------\n");
            
        });
    }
}
