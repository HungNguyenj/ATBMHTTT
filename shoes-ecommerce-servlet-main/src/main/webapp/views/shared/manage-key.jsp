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
<%DSModel dsModel = (DSModel) request.getAttribute("dsModel");
    if (dsModel == null) {
        dsModel = new DSModel();
        dsModel.setPrivate_key("");
        dsModel.setPublic_key("");
    }%>
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

                    <div class="form-group">
                        <label for="privatekey" class="form-label">Private key</label>
                        <input class="form-control" type="text" name="privatekey" id="privatekey" readonly
                               value="<%=dsModel.getPrivate_key()%>">
                    </div>
                </div>
                <div class="main-account-body-row mt-3">
                        <button id="new-keys" class="btn btn-primary" type="button">
                            <a href="/digital-signature" style="color: white; text-decoration: none;">Tạo Key mới</a>
                        </button>
                </div>
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
                            <a href="#" style="color: white; text-decoration: none;">Report</a>
                        </button>
                </div>

            </div>
        </form>

        <!-- Form tạo file và tải về -->
        <form action="/download-keys" method="post">
            <div class="form-group mt-4">
                <input class="form-control" type="hidden" name="savepublickey" id="savepublickey" readonly
                       value="<%=dsModel.getPublic_key()%>">
                <input class="form-control" type="hidden" name="saveprivatekey" id="saveprivatekey" readonly
                       value="<%=dsModel.getPrivate_key()%>">
                <button type="submit" class="btn btn-success">Tải xuống khóa (Public & Private)</button>
            </div>
        </form>


    </div>
</div>


<!--===============================================================================================-->

</body>

</html>
