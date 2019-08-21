package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.dao.goods.BrandMapper;
import com.qc.example.goods.BrandExample;
import com.qc.pojo.goods.Brand;
import com.qc.service.IBrandService;
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
}
