package controller;

import entidades.Encuestado;
import services.Test;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.*;

import entidades.Encuestador;


public class EncuestadorController {

    public boolean crearEncuestador(Encuestador est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into ENCUESTADOR(username, password, rol) values(?,?,?)";
            con = Test.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, est.getUserName());
            prepareStatement.setString(2, est.getPassword());
            prepareStatement.setString(3, String.valueOf(est.isRole()));
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        return ok;
    }

    public static List<Encuestador> listaEncuestador() {
        List<Encuestador> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from ENCUESTADOR;";
            con = Test.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Encuestador en = new Encuestador();
                en.setId(rs.getInt("ID"));
                en.setUserName(rs.getString("USERNAME"));
                en.setPassword(rs.getString("PASSWORD"));
                en.setRole(rs.getBoolean("ROL"));

                lista.add(en);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        return lista;
    }


}
