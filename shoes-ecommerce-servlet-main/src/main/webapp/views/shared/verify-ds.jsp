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
            <form class="form" role="form" autocomplete="off" method="post" action="/digital-signature">
                <div class="form-group">
                    <label for="signature">Chữ ký của bạn</label>
                    <input type="text" class="form-control" id="signature" name="mysignature">
                </div>
<%--                <div class="form-group">--%>
<%--                    <label for="signature">Chữ ký của bạn</label>--%>
<%--                    <input type="text" class="form-control" id="signature" name="mysignature">--%>
<%--                </div>--%>
                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-success btn-lg float-right">Xác minh</button>
                </div>
            </form>
            <a href="/digital-signature" class="text-body">Tạo chữ ký</a>
<%--            <button type="submit" class="btn btn-success btn-lg float-right">Private key</button>--%>
<%--            <button type="submit" class="btn btn-success btn-lg float-right">Public key</button>--%>
        </div>
    </div>
</div>
    <!-- /form card change password -->
</body>
</html>