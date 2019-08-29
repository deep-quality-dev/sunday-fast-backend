package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email taozui1995@gmail.com
 * @date 2019-06-25 12:48:47
 */
@Data
@TableName("t_region")
public class RegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Integer id;
	/**
	 * 父id
	 */
	private Integer pid;
	/**
	 * 简称
	 */
	private String shortname;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 全称
	 */
	private String mergerName;
	/**
	 * 层级 1 2 3 省市区县
	 */
	private Integer level;
	/**
	 * 拼音
	 */
	private String pinyin;
	/**
	 * 长途区号
	 */
	private String code;
	/**
	 * 邮编
	 */
	private String zipCode;
	/**
	 * 首字母
	 */
	private String first;
	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 纬度
	 */
	private String lat;

}
