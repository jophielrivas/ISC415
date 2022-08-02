package controller;

import entidades.Encuestado;
import entidades.Encuestador;
import services.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.*;

public class EncuestadoController {
    public boolean crearEncuestado(Encuestado est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into ENCUESTADO(nombre, sector, nivel, id_encuestador, latitud, longitud) values(?,?,?,?,?,?)";
            con = Test.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, est.getNombre());
            prepareStatement.setString(2, est.getSector());
            prepareStatement.setString(3, est.getNivelEscolar());
            prepareStatement.setString(4, String.valueOf(est.getIdEncuestador()));
            prepareStatement.setString(5, String.valueOf(est.getLatitud()));
            prepareStatement.setString(6, String.valueOf(est.getLongitud()));
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

    public static List<Encuestado> listaEncuestado() {
        List<Encuestado> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from ENCUESTADO;";
            con = Test.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Encuestado en = new Encuestado();
                en.setId(rs.getInt("ID"));
                en.setNombre(rs.getString("NOMBRE"));
                en.setSector(rs.getString("SECTOR"));
                en.setNivelEscolar(rs.getString("NIVEL"));
                en.setIdEncuestador(rs.getInt("ID_ENCUESTADOR"));
                en.setLatitud(rs.getDouble("LATITUD"));
                en.setLongitud(rs.getDouble("LONGITUD"));

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
