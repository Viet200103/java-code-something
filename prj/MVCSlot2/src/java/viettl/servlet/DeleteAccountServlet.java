/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import viettl.data.registration.RegistrationDao;

/**
 *
 * @author viett
 */
@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {
    
    private final String ERROR_PAGE = "errors.html";

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
        
        // 1. Get all parameters
        String userName = request.getParameter("pimary_key");
        String lastSearchValue = request.getParameter("lastSearchValue");
        String url = ERROR_PAGE;
        
        try {
            // 2. Call model
            // 2.1 new DAO object
            RegistrationDao dao = new RegistrationDao();
            
            // 2.2 Call methods of DAO
            boolean result = dao.deleteAccount(userName);
            // 3. Process result
            if (result) {
                url = "DispatchServlet"
                        + "?action=Search"
                        + "&input_name=" + lastSearchValue;
            }// end delete is success
            
        } catch (ClassNotFoundException ex) {
            log("CreateAccountServlet _ ClassNotFoundException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateAccountServlet _ SQLException: " + ex.getMessage());
        }     
        finally {
            // Không dùng forward do các url bị trùng lặp 
            // vì forward lưu trữ url và truyền lại cho controller tiếp theo
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
