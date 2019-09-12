package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.common.BaseModel;
import com.qc.common.BaseQueryResult;
import com.qc.common.PageInfo;
import com.qc.contract.goods.req.GetBrandRequest;
import com.qc.dao.goods.BrandMapper;
import com.qc.example.goods.BrandExample;
import com.qc.pojo.goods.Brand;
import com.qc.service.IBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cys
 * @date 2019/8/20
 */
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getAllBrand() {
        BrandExample example = new BrandExample();
        return brandMapper.selectByExample(example);
    }

    @Override
    public BaseQueryResult<Brand> getAllBrandByPage(PageInfo pageInfo) {
        BrandExample example = new BrandExample();
        return BaseModel.selectByPage(brandMapper, example, pageInfo);
    }

    @Override
    public BaseQueryResult<Brand> getBrandByCondition(GetBrandRequest request) {
        BrandExample example = new BrandExample();
        BrandExample.Criteria criteria = example.createCriteria();
        if (request == null) {
            return null;
        }
        if (request.getSearch() != null) {
            if (StringUtils.isNotBlank(request.getSearch().getName())) {
                criteria.andNameLike("%" + request.getSearch().getName() + "%");
            }
            if (StringUtils.isNotBlank(request.getSearch().getLetter())) {
                criteria.andLetterEqualTo(StringUtils.swapCase(request.getSearch().getLetter()));
            }
        }

        return BaseModel.selectByPage(brandMapper, example, request.getPage());
    }

    @Override
    public void saveBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public Brand getBrandById(Integer brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void deleteBrandById(Integer brandId) {
        brandMapper.deleteByPrimaryKey(brandId);
    }
}
