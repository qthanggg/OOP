package data;

import java.util.List;
import java.util.ArrayList;
import tools.MyTool;
import mng.LogIn;

public class DealerList extends ArrayList<Dealer> {

    LogIn loginObj = null;
    private static final String PHONEPATTERN = "\\d{9,11}";
    private String datafile = "";
    boolean changed = false;

    public DealerList(LogIn loginObj) {
//        initWithFile();
        this.loginObj = loginObj;
    }

    private void loadDealerFromFile() {
        List<String> list = MyTool.readLinesFromFile(datafile);
        for (String s : list) {
            Dealer d = new Dealer(s);
            this.add(d);
        }
    }

    public void initWithFile() {
        Config cR = new Config();
        datafile = cR.getDealerFile();
        loadDealerFromFile();
    }

    public DealerList getContinuingList() {
        DealerList result = new DealerList(loginObj);
        result.removeAll(result);
        for (Dealer d : this) {
            if (d.isContinuing()) {
                result.add(d);
            }
        }
        return result;
    }

    public DealerList getUnContinuingList() {
        DealerList result = new DealerList(loginObj);
        result.removeAll(result);
        for (Dealer d : this) {
            if (!d.isContinuing()) {
                result.add(d);
            }
        }
        return result;
    }

   

    private int searchDealer(String ID) {
       if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equalsIgnoreCase(ID)) {
                return i;
            }
        }
        return -1;

    }

    public void searchDealer() {
        String ID = MyTool.readPattern("Input ID need to search: ", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos <= 0) {
            System.out.println("Not found!");
        } else {           
          System.out.println("ID in " + (pos+1) + "th in Dealer");
             System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING  |");
              System.out.println("+----------+----------+--------------------+---------------+---------------+");
            this.get(pos).showInfor();
             System.out.println("+----------+----------+--------------------+---------------+---------------+");
        }
    }


    public void addDealer() {
        String ID;
        String name;
        String addr;
        String phone;
        boolean continuing;
        int pos;
        do {
            ID = MyTool.readPattern("ID of new dealer", Dealer.ID_FORMAT);
            ID = ID.toUpperCase();
            pos = searchDealer(ID);
            if (pos >= 0) {
                System.out.println("ID is duplicated!");
            }
        } while (pos >= 0);
        name = MyTool.readNonBlank("Name of new dealer").toUpperCase();
        addr = MyTool.readNonBlank("Address of new dealer");
        phone = MyTool.readPattern("Phone number", Dealer.PHONE_FORMAT);
        continuing = true;
        Dealer d = new Dealer(ID, name, addr, phone, continuing);
        this.add(d);
        System.out.println("New dealer has been added !");
        changed = true;
    }

    public void removeDealer() {
        String ID = MyTool.readPattern("Input ID need to remove", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("Not found this ID to remove!!");
        }
        else if(pos > 0){
            changed = false;
            this.get(pos).setContinuing(changed);
            System.out.println("The " + ID + " is removed!");
            changed = true;
        }
        else{
            System.out.println("The " + ID + " is always false or remove before !!");
        }
    }

    public void updateDealer() {
        System.out.println("Dealer's ID needs updating: ");
        String ID = MyTool.sc.nextLine();
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("Dealer " + ID + " not found!");
        } else {
            Dealer d = this.get(pos);
            String newName = "";
            System.out.println("New name. Enter for omitting: ");
            newName = MyTool.sc.nextLine().trim().toUpperCase();
            if (!newName.isEmpty()) {
                d.setName(newName);
                changed = true;
            }

            String newAddr = "";
            System.out.println("New address. Enter for omitting:  ");
            newAddr = MyTool.sc.nextLine().trim().toUpperCase();
            if (!newAddr.isEmpty()) {
                d.setAddr(newAddr);
                changed = true;
            }

            String newPhone = "";
            System.out.println("New phone. Enter for omitting  ");
            newPhone = MyTool.sc.nextLine().trim().toUpperCase();
            if (!newPhone.isEmpty()) {
                if (newPhone.matches(PHONEPATTERN)) {
                    d.setPhone(newPhone);
                    changed = true;
                }
                else{                    
                    d.setPhone(MyTool.readPattern(" Wrong format of phone \nInput new phone again: ", PHONEPATTERN));
                }
                
            }

            System.out.println("Succesfully!");
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("Empty List");
        } else {
             System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING  |");
             System.out.println("+----------+----------+--------------------+---------------+---------------+");
           
            for (int i = 0; i < this.size(); i++) {
                this.get(i).showInfor();
            }
            System.out.println("+----------+----------+--------------------+---------------+---------------+");
        }
    }

    public void printContinuingDealers() {
        this.getContinuingList().printAllDealers();

    }

    public void printUnContinuingDealers() {
        this.getUnContinuingList().printAllDealers();
    }

    public void writeDealerToFile() {
        if (changed) {
            MyTool.writeFile(datafile, this);
            System.out.println("Success!");
            changed = false;
        }
    }
//     public void printDealerToFile() {
//        if (changed) {
//            MyTool.writeFile(datafile, this);
//            System.out.println("Success!");
//            changed = false;
//        }
//         System.out.println(datafile);
//    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}
