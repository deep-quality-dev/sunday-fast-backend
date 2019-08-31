package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class HotelInfo {
	/**
	 * 
	 */
	private Long id;
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
	 * 酒店电话
	 */
	private String tel;
	/**
	 * 开业时间
	 */
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
	 * 热水
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
	 * 补充说明
	 */
	private String other;
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

	/**
	 * 是否收藏
	 */
	private int collect = 0;

	/**
	 * 评论数
	 */
	private int commentCount;

	/**
	 * 好评率
	 */
	private double commentRate;

}
