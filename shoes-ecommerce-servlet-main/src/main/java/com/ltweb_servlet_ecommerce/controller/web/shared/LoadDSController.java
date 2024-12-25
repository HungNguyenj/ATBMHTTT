package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.impl.DSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

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
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/load-ds"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2Mb
        maxFileSize = 1024 * 1024 * 10, // 10Mb
        maxRequestSize = 1024 * 1024 * 50 // 50Mb
)
public class LoadDSController extends HttpServlet {
    IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/load-ds.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        } else {
            //load
            //public key
            Part publicKeyPart = req.getPart("publickey");
            String publicKeyContent = readFileContent(publicKeyPart);

            //private key
            Part privateKeyPart = req.getPart("privatekey");
            String privateKeyContent = readFileContent(privateKeyPart);

            System.out.println("private: " + privateKeyContent);
            System.out.println("public: " + publicKeyContent);

            if (publicKeyContent.isEmpty() || privateKeyContent.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/load-ds?message=field_is_blank&toast=danger");
            } else {
                try {
                    dsService = new DSService();

                    PublicKey publicKey = KeyUtil.base64ToPublicKey(publicKeyContent);
                    PrivateKey privateKey = KeyUtil.base64ToPrivateKey(privateKeyContent);

                    dsService.loadPublic(publicKey);
                    dsService.loadPrivate(privateKey);

                    DSModel dsModel = new DSModel();
                    dsModel.setUser_id(userModel.getId());
                    dsModel.setUsedNow(1);

                    DSModel tempModel = dsService.findWithFilter(dsModel);
                    DSModel temp;

                    if (tempModel == null) {
                        dsModel.setPublic_key(publicKeyContent);
                        dsModel.setPrivate_key(privateKeyContent);

                        temp = dsService.save(dsModel);
                    } else {
                        tempModel.setUsedNow(2);
                        tempModel.setExpiredTime(tempModel.getExpiredTime());
                        tempModel = dsService.update(tempModel);

                        dsModel.setPublic_key(publicKeyContent);
                        dsModel.setPrivate_key(privateKeyContent);

                        temp = dsService.update(dsModel);
                    }
                    System.out.println(temp.toString());

                    resp.sendRedirect(req.getContextPath() + "/user-info?message=update_info_success&toast=success");

                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchProviderException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath() + "/manage-key?message=key_not_valid&toast=danger");
                }
            }
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
