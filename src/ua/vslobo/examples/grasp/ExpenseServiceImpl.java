package ua.vslobo.examples.grasp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.ExpenseEntity;
import ua.vslobo.examples.layered.architecture.mock.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    // services and infrastructure


    @Override
    @Transactional
    public void createExpense(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        businessLogicMethod(expenseEntity);
    }

    private void businessLogicMethod(ExpenseEntity expenseEntity) {
        // do nothing with expenseEntity
        // violates the principle of encapsulation, GRASP patterns
        anotherBusinessLogicMethod(expenseEntity); // only next method needs expenseEntity
    }

    private void anotherBusinessLogicMethod(ExpenseEntity expenseEntity) {
        // here we use expenseEntity
    }

}
