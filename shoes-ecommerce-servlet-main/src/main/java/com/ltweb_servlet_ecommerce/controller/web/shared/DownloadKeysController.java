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
        String typedownload = req.getParameter("typedownload");
        String publickey = req.getParameter("savepublickey");
        String privatekey = req.getParameter("saveprivatekey");

        if (publickey.isEmpty() || privatekey.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/manage-key?message=key_not_valid&toast=danger");
        } else {
            if (typedownload.equals("publickey")) {
                String filename = "public_key.txt";
                String fileContent = publickey;

                resp.setContentType("text/plain");
                resp.setHeader("Content-Disposition", "attachment;filename=" + filename);

                // Ghi nội dung vào file
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.write(fileContent.getBytes());
                    out.flush();
                }
            } else if (typedownload.equals("privatekey")) {
                String filename = "private_key.txt";
                String fileContent = privatekey;

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
}
