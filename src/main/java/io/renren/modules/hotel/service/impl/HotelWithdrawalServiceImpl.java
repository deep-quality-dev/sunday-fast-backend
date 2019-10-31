package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelWithdrawalDao;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelSystemEntity;
import io.renren.modules.hotel.entity.HotelWithdrawalEntity;
import io.renren.modules.hotel.form.WithdrawalApplyForm;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelSystemService;
import io.renren.modules.hotel.service.HotelWithdrawalService;

@Service("hotelWithdrawalService")
public class HotelWithdrawalServiceImpl extends ServiceImpl<HotelWithdrawalDao, HotelWithdrawalEntity> implements HotelWithdrawalService {

	@Autowired
	private HotelSystemService hotelSystemService;

	@Autowired
	private HotelSellerService hotelSellerService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerName = params.get("sellerName");
		IPage<HotelWithdrawalEntity> page = this.page(new Query<HotelWithdrawalEntity>().getPage(params), new QueryWrapper<HotelWithdrawalEntity>().eq(null != params.get("seller_id"), "seller_id", params.get("seller_id")).like(null != sellerName, "name", sellerName));

		return new PageUtils(page);
	}

	@Override
	public Map<String, Object> withdrawalApplyData(Long sellerId) {
		Map<String, Object> datas = new HashMap<String, Object>();
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(sellerId);
		datas.put("amount", hotelSellerEntity.getBalance());
		// 系统最低提现金额
		datas.put("system", "0.00");
		datas.put("rate", "0.00");
		HotelSystemEntity hotelSystemEntity = hotelSystemService.getOne(Wrappers.lambdaQuery());
		if (null != hotelSystemEntity) {
			datas.put("system", hotelSystemEntity.getZdMoney());
			datas.put("rate", hotelSystemEntity.getTxSxf());
		}

		return datas;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void withdrawalApply(Long sellerId, WithdrawalApplyForm withdrawalApplyForm) {
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(sellerId);
		BigDecimal canWitdhDrawal = hotelSellerEntity.getBalance();
		if (NumberUtil.compare(withdrawalApplyForm.getAmount().doubleValue(), canWitdhDrawal.doubleValue()) == 1) {
			throw new RRException("提现金额大于可提现金额");
		}
		HotelSystemEntity hotelSystemEntity = hotelSystemService.getOne(Wrappers.lambdaQuery());
		if (null != hotelSystemEntity) {
			if (NumberUtil.compare(hotelSystemEntity.getZdMoney().doubleValue(), withdrawalApplyForm.getAmount().doubleValue()) == 1) {
				throw new RRException("提现金额不能低于系统提现金额：" + hotelSystemEntity.getZdMoney().doubleValue());
			}
		}
		HotelWithdrawalEntity hotelWithdrawalEntity = new HotelWithdrawalEntity();
		hotelWithdrawalEntity.setEnabled(1);
		hotelWithdrawalEntity.setName(hotelSellerEntity.getLinkName() + "-" + hotelSellerEntity.getName());
		hotelWithdrawalEntity.setSellerId(sellerId);
		hotelWithdrawalEntity.setState(1);
		hotelWithdrawalEntity.setType(withdrawalApplyForm.getAccountType());
		hotelWithdrawalEntity.setTime(DateUtil.now());
		hotelWithdrawalEntity.setUsername(withdrawalApplyForm.getAccount());
		hotelWithdrawalEntity.setWithdrawCost(withdrawalApplyForm.getAmount()); // 实际金额
		hotelWithdrawalEntity.setRealityCost(withdrawalApplyForm.getAmount());
		// 计算手续费
		if (null != hotelSystemEntity) {
			BigDecimal realityCost = NumberUtil.sub(withdrawalApplyForm.getAmount(), NumberUtil.div(withdrawalApplyForm.getAmount(), new BigDecimal(hotelSystemEntity.getTxSxf())));
			hotelWithdrawalEntity.setRealityCost(realityCost);
			if (null != hotelSystemEntity) {
				hotelWithdrawalEntity.setRealityCost(NumberUtil.mul(withdrawalApplyForm.getAmount(), NumberUtil.sub(1, new BigDecimal(hotelSystemEntity.getTxSxf()))));
			}
		}
		hotelWithdrawalEntity.setRate(new BigDecimal(hotelSystemEntity.getTxSxf()).floatValue());
		baseMapper.insert(hotelWithdrawalEntity);
		hotelSellerEntity.setBalance(NumberUtil.sub(hotelSellerEntity.getBalance(), withdrawalApplyForm.getAmount()));
		hotelSellerService.updateById(hotelSellerEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void withdrawalApplyAudit(Long id) {
		HotelWithdrawalEntity hotelWithdrawalEntity = baseMapper.selectById(id);
		hotelWithdrawalEntity.setState(2);
		hotelWithdrawalEntity.setAuditTime(DateUtil.now());
		baseMapper.updateById(hotelWithdrawalEntity);
	}

}