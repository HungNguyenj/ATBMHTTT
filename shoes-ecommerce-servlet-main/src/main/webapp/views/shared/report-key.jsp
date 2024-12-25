<%@ page import="com.ltweb_servlet_ecommerce.model.UserModel" %>
<%@ page import="com.ltweb_servlet_ecommerce.model.DSModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Report Keys</title>
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

<div class="container col-md-4" id="account-user">
    <div class="main-account">
        <div class="main-account-header" style="margin-top: 100px;">
            <h3>Báo cáo lộ Key</h3>
        </div>

        <form class="form" role="form" autocomplete="off" method="post" action="/report-key" enctype="multipart/form-data">
            <div class="main-account-body">
                <div class="main-account-body-col">

                    <div class="form-group">
                        <label for="file-publickey" class="form-label">Upload Public Key</label>
                        <input class="form-control" type="file" name="file-publickey" id="file-publickey">
                    </div>

                    <div class="form-group">
                        <label for="file-privatekey" class="form-label">Upload Private Key</label>
                        <input class="form-control" type="file" name="file-privatekey" id="file-privatekey">
                    </div>

                    <div class="form-group">
                        <label for="date-input" class="form-label">Thời điểm khóa bị lộ</label>
                        <input class="form-control" type="date" name="date-input" id="date-input">
                    </div>

                    <div class="form-group mt-3">
                        <button type="submit" class="btn btn-success btn-lg float-right">Báo cáo</button>
                    </div>
                </div>
            </div>
        </form>


    </div>
</div>


<!--===============================================================================================-->

</body>

</html>
