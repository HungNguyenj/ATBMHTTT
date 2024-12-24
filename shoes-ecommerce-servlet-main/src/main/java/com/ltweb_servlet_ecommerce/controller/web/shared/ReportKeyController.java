package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {"/report-key"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2Mb
        maxFileSize = 1024 * 1024 * 10, // 10Mb
        maxRequestSize = 1024 * 1024 * 50 // 50Mb
)
public class ReportKeyController extends HttpServlet {
    @Inject
    IDSService dsService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/report-key.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        try {
            //public key
            Part publicKeyPart = req.getPart("file-publickey");
            String publicKeyContent = readFileContent(publicKeyPart);

            //private key
            Part privateKeyPart = req.getPart("file-privatekey");
            String privateKeyContent = readFileContent(privateKeyPart);

            System.out.println("private: " + privateKeyContent);
            System.out.println("public: " + publicKeyContent);

            String dateString = req.getParameter("date-input");
            if (dateString == null) {
                resp.sendRedirect(req.getContextPath() + "/report-key?message=date_not_valid&toast=danger");
            }
            LocalDate localDate = LocalDate.parse(dateString);

            Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
            System.out.println(timestamp);

            DSModel dsModel = new DSModel();
            dsModel.setUser_id(userModel.getId());
            List<DSModel> list = dsService.findAllWithFilter(dsModel, null);
            if (list.size() == 0) {
                resp.sendRedirect(req.getContextPath() + "/digital-signature");
            }
            System.out.println(list.size());

            //create dsmodel from 2 key import
            DSModel temp = new DSModel();
            temp.setPublic_key(publicKeyContent);
            temp.setPrivate_key(privateKeyContent);

            temp = dsService.findWithFilter(temp);
            System.out.println(temp);

            if (list.contains(temp)) {
                temp.setIsReported(1);
                temp.setReportedAt(timestamp);
                if (temp.getUsedNow() == 1) {
                    temp.setUsedNow(2);
                }
                temp = dsService.update(temp);
                System.out.println(temp);

                resp.sendRedirect(req.getContextPath() + "/report-key?message=report_success&toast=success");
            } else {
                resp.sendRedirect(req.getContextPath() + "/report-key?message=not_found_key&toast=danger");
            }


        } catch (Exception ex) {
            resp.sendRedirect(req.getContextPath() + "/report-key?message=key_not_valid&toast=danger");
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
