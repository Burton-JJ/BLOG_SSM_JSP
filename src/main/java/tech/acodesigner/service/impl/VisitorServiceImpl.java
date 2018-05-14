package tech.acodesigner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.acodesigner.dao.VisitorDao;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.service.VisitorService;

import java.util.List;

/**
 * @program: Blog_SSM
 * @description: 访客
 * @author: Burton_J
 * @create: 2018-05-14 13:23
 **/
@Service("VisitorService")
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorDao visitorDao;

    @Override
    public void saveVisitors(List visitors) {

        visitorDao.saveVisitor(visitors);
    }

    @Override
    public int getVisitorNum() {
        int visitorNum = visitorDao.getAllNum();
        return visitorNum;
    }
}
