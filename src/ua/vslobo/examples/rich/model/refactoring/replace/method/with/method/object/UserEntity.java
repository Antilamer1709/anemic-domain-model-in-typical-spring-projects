package ua.vslobo.examples.rich.model.refactoring.replace.method.with.method.object;

import lombok.Data;
import ua.vslobo.examples.rich.model.extreme.ExpenseEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer discount;

    @Column
    private String email;

    @OneToMany(mappedBy="user")
    private List<ExpenseEntity> expenses;
    // other fields

    // Before, very long computation was here, in UserEntity class
    // now we have moved it to another class
    public Integer calculateAllExpensesPrice() {
        return new ExpensesPriceCalculator(this).compute();
    }

}

class ExpensesPriceCalculator {

    private List<ExpenseEntity> expenses;

    private Integer discount;

    private Integer price;


    public ExpensesPriceCalculator(UserEntity user) {
        // Copy relevant information from the expense
        //...
        this.expenses = user.getExpenses();
        this.discount = user.getDiscount();
    }

    // really complex logic should be here to justify this pattern
    public Integer compute() {
        // Perform long computation.
        //...
        initDiscount();
        validateExpenses();
        filterExpenses();
        //...
        return calculateFinalPrice();
    }


    private void initDiscount() {
    }
    private void validateExpenses() {
    }
    private void filterExpenses() {
    }
    private Integer calculateFinalPrice() {
        return price;
    }

}
