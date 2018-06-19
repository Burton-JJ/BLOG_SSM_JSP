package tech.acodesigner.web;

import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.acodesigner.dto.ArticleDto;
import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.*;
import tech.acodesigner.service.*;
import tech.acodesigner.util.ImageUploadUtil;
import tech.acodesigner.util.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/15 20:05
 * @param
 * @return
 *
 */
@SuppressWarnings("Since15")
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @Autowired
    private AboutService aboutService;

    //进入管理界面的主界面 可以返回首页 前台
    @RequestMapping(method = RequestMethod.GET)
    public String showManageView(Model model) {
        //manageView.返回前台
        model.addAttribute("mainPage", "manageView.jsp");
        return "manage/manage";
    }

    //对文章的管理
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String showArticleList(Model model) {
        List<ArticleDto> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("mainPage", "article.jsp");
        return "manage/manage";
    }

    //写文章或者文章修改
    @RequestMapping(value = {"/article/write", "/article/modify/{articleId}"}, method = RequestMethod.GET)
    public String editArticle(@PathVariable("articleId") Optional<Integer> articleId, HttpServletRequest request, Model model, RedirectAttributes attributes) {
        //若articleId.isPresent()为true则修改文章
        //否则 添加新文章
        if (articleId.isPresent()) {
            OperationResult<ArticleDto> result = articleService.getArticleById(articleId.get());
            if (result.isSuccess()) {
                model.addAttribute("article", result.getData());
            } else {
                attributes.addFlashAttribute("info", result.getInfo());
                return "redirect:/manage/article";
            }
        }

        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);

        List<Image> articleImages = imageService.getImages(1);
