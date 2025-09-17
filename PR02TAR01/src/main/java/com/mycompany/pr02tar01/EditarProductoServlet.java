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

@WebServlet("/EditarProductoServlet")
public class EditarProductoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");
        boolean exito = false;
        String mensaje = "";
        try {
            int id = Integer.parseInt(idStr);
            double precio = Double.parseDouble(precioStr);
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE productos SET nombre=?, descripcion=?, precio=? WHERE id=?") ) {
                ps.setString(1, nombre);
                ps.setString(2, descripcion);
                ps.setDouble(3, precio);
                ps.setInt(4, id);
                int filas = ps.executeUpdate();
                exito = filas > 0;
                mensaje = exito ? "Producto actualizado correctamente" : "No se pudo actualizar el producto";
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
