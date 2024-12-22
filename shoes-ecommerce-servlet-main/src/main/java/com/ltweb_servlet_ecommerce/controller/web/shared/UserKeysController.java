package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/manage-key"})
public class UserKeysController extends HttpServlet {
    @Inject
    private IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel != null) {
            try {
                DSModel temp = new DSModel();
                temp.setUser_id(userModel.getId());
                List<DSModel> listDS = dsService.findAllWithFilter(temp, null);
                DSModel dsModel = new DSModel();
                dsModel.setPublic_key("");
                dsModel.setPrivate_key("");
                for (DSModel ds : listDS) {
                    if (ds.getUsed() == 1) {
                        dsModel = ds;
                    }
                }
                System.out.println(listDS.size());
                System.out.println(dsModel.toString());

                req.setAttribute("userModel", userModel);
                req.setAttribute("dsModel", dsModel);
                RequestDispatcher rd = req.getRequestDispatcher("/views/shared/manage-key.jsp");
                rd.forward(req, resp);
            } catch (SQLException e) {
                resp.sendRedirect(req.getContextPath() + "/sign-in");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //report key
    }
}