//        String[] images = ImagesUtil.getImages(request.getServletContext().getRealPath("images/article"));
        model.addAttribute("articleImages", articleImages);

        model.addAttribute("mainPage", "articleEditor.jsp");
        return "manage/manage";
    }

    //保存新文章 保存修改后文章
    @RequestMapping(value = {"/article/save", "/article/save/{articleId}"}, method = RequestMethod.POST)
    public String saveArticle(@PathVariable("articleId") Optional<Integer> articleId, HttpServletRequest request, ArticleDto articleDto,RedirectAttributes attributes) {
//        String title = request.getParameter("title");//文章标题
//        String content = request.getParameter("content");//文章内容
//        String categoryId = request.getParameter("categoryId");//文章类别
//        String image = request.getParameter("image");//文章在首页图片
        Article article = new Article(Integer.parseInt(request.getParameter("categoryId")), request.getParameter("title"),
                request.getParameter("content"), Integer.parseInt(request.getParameter("imageId")));
//        String title = articleDto.getTitle();
//        String content = articleDto.getContent();
//        int categoryId = articleDto.getArticleId();
//        int imageId = articleDto.getImageId();
//        Article article = new Article(categoryId, title,
//               content,imageId);
        OperationResult result = null;
        //若articleId.isPresent()为true 则是保存修改后文章
        if (articleId.isPresent()) {
            //为新的article赋值原来修改前article的点击数 文章号
            article.setClicks(articleService.getArticleById(articleId.get()).getData().getClicks());//这条缺少也可
            article.setArticleId(articleId.get());//关键所在
            result = articleService.updateArticle(article);

        } else {
            //添加新文章
            article.setPubDate(new Date(System.currentTimeMillis()));
            result = articleService.saveArticle(article);
        }
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/article";
    }

    //删除文章
    @RequestMapping(value = "/article/delete/{articleId}", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable("articleId") Integer articleId, RedirectAttributes attributes) {
        OperationResult result = articleService.deleteArticle(articleId);
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/article";
    }

    //对类别的管理 后台主页面点击分类 由此跳转
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String showCategoryList(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("mainPage", "category.jsp");
        return "manage/manage";
    }

    //添加分类 修改分类
    @RequestMapping(value = {"/category/add", "/category/modify/{categoryId}"}, method = RequestMethod.GET)
    public String editCategory(@PathVariable("categoryId") Optional<Integer> categoryId, Model model, RedirectAttributes attributes) {
        if (categoryId.isPresent()) {
            //修改分类
            OperationResult<Category> result = categoryService.getCategory(categoryId.get());
            if (result.isSuccess()) {
                model.addAttribute("category", result.getData());
            } else {
                attributes.addFlashAttribute("info", result.getInfo());
                return "redirect:/manage/category";
            }
        }
        model.addAttribute("mainPage", "categoryEditor.jsp");
        return "manage/manage";
    }

    //保存新分类 保存修改类别的信息
    @RequestMapping(value = {"/category/save", "/category/save/{categoryId}"}, method = RequestMethod.POST)
    //参数String categoryName 直接从前端input传过来
    public String saveCategory(@PathVariable("categoryId") Optional<Integer> categoryId,String categoryName, RedirectAttributes attributes) {
       // String categoryName = request.getParameter("categoryName");
        OperationResult result = null;
        //若categoryId.isPresent()为true 则是保存修改后的信息
        if (categoryId.isPresent()) {
            result = categoryService.updateCategory(new Category(categoryId.get(), categoryName));
        } else {
            //否则保存新分类
            result = categoryService.saveCategory(categoryName);
        }
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/category";
    }


    //删除分类
    @RequestMapping(value = "/category/delete/{categoryId}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable("categoryId") Integer categoryId, RedirectAttributes attributes) {
        OperationResult result = categoryService.deleteCategory(categoryId);
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/category";
    }

    //对链接的管理
    @RequestMapping(value = "/link", method = RequestMethod.GET)
    public String showLinkList(Model model) {
        List<Link> links = linkService.getLinks();
        model.addAttribute("links", links);
        model.addAttribute("mainPage", "link.jsp");
        return "manage/manage";
    }

    //对存在链接的修改 新增链接
    @RequestMapping(value = {"/link/add", "/link/modify/{linkId}"}, method = RequestMethod.GET)
    public String editLink(@PathVariable("linkId") Optional<Integer> linkId, Model model, RedirectAttributes attributes) {
        //如果(linkId.isPresent())为true 则为对存在链接的修改
        if (linkId.isPresent()) {
            OperationResult<Link> result = linkService.getLinkById(linkId.get());
            if (result.isSuccess()) {
                model.addAttribute("link", result.getData());
            } else {
                attributes.addFlashAttribute("info", result.getInfo());
                return "redirect:/manage/link";
            }
        }
        model.addAttribute("mainPage", "linkEditor.jsp");
        return "manage/manage";
    }

    //保存新链接 保存修改过的链接
    @RequestMapping(value = {"/link/save", "/link/save/{linkId}"}, method = RequestMethod.POST)
    public String saveLink(@PathVariable("linkId") Optional<Integer> linkId, HttpServletRequest request, RedirectAttributes attributes) {
        String linkName = request.getParameter("linkName");
        String url = request.getParameter("url");
        OperationResult result = null;
        if (linkId.isPresent()) {
            //保存修改过的链接
            result = linkService.updateLink(new Link(linkId.get(), linkName, url));
        } else {
            //保存新链接
            result = linkService.saveLink(new Link(linkName, url));
        }
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/link";
    }

    //删除链接
    @RequestMapping(value = "/link/delete/{linkId}", method = RequestMethod.GET)
    public String deleteLink(@PathVariable("linkId") Integer linkId, RedirectAttributes attributes) {
        OperationResult result = linkService.deleteLink(linkId);
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/link";
    }

    //对图片的管理
    @RequestMapping(value = "/image", method = {RequestMethod.GET, RequestMethod.POST})
    public String showImagesAndUpload(HttpServletRequest request, Model model) {

        List<Image> userImages = imageService.getImages(0);
//        Image firstImage = userImages.get(0);
//        System.out.println(firstImage.toString());
        List<Image> articleImages = imageService.getImages(1);


//        String imageBasePath = request.getServletContext().getRealPath("images");
////        processUpload(request, imageBasePath);
//
//        String[] userImages = ImagesUtil.getImages(imageBasePath + File.separator + "user");
//        System.out.println(userImages.length);
//        for (String userImage:userImages
//             ) {
//            System.out.println(userImage);
//        }
//        String[] articleImages = ImagesUtil.getImages(imageBasePath + File.separator + "article");
//        System.out.println(articleImages.length);
        model.addAttribute("userImages", userImages);
        model.addAttribute("articleImages", articleImages);
        model.addAttribute("mainPage", "image.jsp");
        return "manage/manage";
    }

