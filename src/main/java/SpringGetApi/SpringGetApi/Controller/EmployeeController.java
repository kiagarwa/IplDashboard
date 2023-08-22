package SpringGetApi.SpringGetApi.Controller;

import SpringGetApi.SpringGetApi.Data.Employee;
import SpringGetApi.SpringGetApi.Repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @GetMapping("/employees")
    List<Employee> all(){
        return employeeRepository.findAll();
    }
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }


    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee , @PathVariable Long id){
        return employeeRepository.findById(id).map( employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return  employeeRepository.save(employee);
        }).orElseGet(() ->{
            newEmployee.setId(id);
            return employeeRepository.save(newEmployee);
        });

    }





}
