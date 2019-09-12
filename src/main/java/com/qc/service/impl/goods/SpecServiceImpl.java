package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.common.BaseModel;
import com.qc.common.BaseQueryResult;
import com.qc.contract.goods.req.GetSpecRequest;
import com.qc.dao.goods.SpecMapper;
import com.qc.dao.goods.TemplateMapper;
import com.qc.example.goods.SpecExample;
import com.qc.pojo.goods.Spec;
import com.qc.pojo.goods.Template;
import com.qc.service.ISpecService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cys
 * @date 2019/9/9
 */
@Data
@Service(interfaceClass = ISpecService.class)
public class SpecServiceImpl implements ISpecService {
    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public BaseQueryResult<Spec> getSpecByPage(GetSpecRequest request) {
        SpecExample example = new SpecExample();
        SpecExample.Criteria criteria = example.createCriteria();
        if (request == null) {
            return null;
        }
        if (request.getSearchMap() != null) {
            if (StringUtils.isNotBlank(request.getSearchMap().getName())) {
                criteria.andNameLike("%" + request.getSearchMap().getName() + "%");
            }
            if (StringUtils.isNotBlank(request.getSearchMap().getOptions())) {
                criteria.andOptionsLike("%" + request.getSearchMap().getOptions() + "%");
            }
            // 模板ID
            if(request.getSearchMap().getTemplateId() != null ){
                criteria.andTemplateIdEqualTo(request.getSearchMap().getTemplateId());
            }


        }
        return BaseModel.selectByPage(specMapper, example, request.getPage());
    }

    @Override
    public void saveSpec(Spec spec) {
        specMapper.insertSelective(spec);
        Template template = templateMapper.selectByPrimaryKey(spec.getTemplateId());
        if (template != null) {
            template.setSpecNum(template.getSpecNum() + 1);
            templateMapper.updateByPrimaryKey(template);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpec(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpecById(Integer id) {
        Spec spec = this.getSpecById(id);
        if (spec != null) {
            Template template = templateMapper.selectByPrimaryKey(spec.getTemplateId());
            template.setSpecNum(template.getSpecNum() == 0 ? template.getSpecNum() : template.getSpecNum() -1);
            templateMapper.updateByPrimaryKey(template);
        }
        specMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Spec getSpecById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }
}
