/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet.shop;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import viettl.data.order.InputAddressError;

/**
 *
 * @author viett
 */
@WebServlet(name = "InputAddressServlet", urlPatterns = {"/InputAddressServlet"})
public class InputAddressServlet extends HttpServlet {
    
    private final String CREATE_ORDER_CONTROLLER = "CreateOrderServlet";
    private final String INPUT_ADDRESS_PAGE = "inputaddress.jsp";
    
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
        
        String addressAction = request.getParameter("address-action");
        
        if (addressAction.equals("cancel")) {
            String url = "DispatchServlet?action=View Your Cart";
            response.sendRedirect(url);
        }
        
        request.setAttribute("addressError", null);
        if (addressAction.equals("complete")) {
            
            String address = request.getParameter("user-address");
            boolean isAddressValid = address != null && !address.trim().isEmpty();
            
            String url;
            if (isAddressValid) {
                url = CREATE_ORDER_CONTROLLER;
            } else {
                url = INPUT_ADDRESS_PAGE;
                InputAddressError error = new InputAddressError();
                error.setAddressLengthError("Address is not empty");
                request.setAttribute("addressError", error);
            }
            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
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