//    private void processUpload(HttpServletRequest request, String imageBasePath) {
//        System.out.println("process upload");
//        FileItemFactory factory = new DiskFileItemFactory();
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        List<FileItem> items = null;
//        try {
//            items = upload.parseRequest(request);
//        } catch (FileUploadException e) {
//            return;
////			e.printStackTrace();
//        }
//        if (items == null) {
//            return;
//        }
//        Iterator<FileItem> itr = items.iterator();
//        while (itr.hasNext()) {
//            FileItem item = itr.next();
//            if (item.isFormField()) {
//                System.out.println("error");
//            } else if ("userImage".equals(item.getFieldName())) {
//                try {
//                    String imageName = item.getName();
//                    String filePath = imageBasePath + File.separator
//                            + "user"
//                            + File.separator + imageName;
//                    System.out.println(filePath);
//                    item.write(new File(filePath));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if ("articleImage".equals(item.getFieldName())) {
//                try {
//                    String imageName = item.getName();
//                    String filePath = imageBasePath + File.separator
//                            + "article"
//                            + File.separator + imageName;
//                    System.out.println(filePath);
//                    item.write(new File(filePath));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    //对图片的保存  写入本地 webapp/images/user或者article
    @RequestMapping("/image/{savaImage}")
    public String saveImage( HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "savaImage") String imageType,@RequestParam(value = "upload",required =false) MultipartFile file,
                            RedirectAttributes attributes){
        //图片存入本地服务器
        String dirPath = "";

        int type = -1;//图片类型 -1 无意义 0代表用户头像 1 代表首页文章图片 2 代表文章内容图片
        if(imageType.equals("saveUserImage")){
            //用户图片路径
            dirPath = request.getSession().getServletContext().getRealPath("/images/user");
            System.out.println("hhhhhhhhhhhhhhhhhhh"+dirPath);// /有一个\为转义字符
            type = 0;
            try {
                ImageUploadUtil.upload(request, dirPath, file, type, attributes);
                if(file == null||file.isEmpty()){
                    System.out.println("不存在");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/manage/image";
        } else if (imageType.equals("saveArticleImage")){
            //文章首页图片路径
            dirPath = request.getSession().getServletContext().getRealPath("/images/article");
            System.out.println("hhhhhhhhhhhhhhhhhhh"+dirPath);
            type = 1;
            try {
                ImageUploadUtil.upload(request, dirPath, file, type, attributes);
                if(file == null||file.isEmpty()){
                    System.out.println("不存在");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/manage/image";
        }else {
            System.out.println("进入文章内容图片上传");
            //文章内容图片
            dirPath = request.getSession().getServletContext().getRealPath("/images/article");
           type = 2;
            try {
                System.out.println(file.getContentType());
                ImageUploadUtil.ckeditor(request, response, dirPath,file,type,attributes);

                if(file == null||file.isEmpty()){
                    System.out.println("不存在");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

//        File dir = new File(dirPath);
//        //如果文件夹不存在或者不是文件夹 则创建
//        if(!dir.exists()&&!dir.isDirectory()){
//            dir.mkdir();
//        }
//        //得到文件（这里是图片）名  比如123.jpg
//        String originalFilename = file.getOriginalFilename();
//        System.out.println(originalFilename);
//
//        String imgPath = dirPath+"\\"+originalFilename;
//        File dest = new File(imgPath);
//        if(!dest.exists()){
//            try {
//                dest.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //写入图片到指定位置
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //图片名存入数据库
//        Image image = new Image(type,originalFilename);
//        OperationResult result = null;
//        result = imageService.saveImage(image);
//        attributes.addFlashAttribute("info", result.getInfo());


    }

    //图片删除操作
    @RequestMapping("/image/delete/{imageId}")
    public String deleteImage(HttpServletRequest request,@PathVariable(value = "imageId" ) int imageId ,RedirectAttributes attributes){
        System.out.println("11111111111");
        //得到要删除的图片信息
        Image image = imageService.getImageById(imageId);
        //在数据库中删除
        OperationResult result = new OperationResult();
        result = imageService.deleteImageById(imageId);
        attributes.addFlashAttribute("info",result.getInfo());
        //删除结果
        boolean isSuccess = result.isSuccess();

        //在本地服务器删除图片 根据数据库中删除结果再决定是否删除本地服务器图片

        System.out.println(image.getImageId());
        System.out.println(image.getImageType());
        System.out.println(image.getImageName());
        int type = image.getImageType();
        String imageName = image.getImageName();
        String dirPath = "";
        if(type == 0)
        {
            //用户图片路径 有一个\为转义字符
            //dirPath = "E:\\IDEA_files\\blog2\\Blog_SSM\\src\\main\\webapp\\images\\user";
            //request.getSession().getServletContext().getRealPath("/");取到target/mblog
            dirPath = request.getSession().getServletContext().getRealPath("/images/user");
            System.out.println("request.getSession().getServletContext().getRealPath(\"/images/user\")="+dirPath);
            System.out.println("request.getSession().getServletContext().getRealPath(\"/\")= " +request.getSession().getServletContext().getRealPath("/"));
        } else {
            //文章图片路径
            //dirPath = "E:\\IDEA_files\\blog2\\Blog_SSM\\src\\main\\webapp\\images\\article";
            dirPath = request.getSession().getServletContext().getRealPath("/images/article");

        }
        //图片全路径 比如d:\a\a.jpg
        String imgPath = dirPath+"/"+imageName;
        File f = new File(imgPath);
        //如果图片存在 且可以删除 就删除
        if(f.exists() && f.isFile() && isSuccess){
            f.delete();
        }


        return "redirect:/manage/image";
    }


    //用户的管理 超级用户无法修改个人信息
    //todo
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("mainPage", "user.jsp");
        return "manage/manage";
    }

    //todo
    //注册或修改用户    有待修改
    @RequestMapping(value = {"/user/register", "/user/modify/{userId}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable("userId") Optional<Integer> userId, HttpServletRequest request, Model model, RedirectAttributes attributes) {
        if (userId.isPresent()) {
            OperationResult<Link> result = userService.getUser(userId.get());
            if (result.isSuccess()) {
                model.addAttribute("user", result.getData());
            } else {
                attributes.addFlashAttribute("info", result.getInfo());
                return "redirect:/manage/user";
            }
        }
       // String[] images = ImagesUtil.getImages(request.getServletContext().getRealPath("images/user"));
        List<Image> userImages = imageService.getImages(0);
        model.addAttribute("userImages", userImages);
        model.addAttribute("mainPage", "userEditor.jsp");
        return "manage/manage";
    }


    //保存用户 保存修改过的用户
    @RequestMapping(value = {"/user/save", "/user/save/{userId}"}, method = RequestMethod.POST)
    public String saveUser(@PathVariable("userId") Optional<Integer> userId, HttpServletRequest request, RedirectAttributes attributes) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userImageId = Integer.parseInt(request.getParameter("userImageId"));
        OperationResult result = null;
        if (userId.isPresent()) {
            result = userService.updateUser(new User(userId.get(), username, MD5Util.encoderPassword(password), userImageId));
        } else {
            result = userService.registerUser(new User(username, MD5Util.encoderPassword(password), userImageId));
        }
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/user";
    }

    //删除用户
    @RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userId") Integer userId, RedirectAttributes attributes) {
        OperationResult result = userService.deleteUser(userId);
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/user";
    }

    //对于关于的管理
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String showAbout(Model model) {
        model.addAttribute("about", aboutService.getAbout());
        model.addAttribute("mainPage", "about.jsp");
        return "manage/manage";
    }

    //修改关于内容
    @RequestMapping(value = "/about/modify", method = RequestMethod.GET)
    public String modifyAbout(Model model) {
        model.addAttribute("about", aboutService.getAbout());
        model.addAttribute("mainPage", "aboutEditor.jsp");
        return "manage/manage";
    }

    //保存关于内容
    @RequestMapping(value = "/about/save", method = RequestMethod.POST)
    public String saveAbout(@RequestParam(value = "content") String content, RedirectAttributes attributes) {
//        String content = request.getParameter("content");
        OperationResult result = aboutService.updateAbout(content);
        attributes.addFlashAttribute("info", result.getInfo());
        return "redirect:/manage/about";
    }

    //退出登录
    @RequestMapping(value = "/quit", method = RequestMethod.GET)
    public String quit(HttpSession session) {
        //session.invalidate(); 使得再次进入后台管理需要输入密码账户 因为账户密码保存在session中
        session.invalidate();
        return "redirect:/blog";
    }

}
