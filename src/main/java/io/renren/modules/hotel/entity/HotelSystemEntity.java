package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:32
 */
@Data
@TableName("t_hotel_system")
public class HotelSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * appid
	 */
	private String appid;
	/**
	 * appsecret
	 */
	private String appsecret;
	/**
	 * 商户号
	 */
	private String mchid;
	/**
	 * 商户秘钥
	 */
	private String wxkey;
	/**
	 * 
	 */
	private String uniacid;
	/**
	 * 积分规则
	 */
	private String jfRule;
	/**
	 * 版权名称
	 */
	private String bqName;
	/**
	 * 网站名称
	 */
	private String linkName;
	/**
	 * 网站logo
	 */
	private String linkLogo;
	/**
	 * 技术支持
	 */
	private String support;
	/**
	 * 
	 */
	private String bqLogo;
	/**
	 * 
	 */
	private String color;
	/**
	 * 
	 */
	private String tzAppid;
	/**
	 * 
	 */
	private String tzName;
	/**
	 * 平台名称
	 */
	private String ptName;
	/**
	 * 平台电话
	 */
	private String tel;
	/**
	 * 访问量
	 */
	private Integer totalNum;
	/**
	 * 短信appkey
	 */
	private String appkey;
	/**
	 * 短信模板id
	 */
	private String tplId;
	/**
	 * 默认门店ID
	 */
	private Integer sellerId;
	/**
	 * 证书
	 */
	private String apiclientCert;
	/**
	 * 证书
	 */
	private String apiclientKey;
	/**
	 * 最低提现金额
	 */
	private BigDecimal zdMoney;
	/**
	 * 提现手续费
	 */
	private String txSxf;
	/**
	 * 认筹条款
	 */
	private String rcTk;
	/**
	 * 报名成功通知模板id
	 */
	private String tid1;
	/**
	 * 提现须知
	 */
	private String txNotice;
	/**
	 * 风格设置,1单店,2多店
	 */
	private Integer type;
	/**
	 * 1手动打款,2自动打款
	 */
	private Integer txMode;
	/**
	 * 商家入住1开通,2不开通
	 */
	private Integer isSjrz;
	/**
	 * IP地址
	 */
	private String clientIp;
	/**
	 * 认证须知
	 */
	private String rzNotice;
	/**
	 * 会员规则
	 */
	private String hyRule;
	/**
	 * 首页背景logo
	 */
	private String bjLogo;
	/**
	 * 地图key
	 */
	private String mapKey;
	/**
	 * 短信验证1开启,2关闭
	 */
	private Integer isDxyz;
	/**
	 * 评论积分
	 */
	private Integer plScore;
	/**
	 * 消费积分
	 */
	private Integer xfScore;
	/**
	 * 
	 */
	private String hyImg;
	/**
	 * 
	 */
	private String rzTid;
	/**
	 * 
	 */
	private Integer openMember;
	/**
	 * 
	 */
	private String jjrzTid;
	/**
	 * 
	 */
	private Integer isSfz;
	/**
	 * 
	 */
	private String tplId2;
	/**
	 * 
	 */
	private Integer isOrder;
	/**
	 * 
	 */
	private String tid3;
	/**
	 * 
	 */
	private String tid4;

}
