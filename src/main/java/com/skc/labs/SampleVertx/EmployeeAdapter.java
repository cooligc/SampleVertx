package com.skc.labs.SampleVertx;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeAdapter {

  private static final Logger LOGGER = Logger.getLogger(EmployeeAdapter.class.getName());

  private EmployeeService employeeService;

  public EmployeeAdapter(){
    this.employeeService = new EmployeeService();
  }


  public void getAllEmployees(RoutingContext routingContext) {

    LOGGER.log(Level.INFO, "Going to retrive all the employee details");

    List<Employee>  employees = employeeService.getAllEmployees();

    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(employees));

    LOGGER.log(Level.INFO, "Successfully retrive the employee details = "+employees);

  }

  public void getEmployee(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");

    LOGGER.log(Level.INFO, "Going to retrive the employee details for id = "+ id);

    if (id == null) {
      LOGGER.log(Level.SEVERE, "Parameter not found id=" + id);
      routingContext.response().setStatusCode(404).setStatusMessage("Employee Not found").end();
    } else {
      Integer employeeId = Integer.valueOf(id);

      Employee employee = employeeService.getEmployee(employeeId);
      LOGGER.log(Level.SEVERE, "Employee to be returned for  id=" + employee);
      routingContext.response().setStatusCode(200).end(Json.encodePrettily(employee));
    }

  }

  public void createEmployee(RoutingContext routingContext) {
    Employee employee = Json.decodeValue(routingContext.getBodyAsString(),
      Employee.class);
    employee = employeeService.createEmployee(employee);
    routingContext.response()
      .setStatusCode(201)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(employee));

  }

  public void deleteEmployee(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    employeeService.deleteEmployee(Integer.valueOf(id));
    routingContext.response().setStatusCode(204).end();
  }
}
