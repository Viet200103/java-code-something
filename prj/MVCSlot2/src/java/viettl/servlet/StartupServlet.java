/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import viettl.data.registration.RegistrationDao;
import viettl.data.registration.RegistrationDto;

/**
 *
 * @author viett
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String SEARCH_PAGE = "search.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {

            HttpSession session = request.getSession(false);
            Cookie[] cookies = request.getCookies();

            //2 traversal cookies
            if (session == null && cookies != null) {
                for (Cookie cookie : cookies) {
                    //3 get username and password from name value
                    String username = cookie.getName();
                    String password = cookie.getValue();

                    // 4 call DAO dao
                    RegistrationDao dao = new RegistrationDao();

                    RegistrationDto registrationDTO;
                    try {
                        registrationDTO = dao.checkLogin(username, password);
                        session = request.getSession();
                        session.setAttribute("userInfo", registrationDTO);
                    } catch (ClassNotFoundException ex) {
                        log("CreateAccountServlet _ ClassNotFoundException: " + ex.getMessage());
                    } catch (SQLException ex) {
                        log("CreateAccountServlet _ SQLException: " + ex.getMessage());
                    }

                }
            }
            if (session != null && session.getAttribute("userInfo") != null) {
                url = SEARCH_PAGE;
            }
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
