
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tag.jsp" %>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <div class="row center">
                <div class="col s4">名称</div>
                <div class="col s1">时间</div>
                <div class="col s7">操作</div>
            </div>
            <hr>

            <ul class="collapsible popout">
                <c:forEach var="article" items="${articles}">
                    <li>
                        <div class="collapsible-header">
                            <div class="row center">
                                <div class="col s3">
                                        ${article.title }
                                </div>
                                <div class="col s3">
                                    <fmt:formatDate value="${article.pubDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                                <div class="col s6">
                                    <a href="/manage/article/modify/${article.articleId}"
                                       class="waves-effect waves-light btn green hoverable">修改</a>
                                    <a class="waves-effect waves-light btn red hoverable"
                                       href="/manage/article/delete/${article.articleId}">删除</a>
                                </div>
                            </div>
                        </div>
                        <!-- 文章界面点击文章名 下面会显示 -->
                        <div class="collapsible-body">
                            <p>${article.content}</p>
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <br>
            <div class="center">
                <a href="/manage/article/write" class="waves-effect waves-light btn green hoverable">写文章</a>
            </div>
        </div>
    </div>
</div>