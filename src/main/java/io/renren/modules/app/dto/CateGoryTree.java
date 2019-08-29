package io.renren.modules.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CateGoryTree extends TreeNode {

	private String name;

	private String img;

	private int sort;

	public CateGoryTree() {
	}

	public CateGoryTree(int id, String name, int parentId, String img, int sort) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.img = img;
		this.sort = sort;
	}
}
