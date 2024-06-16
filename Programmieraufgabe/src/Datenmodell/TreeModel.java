package Datenmodell;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import turban.utils.*;

public class TreeModel extends JFrame{
	
	private DefaultMutableTreeNode _root;
	private DefaultTreeModel _treeModel;
	private JTree _jTree;
	private JTextArea _testResultArea;
	private Class<?>[] _testClasses;
	private JPanel _mainpanel;
	private volatile boolean stopTest;
	
	public TreeModel(Class<?>[] testClasses) {
		
		_root = new DefaultMutableTreeNode("root");
		_treeModel = new DefaultTreeModel(_root);
		_jTree = new JTree(_treeModel);
		_jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		_testClasses = testClasses;
		buildTree();
		
		_testResultArea = new JTextArea("");
		_testResultArea.setEditable(false);
		
		JScrollPane treeView = new JScrollPane(_jTree);
        
		_jTree.setRootVisible(false);
        List<Integer> lstIntsExpStates=TreeExpansionUtil.getExpansionState( _jTree);
        _treeModel.reload();
        TreeExpansionUtil.setExpansionState( _jTree,lstIntsExpStates);
        
        JScrollPane resultView = new JScrollPane(_testResultArea);

        _mainpanel = new JPanel();
		_mainpanel.setLayout(new GridLayout(1,2));
        _mainpanel.add(treeView);
        _mainpanel.add(resultView);
        
        getContentPane().add(_mainpanel,BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(2,3));
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton toDB = new JButton("In Datenbank speichern");
        JButton xml = new JButton("zu XML konvertieren");
        JProgressBar progressBar = new JProgressBar(0, calculateTotalTestsClasses());
        
        btnPanel.add(start);
        btnPanel.add(progressBar);
        btnPanel.add(stop);
        btnPanel.add(toDB);
        btnPanel.add(new JPanel());
        btnPanel.add(xml);
        getContentPane().add(btnPanel,BorderLayout.SOUTH);
        
        start.addActionListener((ActionEvent)->{
        	startJUnit();
        });
        
        stop.addActionListener((ActionEvent)->{
        	stopJUnit();
        });
        
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900,500);
	}

	private int calculateTotalTestsClasses() {
		int totalTests = 0;
        for (Class<?> testClass : _testClasses) {
        	totalTests++;
        }
        return totalTests;
	}
	
	private void stopJUnit() {
		stopTest = true;
	}

	private void startJUnit() {
		_testResultArea.setText("");
		Thread th = new Thread(()->{
			try {

			stopTest = false;
			
			if(!stopTest) {
				
				TestRunner.runTests(_testClasses, _testResultArea);
				//TODO ProgressBar mitgeben, für jeden testlauf dann updaten 
			}
			
			
			}catch(Throwable thr) {
				ErrorHandler.logException(thr, TreeModel.class, "Fehler!", thr);
				System.out.println("InterruptedException reached!");
			}
		
		
	});
	
	th.start();
	}

	private void buildTree() {

		for (Class<?> testClass : _testClasses) {
            DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(testClass.getSimpleName());
            _root.add(classNode);
            addTestMethods(classNode, testClass);
        }
	}
	
	private void addTestMethods(DefaultMutableTreeNode classNode, Class<?> testClass) {
	    Method[] methods = testClass.getDeclaredMethods();
	    for (Method method : methods) {
	        StringBuilder methodName = new StringBuilder();
	        Annotation[] annotations = method.getDeclaredAnnotations();
	        for (Annotation annotation : annotations) {
	            methodName.append("@").append(annotation.annotationType().getSimpleName()).append(" ");
	        }
	        methodName.append(method.getName());
	        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(methodName.toString());
	        classNode.add(methodNode);
	    }
	}
    
}
