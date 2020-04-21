package ua.vslobo.examples.layered.architecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vslobo.examples.layered.architecture.mock.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    // Consider them as @Autowired
    private final ExpenseRepo expenseRepo;
    private final AuthenticationBO authenticationBO;
    // other injected classes

    @Override
    @Transactional
    public void createExpense(ExpenseDTO expenseDTO) {
        validateExpense(expenseDTO);
        ExpenseEntity expenseEntity = new ExpenseEntity();
        initExpenseEntity(expenseEntity, expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    private void validateExpense(ExpenseDTO expenseDTO) {
        //validations
    }
    // other methods

    private void initExpenseEntity(ExpenseEntity expenseEntity, ExpenseDTO expenseDTO) {
        UserEntity userEntity = authenticationBO.getLoggedUser();

        expenseEntity.setUser(userEntity);
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        expenseEntity.setDate(LocalDateTime.now());
        initExpenseTypes(expenseEntity, expenseDTO.getTypes());
    }

    private void initExpenseTypes(ExpenseEntity expenseEntity, Object types) {
        // other initializations
    }
    // other methods

}
