package com.mycompany.pr02tar01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/EliminarProductoServlet")
public class EliminarProductoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String idStr = request.getParameter("id");
        boolean exito = false;
        String mensaje = "";
        try {
            int id = Integer.parseInt(idStr);
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM productos WHERE id=?") ) {
                ps.setInt(1, id);
                int filas = ps.executeUpdate();
                exito = filas > 0;
                mensaje = exito ? "Producto eliminado correctamente" : "No se pudo eliminar el producto";
            }
        } catch (Exception e) {
            mensaje = "Error: " + e.getMessage();
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print("{\"exito\":" + exito + ",\"mensaje\":\"" + mensaje + "\"}");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
