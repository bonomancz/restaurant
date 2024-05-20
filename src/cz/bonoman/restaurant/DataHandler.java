package cz.bonoman.restaurant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private String dishesTxtFile, tablesTxtFile, ordersTxtFile;
    private File dishesFile, tablesFile, ordersFile;

    public DataHandler(){
        this.dishesTxtFile = "./appData/dishes.txt";
        this.ordersTxtFile = "./appData/orders.txt";
        this.tablesTxtFile = "./appData/tables.txt";
        this.dishesFile = new File(dishesTxtFile);
        this.ordersFile = new File(ordersTxtFile);
        this.tablesFile = new File(tablesTxtFile);
    }

    public void saveDishesToStorage(List<Dishes> inputList){
        if(this.isWriteable(this.dishesFile)){
            ArrayList<String> writeList = new ArrayList<>();
            for(Dishes dish : inputList){
                writeList.add(dish.getId() + "," + dish.getTitle() + "," + dish.getImage() + "," + dish.getPrice() + "," + dish.getPreparationTime());
            }
            fileWrite(this.dishesFile, writeList);
        }
    }

    public void saveTablesToStorage(List<Tables> inputList){
        if(this.isWriteable(this.tablesFile)) {
            ArrayList<String> writeList = new ArrayList<>();
            for (Tables table : inputList) {
                writeList.add(table.getId() + "," + table.getCapacity());
            }
            fileWrite(this.tablesFile, writeList);
        }
    }

    public void saveOrdersToStorage(List<Orders> inputList){
        if(this.isWriteable(this.ordersFile)){
            ArrayList<String> writeList = new ArrayList<>();
            for(Orders order : inputList){
                writeList.add(order.getId() + "," + order.getDish().getId() + "," + order.getTable().getId() + "," + order.getCount() + "," + order.getIsDelivered() + "," + order.getIsPaid() + "," + order.getOrderedTime() + "," + order.getFulfilmentTime());
            }
            fileWrite(this.ordersFile, writeList);
        }
    }

    public boolean isDataStorageAvailable(){
        boolean isDataStorageAvailable = true;
        try {
            if (!this.isWriteable(this.dishesFile)) {
                isDataStorageAvailable = false;
            }
            if (!this.isWriteable(this.tablesFile)) {
                isDataStorageAvailable = false;
            }
            if (!this.isWriteable(this.ordersFile)) {
                isDataStorageAvailable = false;
            }
        }catch(Exception e){
            isDataStorageAvailable = false;
            e.printStackTrace();
        }
        return isDataStorageAvailable;
    }

    private boolean isWriteable(File inputFile){
        boolean isWriteable = true;
        try {
            if (!inputFile.canWrite()) {
                isWriteable = false;
            }
        }catch(Exception e){
            isWriteable = false;
            e.printStackTrace();
        }
        return isWriteable;
    }

    private boolean isReadable(File inputFile){
        boolean isReadable = true;
        try {
            if (!inputFile.canRead()) {
                isReadable = false;
            }
        }catch(Exception e){
            isReadable = false;
            e.printStackTrace();
        }
        return isReadable;
    }

    public void fileWrite(File file, List<String> inputLines){
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            for (String line : inputLines){
                outputWriter.println(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void fileRead(){

    }

    public void prepareDataFiles(){
        try {
            this.fileCreate(dishesFile);
            this.fileCreate(tablesFile);
            this.fileCreate(ordersFile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void fileCreate(File file){
        try {
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public File getDishesFile(){return this.dishesFile;}
    public File getTablesFile(){return this.tablesFile;}
    public File getOrdersFile(){return this.ordersFile;}
}
