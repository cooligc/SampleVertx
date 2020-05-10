package com.skc.labs.SampleVertx;

import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeService {

  private static final List<Employee> EMPLOYEES = new CopyOnWriteArrayList<>();

  static {
    for (int i = 0; i < 10; i++) {
      Employee employee = new Employee();
      employee.setId(i+1);
      employee.setDeptName("DEPT"+(i%4));
      employee.setEmployeeName(UUID.randomUUID().toString());
      EMPLOYEES.add(employee);
    }
  }


  public List<Employee> getAllEmployees(){
    return EMPLOYEES;
  }

  public Employee getEmployee(int id){
    return EMPLOYEES.get(id);
  }


  public Employee createEmployee(Employee employee) {
    employee.setId(EMPLOYEES.size());
    EMPLOYEES.add(employee);
    return employee;
  }

  public void deleteEmployee(int id){
    EMPLOYEES.remove(id);
  }
}
