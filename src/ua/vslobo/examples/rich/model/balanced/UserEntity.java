package ua.vslobo.examples.rich.model.balanced;

import lombok.Data;
import ua.vslobo.examples.anemic.model.mock.ExpenseTypeDictEntity;
import ua.vslobo.examples.rich.model.extreme.ExpenseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String email;

    @OneToMany(mappedBy="user")
    private List<ExpenseEntity> expenses;
    // other fields

    public Integer calculateAllExpensesPrice() {
        return expenses.stream()
                .mapToInt(expenseEntity -> expenseEntity.getPrice() * calculateUserDiscount(expenseEntity))
                .sum();
    }

    private Integer calculateUserDiscount(ExpenseEntity expenseEntity) {
        Integer discount = 0;
        // calculations
        return discount;
    }


    public List<ExpenseTypeDictEntity> getExpenseTypes() {
        return expenses.stream()
                .flatMap(expenseEntity -> expenseEntity.getExpenseTypeDict().stream())
                .collect(Collectors.toList());
    }

}
