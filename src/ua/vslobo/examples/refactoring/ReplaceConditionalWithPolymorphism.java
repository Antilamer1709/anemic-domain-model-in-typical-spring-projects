package ua.vslobo.examples.refactoring;

import ua.vslobo.examples.layered.architecture.mock.UserEntity;
import ua.vslobo.examples.refactoring.mock.EntityType;

public class ReplaceConditionalWithPolymorphism {

    // Before
    class ExpenseEntityBefore {
        private Integer price;
        private Integer amount;
        private Integer discount;
        private EntityType type;
        private UserEntity user;

        // ...
        Integer getPrice() {
            switch (type) {
                case BASIC:
                    return getBasicPrice();
                case SALE:
                    return getBasicPrice() * calculateCasualDiscount();
                case WHOLESALE:
                    return getBasicPrice() * amount * calculateWholesaleDiscount();
            }
            throw new RuntimeException("Should be unreachable");
        }

        // imagine that that methods do something really useful and complex
        private Integer getBasicPrice() {
            return price * amount;
        }

        private Integer calculateCasualDiscount() {
            return price * (discount + user.getUserDiscount());
        }

        private Integer calculateWholesaleDiscount() {
            return (amount / 1000) + calculateCasualDiscount();
        }
    }





    // After
    // contains ONLY shared fields and methods
    abstract class ExpenseEntity {
        protected Integer price;
        protected Integer amount;
        protected Integer discount;
        protected UserEntity user;

        // ...
        public abstract Integer getPrice();

        protected Integer getBasicPrice() {
            // get the basic price
            return price * amount;
        }
    }

    // Casual expense, which does not have any specific fields or methods
    class BasicExpenseEntity extends ExpenseEntity {
        @Override
        public Integer getPrice() {
            // get the price for a casual expense
            return getBasicPrice();
        }
    }

    // Expense that user made with the discount
    // contains ONLY sale fields and methods
    class SaleExpenseEntity extends ExpenseEntity {
        @Override
        public Integer getPrice() {
            // get price with the discount
            return getBasicPrice() * calculateCasualDiscount();
        }

        private Integer calculateCasualDiscount() {
            return price * (discount + user.getUserDiscount());
        }
    }

    // Goods that user has bought in large amount
    // contains ONLY wholesale fields and methods
    class WholesaleExpenseEntity extends ExpenseEntity {
        @Override
        public Integer getPrice() {
            // get the wholesale price
            return getBasicPrice() * amount * calculateWholesaleDiscount();
        }
        private Integer calculateWholesaleDiscount() {
            return (amount / 1000) + discount;
        }
    }

}
