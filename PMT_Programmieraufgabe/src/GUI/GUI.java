package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;
import javax.management.loading.PrivateClassLoader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import org.mariadb.jdbc.internal.com.read.resultset.rowprotocol.TextRowProtocol;

import Datenmodell.DBExtractor;
import Datenmodell.GuifiableObject;
import Datenmodell.TestClass;
import Datenmodell.TestMethod;
import Datenmodell.TestRunner;
import Datenmodell.TestSet;
import Datenmodell.TestStatus;
import Tests.ExceptionTests;
import Tests.FailingTests;
import Tests.IgnoredTests;
import Tests.SuccessfulTests;
import turban.utils.ErrorHandler;
import turban.utils.UserMsgException;

public class GUI extends JFrame {

	private JPanel _treePanel;
	private JPanel _textPanel;
	private JPanel _buttonPanel;
	private JTree _jTree;
	private DefaultTreeModel _treeModel;
	private FlexibleTreeNode<GuifiableObject> _tnRoot;
	private TestSet _testSet;
	private JTextArea _errorTextArea;
	private volatile boolean _stopTesting;
	private JProgressBar _progressBar;
	private Timestamp _startTimestamp;
	private Timestamp _endTimestamp;
	private boolean _testRan;

	public GUI(String strTitel, TestSet testSetToPerform) {

		super(strTitel);
		_testSet = testSetToPerform;
		_testRan = false;

		_treePanel = new JPanel(new BorderLayout());
		_textPanel = new JPanel(new BorderLayout());
		_buttonPanel = new JPanel(new GridLayout(2, 3));

		_errorTextArea = new JTextArea("Fehlermeldungen");
		_errorTextArea.setEditable(false);
		JScrollPane scrollErrorTextArea = new JScrollPane(_errorTextArea);
		_textPanel.add(scrollErrorTextArea, BorderLayout.CENTER);

		buildTree(testSetToPerform);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(ae -> {
			start();
		});
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(ae -> {
			stop();
			
		});
		_buttonPanel.add(startButton);

		_progressBar = new JProgressBar(0, _testSet.getTestClasses().size());
		_progressBar.setStringPainted(true);
		_buttonPanel.add(_progressBar);

		_buttonPanel.add(stopButton);
		
		JButton toDBButton = new JButton("In Datenbank laden");
		toDBButton.addActionListener( ae ->{
			try {
				saveToDB();
			} catch (UserMsgException e) {
				
			}
		});
		
		_buttonPanel.add(toDBButton);
		addPanelsToContentPane();

		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void saveToDB() throws UserMsgException {
		if(_testRan) {
			DBExtractor extractor = new DBExtractor("jdbc:mysql://sql.razeserver.net:3306/UniDB", "Leon", "LeonsDB");
			extractor.writeTODB(_testSet, _startTimestamp, _endTimestamp);
		}else {
			throw new UserMsgException("Wurde nicht in Datenbank gespeichert.","Es wurden keine Tests durchgeführt, deswegen wurde nichts in der Datenbank gespeichert. Sie müssen die Tests durchführen, um sie zu speichern" ,"");
		}
		
	}

	private void stop() {
		if(!_stopTesting) {
			_stopTesting = true;
			_errorTextArea.append("\n\nTests wurden gestoppt!");
		}
	}

	private void updateProgressBar() {
		if (!_stopTesting) {
			SwingUtilities.invokeLater(() -> {
				try {
					int value = _progressBar.getValue();
					_progressBar.setValue(value + 1);
				} catch (Throwable th) {
					ErrorHandler.logException(th, true, GUI.class, "Fehler bei updateProgressBar");
				}
			});
		}
	}

	private void start() {
		_testRan = true;
		_progressBar.setValue(0);
		_startTimestamp = new Timestamp(System.currentTimeMillis());
		TestRunner testRunner = new TestRunner();
		Thread thread = new Thread(() -> {
			try {
				_stopTesting = false;
				_errorTextArea.setText("Fehlermeldungen: \n");
				for (TestClass cl : _testSet.getTestClasses()) {
					if (!_stopTesting) {
						_endTimestamp = testRunner.runTests1(cl);
						updateProgressBar();

					}
				}
				updateErrorTextArea();

			} catch (Throwable th) {
				ErrorHandler.logException(th, false, TestRunner.class, "Fehler in start-Methode");
			}
		});
		thread.start();
	}
	
	

	private void updateErrorTextArea() {

		StringBuilder errorString = new StringBuilder();
		for (TestClass cl : _testSet.getTestClasses()) {
			for (TestMethod method : cl.getTestMethods()) {
				if (method.getTestStatus() != TestStatus.Undefined) {
					if (method.getTestStatus() == TestStatus.Failed) {
						errorString.append("Klasse: ").append(cl.toGuiString()).append("\n")
								.append(method.printFehlermeldung()).append("\n");
					} else {
						errorString.append(method.toGuiString()).append("\nKeine Fehler aufgetreten\n");
					}
				}

			}
		}

		if (!_stopTesting) {
			_errorTextArea.append(errorString.toString());
		}
		

	}

	private void buildTree(TestSet testSetToPerform) {
		_tnRoot = new FlexibleTreeNode<GuifiableObject>(new GuifiableObject("Root"));
		for (int i = 0; i < testSetToPerform.getTestClasses().size(); i++) {
			FlexibleTreeNode<GuifiableObject> nodeClass = new FlexibleTreeNode<GuifiableObject>(
					testSetToPerform.getTestClasses().get(i));
			_tnRoot.add(nodeClass);
			for (int j = 0; j < testSetToPerform.getTestClasses().get(i).getTestMethods().size(); j++) {
				FlexibleTreeNode<GuifiableObject> nodeMethod = new FlexibleTreeNode<GuifiableObject>(
						testSetToPerform.getTestClasses().get(i).getTestMethods().get(j));
				nodeClass.add(nodeMethod);
			}
		}
		
		

		_treeModel = new DefaultTreeModel(_tnRoot);
		_jTree = new JTree(_treeModel);
		_jTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				updateNodes();
			}
		});
		_jTree.setRootVisible(false);

		JScrollPane scrollJTree = new JScrollPane(_jTree);
		_treePanel.add(scrollJTree, BorderLayout.CENTER);
		
		//TODO ->
	}

	private void addPanelsToContentPane() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(_treePanel);
		mainPanel.add(_textPanel);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(_buttonPanel, BorderLayout.SOUTH);
		

	}

	private void updateNodes() {
		FlexibleTreeNode<GuifiableObject> tnSelected = (FlexibleTreeNode<GuifiableObject>) _jTree
				.getLastSelectedPathComponent();
		if (tnSelected == null) {
			return;
		} else {
			if (!_stopTesting) {
				GuifiableObject tnSelectedGuifiableObject = (GuifiableObject) tnSelected.getUserObject();

				if (tnSelectedGuifiableObject instanceof TestMethod) {
					TestMethod tnSelectedMethod = (TestMethod) tnSelectedGuifiableObject;
					_errorTextArea.setText(tnSelectedMethod.printFehlermeldung());
				} else if (tnSelectedGuifiableObject instanceof TestClass) {
					TestClass tnSelectedClass = (TestClass) tnSelectedGuifiableObject;
					_errorTextArea.setText(tnSelectedClass.toString());
				}
			}

		}
	}

	public static void main(String[] args) {

		List<Class<?>> testClassesList = new ArrayList<>();

		testClassesList.add(ExceptionTests.class);
		testClassesList.add(FailingTests.class);
		testClassesList.add(IgnoredTests.class);
		testClassesList.add(SuccessfulTests.class);

		TestSet testSetToPerform = TestSet.getTestSet(testClassesList);
		new GUI("Programmieraufgabe", testSetToPerform);
	}
}
