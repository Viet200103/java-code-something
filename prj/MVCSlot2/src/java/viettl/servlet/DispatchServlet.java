/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author viett
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastNameServlet";
    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
    private final String STARTUP_CONTROLLER = "StartupServlet";
    private final String UPDATE_CONTROLLER = "UpdateServlet";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
    private final String REMOVE_ITEM_FROM_CART_CONTROLLER = "RemoveItemFromCartServlet";
    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccountServlet";
    private final String VIEW_CART_PAGE = "ViewCartServlet";
    private final String PRODUCT_CONTROLLER = "ProductServlet";
    private final String ORDER_CONTROLLER = "CreateOrderServlet";
    private final String VIEW_INVOICE_CONTROLLER = "ViewInvoiceServlet";

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

        // 1. Which button did user click?
        // 2. Mapp new function to dispatcher
        // 3. Create functional Servlet
        String button = request.getParameter("action");
        String url = LOGIN_PAGE;

        try {
            if (button == null) {
                url = STARTUP_CONTROLLER;
            } else {
                switch (button) {
                    case "Login":
                        url = LOGIN_CONTROLLER;
                        break;
                    case "Logout":
                        url = LOGOUT_CONTROLLER;
                        break;
                    case "Search":
                        url = SEARCH_LASTNAME_CONTROLLER;
                        break;
                    case "Delete":
                        url = DELETE_ACCOUNT_CONTROLLER;
                        break;
                    case "Update":
                        url = UPDATE_CONTROLLER;
                        break;
                    case "Add Book To Your Cart":
                        url = ADD_TO_CART_CONTROLLER;
                        break;
                    case "View Your Cart":
                        url = VIEW_CART_PAGE;
                        break;
                    case "Remove Selected Items":
                        url = REMOVE_ITEM_FROM_CART_CONTROLLER;
                        break;
                    case "Buy":
                        url = ORDER_CONTROLLER;
                        break;
                    case "Create New Account":
                        url = CREATE_ACCOUNT_CONTROLLER;
                        break;
                    case "Shopping":
                        url = PRODUCT_CONTROLLER;
                        break;
                    case "view-invoice":
                        url = VIEW_INVOICE_CONTROLLER;
                        break;
                    default:
                        url = STARTUP_CONTROLLER;
                }
            }
        } finally {
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
