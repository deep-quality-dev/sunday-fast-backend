package io.renren.modules.hotel.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.OrderTypeConstants;
import io.renren.modules.constants.PayMethodConstants;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.config.WxPayConfiguration;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelMemberLevelPayDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelPayEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.form.BecomeVipForm;
import io.renren.modules.hotel.form.BindVipCardForm;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMemberLevelService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelWxConfigService;
import io.renren.modules.hotel.vo.VipCardItemVo;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.wx.OrderType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelMemberLevelService")
public class HotelMemberLevelServiceImpl extends ServiceImpl<HotelMemberLevelDao, HotelMemberLevelEntity> implements HotelMemberLevelService {

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@Autowired
	private HotelMemberLevelService hotelMemberLevelService;

	@Autowired
	private HotelSellerService hotelSellerService;

	@Autowired
	private HotelMemberLevelPayDao hotelMemberLevelPayDao;

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private HotelSellerDao hotelSellerDao;

	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	@Autowired
	private HotelScoreService hotelScoreService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelMemberLevelEntity> page = this.page(new Query<HotelMemberLevelEntity>().getPage(params), new QueryWrapper<HotelMemberLevelEntity>().eq(sellerId != null, "seller_id", sellerId));

		return new PageUtils(page);
	}

	@Override
	public void bindCard(Long userId, BindVipCardForm vipCardForm) {
		// TODO 查询酒店系统是否有会员

	}

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public WxPayMpOrderResult becomeVip(Long userId, BecomeVipForm becomeVipForm) {
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelMemberLevelEntity hotelMemberLevelEntity = hotelMemberLevelService.getById(becomeVipForm.getLevelId());
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, hotelMemberLevelEntity.getSellerId()).eq(HotelMemberLevelDetailEntity::getStatus, 1));
		HotelSellerEntity hotelSellerEntity = hotelSellerDao.selectById(hotelMemberLevelEntity.getSellerId());
		File qrCode = null;
		int flag = 1; // 1 开卡，2 升级
		if (null != hotelMemberLevelDetailEntity) {
			log.error("会员办卡--卡片变更，userId:{},parms:{}", userId, JSON.toJSONString(becomeVipForm));
			hotelMemberLevelDetailEntity.setLevelId(becomeVipForm.getLevelId());
			flag = 2;
			if (1 != hotelMemberLevelEntity.getPayFlag()) {
				hotelMemberLevelDetailService.updateById(hotelMemberLevelDetailEntity);
			}
		} else {
			hotelMemberLevelDetailEntity = new HotelMemberLevelDetailEntity();
			BeanUtil.copyProperties(becomeVipForm, hotelMemberLevelDetailEntity);
			hotelMemberLevelDetailEntity.setMemberId(userId);
			hotelMemberLevelDetailEntity.setStatus(1);
			hotelMemberLevelDetailEntity.setCardNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS") + userId);
			hotelMemberLevelDetailEntity.setSellerId(hotelMemberLevelEntity.getSellerId());
			hotelMemberLevelDetailEntity.setMobile(hotelMemberEntity.getTel());
			if (StrUtil.isNotEmpty(becomeVipForm.getMobile())) {
				hotelMemberLevelDetailEntity.setMobile(becomeVipForm.getMobile());
			}
			// 生成二维码
			JSONObject cardInfo = new JSONObject();
			cardInfo.put("sellerId", hotelMemberLevelDetailEntity.getSellerId());
			cardInfo.put("memberId", hotelMemberLevelDetailEntity.getMemberId());
			qrCode = QrCodeUtil.generate(cardInfo.toJSONString(), 300, 300, FileUtil.file(System.getProperty("java.io.tmpdir") + "/" + hotelMemberLevelDetailEntity.getCardNo() + ".jpg"));
			String url = OSSFactory.build().uploadSuffix(getBytes(qrCode.getPath()), ".jpg");
			hotelMemberLevelDetailEntity.setQrCode(url);
			if (1 != hotelMemberLevelEntity.getPayFlag()) {
				hotelMemberLevelDetailService.save(hotelMemberLevelDetailEntity);
			}
		}
		if (null != qrCode) {
			FileUtil.del(qrCode);
		}
		// 免费办理
		if (1 != hotelMemberLevelEntity.getPayFlag()) {
			// 发送模板消息
			sendBecomeVipMsg(becomeVipForm.getFormId(), hotelMemberEntity, hotelMemberLevelEntity, hotelMemberLevelDetailEntity, hotelSellerEntity, flag);
			return null;
		}
		// 积分支付
		if (PayMethodConstants.INTEGRAL.equals(becomeVipForm.getPayMethod())) {
			// 扣除积分
			hotelMemberLevelDetailService.integralTransaction(hotelSellerEntity.getId(), userId, hotelMemberLevelEntity.getPayIntegral());
			hotelScoreService.transactionScore(hotelSellerEntity.getId(), userId, 2, hotelMemberLevelEntity.getPayIntegral().intValue(), hotelSellerEntity.getName() + "," + hotelMemberLevelEntity.getName());
			sendBecomeVipMsg(becomeVipForm.getFormId(), hotelMemberEntity, hotelMemberLevelEntity, hotelMemberLevelDetailEntity, hotelSellerEntity, flag);
			return null;
		}
		HotelMemberLevelPayEntity hotelMemberLevelPayEntity = new HotelMemberLevelPayEntity();
		hotelMemberLevelPayEntity.setOutTradeNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS"));
		hotelMemberLevelPayEntity.setLevelId(becomeVipForm.getLevelId());
		hotelMemberLevelPayEntity.setMemberId(userId);
		hotelMemberLevelPayEntity.setDetail(JSON.toJSONString(hotelMemberLevelDetailEntity));
		hotelMemberLevelPayEntity.setCreateTime(DateUtil.date());
		hotelMemberLevelPayDao.insert(hotelMemberLevelPayEntity);
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "(办理会员卡)");
		wxPayUnifiedOrderRequest.setOutTradeNo(hotelMemberLevelPayEntity.getOutTradeNo());
		wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
		wxPayUnifiedOrderRequest.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setTotalFee(1);
		wxPayUnifiedOrderRequest.setAttach(JSON.toJSONString(new OrderType(OrderTypeConstants.order_becomvip, becomeVipForm.getFormId())));
		wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
		WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
		log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
		return mpOrderResult;
	}

	public void sendBecomeVipMsg(String formId, HotelMemberEntity hotelMemberEntity, HotelMemberLevelEntity hotelMemberLevelEntity, HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity, HotelSellerEntity hotelSellerEntity, int flag) {
		// 发送办卡成功通知
		List<WxMaTemplateData> maTemplateDatas = new ArrayList<WxMaTemplateData>();
		maTemplateDatas.add(new WxMaTemplateData("first", "会员卡" + (flag == 1 ? "开通" : "升级") + "成功通知"));
		maTemplateDatas.add(new WxMaTemplateData("keyword1", hotelSellerEntity.getName() + "-" + hotelMemberLevelEntity.getName()));
		maTemplateDatas.add(new WxMaTemplateData("keyword2", hotelMemberLevelDetailEntity.getCardNo()));
		maTemplateDatas.add(new WxMaTemplateData("keyword3", DateUtil.format(hotelMemberLevelDetailEntity.getCreateDate(), "yyyy-MM-dd HH:mm:ss")));
		maTemplateDatas.add(new WxMaTemplateData("keyword4", hotelMemberLevelDetailEntity.getName()));
		maTemplateDatas.add(new WxMaTemplateData("keyword5", hotelMemberLevelDetailEntity.getMobile()));
		WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage(hotelMemberEntity.getOpenid(), "GNGr-p7XQZcUIol8KsnIxLNoXzyqN7BogHACy03gvos", null, formId, maTemplateDatas, null);
		try {
			HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
			WxMaConfiguration.getMaService(hotelWxConfigEntity.getAppId()).getMsgService().sendTemplateMsg(maTemplateMessage);
		} catch (Exception e) {
			throw new RRException("发送微信通知失败");
		}
	}

	@Override
	public int checkVipStatus(Long userId, Long sellerId) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		return hotelMemberLevelDetailEntity == null ? -1 : 1;
	}

	@Override
	public VipCardItemVo vipCardInfo(Long userId, Long sellerId) {
		VipCardItemVo cardInfoVo = new VipCardItemVo();
		cardInfoVo = baseMapper.userCardDetailById(userId, sellerId);
		return cardInfoVo;
	}

	@Override
	public List<VipCardItemVo> vipCardList(Long userId, Long sellerId) {
		List<VipCardItemVo> cardItemVos = new ArrayList<VipCardItemVo>();
		Long levelId = null;
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getStatus, 1));
		if (null != hotelMemberLevelDetailEntity) {
			levelId = hotelMemberLevelDetailEntity.getLevelId();
		}
		cardItemVos = baseMapper.seletSellerVipsList(levelId, sellerId);
		return cardItemVos;
	}

	@Override
	public List<VipCardItemVo> userCardlist(Long userId) {
		return baseMapper.userCardList(userId);
	}

	@Override
	public BecomeVipForm getSellerCardInfo(Long userId, Long sellerId) {
		BecomeVipForm becomeVipForm = new BecomeVipForm();
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		if (null == hotelMemberLevelDetailEntity) {
			return null;
		}
		becomeVipForm.setCertificate(hotelMemberLevelDetailEntity.getCertificate());
		becomeVipForm.setCertificateNo(hotelMemberLevelDetailEntity.getCertificateNo());
		becomeVipForm.setName(hotelMemberLevelDetailEntity.getName());
		becomeVipForm.setLevelId(hotelMemberLevelDetailEntity.getLevelId());
		return becomeVipForm;
	}

	private byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public void becomeVipCallBack(String outTradeNo, String formId) {
		HotelMemberLevelPayEntity hotelMemberLevelPayEntity = hotelMemberLevelPayDao.selectOne(Wrappers.<HotelMemberLevelPayEntity>lambdaQuery().eq(HotelMemberLevelPayEntity::getOutTradeNo, outTradeNo).eq(HotelMemberLevelPayEntity::getStatus, 0));
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = JSON.parseObject(hotelMemberLevelPayEntity.getDetail(), HotelMemberLevelDetailEntity.class);
		int flag = 1;
		if (null == hotelMemberLevelDetailEntity.getId() || 0 == hotelMemberLevelDetailEntity.getId()) {
			hotelMemberLevelDetailService.save(hotelMemberLevelDetailEntity);
		} else {
			hotelMemberLevelDetailService.updateById(hotelMemberLevelDetailEntity);
			flag = 2;
		}
		HotelMemberLevelEntity hotelMemberLevelEntity = baseMapper.selectById(hotelMemberLevelDetailEntity.getLevelId());
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelMemberLevelDetailEntity.getSellerId());
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(hotelMemberLevelDetailEntity.getMemberId());
		// 发送办卡成功通知
		sendBecomeVipMsg(formId, hotelMemberEntity, hotelMemberLevelEntity, hotelMemberLevelDetailEntity, hotelSellerEntity, flag);
	}

	@Override
	public String cardRule(Long cardId) {
		HotelMemberLevelEntity hotelMemberLevelEntity = baseMapper.selectById(cardId);
		return hotelMemberLevelEntity.getRule();
	}

}