package controladores;

import clases.Usuario;
import servicios.ServicioBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class usuarioControlador {
    public boolean crearUsuario(Usuario est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into USUARIO(user, password, tipo) values(?,?,?)";
            con = ServicioBD.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, est.getUser());
            prepareStatement.setString(2, est.getPass());
            prepareStatement.setString(3, String.valueOf(est.isTipo()));
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


}
