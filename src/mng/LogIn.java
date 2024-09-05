package mng;

import data.Account;
import data.AccountCheacker;
import data.DealerList;
import java.awt.Choice;
import java.util.Scanner;
import tools.MyTool;

public class LogIn {

    private Account acc = null;

    public LogIn(Account acc) {
        this.acc = acc;
    }

    public static Account inputAccount() {
        Scanner sc = new Scanner(System.in);
        String accName;
        String pwd;
        String role;
        System.out.print("Input Accname: ");
        accName = sc.nextLine();
        System.out.print("Input Password: ");
        pwd = sc.nextLine();
        System.out.print("Input role: ");
        role = sc.nextLine();
        Account thang = new Account(accName, pwd, role);
        return thang;
    }

    public Account getAcc() {
        return acc;
    }

    public static void main(String[] args) {
        Account acc = null;
        boolean cont = false;
        boolean valid = false;
        do {
            AccountCheacker accChk = new AccountCheacker();
            acc = inputAccount();
            valid = accChk.check(acc);
            if (valid) {
                break;
            }
            if (!valid) {
                cont = MyTool.readBool("Invalid account - Try again ?");
            }
            if (!valid && !cont) {
                System.exit(0);
            }
        } while (cont);
       // do {
            LogIn loginObj = new LogIn(acc);
            if (acc.getRole().equalsIgnoreCase("ACC-1")) {
                String[] options = {"Add new dealer", "Search a dealer",
                    "Remove a dealer", "Uppdate a dealer",
                    "Print all dealers", "Print continuing dealers",
                    "Print UN-Continuing dealers", "Write to file"};

                Menu mnu = new Menu(options);
                DealerList dList = new DealerList(loginObj);
                dList.initWithFile();
                int choice = 0;
                do {
                    choice = mnu.getChoice("Managing dealers");
                    switch (choice) {
                        case 1:
                            dList.addDealer();
                            break;
                        case 2:
                            dList.searchDealer();
                            break;
                        case 3:
                            dList.removeDealer();
                            break;
                        case 4:
                            dList.updateDealer();
                            break;
                        case 5:
                            dList.printAllDealers();
                            break;
                        case 6:
                            dList.printContinuingDealers();
                            break;
                        case 7:
                            dList.printUnContinuingDealers();
                            break;
                        case 8:
                            dList.writeDealerToFile();
                            break;
                        default:
                            if (dList.isChanged()) {
                                boolean res = MyTool.readBool("Data changed. Write to file?");
                                if (res == true) {
                                    dList.writeDealerToFile();
                                }
                            }
                    }
                } while (choice > 0 && choice <= mnu.size());
                System.out.println("Bye.");

            }
        } //while (true);

    }


