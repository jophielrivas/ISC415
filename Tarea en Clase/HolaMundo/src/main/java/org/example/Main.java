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
        AtomicReference<String> user = null;
        AtomicReference<String> pass = null;
        String admin = "admin";
        String adminpass = "1234";

        app.get("/", ctx -> {

            if(user.get() == null){
                ctx.render("public/login.html");
            }

            else{
                ctx.render("public/home.html");
            }

        });


        app.post("/login", ctx -> {
            user.set(ctx.formParam("username"));
            pass.set(ctx.formParam("password"));

            if(admin.equalsIgnoreCase(user.get()) && adminpass.equalsIgnoreCase(pass.get())){

                ctx.sessionAttribute("user", admin);

                ctx.redirect("/");
            }
            else{
                ctx.redirect("\401.html");
            }
        });
    }
}
