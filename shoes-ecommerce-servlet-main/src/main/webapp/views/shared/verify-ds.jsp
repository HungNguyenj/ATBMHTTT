<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 3/29/2024
  Time: 1:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chữ ký điện tử</title>
</head>
<body>
<div class="col-md-6 offset-md-3">
    <span class="anchor" id="formChangePassword"></span>
    <hr class="mb-5">

    <!-- form card change password -->
    <div class="card card-outline-secondary">
        <div class="card-header">
            <h3 class="mb-0">Xác minh chữ ký</h3>
            <%
                String error = (String)(request.getAttribute("error"));
                if (error != null) {
                    request.getAttribute("error");
                } else {
                    error = "";
                }
            %>
            <p style="margin: 5px 0px; color: #fc1616"> <%= error%> </p>
        </div>
        <div class="card-body">
            <form class="form" role="form" autocomplete="off" method="post" action="/verify-ds" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="slug">Mã đơn hàng của bạn</label>
                    <input type="text" class="form-control" id="slug" name="slug">
                </div>
                <div class="form-group">
                    <label for=file-sign class="form-label">Tải Chữ ký</label>
                    <input class="form-control" type="file" name="signature" id="file-sign">
                </div>
                <div class="form-group">
                    <label for="file-publickey" class="form-label">Tải Public Key</label>
                    <input class="form-control" type="file" name="file-publickey" id="file-publickey">
                </div>
<%--                <div class="form-group">--%>
<%--                    <label for="publickey">Public key của bạn</label>--%>
<%--                    <input type="text" class="form-control" id="publickey" name="mypublickey">--%>
<%--                </div>--%>
                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-success btn-lg float-right">Xác minh</button>
                </div>
                <div class="form-group">
                    <label for="verifymessage">Xác nhận chữ ký</label>
                    <input
                            type="text"
                            class="form-control"
                            id="verifymessage"
                            name="verifymessage"
                            readonly
                        <% if (request.getAttribute("verifymessage") != null && !request.getAttribute("verifymessage").toString().isEmpty()) { %>
                            value="<%= request.getAttribute("verifymessage") %>"
                        <% } %>
                    >
                </div>
            </form>
            <div><a href="/manage-key" class="text-body">Quản lý key</a></div>

<%--            <button type="submit" class="btn btn-success btn-lg float-right">Private key</button>--%>
<%--            <button type="submit" class="btn btn-success btn-lg float-right">Public key</button>--%>
        </div>
    </div>
</div>
    <!-- /form card change password -->
</body>
</html>
