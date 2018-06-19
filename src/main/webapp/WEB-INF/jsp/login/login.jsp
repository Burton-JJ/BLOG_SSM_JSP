
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp" %>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            if (${not empty info})
                showErrorInfo();
        });
        function showErrorInfo() {
            $("div.info").fadeIn(1000, function () {
                $("div.info").delay(1000).fadeOut(500);
            });
        }
        function check() {
            var username = $("#username").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            if (username == "") {
                $("#info").text("用户名不能为空！");
                showErrorInfo();
                return false;
            }
            if (password == "") {
                $("#info").text("密码不能为空！");
                showErrorInfo();
                return false;
            }
            if (confirmPassword == "") {
                $("#info").text("确认密码不能为空！");
                showErrorInfo();
                return false;
            }
            if (password != null && confirmPassword != null && password != "" && password != confirmPassword) {
                $("#info").text("两次密码输入不一致！");
                showErrorInfo();
                return false;
            }
            return true;
        }
    </script>
</head>
<body class="grey lighten-4">

<div class="row">
    <div class="col s4"></div>
    <div class="col s4">

        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <div class="card-panel hoverable">
            <div class="center-align" style="margin-bottom: 40px">
                <h4><a href="blog" class="brand-logo indigo-text darken-3">Burton's Blog</a></h4>
            </div>

            <form id="loginForm" method="post" action="/login/submit" onsubmit="return check()">
                <%
                    String username = "";
                    String password = "";
                    Cookie[] cookies = request.getCookies();
                    System.out.println(cookies.length);
                    if(cookies !=null && cookies.length!= 0){
                        for(Cookie cookie : cookies){

                            System.out.println("cookie的name和value："+cookie.getName()+"  "+cookie.getValue());
                            if(cookie.getName().equals("username")){
                                username = cookie.getValue();
                                System.out.println("111");
                                System.out.println("username:"+username);
                            }
                            if(cookie.getName().equals("password")){
                                password = cookie.getValue();
                            }
                        }
                    }
                %>
                <div class="input-field">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="username" name="username" type="text" class="validate" value="<%=username%>">
                    <label for="username">用户名</label>
                </div>
                <div class="input-field">
                    <i class="material-icons prefix">lock</i>
                    <input id="password" name="password" type="password" class="validate" value="<%=password%>">
                    <label for="password">密码</label>
                </div>
                <div class="switch ">
                    <label>

                        <input type="checkbox" name="isUseCookie" value="rememberMe">
                        <span class="lever"></span>
                        10天内记住密码
                    </label>
                </div>

                <div class="center-align">

                    <button class="btn waves-effect waves-light hoverable" type="submit" name="action">登录
                        <i class="material-icons right">send</i>
                    </button>

                </div>


            </form>
        </div>

        <%@ include file="../common/info.jsp" %>
    </div>
    <div class="col s4"></div>
</div>

</body>
</html>
