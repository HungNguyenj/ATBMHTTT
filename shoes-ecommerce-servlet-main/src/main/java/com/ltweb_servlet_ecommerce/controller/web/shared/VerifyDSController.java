package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

@WebServlet(urlPatterns = {"/verify-ds"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2Mb
        maxFileSize = 1024 * 1024 * 10, // 10Mb
        maxRequestSize = 1024 * 1024 * 50 // 50Mb
)
public class VerifyDSController extends HttpServlet {

    @Inject
    IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared//verify-ds.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Verify
        try {

            Part signPart = req.getPart("signature");
            String signContent = readFileContent(signPart);

            Part publicKeyPart = req.getPart("file-publickey");
            String publicKeyContent = readFileContent(publicKeyPart);

            System.out.println(signContent);
            System.out.println(publicKeyContent);

            String slug = req.getParameter("slug");

            if (signContent.isEmpty() || publicKeyContent.isEmpty() || slug.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/verify-ds?message=field_is_blank&toast=danger");
            } else {
                boolean isVerified = false;

                try {
                    PublicKey publicKey = KeyUtil.base64ToPublicKey(publicKeyContent);

                    dsService.loadPublic(publicKey);

                    String mes = slug;
                    isVerified = dsService.verify(mes, signContent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String verificationResult = isVerified ? "Chữ ký hợp lệ" : "Chữ ký không hợp lệ";
                req.setAttribute("verifymessage", verificationResult);
                req.getRequestDispatcher("/views/shared//verify-ds.jsp").forward(req, resp);
            }
        } catch (Exception ex) {
        resp.sendRedirect(req.getContextPath() + "/verify-ds?message=key_not_valid&toast=danger");
    }


    }

    private String readFileContent(Part filePart) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (InputStream inputStream = filePart.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        }
        return fileContent.toString();
    }
}
