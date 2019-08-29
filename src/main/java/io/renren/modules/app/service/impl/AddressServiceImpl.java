package io.renren.modules.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.AddressDao;
import io.renren.modules.app.entity.AddressEntity;
import io.renren.modules.app.service.AddressService;

@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressEntity> page = this.page(new Query<AddressEntity>().getPage(params), new QueryWrapper<AddressEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<AddressEntity> userAddessList(Integer userId) {
        return baseMapper.selectList(Wrappers.<AddressEntity>lambdaQuery().eq(AddressEntity::getUid, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserAddress(AddressEntity addressEntity, Integer userId) {
        checkParams(addressEntity);
        addressEntity.setUid(userId);
        if (addressEntity.getIsDefault() == 1) {
            AddressEntity defaultAddress = baseMapper.selectOne(Wrappers.<AddressEntity>lambdaQuery().eq(AddressEntity::getUid, userId).eq(AddressEntity::getIsDefault, 1));
            if (null != defaultAddress) {
                defaultAddress.setIsDefault(0);
                baseMapper.updateById(defaultAddress);
            }
        }
        baseMapper.insert(addressEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAddress(Integer id, Integer userId) {
        AddressEntity addressEntity = baseMapper.selectById(id);
        if (null == addressEntity) {
            throw new RRException("参数错误");
        }
        if (addressEntity.getUid().intValue() != userId.intValue()) {
            throw new RRException("非法操作");
        }
        baseMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updUserAddress(AddressEntity addressEntity, Integer userId) {
        checkParams(addressEntity);
        AddressEntity dbAddressEntity = baseMapper.selectById(addressEntity.getId());
        if (addressEntity.getIsDefault() == 1) {
            AddressEntity defaultAddress = baseMapper.selectOne(Wrappers.<AddressEntity>lambdaQuery().eq(AddressEntity::getUid, userId).eq(AddressEntity::getIsDefault, 1));
            if (null != defaultAddress) {
                defaultAddress.setIsDefault(0);
                baseMapper.updateById(defaultAddress);
            }
        }
        BeanUtil.copyProperties(addressEntity, dbAddressEntity);
        baseMapper.updateById(dbAddressEntity);
    }

    private boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return false;
            }
            return isMatch;
        }
    }

    private void checkParams(AddressEntity addressEntity) {
        if (null == addressEntity) {
            throw new RRException("参数不能为空");
        }
        if (StrUtil.isEmpty(addressEntity.getConsignee())) {
            throw new RRException("收货人不能为空");
        }
        if (StrUtil.isEmpty(addressEntity.getAddress())) {
            throw new RRException("收货地址");
        }
        if (StrUtil.isEmpty(addressEntity.getMobile())) {
            throw new RRException("联系电话不能为空");
        }
        if (!isPhone(addressEntity.getMobile())) {
            throw new RRException("手机号码不合法");
        }
    }
}