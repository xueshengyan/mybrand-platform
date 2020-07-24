package com.gqgx.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.gqgx.common.entity.BrandLargeType;
import com.gqgx.common.entity.BrandSmallType;
import com.gqgx.common.entity.RecordStatus;
import com.gqgx.common.mapper.BrandSmallTypeMapper;
import com.gqgx.common.paging.LayuiPage;
import com.gqgx.common.paging.PagingResult;
import com.gqgx.common.service.BrandSmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Hunter on 2020-07-23.
 */
@Service
public class BrandSmallTypeServiceImpl implements BrandSmallTypeService {

    @Autowired
    private BrandSmallTypeMapper mapper;

    @Override
    public BrandSmallType getBrandSmallType(String smallNo) {
        return mapper.selectByPrimaryKey(smallNo);
    }

    @Override
    public BrandSmallType getBrandSmallType(Long smallTypeId) {
        return mapper.selectByPrimaryKey(smallTypeId);
    }

    @Override
    public int saveBrandSmallType(BrandSmallType brandSmallType) {
        int count = 0;
        if(brandSmallType.getId() != null){
            //更新
            mapper.updateByPrimaryKey(brandSmallType);
        }else {
            //添加,设置默认值
            brandSmallType.setCreateDate(new Date());
            brandSmallType.setUpdateCount(0);
            brandSmallType.setRecordStatus(RecordStatus.ACTIVE);
            mapper.insertSelective(brandSmallType);
        }
        return count;
    }

    @Override
    public int deleteBrandSmallType(BrandSmallType brandSmallType) {
        return mapper.deleteByPrimaryKey(brandSmallType);
    }

    @Override
    public int deleteBrandSmallTypeByIds(Long[] ids) {

        Example example = new Example(BrandSmallType.class);
        Example.Criteria cb = example.createCriteria();
        cb.andIn("id", Arrays.asList(ids));
        return mapper.deleteByExample(example);
    }

    @Override
    public PagingResult<BrandSmallType> findBrandSmallType(BrandSmallType brandSmallType, LayuiPage page) {
        //正则表达式
        Example example = new Example(BrandSmallType.class);
        //正则表达式 的过滤
        Example.Criteria cb = example.createCriteria();

        //通过项目名称过滤
        if (brandSmallType.getName() != null && !"".equals(brandSmallType.getName().trim())) {
            cb.andLike("name", "%" + brandSmallType.getName()+"%");
        }

        //排序：目录 升序
        example.orderBy("no").asc();

        //分页
        if(page != null){
            PageHelper.startPage(page.getPage(), page.getLimit());
        }

        List<BrandSmallType> list = mapper.selectByExample(example);

        return new PagingResult<>(list);

    }

    @Override
    public List<BrandSmallType> findBrandSmallTypeList(BrandSmallType brandSmallType) {
        //正则表达式
        Example example = new Example(BrandSmallType.class);
        //正则表达式 的过滤
        Example.Criteria cb = example.createCriteria();

        //通过项目名称过滤
        if (brandSmallType.getName() != null && !"".equals(brandSmallType.getName().trim())) {
            cb.andLike("name", "%" + brandSmallType.getName()+"%");
        }

        //排序：目录 升序
        example.orderBy("no").asc();

        return mapper.selectByExample(example);
    }
}
