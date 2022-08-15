package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServicioBD {
    private static ServicioBD instancia;
    private String URL = "jdbc:h2:~/carrito";

    private ServicioBD(){
        registrarDriver();
    }

    public static ServicioBD getInstancia(){
        if(instancia==null){
            instancia = new ServicioBD();
        }
        return instancia;
    }

    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            System.out.println(ex);;
        }
    }
}
