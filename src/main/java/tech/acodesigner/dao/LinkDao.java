package tech.acodesigner.dao;

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
public interface LinkDao {

    public Link getLinkById(int linkId);

    public List<Link> getLinks();

    public int saveLink(Link link);

    public int updateLink(Link link);

    public int deleteLink(int linkId);

}
