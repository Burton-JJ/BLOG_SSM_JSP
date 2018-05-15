package tech.acodesigner.service;
/**
 *
 * @description
 * @author Burton
 * @date 2018/5/15 20:13
 * @param
 * @return
 *
 */
import tech.acodesigner.dto.OperationResult;

import java.util.List;

public interface VisitorService {

    public  void saveVisitors(List visitors);

    public int getVisitorNum();


}
