package com.skc.labs.SampleVertx;

public class Employee {
  private int id;
  private String employeeName;
  private String deptName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public String toString() {
    return "Employee{" +
      "id=" + id +
      ", employeeName='" + employeeName + '\'' +
      ", deptName='" + deptName + '\'' +
      '}';
  }
}
