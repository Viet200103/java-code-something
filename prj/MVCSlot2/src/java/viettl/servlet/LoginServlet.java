/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import viettl.data.registration.RegistrationDto;
import viettl.data.registration.RegistrationDao;

/**
 *
 * @author viett
 */
public class LoginServlet extends HttpServlet {

    private static final String INVALID_PAGE = "invalid.html";
    private static final String SEARCH_PAGE = "search.jsp";

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

        //1. Get all client information
        String userName = request.getParameter("input_username");
        String password = request.getParameter("input_password");

        String url = INVALID_PAGE;

        try {
            //2. cal model
            //2.1 New DAO object
            RegistrationDao rDao = new RegistrationDao();
            //2.2 Call method of DAO
            RegistrationDto registrationDTO = rDao.checkLogin(userName, password);
            //3. process result
            // 4. Create DAO
            // 5. Create DTO if need
            // 6. Forward other resource to process
            
            // login thành công thì phải có welcome và logout
            if (registrationDTO != null) {
                url = SEARCH_PAGE;
                
                HttpSession session = request.getSession();
                session.setAttribute("userInfo", registrationDTO);
                
                Cookie cookie = new Cookie(userName, password);
                cookie.setMaxAge(60 * 5);
                response.addCookie(cookie);
            }

        } catch (ClassNotFoundException ex) {
            log("CreateAccountServlet _ ClassNotFoundException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateAccountServlet _ SQLException: " + ex.getMessage());
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
