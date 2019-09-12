package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.common.ServiceException;
import com.qc.dao.goods.CategoryMapper;
import com.qc.example.goods.CategoryExample;
import com.qc.pojo.goods.Category;
import com.qc.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.qc.common.ServiceException.API_OK;

/**
 * @author cys
 * @date 2019/9/11
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findList(Map<String, Object> map) {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();

        // 上级ID
        if (map.get("parentId") != null) {
            criteria.andParentIdEqualTo((Integer) map.get("parentId"));
        }
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectByExample(new CategoryExample());
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        int count = categoryMapper.countByExample(example);
        if (count > 0) {
            throw new RuntimeException("存在下级分类, 删除失败");
        }
        categoryMapper.deleteByPrimaryKey(id);
    }
}
