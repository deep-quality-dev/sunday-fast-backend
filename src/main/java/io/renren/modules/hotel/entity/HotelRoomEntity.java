package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 房型信息
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@Data
@TableName("t_hotel_room")
public class HotelRoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 房型名字
	 */
	private String name;
	/**
	 * 价格
	 */
	private BigDecimal price;
	
	/**
	 * 门市价
	 */
	private BigDecimal oprice;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 楼层
	 */
	private String floor;
	/**
	 * 可住人数
	 */
	private Integer people;
	/**
	 * 加床
	 */
	private Integer bed;
	/**
	 * 早餐
	 */
	private Integer breakfast;
	/**
	 * 房间设施
	 */
	private String facilities;
	/**
	 * 窗户
	 */
	private Integer windows;
	/**
	 * 房型大图
	 */
	private String logo;
	/**
	 * 数量
	 */
	private Integer totalNum;
	/**
	 * 
	 */
	private String uniacid;

	/**
	 * 床型尺寸
	 */
	private String size;
	/**
	 * 押金是否可退,1否，2是
	 */
	private Integer isRefund;
	/**
	 * 1在线,2到店,3入住+到店
	 */
	private Integer yjState;
	/**
	 * 押金金额
	 */
	private BigDecimal yjCost;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 房间状态
	 */
	private Integer state;
	/**
	 * 房间类别
	 */
	private Integer classify;
	/**
	 * 入住时长
	 */
	private String rzTime;

	private int discounts = 0;

	@TableField(exist = false)
	private List<HotelRoomMoneyEntity> hotelRoomMoney;

	private String tags;

	@TableField(exist = false)
	private List<String> tagList;

}
