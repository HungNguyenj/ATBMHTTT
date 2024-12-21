<%@ page import="com.ltweb_servlet_ecommerce.model.OrderModel" %>
<%@ page import="com.ltweb_servlet_ecommerce.model.UserModel" %>
<%@ page import="com.ltweb_servlet_ecommerce.utils.SessionUtil" %>
<%@ page import="com.ltweb_servlet_ecommerce.model.DSModel" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.IDSService" %>
<%@ page import="com.ltweb_servlet_ecommerce.service.impl.DSService" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.security.NoSuchProviderException" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 3/29/2024
  Time: 1:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chữ ký điện tử cho đơn hàng</title>
</head>
<body>
<div class="col-md-6 offset-md-3">
    <span class="anchor" id="formChangePassword"></span>
    <hr class="mb-5">

    <!-- form card change password -->
    <div class="card card-outline-secondary">
        <div class="card-header">
            <h3 class="mb-0">Ký vào đơn hàng</h3>
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
            <% OrderModel orderModel = (OrderModel) request.getAttribute("order");%>
            <%
                DSModel dsModel;
                try {
                    UserModel userModel = (UserModel) SessionUtil.getValue(request, "USER_MODEL");
                    IDSService dsService = new DSService();
                    DSModel temp = new DSModel();
                    temp.setUser_id(userModel.getId());
                    dsModel = dsService.findWithFilter(temp);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchProviderException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            %>
            <form class="form" role="form" autocomplete="off" method="post" action="/sign-order">
                <div class="form-group">
                    <label for="slug">Mã đơn của bạn</label>
                    <input type="text" class="form-control" id="slug" name="slug" readonly value="<%=orderModel.getSlug()%>">
                </div>
                <div class="form-group">
                    <label for="privatekey">Private key của bạn</label>
                    <input type="text" class="form-control" id="privatekey" name="privatekey" readonly value="<%=dsModel.getPrivate_key()%>">
                </div>
                <div class="form-group">
                    <label for="publickey">Public key của bạn</label>
                    <input type="text" class="form-control" id="publickey" name="publickey" readonly value="<%=dsModel.getPublic_key()%>">
                </div>
                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-success btn-lg float-right" name="handlesign" value="true">Ký</button>
                    <button type="submit" class="btn btn-cancel btn-lg float-right" name="handlesign" value="false">Bỏ qua</button>
                </div>
            </form>

        </div>
    </div>
</div>
    <!-- /form card change password -->
</body>
</html>
