import com.google.gson.Gson;
import controller.EncuestadoController;
import controller.EncuestadorController;
import entidades.Encuestado;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7072);

        app.get("/", ctx -> ctx.result("Hello Miguel1"));
        app.get("/login", ctx -> ctx.result("Hello World"));

        app.get("/example", ctx -> ctx.html("Ths is a Javalin Example"));
        app.get("/encuestadores", ctx -> ctx.json(EncuestadorController.listaEncuestador()));
        app.get("/encuestados", ctx -> ctx.json(EncuestadoController.listaEncuestado()));
        app.post("/formulario", ctx -> {
            Gson gson = new Gson();
            Encuestado nuevo = gson.fromJson(ctx.body(), Encuestado.class);
            EncuestadoController en = new EncuestadoController();
            if (en.crearEncuestado(nuevo)) {
                System.out.println("Se creo un encuestado");
            }
        });
    }
}
