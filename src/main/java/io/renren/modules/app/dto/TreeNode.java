package io.renren.modules.app.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TreeNode {
	protected int id;
	protected int parentId;
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
