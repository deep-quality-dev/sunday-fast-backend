package io.renren.modules.hotel.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelSellerEmployeeDao;
import io.renren.modules.hotel.entity.HotelSellerEmployeeEntity;
import io.renren.modules.hotel.service.HotelSellerEmployeeService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;

@Service("hotelSellerEmployeeService")
public class HotelSellerEmployeeServiceImpl extends ServiceImpl<HotelSellerEmployeeDao, HotelSellerEmployeeEntity> implements HotelSellerEmployeeService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelSellerEmployeeEntity> page = this.page(new Query<HotelSellerEmployeeEntity>().getPage(params), new QueryWrapper<HotelSellerEmployeeEntity>().eq("seller_id", params.get("seller_id")));

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveEmployee(HotelSellerEmployeeEntity hotelSellerEmployee, Long userId) {
		// 创建系统用户
		SysUserEntity user = new SysUserEntity();
		user.setCreateTime(new Date());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(hotelSellerEmployee.getPassword(), salt).toHex());
		user.setSalt(salt);
		user.setUsername(hotelSellerEmployee.getUserName());
		user.setRoleIdList(Arrays.asList(Long.valueOf(String.valueOf(Constant.SELLER_EMPLOYEE_ROLE))));
		user.setEmail(hotelSellerEmployee.getEmail());
		user.setMobile(hotelSellerEmployee.getMobile());
		user.setCreateUserId(userId);
		user.setStatus(1);
		sysUserService.save(user);
		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		hotelSellerEmployee.setUserId(user.getUserId());
		hotelSellerEmployee.setCreateTime(DateUtil.date());
		baseMapper.insert(hotelSellerEmployee);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateEmployee(HotelSellerEmployeeEntity hotelSellerEmployee) {
		// 创建系统用户
		SysUserEntity user = sysUserService.getById(hotelSellerEmployee.getUserId());
		user.setCreateTime(new Date());
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(hotelSellerEmployee.getPassword(), salt).toHex());
		user.setSalt(salt);
		user.setUsername(hotelSellerEmployee.getUserName());
		user.setRoleIdList(Arrays.asList(Long.valueOf(String.valueOf(Constant.SELLER_EMPLOYEE_ROLE))));
		user.setEmail(hotelSellerEmployee.getEmail());
		user.setMobile(hotelSellerEmployee.getMobile());
		sysUserService.updateById(user);
		baseMapper.updateById(hotelSellerEmployee);

	}

}