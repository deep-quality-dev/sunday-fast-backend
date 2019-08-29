package io.renren.modules.app.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SpecForm {

	private Integer id;
	/**
	 * 规格名
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 规格项目
	 */
	List<SpecItemForm> specItems = new ArrayList<SpecItemForm>();
}
