package com.mycompany.pr02tar01;

// ...existing code...
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/MostrarProductosServlet")
public class MostrarProductosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, descripcion, precio FROM productos");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                productos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = new Gson().toJson(productos);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(json);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
