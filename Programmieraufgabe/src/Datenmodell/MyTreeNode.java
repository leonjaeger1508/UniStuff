package Datenmodell;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyTreeNode<T> extends DefaultMutableTreeNode{

	private DefaultMutableTreeNode root;
	
	public MyTreeNode(T userObj) {
		super(userObj);
	}
	
	@Override
	public T getUserObject() {
		return (T) super.getUserObject();
	}
	
	
	
	
	
}
