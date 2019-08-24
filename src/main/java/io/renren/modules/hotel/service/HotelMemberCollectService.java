package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.form.AddCollectForm;
import io.renren.modules.hotel.vo.CollectItemVo;

import java.util.Map;

/**
 * 用户收藏表
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 21:28:42
 */
public interface HotelMemberCollectService extends IService<HotelMemberCollectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加收藏
     * @param userId
     * @param addCollectForm
     */
	void addCollcet(Long userId, AddCollectForm addCollectForm);

	/**
	 * 删除/取消收藏
	 * @param userId
	 * @param addCollectForm
	 */
	void delCollect(Long userId, AddCollectForm addCollectForm);

	/**
	 * 收藏列表
	 * @param page
	 * @param limie
	 * @param type
	 * @return
	 */
	Page<CollectItemVo> collectList(Page<CollectItemVo> page, Long userId, int type);
}

