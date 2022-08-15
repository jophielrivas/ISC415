import HttpHandler.Handler;
import com.google.gson.Gson;
import io.javalin.Javalin;
import model.Encuestador;
import model.Formulario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7071);
        AtomicInteger idFormulario = new AtomicInteger();
        Handler handler = new Handler();


        //Metodo para hacer el login
        app.get("/login", ctx -> {
            Map<String, Object> model = new HashMap<>();
            ctx.render("templates/login.html",model);
        });

        app.post("/login", ctx -> {
            String usuario = ctx.formParam("usuario");
            String pass = ctx.formParam("password");

            Encuestador[] encuestadores = handler.HandlerHTTPEncuestador();
            List<String> usuarios = new ArrayList<>();
            List<String> passwords = new ArrayList<>();

            for(int i = 0; i < encuestadores.length; i++){
                usuarios.add(encuestadores[i].getUserName());
                passwords.add(encuestadores[i].getPassword());
            }

            if(usuarios.contains(usuario) && passwords.contains(pass)){
                ctx.redirect("/formulario");
                ctx.cookie("usuario", usuario);
                for(int i = 0; i < encuestadores.length; i++) {
                    if(encuestadores[i].getUserName().equals(usuario)) {
                        ctx.cookie("id",  String.valueOf(encuestadores[i].getId()));
                        ctx.cookie("rol", String.valueOf(encuestadores[i].isRole()));
                    }
                }
            }else {
                ctx.redirect("/login");
            }
        });



        //Metodo para crear un formulario
        app.get("/formulario", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("accion","/formulario");
            Formulario encuestado = new Formulario();
            model.put("formulario",encuestado);
            ctx.render("/templates/formulario.html", model);

        });

        //Metodo para enviar los formularios al servidor
        app.post("/formulario", ctx -> {
            String nombre = ctx.formParam("nombre");
            String sector = ctx.formParam("sector");
            String nivel = ctx.formParam("nivel");
            int id_encuestador = Integer.parseInt(ctx.cookie("id"));
            double latitud = Double.parseDouble(ctx.formParam("latitud"));
            double longitud = Double.parseDouble(ctx.formParam("longitud"));

            Formulario encuestado = new Formulario(0,nombre,sector,nivel,id_encuestador,latitud,longitud);
            handler.HandlerHTTPEnviarEncuesta(new Gson().toJson(encuestado));
            ctx.redirect("/formulario");
        });

        //Metodo para ver la lista de los formularios
        app.get("/listaFormularios", ctx -> {
            Map<String, Object> model = new HashMap<>();

            model.put("formularios", handler.HandlerHTTPFormulario());

            ctx.render("/templates/listaFormularios.html", model);
        });

        //Método para ver la lista de los encuestadores
        app.get("/encuestadores", ctx -> {
            Map<String, Object> model = new HashMap<>();

            model.put("encuestadores", handler.HandlerHTTPEncuestador());

            ctx.render("/templates/encuestadores.html", model);
        });

    }
}
