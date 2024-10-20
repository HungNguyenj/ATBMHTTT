<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="author" content="Untree.co">
    <link rel="shortcut icon" href="<c:url value="https://www.nike.com/favicon.ico"/> ">

    <meta name="description" content="" />
    <title><dec:title default="Nai Login"/></title>
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
    />
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <link href="<c:url value="/template/web/css/custom.css"/>" rel="stylesheet">
</head>
<body>


<div class="container">
    <dec:body/>
</div>
<script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"/>"></script>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"/>"></script>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.umd.min.js"
></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<c:if test="${not empty toast && not empty message}">
    <script>
        window.addEventListener("DOMContentLoaded",function (){
            Toastify({
                text: '${message}',
                duration: 3000,
                className: "bg-${toast}",
                close: true,
                gravity: "top", // `top` or `bottom`
                position: "right",
            }).showToast();
        })
    </script>

</c:if>
</body>
</html>