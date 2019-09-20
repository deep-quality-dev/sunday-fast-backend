package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.CommonConstant;
import io.renren.modules.hotel.dao.HotelMemberDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.enums.EnumSmsChannelTemplate;
import io.renren.modules.hotel.handler.message.SmsMessageHandler;
import io.renren.modules.hotel.handler.message.template.MobileMsgTemplate;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Slf4j
@Service("hotelMemberService")
public class HotelMemberServiceImpl extends ServiceImpl<HotelMemberDao, HotelMemberEntity> implements HotelMemberService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private Map<String, SmsMessageHandler> messageHandlerMap;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object name = params.get("name");
		IPage<HotelMemberEntity> page = this.page(new Query<HotelMemberEntity>().getPage(params), new QueryWrapper<HotelMemberEntity>().like(name != null, "name", name));
		return new PageUtils(page);
	}

	@Override
	public MemberVo userInfo(Long userId) {
		log.info("获取用户信息--start,userId:{}", userId);
		MemberVo memberVo = new MemberVo();
		HotelMemberEntity hotelMemberEntity = this.getById(userId);
		memberVo.setHeadImgUrl(hotelMemberEntity.getImg());
		memberVo.setZsName(hotelMemberEntity.getZsName());
		memberVo.setNickName(hotelMemberEntity.getName());
		memberVo.setBirthday(hotelMemberEntity.getBirthday());
		memberVo.setGender(hotelMemberEntity.getGender());
		memberVo.setAuthFlag(StrUtil.isEmpty(hotelMemberEntity.getIdentityNo()) ? 0 : 1);
		if (StrUtil.isNotEmpty(hotelMemberEntity.getIdentityNo())) {
			memberVo.setIdentityNo(hotelMemberEntity.getIdentityNo().replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2"));
		}
		log.info("获取用户信息--end，result:{}", JSON.toJSONString(memberVo));
		return memberVo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HotelMemberEntity wxLogin(WxMpUser user, Long sellerId) {
		log.info("微信登陆--start,sellerId:{},params:{}", sellerId, JSON.toJSONString(user));
		HotelMemberEntity hotelMemberEntity = null;
		hotelMemberEntity = this.getOne(new QueryWrapper<HotelMemberEntity>().eq("seller_id", sellerId).eq("openid", user.getOpenId()));
		if (null == hotelMemberEntity) {
			log.info("用户不存在，创建新用户");
			hotelMemberEntity = new HotelMemberEntity();
			hotelMemberEntity.setOpenid(user.getOpenId());
			hotelMemberEntity.setSellerId(sellerId);
			hotelMemberEntity.setImg(user.getHeadImgUrl());
			hotelMemberEntity.setJoinTime(DateUtil.date());
			hotelMemberEntity.setName(user.getNickname());
			this.save(hotelMemberEntity);
			// 增加会员积分

			// 赠送优惠券
		} else {
			hotelMemberEntity.setImg(user.getHeadImgUrl());
			hotelMemberEntity.setName(user.getNickname());
			this.updateById(hotelMemberEntity);
			log.info("更新用户");
		}
		log.info("微信登陆--end,userId:{}", hotelMemberEntity.getId());
		return hotelMemberEntity;
	}

	@Override
	@Transactional
	public void updateUserScore(Long userId, String score) {
		log.info("更新用户积分--end");
		return;
//		log.info("更新用户积分--start,userId:{},score:{}", userId, score);
//		HotelMemberEntity memberEntity = this.getById(userId);
//		BigDecimal scoreCost = NumberUtil.add(null == memberEntity.getScore() ? "0.0" : memberEntity.getScore().toString(), String.valueOf(score));
//		memberEntity.setScore(scoreCost.intValue());
//		this.updateById(memberEntity);
		// TODO 下发积分变动通知
	}

	@Override
	public void sendSmsCode(String mobile) {
		long startTime = System.currentTimeMillis();
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode != null) {
			log.error("用户:{}验证码未失效{}", mobile, tempCode);
			throw new RRException("验证码未失效，请稍后再试");
		}
		String code = RandomUtil.randomNumbers(4);
		JSONObject contextJson = new JSONObject();
		contextJson.put("code", code);
		contextJson.put("product", "Pig4Cloud");
		log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
		// TODO 组装数据采用MQ发送
		MobileMsgTemplate mobileMsgTemplate = new MobileMsgTemplate(mobile, contextJson.toJSONString(), CommonConstant.ALIYUN_SMS, EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getSignName(), EnumSmsChannelTemplate.LOGIN_NAME_LOGIN.getTemplate());
		redisTemplate.opsForValue().set(CommonConstant.DEFAULT_CODE_KEY + mobile, code, CommonConstant.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
		String channel = mobileMsgTemplate.getChannel();
		SmsMessageHandler messageHandler = messageHandlerMap.get(channel);
		if (messageHandler == null) {
			log.error("没有找到指定的路由通道，不进行发送处理完毕！");
			return;
		}
		messageHandler.execute(mobileMsgTemplate);
		long useTime = System.currentTimeMillis() - startTime;
		log.info("调用 {} 短信网关处理完毕，耗时 {}毫秒", mobileMsgTemplate.getType(), useTime);
	}

	@Override
	@Transactional
	public void bindSms(Long sellerId, Long userId, String mobile, String vcode) {
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode == null) {
			log.error("验证码不存在,userId:{},mobile:{}", userId, mobile);
			throw new RRException("验证码未失效，请稍后再试");
		}
		if (!tempCode.toString().contentEquals(vcode)) {
			throw new RRException("验证码不正确");
		}
		HotelMemberEntity hotelMemberEntity = this.getById(userId);
		if (StrUtil.isBlank(hotelMemberEntity.getTel())) {
			hotelMemberEntity.setTel(mobile);
			this.updateById(hotelMemberEntity);
		}
		log.info("用户绑定手机成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HotelMemberEntity wxMaLogin(WxMaUserInfo userInfo) {
		log.info("微信登陆--start,params:{}", JSON.toJSONString(userInfo));
		HotelMemberEntity hotelMemberEntity = null;
		hotelMemberEntity = this.getOne(new QueryWrapper<HotelMemberEntity>().eq("openid", userInfo.getOpenId()));
		if (null == hotelMemberEntity) {
			hotelMemberEntity = new HotelMemberEntity();
			hotelMemberEntity.setOpenid(userInfo.getOpenId());
			hotelMemberEntity.setImg(userInfo.getAvatarUrl());
			hotelMemberEntity.setJoinTime(DateUtil.date());
			hotelMemberEntity.setName(userInfo.getNickName());
			this.save(hotelMemberEntity);
		}
		return hotelMemberEntity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bindWxPhone(String openId, WxMaPhoneNumberInfo phoneNoInfo) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectOne(Wrappers.<HotelMemberEntity>lambdaQuery().eq(HotelMemberEntity::getOpenid, openId));
		if (null == hotelMemberEntity) {
			throw new RRException("用户信息未找到");
		}
		hotelMemberEntity.setTel(phoneNoInfo.getPhoneNumber());
		baseMapper.updateById(hotelMemberEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void autonym(Long userId, String relaName, String identityNo) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		hotelMemberEntity.setZsName(relaName);
		hotelMemberEntity.setIdentityNo(identityNo);
		baseMapper.updateById(hotelMemberEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserInfo(Long userId, MemberVo userInfo) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		if (StrUtil.isNotEmpty(userInfo.getBirthday())) {
			hotelMemberEntity.setBirthday(userInfo.getBirthday());
		}
		if (StrUtil.isNotEmpty(userInfo.getNickName())) {
			hotelMemberEntity.setName(userInfo.getNickName());
		}
		if (StrUtil.isNotEmpty(userInfo.getMobile())) {
			hotelMemberEntity.setTel(userInfo.getMobile());
		}
		baseMapper.updateById(hotelMemberEntity);

	}

}