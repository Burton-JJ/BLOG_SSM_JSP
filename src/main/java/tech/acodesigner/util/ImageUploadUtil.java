package tech.acodesigner.util;


/**
 *
 * @description
 * @author Burton
 * @date 2018/5/15 20:13
 * @param
 * @return
 *
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Image;
import tech.acodesigner.service.ImageService;


/**
 * @program: Blog_SSM
 * @description: ckeditor 图片上传
 * @author: Burton_J
 * @create: 2018-05-14 23:25
 **/
public class ImageUploadUtil {

    // 图片类型
    private static List<String> fileTypes = new ArrayList<>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    /**
     * 图片上传
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @Title upload
     */
    public static String upload(HttpServletRequest request, String dirPath, MultipartFile file, int type,
                                RedirectAttributes attributes) throws IllegalStateException,
            IOException {

        String myFileName = null;
        // 取得当前上传文件的文件名称
        myFileName = file.getOriginalFilename();
        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
        if (myFileName.trim() != "") {
            // 获得图片的原始名称
            String originalFilename = file.getOriginalFilename();
            // 获得图片后缀名称,如果后缀不为图片格式，则不上传
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            if (!fileTypes.contains(suffix)) {
                //todo
            }

            // 上传的文件夹
            File dir = new File(dirPath);
            if (dir == null || !dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }

            //完全路劲 下面的路劲在windows中用“\\” liunx用如下“/”
            String imgPath = dirPath + "/" + originalFilename;
            // 定义上传路径 .../upload/111112323.jpg
            File dest = new File(imgPath);
            System.out.println("上传的位置：" + imgPath);
            if (!dest.exists()) {
                try {
                    dest.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.transferTo(dest);
        }

        //上传文件名到数据库
        //图片名存入数据库
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        ImageService imageService = (ImageService)context.getBean("ImageService");
        Image image = new Image(type,myFileName);
        OperationResult result = null;
        result =imageService.saveImage(image);
        attributes.addFlashAttribute("info", result.getInfo());


        return myFileName;
    }

    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @param request
     * @param response
     * @param DirectoryName 文件上传目录：比如upload(无需带前面的/) upload/..
     * @throws IOException
     * @Title ckeditor
     */
    public static void ckeditor(HttpServletRequest request, HttpServletResponse response, String DirectoryName,
                                MultipartFile file, int type,RedirectAttributes attributes)
            throws IOException {

        System.out.println("1111111");
        String myFileName = upload(request, DirectoryName, file ,type ,attributes);
        System.out.println("2222222");
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        // request.getContextPath()得到项目名 例如本项目 得到 /Blog_SSM
        //完全路劲 下面的路劲在windows中用“\\” liunx用如下“/”
        String imageContextPath = DirectoryName + "/" + myFileName;
        String DirectoryName1 = "images/article";
        String imageContextPath1 = request.getContextPath() + "/" + DirectoryName1 + "/" + myFileName;
        System.out.println( " request.getContextPath()="+request.getContextPath());
        System.out.println("request.getContextPath() + \"/\" + DirectoryName1 + \"/\" + myFileName;="+imageContextPath1);
        //System.out.println(imageContextPath1);
        response.setContentType("text/html;charset=UTF-8");
        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath1 + "',''" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }
}
