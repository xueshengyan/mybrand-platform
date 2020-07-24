package com.gqgx.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.gqgx.common.entity.BrandLargeType;
import com.gqgx.common.entity.RecordStatus;
import com.gqgx.common.mapper.BrandLargeTypeMapper;
import com.gqgx.common.paging.LayuiPage;
import com.gqgx.common.paging.PagingResult;
import com.gqgx.common.service.BrandLargeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by Hunter on 2020-07-17.
 */
@Service
public class BrandLargeTypeServiceImpl implements BrandLargeTypeService {


    @Autowired
    private BrandLargeTypeMapper mapper;

    @Override
    public BrandLargeType getBrandLargeType(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveBrandLargeType(BrandLargeType brandLargeType) {
        int count = 0;
        if(brandLargeType != null){
            //更新
            count = mapper.updateByPrimaryKeySelective(brandLargeType);
        }else {
            //添加,设置默认值
            brandLargeType.setCreateDate(new Date());
            brandLargeType.setUpdateCount(0);
            brandLargeType.setRecordStatus(RecordStatus.ACTIVE);
            count = mapper.insertSelective(brandLargeType);
        }
        return count;
    }

    @Override
    public int deleteBrandLargeType(BrandLargeType brandLargeType) {
        return mapper.deleteByPrimaryKey(brandLargeType);
    }

    //TODO:
    @Override
    public int deleteBrandLargeTypeByIds(Long[] ids) {
        return 0;
    }

    @Override
    public PagingResult<BrandLargeType> findBrandLargeType(BrandLargeType brandLargeType, LayuiPage page) {

        //正则表达式
        Example example = new Example(BrandLargeType.class);
        //正则表达式 的过滤
        Example.Criteria cb = example.createCriteria();

        //通过项目名称过滤
        if (brandLargeType.getName() != null && !"".equals(brandLargeType.getName().trim())) {
            cb.andLike("name", "%"+brandLargeType.getName()+"%");
        }
        //	中文序号过滤
        if(brandLargeType.getCnno() != null && !"".equals(brandLargeType.getCnno())){
            cb.andLike("cnno", "%"+brandLargeType.getCnno()+"%");
        }

        //排序：目录 升序
        example.orderBy("catalog").asc();


        //分页
        if(page != null){
            PageHelper.startPage(page.getPage(), page.getLimit());
        }
        List<BrandLargeType> list = mapper.selectByExample(example);

        return new PagingResult<>(list);
    }

    @Override
    public List<BrandLargeType> findBrandLargeTypeList(BrandLargeType brandLargeType) {
        //正则表达式
        Example example = new Example(BrandLargeType.class);
        //正则表达式 的过滤
        Example.Criteria cb = example.createCriteria();

        //通过项目名称过滤
        if (brandLargeType.getName() != null && !"".equals(brandLargeType.getName().trim())) {
            cb.andLike("name", "%"+brandLargeType.getName()+"%");
        }
        //	中文序号过滤
        if(brandLargeType.getCnno() != null && !"".equals(brandLargeType.getCnno())){
            cb.andLike("cnno", "%"+brandLargeType.getCnno()+"%");
        }

        //排序：目录 升序
        example.orderBy("catalog").asc();
        List<BrandLargeType> list = mapper.selectByExample(example);
        return list;
    }
}
