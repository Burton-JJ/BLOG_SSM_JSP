package tech.acodesigner.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.acodesigner.dto.*;
import tech.acodesigner.entity.Link;
import tech.acodesigner.service.AboutService;
import tech.acodesigner.service.ArticleService;
import tech.acodesigner.service.CategoryService;
import tech.acodesigner.service.LinkService;
import tech.acodesigner.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
@SuppressWarnings("Since15")
@Controller
public class BlogController {

    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private AboutService aboutService;

    @RequestMapping(value = {"/blog", "/"}, method = RequestMethod.GET)
    public String showBlogView(HttpServletRequest request, Model model, @RequestParam(value = "page", required = false) String page,
                               @RequestParam(value = "search", required = false) String search) {
        List<ArticleLiteDto> recentArticles = articleService.getRecentArticles();
        List<ArticleLiteDto> mostViewedArticles = articleService.getMostViewedArticles();
        request.getSession().setAttribute("recentArticles", recentArticles);

       //request.getServletContext().setAttribute("recentArticles", recentArticles);有效
        //recentArticles mostViewedArticles 为多个页面共享的变量 不能放入model等模型变量中 因为最后实在request中 只在一次请求有效
        //应该放在session 一个用户的会话中 或者servletContent 所有用户共享域中
        //model.addAttribute("recentArticles", recentArticles);无效
        request.getSession().setAttribute("mostViewedArticles", mostViewedArticles);
        //request.getServletContext().setAttribute("mostViewedArticles", mostViewedArticles);
       // model.addAttribute("mostViewedArticles", mostViewedArticles);
        List<Link> links = linkService.getLinks();
        request.getSession().setAttribute("links", links);
       // request.getServletContext().setAttribute("links", links);
        //model.addAttribute("links", links);
        if (page == null || page == "") {
            page = "1";
        }
        int pageSize = 4;
        PageUtil pageUtil = new PageUtil(Integer.parseInt(page), pageSize, search);
        List<ArticleDto> articles = null;
        String pageCode = null;
        String searchOption = null;

        if (search != null && !search.equals("")) {
            searchOption = "&search=" + search;
        } else {
            searchOption = "";
        }

        //ByRange 根据上面的pageUtil中的page=1,start=0和pagesize取出对应记录
            articles = articleService.pagination(pageUtil);
            int articleTotalNum = articleService.countArticleNum(search);

        System.out.println("searchOption = "+searchOption);
        System.out.println("total = "+articleTotalNum);
        //后台java获取编译文件路径

//        String path1 = request.getSession().getServletContext().getRealPath("/images/user");
//        System.out.println("编译后路径："+path1);
        pageCode = this.genPagination(articleTotalNum, Integer.parseInt(page), pageSize, searchOption);

        model.addAttribute("pageCode", pageCode);
        model.addAttribute("articles", articles);
        model.addAttribute("mainPage", "article.jsp");
        return "blog/blog";
    }

    private String genPagination(int totalNum, int curPage, int pageSize, String search) {
        int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<ul class=\"pagination\">");
        //到第一页
        pageCode.append("<li class='waves-effect'><a href='blog?page=1"+search+"'><i class=\"material-icons\">first_page</i></a></li>");
        //如果当前W为第一页 则向前一页失效
        if (curPage == 1) {
            pageCode.append("<li class=\"disabled\"><a><i class=\"material-icons\">chevron_left</i></a></li>");
        } else {
            //r如果当前不是第一页 则向前一页
            pageCode.append("<li class='waves-effect'><a href=\"blog?page=" + (curPage - 1) + search + "\"><i class=\"material-icons\">chevron_left</i></a></li>");
        }
        //展示的页码最多为5页
        for (int i = curPage - 2; i <= curPage + 2; i++) {
            if (i < 1 || i > totalPage) {
                continue;
            }
            if (i == curPage) {
                pageCode.append("<li class='active waves-effect'><a href='#'>" + i
                        + "</a></li>");
            } else {
                pageCode.append("<li class='waves-effect'><a href='blog?page=" + i + search+ "'>" + i
                        + "</a></li>");
            }
        }
        //如果当前W为最后页 则向后一页失效
        if (curPage == totalPage) {
            pageCode.append("<li class='disabled'><a><i class=\"material-icons\">chevron_right</i></a></li>");
        } else {
            //向后一页
            pageCode.append("<li class='waves-effect'><a href='blog?page=" + (curPage + 1) + search
                    + "'><i class=\"material-icons\">chevron_right</i></a></li>");
        }
        //到最后一页
        pageCode.append("<li class='waves-effect'><a href='blog?page=" + totalPage + search + "'><i class=\"material-icons\">last_page</i></a></li>");
        pageCode.append("</ul>");
        return pageCode.toString();
    }

    //文章详情页的展示
    @RequestMapping(value = "/blog/article/{articleId}", method = RequestMethod.GET)
    public String showArticleView(@PathVariable("articleId") int articleId, Model model, RedirectAttributes attributes) {
        OperationResult<ArticleDto> articleResult = articleService.getArticleById(articleId);
        if (articleResult.isSuccess()) {
            OperationResult addClickResult = articleService.addClicks(articleId);
            if (addClickResult.isSuccess() == false) {
                logger.error("add click error.\tarticle:%s", articleId);
            }
            ArticleLiteDto preArticle = articleService.getPreArticle(articleId);
            ArticleLiteDto nextArticle = articleService.getNextArticle(articleId);
            model.addAttribute("articleId", articleId);
            model.addAttribute("article", articleResult.getData());
            model.addAttribute("preArticle", preArticle);
            model.addAttribute("nextArticle", nextArticle);
            model.addAttribute("mainPage", "articleDetail.jsp");
            return "blog/blog";
        } else {
            attributes.addFlashAttribute("info", articleResult.getInfo());
            return "redirect:/blog";
        }
    }

    //类别列表的展示
    @RequestMapping(value = {"/blog/category", "/blog/category/{categoryId}"}, method = RequestMethod.GET)
    public String showCategoryView(@PathVariable("categoryId") Optional<Integer> categoryId, Model model) {
        List<CategoryDto> categories = categoryService.getCategories();
        HashMap<Integer, List<ArticleLiteDto>> articlesList = new HashMap<Integer, List<ArticleLiteDto>>();
        for (CategoryDto category : categories) {
            List<ArticleLiteDto> articles = articleService.getArticlesByCategoryId(category.getCategoryId());
            articlesList.put(category.getCategoryId(), articles);
        }
        if (categoryId.isPresent()) {
            model.addAttribute("categoryId", categoryId.get());
        } else {
            model.addAttribute("categoryId", "");
        }

        model.addAttribute("categories", categories);
        model.addAttribute("articlesList", articlesList);
        model.addAttribute("mainPage", "category.jsp");
        return "blog/blog";
    }

    //对归档列表展示
    @RequestMapping(value = "/blog/archive", method = RequestMethod.GET)
    public String showArchiveView(Model model) {
        //文章按日期进行排序
        List<ArticleDto> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("mainPage", "archive.jsp");
        return "blog/blog";
    }

    //对留言的管理
    @RequestMapping(value = "/blog/message", method = RequestMethod.GET)
    public String showMessageView(Model model) {
        model.addAttribute("articleId", 0);
        model.addAttribute("mainPage", "message.jsp");
        return "blog/blog";
    }

    //对关于页面的展示
    @RequestMapping(value = "/blog/about", method = RequestMethod.GET)
    public String showAboutView(Model model) {
        AboutDto about = aboutService.getAbout();
        model.addAttribute("about", about);
        model.addAttribute("mainPage", "about.jsp");
        return "blog/blog";
    }
}
