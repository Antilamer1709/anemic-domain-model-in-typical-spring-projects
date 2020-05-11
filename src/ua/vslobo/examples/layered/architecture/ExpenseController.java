package ua.vslobo.examples.layered.architecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.ExpenseService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/expense")
public class ExpenseController {

    // Field injection is not the best thing to do, but for the simplicity purpose in the example it is OK
    // Normally, we would use a constructor injection
    @Autowired
    private ExpenseService expenseService;
    // Other injected classes

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    public void createExpense(@RequestBody @Valid ExpenseDTO expenseDTO) {
        expenseService.createExpense(expenseDTO);
    }
    // Other methods

}
