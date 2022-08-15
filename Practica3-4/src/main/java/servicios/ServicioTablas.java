package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioTablas {
    public static void crearTablaUsuarios() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS USUARIO\n" +
                "(\n" +
                "  USER VARCHAR(20) PRIMARY KEY NOT NULL,\n" +
                "   PASSWORD VARCHAR(25) NOT NULL, \n" +
                "   TIPO BOOLEAN NOT NULL\n" +
                ");";

        Connection connection = ServicioBD.getInstancia().getConexion();
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
        connection.close();
    }

    public static void crearTablaProductos() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS PRODUCTO\n" +
                "(\n" +
                "  ID IDENTITY  PRIMARY KEY  NOT NULL AUTO,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  ESTADO VARCHAR(3) NOT NULL DEFAULT 'ACT',\n" +
                "  PRECIO INTEGER NOT NULL \n" +
                ");";

        Connection connection = ServicioBD.getInstancia().getConexion();
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
        connection.close();
    }

    public static void crearTablaCompras() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS COMPRA\n"+
                "(\n" +
                " ID IDENTITY PRIMARY KEY NOT NULL  ,\n"+
                " FECHA DATE NOT NULL,\n"+
                " NOMBRE VARCHAR(250) NOT NULL \n" +
                ");";

        Connection connection = ServicioBD.getInstancia().getConexion();
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
        connection.close();

    }

    public static void crearTablaComprasyProductos() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS COMPRAPRODUCTO\n"+
                "(\n" +
                " COMPRAID INTEGER NOT NULL,\n"+
                " PRODUCTOID INTEGER NOT NULL,\n"+
                " CANTIDAD INTEGER NOT NULL, \n" +
                "FOREIGN KEY (COMPRAID) REFERENCES COMPRA(ID), \n" +
                "FOREIGN KEY (PRODUCTOID) REFERENCES PRODUCTO(ID), \n"+
                "CONSTRAINT PK_ID PRIMARY KEY (COMPRAID,PRODUCTOID) \n" +
                ");";

        Connection connection = ServicioBD.getInstancia().getConexion();
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
        connection.close();

    }

    public static void crearAdmin() throws SQLException {
        String query = "insert into usuario (user,password,tipo) values ('admin','admin',true)";
        Connection connection = null;
        try {
            connection = ServicioBD.getInstancia().getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int fila = preparedStatement.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex);
        }finally {
            try{
                connection.close();
            } catch (SQLException ex){
                System.out.println(ex);
            }
        }
    }

    public static void crearTablas() throws SQLException{
        crearTablaUsuarios();
        crearTablaCompras();
        crearTablaProductos();
        crearTablaComprasyProductos();
        crearAdmin();
    }

}
