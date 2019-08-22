package io.renren.modules.hotel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelContactsEntity;
import io.renren.modules.hotel.form.AddContactsForm;

/**
 * 联系人
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
public interface HotelContactsService extends IService<HotelContactsEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 添加联系人
	 * 
	 * @param userId
	 * @param contactsForm
	 */
	void addContacts(Long userId, AddContactsForm contactsForm);

	/**
	 * 删除联系人
	 * 
	 * @param userId
	 * @param id
	 */
	void delContacts(Long userId, Long id);

	/**
	 * 修改联系人
	 * 
	 * @param userId
	 * @param contactsForm
	 */
	void updContacts(Long userId, AddContactsForm contactsForm);

	/**
	 * 联系人列表
	 * 
	 * @param userId
	 * @return
	 */
	List<AddContactsForm> contactsList(Long userId);

}
