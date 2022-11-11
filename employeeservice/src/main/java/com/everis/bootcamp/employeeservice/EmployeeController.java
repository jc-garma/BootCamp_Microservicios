package com.everis.bootcamp.employeeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    @Autowired
    private ServerProperties serverProperties;

    public EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/employees")
    public List<Employee> obtenerEmployee(){
        //this.repository.findAll();
        List<Employee> result = this.repository.findAll();
        int port = serverProperties.getPort();

        for(Employee e: result){
            String namePort = e.getName()  + " " +port;
            e.setName(namePort);
        }
        return result;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        return this.repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id){
        Employee employee = new Employee("","", 0);
        employee.setId(0L);
        try{
            return this.repository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
        }catch(EmployeeNotFoundException e){
            return employee;
        }
    }

    @PutMapping("/employees/{id}")
    public Employee reemplazarEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        return this.repository.findById(id).map(
                employee1 -> {
                    employee1.setName(employee.getName());
                    employee1.setRole(employee.getRole());
                    employee1.setSueldo(employee.getSueldo());
                    return this.repository.save(employee1);
                }).orElseGet(
                () -> {
                    employee.setId(id);
                    return this.repository.save(employee);
                });
    }

    @DeleteMapping("/employees/{id}")
    public void borrarEmpleado(@PathVariable Long id){
        this.repository.deleteById(id);
    }

}
