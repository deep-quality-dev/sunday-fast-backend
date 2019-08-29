package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.AddressEntity;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface AddressService extends IService<AddressEntity> {

	PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户收获地址列表
     * @param userId
     * @return
     */
    List<AddressEntity> userAddessList(Integer userId);

    /**
     * 添加收获地址
     * @param addressEntity
     * @param userId
     */
    void addUserAddress(AddressEntity addressEntity, Integer userId);

    /**
     * 删除收货地址
     * @param id
     * @param userId
     */
    void delAddress(Integer id, Integer userId);

    /**
     * 修改用户地址
     * @param addressEntity
     * @param userId
     */
    void updUserAddress(AddressEntity addressEntity, Integer userId);
}
