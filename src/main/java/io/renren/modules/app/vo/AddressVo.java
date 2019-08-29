package io.renren.modules.app.vo;

import lombok.Data;

@Data
public class AddressVo {

    private Integer id;
    /**
     *
     */
    private Integer uid;
    /**
     * 收货人
     */
    private String consignee;
    /**
     *
     */
    private String region;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 默认收货地址
     */
    private Integer isDefault;

    private Integer provinceId;
    private Integer cityId;
    private Integer areaId;
    private RegionVo province;
    private RegionVo city;
    private RegionVo area;
}
