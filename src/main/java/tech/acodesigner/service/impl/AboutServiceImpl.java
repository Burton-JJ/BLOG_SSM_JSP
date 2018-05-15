package tech.acodesigner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.acodesigner.dao.ArticleDao;
import tech.acodesigner.dto.AboutDto;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.service.AboutService;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public AboutDto getAbout() {
        return articleDao.getAbout();
    }

    @Override
    public OperationResult updateAbout(String content) {
        int result = articleDao.updateAbout(content);
        OperationResult or = new OperationResult();
        if (result == 0) {
            or.setSuccess(false);
            or.setInfo("修改失败");
        } else {
            or.setSuccess(true);
            or.setInfo("修改成功");
        }
        return or;
    }
}
