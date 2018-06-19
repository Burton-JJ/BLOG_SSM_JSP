
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp" %>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('select').material_select();
    });
    function check() {
        var title = document.getElementById("title").value;
        var content = CKEDITOR.instances.content.getData();
        var category = document.getElementById("categoryId").value;
        if (title == null || title == "") {
            $("#info").text("标题不能为空！");
            showErrorInfo();
            return false;
        }
        if (content == null || content == "") {
            $("#info").text("内容不能为空！");
            showErrorInfo();
            return false;
        }
        if (category == null || category == "") {
            $("#info").text("类别不能为空！");
            showErrorInfo();
            return false;
        }
        return true;
    }
</script>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <!--若为添加新文章 则article为空  -->
            <form action="/manage/article/save/${article.articleId}" method="post" onsubmit="return check()">
                <div class="input-field col s6">
                    <!-- id="title"作为表单信息传入控制器-->
                    <input id="title" name="title" type="text" class="validate" value="${article.title}">
                    <label for="title"><i class="material-icons left">title</i>标题</label>
                </div>
                <br><br><br><br>
                <div class="input-field col s6">
                    <!-- id="categoryId"作为表单信息传入控制器-->
                    <select id="categoryId" name="categoryId">
                        <option value="" disabled selected>类别</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" ${category.categoryId==article.category.categoryId?'selected':''}>
                                    ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <br><br><br><br>
                <div class="input-field col s6">
                    <!-- imageId作为表单信息传入控制器-->
                    <select id="imageId" name="imageId" class="icons">
                        <option value="" disabled selected>图片</option>
                        <c:forEach var="articleImage" items="${articleImages}">
                            <option value="${articleImage.imageId}"
                                    data-icon="../../images/article/${articleImage.imageName}" ${article.imageId==articleImage.imageId?'selected':'' }>${articleImage.imageName}</option>
                        </c:forEach>
                    </select>
                </div>
                <br><br><br><br>
                <div>
                    <!-- id="content" 作为表单信息传入控制器-->
                    <textarea class="ckeditor" id="content" name="content">${article.content}</textarea>
                </div>
                <br>
                <button class="btn waves-effect waves-light green" type="submit" name="action">保存</button>
            </form>
        </div>
    </div>
</div>