package io.renren.modules.app.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartVo {
    private Integer id;
    /**
     * 商品id
     */
    private Integer gid;
    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String img;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     *
     */
    private String specKey;
    /**
     *
     */
    private String specKeyValue;

}
