package ua.vslobo.examples.layered.architecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vslobo.examples.layered.architecture.mock.*;

import java.time.LocalDateTime;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    // Field injection is not the best thing to do, but for the simplicity purpose in the example it is OK
    // Normally, we would use a constructor injection
    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private AuthenticationService authenticationService;
    // Other injected classes


    @Override
    @Transactional
    public void createExpense(ExpenseDTO expenseDTO) {
        validateExpense(expenseDTO);
        ExpenseEntity expenseEntity = new ExpenseEntity();
        initExpenseEntity(expenseEntity, expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    private void validateExpense(ExpenseDTO expenseDTO) {
        // Business validations
        // check for duplicates, etc
    }
    // Other methods

    private void initExpenseEntity(ExpenseEntity expenseEntity, ExpenseDTO expenseDTO) {
        UserEntity userEntity = authenticationService.getLoggedUser();

        expenseEntity.setUser(userEntity);
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        expenseEntity.setDate(LocalDateTime.now());
        initExpenseTypes(expenseEntity, expenseDTO.getTypes());
    }

    private void initExpenseTypes(ExpenseEntity expenseEntity, Object types) {
        // Other initializations
    }
    // Other methods

}
