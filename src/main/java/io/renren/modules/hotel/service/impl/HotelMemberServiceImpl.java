package io.renren.modules.hotel.service.impl;

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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
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
		Object name = null == params.get("name") ? null : StrUtil.trimToNull(params.get("name").toString());
		Object tel = null == params.get("tel") ? null : StrUtil.trimToNull(params.get("tel").toString());
		Object identityNo = null == params.get("identityNo") ? null : StrUtil.trimToNull(params.get("identityNo").toString());
		IPage<HotelMemberEntity> page = this.page(new Query<HotelMemberEntity>().getPage(params), new QueryWrapper<HotelMemberEntity>().like(name != null, "name", name).like(tel != null, "tel", tel).like(identityNo != null, "identity_no", identityNo));
		return new PageUtils(page);
	}

	@Override
	public MemberVo userInfo(Long userId) {
		log.info("??????????????????--start,userId:{}", userId);
		MemberVo memberVo = new MemberVo();
		HotelMemberEntity hotelMemberEntity = this.getById(userId);
		memberVo.setHeadImgUrl(hotelMemberEntity.getImg());
		memberVo.setZsName(hotelMemberEntity.getZsName());
		memberVo.setNickName(hotelMemberEntity.getName());
		memberVo.setBirthday(hotelMemberEntity.getBirthday());
		memberVo.setLevel("1");
		memberVo.setMobile(hotelMemberEntity.getTel());
		memberVo.setGender(hotelMemberEntity.getGender());
		memberVo.setAuthFlag(StrUtil.isEmpty(hotelMemberEntity.getIdentityNo()) ? 0 : 1);
		memberVo.setPayPwdFlag(StrUtil.isEmpty(hotelMemberEntity.getPayPwd()) ? 0 : 1);
		if (StrUtil.isNotEmpty(hotelMemberEntity.getIdentityNo())) {
			memberVo.setIdentityNo(hotelMemberEntity.getIdentityNo());
		}
		log.debug("??????????????????--end???result:{}", JSON.toJSONString(memberVo));
		return memberVo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HotelMemberEntity wxLogin(WxMpUser user, Long sellerId) {
		log.info("????????????--start,sellerId:{},params:{}", sellerId, JSON.toJSONString(user));
		HotelMemberEntity hotelMemberEntity = null;
		hotelMemberEntity = this.getOne(new QueryWrapper<HotelMemberEntity>().eq("seller_id", sellerId).eq("openid", user.getOpenId()));
		if (null == hotelMemberEntity) {
			log.info("?????????????????????????????????");
			hotelMemberEntity = new HotelMemberEntity();
			hotelMemberEntity.setOpenid(user.getOpenId());
			hotelMemberEntity.setSellerId(sellerId);
			hotelMemberEntity.setImg(user.getHeadImgUrl());
			hotelMemberEntity.setJoinTime(DateUtil.date());
			hotelMemberEntity.setName(user.getNickname());
			hotelMemberEntity.setGender(null != user.getSex() ? user.getSex().toString() : "0");

			this.save(hotelMemberEntity);
			// ??????????????????

			// ???????????????
		} else {
			hotelMemberEntity.setImg(user.getHeadImgUrl());
			hotelMemberEntity.setName(user.getNickname());
			this.updateById(hotelMemberEntity);
			log.info("????????????");
		}
		log.info("????????????--end,userId:{}", hotelMemberEntity.getId());
		return hotelMemberEntity;
	}

	@Override
	@Transactional
	public void updateUserScore(Long userId, String score) {
		log.info("??????????????????--end");
		return;
//		log.info("??????????????????--start,userId:{},score:{}", userId, score);
//		HotelMemberEntity memberEntity = this.getById(userId);
//		BigDecimal scoreCost = NumberUtil.add(null == memberEntity.getScore() ? "0.0" : memberEntity.getScore().toString(), String.valueOf(score));
//		memberEntity.setScore(scoreCost.intValue());
//		this.updateById(memberEntity);
		// TODO ????????????????????????
	}

	@Override
	public void sendSmsCode(String mobile) {
		long startTime = System.currentTimeMillis();
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode != null) {
			log.error("??????:{}??????????????????{}", mobile, tempCode);
			throw new RRException("????????????????????????????????????");
		}
		String code = RandomUtil.randomNumbers(4);
		JSONObject contextJson = new JSONObject();
		contextJson.put("code", code);
		log.info("?????????????????????????????? -> ?????????:{} -> ????????????{}", mobile, code);
		// TODO ??????????????????MQ??????
		MobileMsgTemplate mobileMsgTemplate = new MobileMsgTemplate(mobile, contextJson.toJSONString(), CommonConstant.ALIYUN_SMS, EnumSmsChannelTemplate.VALIDATE_CODE.getSignName(), EnumSmsChannelTemplate.VALIDATE_CODE.getTemplate());
		redisTemplate.opsForValue().set(CommonConstant.DEFAULT_CODE_KEY + mobile, code, CommonConstant.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
		String channel = mobileMsgTemplate.getChannel();
		SmsMessageHandler messageHandler = messageHandlerMap.get(channel);
		if (messageHandler == null) {
			log.error("??????????????????????????????????????????????????????????????????");
			return;
		}
		messageHandler.execute(mobileMsgTemplate);
		long useTime = System.currentTimeMillis() - startTime;
		log.info("?????? {} ????????????????????????????????? {}??????", mobileMsgTemplate.getType(), useTime);
	}

	@Override
	@Transactional
	public void bindSms(Long sellerId, Long userId, String mobile, String vcode) {
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode == null) {
			log.error("??????????????????,userId:{},mobile:{}", userId, mobile);
			throw new RRException("????????????????????????????????????");
		}
		if (!tempCode.toString().contentEquals(vcode)) {
			throw new RRException("??????????????????");
		}
		HotelMemberEntity hotelMemberEntity = this.getById(userId);
		if (StrUtil.isBlank(hotelMemberEntity.getTel())) {
			hotelMemberEntity.setTel(mobile);
			this.updateById(hotelMemberEntity);
		}
		log.info("????????????????????????");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HotelMemberEntity wxMaLogin(WxMaUserInfo userInfo) {
		log.info("????????????--start,params:{}", JSON.toJSONString(userInfo));
		HotelMemberEntity hotelMemberEntity = null;
		hotelMemberEntity = this.getOne(new QueryWrapper<HotelMemberEntity>().eq("openid", userInfo.getOpenId()));
		if (null == hotelMemberEntity) {
			hotelMemberEntity = new HotelMemberEntity();
			hotelMemberEntity.setOpenid(userInfo.getOpenId());
			hotelMemberEntity.setLevelId(1L);
			hotelMemberEntity.setImg(userInfo.getAvatarUrl());
			hotelMemberEntity.setJoinTime(DateUtil.date());
			hotelMemberEntity.setName(userInfo.getNickName());
			this.save(hotelMemberEntity);
		}
		return hotelMemberEntity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bindWxPhone(Long userId, WxMaPhoneNumberInfo phoneNoInfo) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		if (null == hotelMemberEntity) {
			throw new RRException("?????????????????????");
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
		if (StrUtil.isNotEmpty(userInfo.getGender())) {
			hotelMemberEntity.setGender(userInfo.getGender());
		}
		baseMapper.updateById(hotelMemberEntity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setPayPwd(Long userId, String pwd, String mobile, String vcode) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode == null || !vcode.equals(tempCode.toString())) {
			throw new RRException("???????????????");
		}
		hotelMemberEntity.setPayPwd(SecureUtil.md5(pwd));
		baseMapper.updateById(hotelMemberEntity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePayPwd(Long userId, String oldPwd, String newPwd) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		if (!hotelMemberEntity.getPayPwd().equals(SecureUtil.md5(oldPwd))) {
			throw new RRException("??????????????????");
		}
		if (StrUtil.isEmpty(newPwd)) {
			throw new RRException("??????????????????");
		}
		hotelMemberEntity.setPayPwd(SecureUtil.md5(newPwd));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void forgetPayPwd(Long userId, String pwd, String mobile, String vcode) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode == null || vcode.equals(tempCode.toString())) {
			throw new RRException("???????????????");
		}
		hotelMemberEntity.setPayPwd(SecureUtil.md5(pwd));
		baseMapper.updateById(hotelMemberEntity);
	}

	@Override
	public void checkPayPwd(Long userId, String pwd) {
		HotelMemberEntity hotelMemberEntity = baseMapper.selectById(userId);
		if (!hotelMemberEntity.getPayPwd().equals(SecureUtil.md5(pwd))) {
			throw new RRException("???????????????");
		}
	}

}