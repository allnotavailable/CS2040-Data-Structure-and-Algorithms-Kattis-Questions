import java.util.TreeSet;

class KattisQuest {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        TreeSet<Quest> treeSet = new TreeSet<Quest>();
        long initial = 0;

        int testcases = fio.nextInt();
        for (int i = 0; i < testcases; i++) {
            String cmd = fio.next();
            if (cmd.equals("add")) {
                long consumption = fio.nextLong();
                long gold = fio.nextLong();
                treeSet.add(new Quest(consumption, initial++, gold));
            } else {
                long energy = fio.nextLong();
                long amountGold = 0;
                while (true) {
                    Quest tempQuest = treeSet.floor(new Quest(energy, 0, 10000000));
                    if (tempQuest == null) {
                        break;
                    }
                    treeSet.remove(tempQuest);
                    amountGold += tempQuest.gold;
                    energy -= tempQuest.energy;
                }
                System.out.println(amountGold);
            }
        }
        
        fio.close();
    } 
}

class Quest implements Comparable<Quest>{
    long energy;
    long index;
    long gold;

    Quest(long energy, long index, long gold) {
        this.energy = energy;
        this.index = index;
        this.gold = gold;
    }

    @Override
    public int compareTo (Quest other) {
        if (this.energy != other.energy) {
            return Long.compare(this.energy, other.energy);
        } else if (this.energy == other.energy && this.gold != other.gold) {
            return Long.compare(this.gold, other.gold);
        } else if (this.energy == other.energy && this.gold == other.gold) {
            return Long.compare(this.index, other.index);
        } else {
            return 0;
        }
    }
}

private Long atmCardId;
    @Column(unique = true, nullable = false)
    private String cardNumber;
    private String nameOnCard;
    private boolean enabled;
    private String pin;
    @OneToMany (mappedBy = "atmCard")
    private List<DepositAccount> depositAccounts;

    private Long customerId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String identificationNumber;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    @OneToMany (mappedBy = "customer")
    private List<DepositAccount> depositAccounts;
    @OneToOne
    @JoinColumn
    private AtmCard atmCard;


    private Long depositAccountId;
    private String accountNumber;
    private boolean enabled;
    @Column(precision = 11, scale = 2)
    private BigDecimal availableBalance;
    @Column(precision = 11, scale = 2)
    private BigDecimal holdBalance;
    @Column(precision = 11, scale = 2)
    private BigDecimal ledgerBalance;
    @Enumerated (EnumType.STRING)
    private DepositAccountType accountType = DepositAccountType.SAVINGS;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn
    private AtmCard atmCard;

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import java.util.Scanner;
import util.exception.AtmCardNotFoundException;

/**
 *
 * @author timothy
 */
public class MainApp {
    
    private AtmCardSessionBeanRemote atmCardSessionBean;
    private CustomerSessionBeanRemote customerSessionBean;
    private DepositAccountSessionBeanRemote depositAccountSessionBean;
    private Customer currCustomer;
    
    

    public MainApp() {
    }

    public MainApp(AtmCardSessionBeanRemote atmCardSessionBean, CustomerSessionBeanRemote customerSessionBean, DepositAccountSessionBeanRemote depositAccountSessionBean) {
        this.atmCardSessionBean = atmCardSessionBean;
        this.customerSessionBean = customerSessionBean;
        this.depositAccountSessionBean = depositAccountSessionBean;
    }
    
    
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("*** Retail Core Banking System :: Automated Teller Machine ***");
            System.out.println("Enter Atm Card Number> ");
            String cardNumber = sc.nextLine().trim();
            System.out.println("Enter Pin");
            String pin = sc.nextLine().trim();
            
            try {
                AtmCard atmCard = atmCardSessionBean.findAtmCardByNumber(cardNumber);
                if (atmCard.getPin().equals(pin)) {
                    currCustomer = customerSessionBean.findCustomerByAtm(atmCard.getCardNumber());
                    menuMain();
                } else {
                    System.out.println("Invalid Pin");
                }
            } catch (AtmCardNotFoundException ex) {
                System.out.println("Atm Card Number Not Found");
            }
        }
    }
    
    public void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Retail Core Banking System ***\n");
            System.out.println("1: Change Atm Card Pin");
            System.out.println("2: Enquire Available Balance");
            System.out.println("3: Exit\n");
            response = 0;
            
            while(response < 1 || response > 3)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    doChangePin();
                } else if (response == 2) {
                    doViewBalance();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again\n");
                }
            }
            if (response == 3) {
                break;
            }
        }
    }
    
    public void doChangePin() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Welcome to Retail Core Banking System :: Change Pin ***\n");
        
        System.out.println("Enter New Pin> ");
        String newPin = sc.nextLine().trim();
        AtmCard newAtm = atmCardSessionBean.changeAtmPin(currCustomer.getAtmCard().getCardNumber(), newPin);
        System.out.println("Pin changed successfully\n");
    }
    
    public void doViewBalance() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Retail Core Banking System :: View Balance ***\n");
            List<DepositAccount> dAcc = depositAccountSessionBean.findAccountFromCustomer(currCustomer.getIdentificationNumber());
            System.out.println("Choose Deposit Accounts\n");
            int count = 1;
            
            for(DepositAccount accounts : dAcc) {
                
                System.out.println(count + ". " + accounts.getAccountNumber());
                
                count++;
                
            }
            System.out.println(count + 1 + ". Back\n");
            
            while (true) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response >= 1 && response <= dAcc.size() + 1) {
                    
                    int selectedAccountIndex = response - 1; 
                    DepositAccount selectedAccount = dAcc.get(selectedAccountIndex);

                    System.out.println("View " + selectedAccount.getAccountNumber() + " Balance");
                    System.out.println("Available Balance: " + selectedAccount.getAvailableBalance());
                    System.out.println("Hold Balance: " + selectedAccount.getHoldBalance());
                    System.out.println("Ledger Balance: " + (selectedAccount.getHoldBalance().add(selectedAccount.getAvailableBalance())));
                    System.out.println("1. Exit\n");
                    System.out.println("> ");
                    response = scanner.nextInt();
                    while (response < 1 || response > 1) {
                        if (response == 1) {
                            break;
                        } else {
                            System.out.println("Invalid option, Please try again\n");
                        }
                    }
                    
                } else if (response == count + 1) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid account index or " + count + 1 + " to go back.");
                }
            }
            if (response == count + 1) {
                break;
            }
        }
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author timothy
 */
public class Main {

    @EJB
    private static DepositAccountSessionBeanRemote depositAccountSessionBean;

    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBean;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;

    
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(atmCardSessionBean, customerSessionBean, depositAccountSessionBean);
        mainApp.runApp();
    }
    
}

