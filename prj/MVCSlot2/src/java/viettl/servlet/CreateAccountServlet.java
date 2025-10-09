/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import viettl.data.registration.RegistrationCreateError;
import viettl.data.registration.RegistrationDto;
import viettl.data.registration.RegistrationDao;

/**
 *
 * @author viett
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String ERROR_PAGE = "createaccount.jsp";
    private final String LOGIN_PAGE = "login.html";

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

        //1. get all param
        String username = request.getParameter("input_username");
        String password = request.getParameter("input_password");
        String confirm = request.getParameter("input_confirm");
        String fullName = request.getParameter("input_fullname");
        
        boolean foundError = false;
        RegistrationCreateError errors = new RegistrationCreateError();

        String url = ERROR_PAGE;
        
        try {
            //2. handle all error of user - bắt lỗi một lần, cofirm chỉ bắt khi không đúng

            if (username.trim().length() < 6 || username.trim().length() > 20) {
                foundError = true;
                errors.setUsernameLengthError("Username is required type from 6 to 20 characters");
            }

            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundError = true;
                errors.setPasswordLengthError("Password is required type from 6 to 30 characters");
            } else if (!confirm.trim().equals(password.trim())) {
                foundError = true;
                errors.setConfirmNotmatched("Confirm mus match password");
            }

            if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
                foundError = true;
                errors.setFullnameLengthError("Full name is required typing from 2 to 50 characters");
            }

            if (foundError) {
                // caching a specific attribute then go to erro page to show
                request.setAttribute("createErrors", errors);

            } else {
                // no errors
                //3. Insert to DB --> DB Model
                RegistrationDao dao = new RegistrationDao();
                RegistrationDto dto = new RegistrationDto(username, password, fullName, foundError);
                boolean result = dao.createAccount(dto);
                
                //4. process result
                if (result) {
                    url = LOGIN_PAGE;
                }
            }

        } catch (ClassNotFoundException ex) {
            log("CreateAccountServlet _ ClassNotFoundException: " + ex.getMessage());
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateAccountServlet _ SQLException: " + msg);
            if (msg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " is existed");
                request.setAttribute("createErrors", errors);
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
