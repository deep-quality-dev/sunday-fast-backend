package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@Data
@TableName("t_hotel_seller")
public class HotelSellerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 1后台添加,2入住
	 */
	private Integer owner;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 星级
	 */
	private String star;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系人
	 */
	private String linkName;
	/**
	 * 联系电话
	 */
	private String linkTel;
	/**
	 * 酒店电话
	 */
	private String tel;
	/**
	 * 办理时间
	 */
	private String handle;
	/**
	 * 开业时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date openTime;
	/**
	 * 唤醒
	 */
	private Integer wake;
	/**
	 * Wi-Fi
	 */
	private Integer wifi;
	/**
	 * 停车场
	 */
	private Integer park;
	/**
	 * 早餐
	 */
	private Integer breakfast;
	/**
	 * 银联支付
	 */
	private Integer unionpay;
	/**
	 * 健身房
	 */
	private Integer gym;
	/**
	 * 会议室
	 */
	private Integer boardroom;
	/**
	 * 
	 */
	private Integer water;
	/**
	 * 酒店政策
	 */
	private String policy;
	/**
	 * 酒店介绍
	 */
	private String introduction;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 退订规则
	 */
	private String rule;
	/**
	 * 温馨提示
	 */
	private String prompt;
	/**
	 * 
	 */
	private String bqLogo;
	/**
	 * 
	 */
	private String support;
	/**
	 * 酒店logo
	 */
	private String ewmLogo;
	/**
	 * 时间
	 */
	private Integer time;
	/**
	 * 经纬度
	 */
	private String coordinates;
	/**
	 * 排序
	 */
	private Integer scort;
	/**
	 * 身份证正面照
	 */
	private String sfzImg1;
	/**
	 * 身份证反面照
	 */
	private String sfzImg2;
	/**
	 * 营业执照
	 */
	private String yyImg;
	/**
	 * 补充说明
	 */
	private String other;
	/**
	 * 房间最低价格
	 */
	private BigDecimal zdMoney;
	/**
	 * 1待审核,2通过，3拒绝
	 */
	private Integer state;
	/**
	 * 申请时间
	 */
	private Integer sqTime;
	/**
	 * 平台优惠券使用
	 */
	private Integer isUse;
	/**
	 * 
	 */
	private Integer llNum;
	/**
	 * 绑定提现人
	 */
	private Integer bdId;
	/**
	 * 余额支付
	 */
	private BigDecimal yeOpen;
	/**
	 * 微信支付
	 */
	private Integer wxOpen;
	/**
	 * 到店支付
	 */
	private Integer ddOpen;

}
