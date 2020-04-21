package ua.vslobo.examples.layered.architecture;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.ExpenseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    // other injected classes

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.OK)
    public void createExpense(@RequestBody @Valid ExpenseDTO expenseDTO) {
        expenseService.createExpense(expenseDTO);
    }
    // other methods

}
