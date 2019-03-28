package io.renren.common.utils;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据权限查询参数
 * 
 * @author taoz
 *
 */
@Data
@EqualsAndHashCode
public class DataScope {
	/**
	 * 限制范围的字段名称
	 */
	private String scopeName = "sellerId";

	/**
	 * 具体的数据范围
	 */
	private List<Integer> sellerId;

	/**
	 * 是否只查询本部门
	 */
	private Boolean isOnly = false;
}
