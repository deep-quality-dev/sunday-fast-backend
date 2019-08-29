package io.renren.modules.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.AddressEntity;
import io.renren.modules.app.entity.RegionEntity;
import io.renren.modules.app.service.AddressService;
import io.renren.modules.app.service.RegionService;
import io.renren.modules.app.vo.AddressVo;
import io.renren.modules.app.vo.RegionVo;

@RestController
@RequestMapping("app/address")
public class APIAddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private RegionService regionService;

    /**
     * 添加地址
     *
     * @return
     */
    @Login
    @PostMapping
    public R addAddress(@RequestBody AddressEntity addressEntity, @RequestAttribute("userId") Integer userId) {
        addressService.addUserAddress(addressEntity, userId);
        return R.ok();
    }

    /**
     * 修改地址
     *
     * @return
     */
    @Login
    @PutMapping
    public R updAddress(@RequestBody AddressEntity addressEntity, @RequestAttribute("userId") Integer userId) {
        addressService.updUserAddress(addressEntity, userId);
        return R.ok();
    }

    /**
     * 用户收获地址列表
     *
     * @return
     */
    @Login
    @GetMapping("/list")
    public R list(@RequestAttribute("userId") Integer userId) {
        List<AddressEntity> addressEntityList = addressService.userAddessList(userId);
        return R.ok().put("data", addressEntityList);
    }

    @Login
    @GetMapping("/{id}")
    public R detail(@PathVariable Integer id, @RequestAttribute("userId") Integer userId) {
        AddressEntity addressEntity = addressService.getById(id);
        if (null == addressEntity || addressEntity.getUid().intValue() != userId) {
            throw new RRException("参数错误");
        }
        AddressVo detail = new AddressVo();
        BeanUtil.copyProperties(addressEntity, detail);
        RegionVo regionVo = null;
        RegionEntity regionEntity = null;
        if (null != addressEntity.getProvinceId()) {
            regionEntity = regionService.getById(addressEntity.getProvinceId());
            if (null != regionEntity) {
                regionVo = new RegionVo();
                BeanUtil.copyProperties(regionEntity, regionVo);
                detail.setProvince(regionVo);
            }
        }
        if (null != addressEntity.getCityId()) {
            regionEntity = regionService.getById(addressEntity.getCityId());
            if (null != regionEntity) {
                regionVo = new RegionVo();
                BeanUtil.copyProperties(regionEntity, regionVo);
                detail.setCity(regionVo);
            }
        }
        if (null != addressEntity.getAreaId()) {
            regionEntity = regionService.getById(addressEntity.getAreaId());
            if (null != regionEntity) {
                regionVo = new RegionVo();
                BeanUtil.copyProperties(regionEntity, regionVo);
                detail.setArea(regionVo);
            }
        }
        return R.ok().put("data", detail);
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @param userId
     * @return
     */
    @Login
    @DeleteMapping("/{id}")
    public R del(@PathVariable Integer id, @RequestAttribute("userId") Integer userId) {
        addressService.delAddress(id, userId);
        return R.ok();
    }
}
