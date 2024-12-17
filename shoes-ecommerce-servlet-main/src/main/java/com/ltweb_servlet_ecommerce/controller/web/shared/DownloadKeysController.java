package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/download-keys"})
public class DownloadKeysController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String publickey = req.getParameter("savepublickey");
        String privatekey = req.getParameter("saveprivatekey");

        if (publickey.isEmpty() || privatekey.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/digital-signature");
        } else {
            String filename = "keys.txt";
            String fileContent = "Public key: " + publickey + "\n" + "\n"
                                + "Private key: " + privatekey;

            // Set response header để tải file về máy
            resp.setContentType("text/plain");
            resp.setHeader("Content-Disposition", "attachment;filename=" + filename);

            // Ghi nội dung vào file
            try (ServletOutputStream out = resp.getOutputStream()) {
                out.write(fileContent.getBytes());
                out.flush();
            }
        }
    }
}
