package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;

/**
 * Utility-class helping to preserve open state of Treenodes in the JTree, when TreeModel.reload(); is called.
 *
 * Usage:
 *     List<Integer> lstIntsExpStates=TreeExpansionUtil.getExpansionState( jTree);
 *     treeModel.reload()
 *     TreeExpansionUtil.setExpansionState( jtree,lstIntsExpStates);
 */
public class TreeExpansionUtil {

	public static List<Integer> getExpansionState(JTree tree) {
		List<Integer> expandedRows = new ArrayList<Integer>();
		try {
			for (int i = 0; i < tree.getRowCount(); i++) {
				if (tree.isExpanded(i)) {
					expandedRows.add(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expandedRows;
	}

	public static void setExpansionState(JTree tree, List<Integer> i) {
		try {
			if (i.size() > 0) {
				for (int row : i) {
					tree.expandRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}