package com.qc.service.impl.goods;

import com.qc.pojo.goods.Brand;
import com.qc.service.IBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author cys
 * @date 2019/8/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceImplTest {


    @Autowired
    private IBrandService brandService;

    @Test
    public void getAllBrand() {
        List<Brand> allBrand = brandService.getAllBrand();
        System.out.println("allBrand = " + allBrand);
    }
}