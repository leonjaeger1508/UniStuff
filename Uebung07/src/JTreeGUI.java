import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import junitgui.tree.TreeExpansionUtil;

public class JTreeGUI extends JFrame {

	
	private JTree jTree;
	private DefaultTreeModel treeModel;
	private FlexibleTreeNode2<MyGuifiableObject> root;
	private JButton add;
	private JButton remove;
	private JButton edit;
	private JCheckBox showRoot;
	private JTextField textField;
	private JPanel buttonPanel;
	
	public JTreeGUI() {
		super("JTreeGUI");//Test Github
		
		root = new FlexibleTreeNode2<>(new MyGuifiableObject("Root"));
		treeModel = new DefaultTreeModel(root);
		jTree = new JTree(treeModel);
		jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		jTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				updateButtons();
			}
		});
		
		JScrollPane treeView = new JScrollPane(jTree);
		treeView.setPreferredSize(new Dimension(450,250));
		this.getContentPane().add(treeView, BorderLayout.CENTER);
		
		add = new JButton("Add");
		remove = new JButton("Remove");
		edit = new JButton("Edit");
		showRoot = new JCheckBox("Show Root");
		buttonPanel = new JPanel(new GridLayout(2,3));
		textField = new JTextField();
		textField.setEditable(true);
		edit.setEnabled(false);
		remove.setEnabled(false);
		
        jTree.setRootVisible(showRoot.isSelected());

		
		buttonPanel.add(add);
		buttonPanel.add(textField);
		buttonPanel.add(edit);
		buttonPanel.add(remove);
		buttonPanel.add(new JPanel());
		buttonPanel.add(showRoot);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		
		add.addActionListener((ActionEvent) ->{
			addNode();
		});
		
		edit.addActionListener((ActionEvent) ->{
			editNode();
		});
		
		remove.addActionListener((ActionEvent) ->{
			removeNode();
		});
		
		showRoot.addActionListener((ActionEvent) ->{
			toggleShowRoot();
		});
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(600,400);
	}

	private void updateButtons() {
        boolean nodeSelected = jTree.getSelectionPath() != null;
        edit.setEnabled(nodeSelected);
        remove.setEnabled(nodeSelected);
    }

	private void editNode() {
		 String str = textField.getText();
	        if (str.isEmpty()) {
	            System.err.println("TextField is empty. Please enter a new value.");
	            return;
	        }      

	        FlexibleTreeNode2<MyGuifiableObject> selectedNode = (FlexibleTreeNode2<MyGuifiableObject>) jTree.getLastSelectedPathComponent();
	        selectedNode.setUserObject(new MyGuifiableObject(str));	//setUserObject von Turban "überschrieben" bzw rückgängig gemacht
	        reloadAndPreserveStateOfTreenodes();
	        textField.setText("");
        
	}
	
	private void addNode() {
		String str = textField.getText();
		if(str.isEmpty()) {
			System.err.println("TextField is empty. Please enter a value");
			return;
		}
		
		FlexibleTreeNode2<MyGuifiableObject> tnToSelect = (FlexibleTreeNode2<MyGuifiableObject>) jTree.getLastSelectedPathComponent();
		
		FlexibleTreeNode2<MyGuifiableObject> newNode = new FlexibleTreeNode2<>(new MyGuifiableObject(str));
		
		if(treeModel.getRoot()== null) {
	        treeModel.setRoot(new FlexibleTreeNode2<MyGuifiableObject>(new MyGuifiableObject(str)));
		}
		else if (tnToSelect != null) {
	    	tnToSelect.add(newNode);
	    	
		} else {
	        root.add(newNode);
	    }
		    
		reloadAndPreserveStateOfTreenodes();
		textField.setText("");
	}
	
	private void removeNode() {
		FlexibleTreeNode2<MyGuifiableObject> tnToSelect = (FlexibleTreeNode2<MyGuifiableObject>) jTree.getLastSelectedPathComponent();
		
		if(tnToSelect.isRoot()) {
			root.removeAllChildren();
	        treeModel.setRoot(null);
			treeModel.reload();
		}
		tnToSelect.removeFromParent();
		reloadAndPreserveStateOfTreenodes();
		

	}

	private void reloadAndPreserveStateOfTreenodes() {
		List<Integer> lstIntsExpStates=TreeExpansionUtil.getExpansionState( jTree);
		treeModel.reload();
		TreeExpansionUtil.setExpansionState( jTree,lstIntsExpStates);
	}
	
	private void toggleShowRoot() {
        jTree.setRootVisible(showRoot.isSelected());
    }
	
	public static void main(String[] args) {
		new JTreeGUI();
	}
	
}
