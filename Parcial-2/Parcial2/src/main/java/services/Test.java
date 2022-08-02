package services;
import java.sql.*;

public class Test {
    private static Test instancia;
    private String URL = "jdbc:h2:~/test";

    private  Test(){
        registrarDriver();
    }

    public static Test getInstancia(){
        if(instancia==null){
            instancia = new Test();
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
