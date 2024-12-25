<%@ page import="com.ltweb_servlet_ecommerce.model.UserModel" %>
<%@ page import="com.ltweb_servlet_ecommerce.model.DSModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Keys</title>
    <!-- Meta Tag -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name='copyright' content=''>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Web Font -->
    <link href="https://fonts.googleapis.com/css?family=Poppins:200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<%UserModel userModel = (UserModel) request.getAttribute("userModel");%>
<%DSModel dsModel = (DSModel) request.getAttribute("dsModel");%>
<div class="container col-md-4" id="account-user">
    <div class="main-account">
        <div class="main-account-header" style="margin-top: 100px;">
            <h3>Quản lý key người dùng</h3>
            <p style="font-size: 20px;">Xin chào <%= userModel.getFullName()%></p>
        </div>

        <form>
            <div class="main-account-body">
                <div class="main-account-body-col">

                    <div class="form-group">
                        <label for="publickey" class="form-label">Public key</label>
                        <input class="form-control" type="text" name="publickey" id="publickey" readonly
                               value="<%=dsModel.getPublic_key()%>">
                    </div>

<%--                    <div class="form-group">--%>
<%--                        <label for="privatekey" class="form-label">Private key</label>--%>
<%--                        <input class="form-control" type="text" name="privatekey" id="privatekey" readonly--%>
<%--                               value="<%=dsModel.getPrivate_key()%>">--%>
<%--                    </div>--%>
                </div>
            </div>
        </form>

        <form id="keyForm" action="/digital-signature" method="post">
            <div class="main-account-body-row mt-3">
<%--                <button id="new-keys" class="btn btn-primary" type="button">--%>
<%--                    <a href="/digital-signature" style="color: white; text-decoration: none;">Tạo Key mới</a>--%>
<%--                </button>--%>
                <button type="button" class="btn btn-primary btn-lg float-right" onclick="submitForm()">Tạo key</button>
            </div>
        </form>

        <div class="main-account-body-row mt-3">
            <button id="load-keys" class="btn btn-primary" type="button">
                <a href="/load-ds" style="color: white; text-decoration: none;">Tải Key lên</a>
            </button>
        </div>
        <div class="main-account-body-row mt-3">
            <button id="verify-keys" class="btn btn-primary" type="button">
                <a href="/verify-ds" style="color: white; text-decoration: none;">Xác minh chữ ký</a>
            </button>
        </div>
        <div class="main-account-body-row mt-3">
            <button id="report-keys" class="btn btn-danger" type="button">
                <a href="/report-key" style="color: white; text-decoration: none;">Báo cáo lộ Key</a>
            </button>
        </div>

        <!-- Form tạo file và tải về -->
        <form action="/download-keys" method="post">
            <div class="form-group mt-4">
                <input class="form-control" type="hidden" name="savepublickey" id="savepublickey" readonly
                       value="<%=dsModel.getPublic_key()%>">
                <input class="form-control" type="hidden" name="saveprivatekey" readonly
                       value="<%=dsModel.getPrivate_key()%>">
                <input class="form-control" type="hidden" name="typedownload" readonly
                       value="publickey">
                <button type="submit" class="btn btn-success">Tải xuống khóa Công khai (Public Key)</button>
            </div>
        </form>
<%--        <form action="/download-keys" method="post">--%>
<%--            <div class="form-group mt-4">--%>
<%--                <input class="form-control" type="hidden" name="savepublickey" readonly--%>
<%--                       value="<%=dsModel.getPublic_key()%>">--%>
<%--                <input class="form-control" type="hidden" name="saveprivatekey" id="saveprivatekey" readonly--%>
<%--                       value="<%=dsModel.getPrivate_key()%>">--%>
<%--                <input class="form-control" type="hidden" name="typedownload" readonly--%>
<%--                       value="privatekey">--%>
<%--                <button type="submit" class="btn btn-success">Tải xuống khóa riêng tư (Private Key)</button>--%>
<%--            </div>--%>
<%--        </form>--%>


    </div>
</div>

<script>
    function submitForm() {
        // Gửi form
        document.getElementById('keyForm').submit();

        // Làm mới trang sau khi form được gửi
        setTimeout(() => {
            window.location.reload();
        }, 1000); // Đợi 1 giây để server xử lý
    }
</script>


<!--===============================================================================================-->

</body>

</html>
