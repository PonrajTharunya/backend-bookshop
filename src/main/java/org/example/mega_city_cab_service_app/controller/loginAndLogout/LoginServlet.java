//package org.example.mega_city_cab_service_app.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.example.mega_city_cab_service_app.dao.UserDAO;
//import org.example.mega_city_cab_service_app.model.User;
//import org.example.mega_city_cab_service_app.util.JsonUtils;
//
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@WebServlet(name = "LoginServlet", value = "/LoginSystem")
//public class LoginServlet extends HttpServlet {
//    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            String jsonInput = readRequestBody(req);
//            String email = JsonUtils.extractValueFromJson(jsonInput, "email");
//            String password = JsonUtils.extractValueFromJson(jsonInput, "password");
//
//            if (email == null || password == null) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                resp.getWriter().write("{\"error\": \"Email and password are required\"}");
//                return;
//            }
//
//            UserDAO userDAO = new UserDAO();
//            User user = userDAO.findByEmail(email);
//
//            if (user != null && validatePassword(password, user)) {
//                HttpSession session = req.getSession();
//                session.setAttribute("role", user.getRole());
//                session.setAttribute("email", user.getEmail());
//                resp.getWriter().write("{\"message\": \"Login successful\"}");
//            } else {
//                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                resp.getWriter().write("{\"error\": \"Invalid credentials\"}");
//            }
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Internal server error", e);
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("{\"error\": \"Internal server error\"}");
//        }
//    }
//
//    private boolean validatePassword(String inputPassword, User user) {
//        // Compare plaintext passwords
//        return inputPassword.equals(user.getPassword());
//    }
//
//    private String readRequestBody(HttpServletRequest req) throws IOException {
//        StringBuilder buffer = new StringBuilder();
//        String line;
//        try (var reader = req.getReader()) {
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line);
//            }
//        }
//        return buffer.toString();
//    }
//}

package org.example.mega_city_cab_service_app.controller.loginAndLogout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.mega_city_cab_service_app.dao.loginuserDAO;
import org.example.mega_city_cab_service_app.model.User;
import org.example.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", value = "/LoginSystem")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private final loginuserDAO userDAO = new loginuserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Read JSON input
            String jsonInput = readRequestBody(req);
            String email = JsonUtils.extractValueFromJson(jsonInput, "email");
            String password = JsonUtils.extractValueFromJson(jsonInput, "password");

            // Validate input
            if (email == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Email and password are required\"}");
                return;
            }

            // Authenticate user
            User user = userDAO.findByEmail(email);
            if (user != null && validatePassword(password, user)) {
                // Set session attributes
                HttpSession session = req.getSession();
                session.setAttribute("role", user.getRole());
                session.setAttribute("email", user.getEmail());
                resp.getWriter().write("{\"message\": \"Login successful\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\": \"Invalid credentials\"}");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }

    private boolean validatePassword(String inputPassword, User user) {
        // Compare plaintext passwords
        return inputPassword.equals(user.getPassword());
    }

    private String readRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        try (var reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }
}