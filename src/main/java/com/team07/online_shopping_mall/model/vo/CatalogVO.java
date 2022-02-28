package com.team07.online_shopping_mall.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 目录
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
public class CatalogVO implements Serializable {

    private Long id;

    private String name;

    private Integer type;

    private Long parentId;

    private Integer orderNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<CatalogVO> childCatalog = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<CatalogVO> getChildCatalog() {
        return childCatalog;
    }

    public void setChildCatalog(List<CatalogVO> childCatalog) {
        this.childCatalog = childCatalog;
    }
}
