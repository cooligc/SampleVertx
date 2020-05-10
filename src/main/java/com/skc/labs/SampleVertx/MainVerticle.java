package com.skc.labs.SampleVertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.logging.Logger;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger(MainVerticle.class.getName());

  private static final int PORT_NUMBER = 8888;

  private EmployeeAdapter employeeAdapter;
  private HealthAdapter healthAdapter;

  public MainVerticle() {
    this.employeeAdapter = new EmployeeAdapter();
    this.healthAdapter = new HealthAdapter();
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    Router router = Router.router(getVertx());


    router.route("/").handler(healthAdapter::healthCheck);
    router.route("/api/employees*").handler(BodyHandler.create());
    router.get("/api/employees").handler(employeeAdapter::getAllEmployees);
    router.get("/api/employees/:id").failureHandler(ctx -> {
      //Exception Handling
      ctx.failure().printStackTrace();
      final JsonObject error = new JsonObject()
        .put("timestamp", System.nanoTime())
        .put("exception", ctx.failure().getClass().getName())
        .put("exceptionMessage", ctx.failure().getMessage())
        .put("path", ctx.request().path());

      ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
      ctx.response().end(error.encode());
    }).handler(employeeAdapter::getEmployee);

    router.post("/api/employees").produces("application/json")
      .consumes("application/json")
      .failureHandler(ctx -> {
        //Exception Handling
        ctx.failure().printStackTrace();
        final JsonObject error = new JsonObject()
          .put("timestamp", System.nanoTime())
          .put("exception", ctx.failure().getClass().getName())
          .put("exceptionMessage", ctx.failure().getMessage())
          .put("path", ctx.request().path());

        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        ctx.response().end(error.encode());
      })
      .handler(employeeAdapter::createEmployee);

    router.delete("/api/employees/:id").handler(employeeAdapter::deleteEmployee);

    vertx.createHttpServer().exceptionHandler(new Handler<Throwable>() {
      @Override
      public void handle(Throwable throwable) {
        throwable.printStackTrace();
        LOGGER.severe(throwable.getMessage());
      }
    }).requestHandler(router::accept).listen(config().getInteger("http.port", PORT_NUMBER), http -> {
      if (http.succeeded()) {
        startFuture.complete();
        System.out.println("HTTP server started on port " + config().getInteger("http.port", PORT_NUMBER));
      } else {
        startFuture.fail(http.cause());
        startFuture.onFailure(throwable -> {
          System.out.println("Unable to start the server in port = " + config().getInteger("http.port", PORT_NUMBER));
          throwable.printStackTrace();
        });
      }
    });


  }
}
