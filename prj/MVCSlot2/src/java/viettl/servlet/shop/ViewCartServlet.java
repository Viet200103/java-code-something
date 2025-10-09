/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.servlet.shop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import viettl.cart.ShoppingCart;
import viettl.data.product.ProductDto;
import viettl.data.product.ProductDao;
import viettl.data.product.ProductViewCart;

/**
 *
 * @author viett
 */
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private String VIEW_CART_PAGE = "viewcart.jsp";

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
        try {
            HttpSession session = request.getSession(false);

            if (session != null) {
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                if (cart != null) {
                    Map<String, Integer> items = cart.getItems();
                    if (items != null || !items.isEmpty()) {
                        ProductDao productDao = new ProductDao();
                        List<ProductViewCart> productList = new ArrayList<>();

                        for (Map.Entry<String, Integer> entry : items.entrySet()) {
                            ProductDto product = productDao.getProductById(entry.getKey());

                            ProductViewCart proViewCart = new ProductViewCart(
                                    product, entry.getValue()
                            );

                            productList.add(proViewCart);
                        }

                        request.setAttribute("productList", productList);
                    }
                }
            }
        } catch (SQLException ex) {
            log("OrderServlet_SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            log("OrderServlet_ClassNotFoundException: " + ex.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_CART_PAGE);
            dispatcher.forward(request, response);
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
