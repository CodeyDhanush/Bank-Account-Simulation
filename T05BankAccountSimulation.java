import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base Account Class
class Account {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    protected List<String> transactionHistory;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: ₹" + amount);
            System.out.println("₹" + amount + " deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: ₹" + amount);
            System.out.println("₹" + amount + " withdrawn successfully.");
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for " + accountHolder + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    public void displayBalance() {
        System.out.println("Current Balance: ₹" + balance);
    }
}

// SavingsAccount subclass
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance, double interestRate) {
        super(accountNumber, accountHolder, initialBalance);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        transactionHistory.add("Interest Added: ₹" + interest);
        System.out.println("Interest of ₹" + interest + " added successfully.");
    }
}

// CurrentAccount subclass
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolder, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            transactionHistory.add("Withdrew: ₹" + amount + " (Overdraft allowed)");
            System.out.println("₹" + amount + " withdrawn successfully with overdraft facility.");
        } else {
            System.out.println("Overdraft limit exceeded or invalid amount!");
        }
    }
}

// Main Class
public class T05BankAccountSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Account creation
        System.out.print("Enter Account Type (1 - Savings, 2 - Current): ");
        int type = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Account Number: ");
        String accNum = sc.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        Account account = null;

        if (type == 1) {
            System.out.print("Enter Interest Rate (%): ");
            double rate = sc.nextDouble();
            account = new SavingsAccount(accNum, name, balance, rate);
        } else if (type == 2) {
            System.out.print("Enter Overdraft Limit: ");
            double overdraft = sc.nextDouble();
            account = new CurrentAccount(accNum, name, balance, overdraft);
        } else {
            System.out.println("Invalid account type. Exiting...");
            System.exit(0);
        }

        // Menu for transactions
        int choice;
        do {
            System.out.println("\n--- Bank Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            if (account instanceof SavingsAccount) {
                System.out.println("3. Add Interest");
            }
            System.out.println("4. View Balance");
            System.out.println("5. View Transaction History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double dep = sc.nextDouble();
                    account.deposit(dep);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double wd = sc.nextDouble();
                    account.withdraw(wd);
                    break;

                case 3:
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).addInterest();
                    } else {
                        System.out.println("Invalid choice for Current Account.");
                    }
                    break;

                case 4:
                    account.displayBalance();
                    break;

                case 5:
                    account.printTransactionHistory();
                    break;

                case 0:
                    System.out.println("Thank you for using the bank system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);

        sc.close();
    }
}

