package com.qc.service.impl.goods;

import com.alibaba.dubbo.config.annotation.Service;
import com.qc.common.BaseModel;
import com.qc.common.BaseQueryResult;
import com.qc.contract.goods.req.GetParaRequest;
import com.qc.dao.goods.ParaMapper;
import com.qc.example.goods.ParaExample;
import com.qc.pojo.goods.Para;
import com.qc.service.IParaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cys
 * @date 2019/9/9
 */
@Service
public class ParaServiceImpl implements IParaService {

    @Autowired
    private ParaMapper paraMapper;

    @Override
    public BaseQueryResult<Para> getParaByPage(GetParaRequest request) {
        ParaExample example = new ParaExample();
        ParaExample.Criteria criteria = example.createCriteria();
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
        return BaseModel.selectByPage(paraMapper, example, request.getPage());
    }

    @Override
    public void savePara(Para para) {
        paraMapper.insertSelective(para);
    }

    @Override
    public void updatePara(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    @Override
    public void deleteParaById(Integer paraId) {
        paraMapper.deleteByPrimaryKey(paraId);
    }

    @Override
    public Para getParaById(Integer paraId) {
        return paraMapper.selectByPrimaryKey(paraId);
    }
}
