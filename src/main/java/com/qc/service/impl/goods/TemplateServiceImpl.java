package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.common.BaseModel;
import com.qc.common.BaseQueryResult;
import com.qc.contract.goods.req.GetTemplateRequest;
import com.qc.dao.goods.TemplateMapper;
import com.qc.example.goods.BrandExample;
import com.qc.example.goods.TemplateExample;
import com.qc.pojo.goods.Brand;
import com.qc.pojo.goods.Template;
import com.qc.service.ITemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author cys
 * @date 2019/9/5
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public BaseQueryResult<Template> getTemplateByPage(GetTemplateRequest request) {
        TemplateExample example = new TemplateExample();
        TemplateExample.Criteria criteria = example.createCriteria();
        if (request == null) {
            return null;
        }
        if (request.getSearchMap() != null) {
            if (StringUtils.isNotBlank(request.getSearchMap().getName())) {
                criteria.andNameLike("%" + request.getSearchMap().getName() + "%");
            }
        }

        return BaseModel.selectByPage(templateMapper, example, request.getPage());
    }

    @Override
    public Template getTemplateById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveTemplate(Template template) {
        template.setParaNum(0);
        template.setSpecNum(0);
        templateMapper.insertSelective(template);
    }

    @Override
    public void updateTemplate(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    @Override
    public void deleteTemplateById(Integer templateId) {
        templateMapper.deleteByPrimaryKey(templateId);
    }

    @Override
    public List<Template> findAll() {
        return templateMapper.selectByExample(new TemplateExample());
    }
}
