package tech.acodesigner.service;

import tech.acodesigner.dto.OperationResult;

import java.util.List;

public interface VisitorService {

    public  void saveVisitors(List visitors);

    public int getVisitorNum();


}
