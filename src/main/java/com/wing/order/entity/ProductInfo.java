package com.wing.order.entity;

import com.wing.order.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    @Enumerated(EnumType.STRING)
    private ProductStatusEnum productStatus = ProductStatusEnum.UP;

    private Date createTime;

    private Date updateTime;

    @OneToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

}
