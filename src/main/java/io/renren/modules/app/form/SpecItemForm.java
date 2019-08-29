package io.renren.modules.app.form;

import lombok.Data;

@Data
public class SpecItemForm {

	private Integer id;
	/**
	 * 规格ID
	 */
	private Integer specId;
	/**
	 * 规格项
	 */
	private String item;
}
