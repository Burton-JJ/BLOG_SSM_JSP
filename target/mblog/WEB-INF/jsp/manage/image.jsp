<%--
  Created by IntelliJ IDEA.
  User: 张秦遥
  Date: 2017/2/22/0022
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('.carousel').carousel();
    });
</script>
<%--------------------------------------------------------------------------%>








<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <div class="carousel">
                <c:forEach var="userImage" items="${userImages}">
                    <div class="carousel-item black-text center card">
                        <div class="card-image">
                            <img src="/images/user/${userImage.imageName}">
                            <%--<span class="card-title">用户图片：${userImage}</span>--%>
                            <a class="btn-floating halfway-fab waves-effect waves-light red" href="/manage/image/delete/${userImage.imageId}"><i class="material-icons" >delete</i></a>
                        </div>
                        <div class="card-content">
                            <p href="/manage/image/delete/${userImage.imageId}">用户图片：${userImage.imageName}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="carousel">
                <c:forEach var="articleImage" items="${articleImages}">
                    <div class="carousel-item black-text center card">
                        <div class="card-image">
                            <img src="/images/article/${articleImage.imageName}">
                            <%--<span class="card-title">文章图片：${articleImage}</span>--%>
                            <a class="btn-floating halfway-fab waves-effect waves-light red" href="/manage/image/delete/${articleImage.imageId}"><i class="material-icons" >delete</i></a>
                        </div>
                        <div class="card-content">
                            <p href="/manage/image/delete/${articleImage.imageId}">文章图片：${articleImage.imageName}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <form name="uploadForm" method="POST" enctype="multipart/form-data" action="/manage/image/saveUserImage">
                用户图片:<input type="file" name="upload" size="30"/>

                <input class="btn btn-primary" type="submit" name="submit" value="上传">
            </form>


            <form name="uploadForm" method="POST" enctype="multipart/form-data" action="/manage/image/saveArticleImage">

                    文章图片:<input type="file" name="upload" size="30"/>
                    <input class="btn btn-primary" type="submit" name="submit" value="上传">
            </form>

        </div>
    </div>
</div>


