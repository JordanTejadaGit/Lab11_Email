/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

/**
 *
 * @author 694952
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        String code = request.getParameter("uuidPassword");


        if (code != null) {
            try {
                User found = as.uuidPassword(code);
                if (found != null) {
                    session.setAttribute("resetPassword", true);
                    session.setAttribute("resetUserPassword", found);
                }
            } catch (Exception ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            session.removeAttribute("resetPassword");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = new User();
        AccountService as = new AccountService();

        String action = request.getParameter("action");
        String emailReset = "jordantejada.school+" + request.getParameter("resetEmail");
        String newPassword = request.getParameter("resetPassword");

        if (action != null) {
            if (action.equals("reset")) {
                UserDB userDB = new UserDB();
                try {
                    user = userDB.get(emailReset);
                    String url = request.getRequestURL().toString();
                    String path = getServletContext().getRealPath("/WEB-INF");
                    try {
                        as.resetPassword(user.getEmail(), path, url);
                        session.setAttribute("message", "Check your email for reset link!");
                    } catch (Exception ex) {
                        Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                        session.setAttribute("message", "Error");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                    session.setAttribute("message", "Error");
                }

            }
            if (action.equals("newPasswordSubmit")) {
                user = (User) session.getAttribute("resetUserPassword");
                try {
                    as.newResetPassword(user.getEmail(), newPassword);
                    session.setAttribute("message", "Reset Password Successful");
                    session.removeAttribute("resetPassword");
                    session.removeAttribute("resetUserPassword");
                } catch (Exception ex) {
                    Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                    session.setAttribute("message", "Error");
                }
            }
        }
        response.sendRedirect("login");
    }
}


