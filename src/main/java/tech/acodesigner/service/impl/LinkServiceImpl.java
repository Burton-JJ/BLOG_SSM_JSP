package tech.acodesigner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.acodesigner.dao.LinkDao;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Link;
import tech.acodesigner.service.LinkService;

import java.util.List;

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
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public OperationResult<Link> getLinkById(int linkId) {
        OperationResult<Link> or = new OperationResult<Link>();
        Link link = linkDao.getLinkById(linkId);
        if (link == null) {
            or.setSuccess(false);
            or.setInfo("该链接不存在");
        } else {
            or.setSuccess(true);
            or.setData(link);
        }
        return or;
    }

    @Override
    public List<Link> getLinks() {
        return linkDao.getLinks();
    }

    @Override
    public OperationResult saveLink(Link link) {
        OperationResult or = new OperationResult();
        int result = linkDao.saveLink(link);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("添加链接失败");
        } else {
            or.setSuccess(true);
            or.setInfo("添加链接成功");
        }
        return or;
    }

    @Override
    public OperationResult updateLink(Link link) {
        OperationResult or = new OperationResult();
        int result = linkDao.updateLink(link);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("修改链接失败");
        } else {
            or.setSuccess(true);
            or.setInfo("修改链接成功");
        }
        return or;
    }

    @Override
    public OperationResult deleteLink(int linkId) {
        OperationResult or = new OperationResult();
        int result = linkDao.deleteLink(linkId);
        if (result <= 0) {
            or.setSuccess(false);
            or.setInfo("删除链接失败");
        } else {
            or.setSuccess(true);
            or.setInfo("删除链接成功");
        }
        return or;
    }

}
