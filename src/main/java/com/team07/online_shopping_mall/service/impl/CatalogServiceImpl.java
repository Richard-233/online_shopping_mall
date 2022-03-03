package com.team07.online_shopping_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.Catalog;
import com.team07.online_shopping_mall.mapper.CatalogMapper;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;
import com.team07.online_shopping_mall.model.vo.CatalogVO;
import com.team07.online_shopping_mall.service.CatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 目录 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {

    @Autowired
    CatalogMapper catalogMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Catalog catalog = new Catalog();
        BeanUtils.copyProperties(addCategoryReq, catalog);
        Catalog catalogOld = catalogMapper.selectByName(addCategoryReq.getName());
        if (catalogOld != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = catalogMapper.insertSelective(catalog);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Catalog updateCategory) {
        if (updateCategory.getName() != null) {
            Catalog catalogOld = catalogMapper.selectByName(updateCategory.getName());
            if (catalogOld != null && !catalogOld.getId().equals(updateCategory.getId())) {
                throw new MallException(MallExceptionEnum.NAME_EXISTED);
            }
        }
        int count = catalogMapper.updateByPrimaryKeySelective(updateCategory);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Long id) {
        Catalog catalogOld = catalogMapper.selectByPrimaryKey(id);
        //查不到记录，删除失败
        if (catalogOld == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = catalogMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        List<Catalog> catalogList = catalogMapper.selectList();
        PageInfo pageInfo = new PageInfo(catalogList);
        return pageInfo;
    }

    @Override
    @Cacheable("listCategoryForCustom")
    public List<CatalogVO> listCategoryForCustom(Long parentId) {
        ArrayList<CatalogVO> catalogVOList = new ArrayList<>();
        recursivelyFindCategories(catalogVOList, parentId);
        return catalogVOList;
    }

    private void recursivelyFindCategories(List<CatalogVO> catalogVOList, Long parentId) {
        //递归获取所有子类别，并组合成为一个“目录树”
        List<Catalog> catalogList = catalogMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(catalogList)) {
            for (int i = 0; i < catalogList.size(); i++) {
                Catalog catalog = catalogList.get(i);
                CatalogVO catalogVO = new CatalogVO();
                BeanUtils.copyProperties(catalog, catalogVO);
                catalogVOList.add(catalogVO);
                recursivelyFindCategories(catalogVO.getChildCatalog(), catalogVO.getId());
            }
        }
    }
}
