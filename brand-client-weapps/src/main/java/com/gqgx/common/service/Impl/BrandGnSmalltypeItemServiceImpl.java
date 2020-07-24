package com.gqgx.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.gqgx.common.entity.BrandGnSmalltypeItem;
import com.gqgx.common.entity.BrandSmallType;
import com.gqgx.common.entity.RecordStatus;
import com.gqgx.common.entity.vo.BrandGnSmalltypeItemVo;
import com.gqgx.common.lang.Objects;
import com.gqgx.common.mapper.BrandGnSmalltypeItemMapper;
import com.gqgx.common.paging.LayuiPage;
import com.gqgx.common.paging.PagingResult;
import com.gqgx.common.service.BrandGnSmalltypeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2020/7/24.
 */
@Service
public class BrandGnSmalltypeItemServiceImpl implements BrandGnSmalltypeItemService{

    @Autowired
    protected BrandGnSmalltypeItemMapper mapper;


    @Override
    public BrandGnSmalltypeItem getBrandGnSmalltypeItem(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveBrandGnSmalltypeItem(BrandGnSmalltypeItem brandGnSmalltypeItem) {
        int count = 0 ;
        if(Objects.isEmpty(brandGnSmalltypeItem.getId())){
            count = mapper.updateByPrimaryKeySelective(brandGnSmalltypeItem);
        }else {
            //设置默认值
            brandGnSmalltypeItem.setCreateDate(new Date());
            brandGnSmalltypeItem.setUpdateCount(0);
            brandGnSmalltypeItem.setRecordStatus(RecordStatus.ACTIVE);
            count = mapper.insertSelective(brandGnSmalltypeItem);
        }
        return count;
    }

    @Override
    public int deleteBrandGnSmalltypeItem(BrandGnSmalltypeItem brandGnSmalltypeItem) {
        return mapper.deleteByPrimaryKey(brandGnSmalltypeItem);
    }

    @Override
    public int deleteBrandGnSmalltypeItemByIds(Long[] ids) {
        Example example =new Example(BrandSmallType.class);
        Example.Criteria cb = example.createCriteria();
        cb.andIn("id", Arrays.asList(ids)) ;
        return mapper.deleteByExample(example);
    }

    @Override
    public PagingResult<BrandGnSmalltypeItem> findBrandGnSmalltypeItem(BrandGnSmalltypeItem item, LayuiPage page) {

        Example example = new Example(BrandGnSmalltypeItem.class);
        example.setOrderByClause("create_date DESC");

        if(!Objects.isEmpty(item.getProjectCnname())) {
            example.createCriteria().andLike("projectCnname", "%"+item.getProjectCnname().trim()+"%");
        }
        if (page != null) {
            PageHelper.startPage(page.getPage(), page.getLimit());
        }
        List<BrandGnSmalltypeItem> list = mapper.selectByExample(example);

        PagingResult<BrandGnSmalltypeItem> pageResult = new PagingResult<>(list);
        return pageResult;
    }

    @Override
    public PagingResult<BrandGnSmalltypeItem> findBrandGnSmalltypeItemList(BrandGnSmalltypeItemVo vo, LayuiPage page) {
        //分页插件，自动添加分页语句
        if (page != null) {
            PageHelper.startPage(page.getPage(), page.getLimit());
        }
        List<BrandGnSmalltypeItem> list = mapper.findByBrandGnSmalltypeItemVo(vo);

        PagingResult<BrandGnSmalltypeItem> pageResult = new PagingResult<>(list);
        return pageResult;
    }
}
