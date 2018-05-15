package tech.acodesigner.dao;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
import tech.acodesigner.entity.Visitor;

import java.util.List;
import java.util.Vector;

public interface VisitorDao {

    public List<Visitor> getVisitors();

    public int saveVisitor(List visitors);

    public int getAllNum();
}
