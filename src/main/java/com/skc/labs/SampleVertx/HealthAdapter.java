package com.skc.labs.SampleVertx;

import io.vertx.ext.web.RoutingContext;

public class HealthAdapter {
  public void healthCheck(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end("I am alive");
  }
}
