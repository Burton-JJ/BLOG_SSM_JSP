package tech.acodesigner.service;

import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Link;

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
public interface LinkService {

    public OperationResult<Link> getLinkById(int linkId);

    public List<Link> getLinks();

    public OperationResult saveLink(Link link);

    public OperationResult updateLink(Link link);

    public OperationResult deleteLink(int linkId);

}
