package tech.acodesigner.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.entity.User;
import tech.acodesigner.service.UserService;
import tech.acodesigner.util.MD5Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = {RequestMethod.GET})
    public String showLoginView(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("curUser");
        if (user != null) {
            return "redirect:/manage";
        } else {
            return "login/login";
        }
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String checkUser(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();

        //破解密码登录后台先不用MD5加密 不然密码不知道
        User user = new User(request.getParameter("username"), MD5Util.encoderPassword(request.getParameter("password")));
        //User user = new User(request.getParameter("username"), request.getParameter("password"));
        OperationResult<UserDto> result = userService.checkUser(user);
        if (result.isSuccess()) {
            session.setAttribute("curUser", result.getData());

//            if (result.getData().getUserType() == 0) {
//                return "redirect:/blog";
//            } else {
//                return "redirect:/manage";
//            }

            //记住密码功能 cookie实现
            String isUseCookie = request.getParameter("isUseCookie");
            System.out.println("记住密码："+isUseCookie);
            if(isUseCookie!= null){
                Cookie usernameCookie = new Cookie("username",request.getParameter("username"));
                Cookie passwordCookie = new Cookie("password",request.getParameter("password"));
                //有效期10天
                usernameCookie.setMaxAge(60*60*24*10);
                passwordCookie.setMaxAge(60*60*24*10);
//                usernameCookie.setPath("/");
//                passwordCookie.setPath("/");
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else{
                Cookie []cookies = request.getCookies();
                for(Cookie cookie:cookies){
                    if(cookie.getName().equals("username")||cookie.getName().equals("password")){
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
            return "redirect:/manage";
        } else {
            attributes.addFlashAttribute("info", result.getInfo());
            return "redirect:/login";//info为用户名或密码错误
        }
    }

}
