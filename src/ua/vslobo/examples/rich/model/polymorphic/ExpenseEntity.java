package ua.vslobo.examples.rich.model.polymorphic;

import ua.vslobo.examples.layered.architecture.mock.UserEntity;
import lombok.Data;
import javax.persistence.*;

// Contains all shared fields and methods
@Entity
@Table
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // We use single table inheritance
public abstract class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Integer id;

    @Column
    protected Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    protected UserEntity user;
    // ...
    // Other common fields


    // Common method for all children
    protected Integer getBasicPrice() {
        // get the basic price
        // long computation
        return price * user.getUserDiscount();
    }

    public abstract Integer getPrice();

}


// Those should be in separate files, but for convenience we place them here

// Casual expense, which does not have any specific fields
@Entity
class BasicExpenseEntity extends ExpenseEntity {

    @Override
    public Integer getPrice() {
        // get the price for a casual expense
        // long computation
        return getBasicPrice();
    }

}


// Expense that user made with the discount
@Entity
class SaleExpenseEntity extends ExpenseEntity {

    @Column
    private Integer discount; // discount for particular item
    // other sale specific columns

    @Override
    public Integer getPrice() {
        // get price with the discount
        // long computation
        return getBasicPrice() * discount;
    }
    // Other sale specific methods
}


// Goods that user has bought in large amount
@Entity
class WholesaleExpenseEntity  extends ExpenseEntity {

    @Column
    private Integer amount;
    // Other wholesale specific columns

    @Override
    public Integer getPrice() {
        // get the wholesale price
        // long computation
        return getBasicPrice() * amount * calculateWholesaleDiscount();
    }

    // Wholesale discount depends from amount. The more you buy, the bigger is the discount
    private Integer calculateWholesaleDiscount() {
        return amount / 1000;
    }
    // Other wholesale specific methods

}
