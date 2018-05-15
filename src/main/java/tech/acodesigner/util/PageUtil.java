package tech.acodesigner.util;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
public class PageUtil {

    private int page;
    private int pageSize;
    private int start;
    //搜寻数据库条件 在输入框中输入内容进行查询
    private String factor;

    public PageUtil(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.start = (page - 1) * pageSize;
    }

    public PageUtil(int page, int pageSize, String factor) {


        this.factor = factor;
        this.page = page;
        this.pageSize = pageSize;
        this.start = (page - 1) * pageSize;

    }
}
