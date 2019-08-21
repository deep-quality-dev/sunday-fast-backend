package io.renren.modules.hotel.vo;

import lombok.Data;

/**
 * 收藏列表数据
 * @author taoz
 *
 */
@Data
public class CollectItemVo {

	private Long id;

	private String name;

	private Long bizId;

	private String bizTye;

	private String image;

	private String address;
}
