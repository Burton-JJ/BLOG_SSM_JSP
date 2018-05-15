package tech.acodesigner.service;

import tech.acodesigner.dto.AboutDto;
import tech.acodesigner.dto.OperationResult;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
public interface AboutService {

    public AboutDto getAbout();

    public OperationResult updateAbout(String content);

}
