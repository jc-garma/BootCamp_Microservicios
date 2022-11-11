package com.everis.bootcamp.employeeservice;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long id) {
        super("No se ha podido encontrar el empleado con id : " + id);

    }
}
