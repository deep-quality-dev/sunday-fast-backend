package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelInvoiceEntity;
import io.renren.modules.hotel.form.AddInvoiceForm;

import java.util.List;
import java.util.Map;

/**
 * 发票
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
public interface HotelInvoiceService extends IService<HotelInvoiceEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 添加发票
	 * 
	 * @param userId
	 * @param addInvoiceForm
	 */
	void addInvoice(Long userId, AddInvoiceForm addInvoiceForm);

	/**
	 * 删除发票
	 * 
	 * @param userId
	 * @param id
	 */
	void delInvoice(Long userId, Long id);

	/**
	 * 修改发票
	 * 
	 * @param userId
	 * @param addInvoiceForm
	 */
	void updInvoice(Long userId, AddInvoiceForm addInvoiceForm);

	/**
	 * 发票列表
	 * 
	 * @param userId
	 * @return
	 */
	List<AddInvoiceForm> invoiceList(Long userId);
}
