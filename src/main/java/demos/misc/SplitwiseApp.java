package demos.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitwiseApp {
}

class Expense{
    String id;
    String desc;
    double amount;
    User1 paidBy;
    SplitType splitType;
    List<Split> splits;

    public Expense(String id, String desc, double amount, User1 paidBy, SplitType splitType, List<Split> splits) {
        this.id = id;
        this.desc = desc;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitType = splitType;
        this.splits = splits;
    }
}


class ExpenseController{
    BalanceSheetController balanceSheetController;
    public Expense createExpense(String expenseId, String desc, double amount,
                                 List<Split> splits, User1 paidBy, SplitType splitType){
        ExpenseSplit split = ExpenseSplitFactory.getSplitObject(splitType);
        split.validateSplit(splits, amount);
        Expense expense = new Expense(expenseId, desc, amount, paidBy, splitType, splits);
        balanceSheetController.updateBalanceSheet(paidBy, amount, splits);
        return  expense;
    }
}

interface ExpenseSplit{
    boolean validateSplit(List<Split> split, double amount);
}

class EqualExpenseSplit implements ExpenseSplit{
    @Override
    public boolean validateSplit(List<Split> split, double amount) {
        return false;
    }
}

class PercentExpenseSplit implements ExpenseSplit{
    @Override
    public boolean validateSplit(List<Split> split, double amount) {
        return false;
    }
}

class ExpenseSplitFactory{
    public static ExpenseSplit getSplitObject(SplitType splitType){
        switch(splitType){
            case PERCENTAGE:
                return new PercentExpenseSplit();
            case EQUAL:
                return new EqualExpenseSplit();
            case MANUAL:
                return new PercentExpenseSplit();
            default:
                throw new IllegalArgumentException("Invalid split type");
        }
    }
}

class Split{
    User1 user;
    double amount;
}

enum SplitType{
    EQUAL, MANUAL, PERCENTAGE;
}

class Group{
    String id;
    String name;
    List<User1> members;
    List<Expense> expenses;
    ExpenseController  expenseController;

    public Group(ExpenseController expenseController) {
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.expenseController = expenseController;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMember(User1 user){
        members.add(user);
    }

    public Expense createExpense(String expenseId, String description, double expenseAmount,
                              List<Split> splitDetails, SplitType splitType, User1 paidByUser){
        Expense expense = expenseController.createExpense(expenseId, description, expenseAmount, splitDetails, paidByUser, splitType);
        expenses.add(expense);
        return expense;
    }
}

class GroupController{
    List<Group> groupList;

    // CRUD Operation for demos.misc.Group
}



class User1 {
    String id;
    String name;
    BalanceSheet balanceSheet;

    public User1(String number, String johnDoe, String dl123456) {

    }
}

class BalanceSheet{
    Map<String, Integer> friendBalance;
    double totalExpense;
    double totalPayByMe;
    double totalOwe;
    double totalGiveBack;
}

class BalanceSheetController{
    public void updateBalanceSheet(User1 paidBy, double amount, List<Split> splits){

    }
}
