package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/files",Location.CLASSPATH);
        }).start(7000);
        User user = new User();
        String admin = "admin";
        String adminpass = "1234";

        app.get("/", ctx -> {
            if(user.getName() == null && user.getPass() == null){
                ctx.render("files/login.html");
            }
            else{
                ctx.render("files/home.html");
            }
        });


        app.post("/login", ctx -> {
            user.setName(ctx.formParam("username"));
            user.setPass(ctx.formParam("password"));

            if(admin.equalsIgnoreCase(user.getName()) && adminpass.equalsIgnoreCase(user.getPass())){

                ctx.sessionAttribute("user", user);

                ctx.redirect("/");
            }
            else{
                ctx.redirect("/login.html");
            }
        });
    }
}
