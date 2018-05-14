package tech.acodesigner.dao;

import tech.acodesigner.entity.Visitor;

import java.util.List;
import java.util.Vector;

public interface VisitorDao {

    public List<Visitor> getVisitors();

    public int saveVisitor(List visitors);

    public int getAllNum();
}
