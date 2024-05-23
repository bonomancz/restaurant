package cz.bonoman.restaurant;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ArrayList<Dishes> loadDishesFromStorage(){
        ArrayList<Dishes> dishes = new ArrayList<>();
        if(this.isReadable(this.dishesFile)){
            for(String record : this.fileRead(this.dishesFile)){
                if(isValidDishRecord(record)){
                    String[] splPart = record.split(";");
                    int id = Integer.parseInt(splPart[0].trim());
                    String title = splPart[1].trim();
                    String image = splPart[2].trim();
                    int price = Integer.parseInt(splPart[3].trim());
                    int preparationTime = Integer.parseInt(splPart[4].trim());
                    dishes.add(new Dishes(title, image, preparationTime, price, id));
                }else{
                    dishes.clear();
                    throw new RuntimeException("Storage: Invalid dish record detected. Returning empty list.");
                }
            }
        }
        return dishes;
    }

    public ArrayList<Tables> loadTablesFromStorage(){
        ArrayList<Tables> tables = new ArrayList<>();
        if(this.isReadable(this.tablesFile)){
            for(String record : this.fileRead(this.tablesFile)){
                if(isValidTableRecord(record)){
                    String[] splPart = record.split(";");
                    int id = Integer.parseInt(splPart[0].trim());
                    int capacity = Integer.parseInt(splPart[1].trim());
                    tables.add(new Tables(id, capacity));
                }else{
                    tables.clear();
                    throw new RuntimeException("Storage: Invalid table record detected. Returning empty list.");
                }
            }
        }
        return tables;
    }

    public ArrayList<Orders> loadOrdersFromStorage(){
        ArrayList<Orders> orders = new ArrayList<>();
        if(this.isReadable(this.ordersFile)){
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss");
            for(String record : this.fileRead(this.ordersFile)){
                if(isValidOrderRecord(record)){
                    String[] splPart = record.split(";");
                    int id = Integer.parseInt(splPart[0].trim());
                    int dishId = Integer.parseInt(splPart[1].trim());
                    int tableId = Integer.parseInt(splPart[2].trim());
                    int count = Integer.parseInt(splPart[3].trim());
                    boolean isDelivered = Boolean.parseBoolean(splPart[4].trim());
                    boolean isPaid = Boolean.parseBoolean(splPart[5].trim());
                    LocalDateTime orderedTime = LocalDateTime.parse(splPart[6].trim(), inputFormatter);
                    LocalDateTime fulfilmentTime = "null".equals(splPart[7].trim()) ? null : LocalDateTime.parse(splPart[7].trim(), inputFormatter);
                    orders.add(new Orders(orderedTime, fulfilmentTime, this.loadTablesFromStorage().get(tableId), this.loadDishesFromStorage().get(dishId), count, isDelivered, isPaid, id));
                }else{
                    orders.clear();
                    throw new RuntimeException("Storage: Invalid order record detected. Returning empty list.");
                }
            }
        }
        return orders;
    }

    private boolean isValidTableRecord(String input){
        boolean isValidTableRecord = false;
        String regex = "^\\d+;\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            isValidTableRecord = true;
        }
        return isValidTableRecord;
    }

    private boolean isValidDishRecord(String input){
        boolean isValidDishRecord = false;
        String regex = "^\\d+;[^;]+;[^;]+;\\d+;\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            isValidDishRecord = true;
        }
        return isValidDishRecord;
    }

    private boolean isValidOrderRecord(String input){
        boolean isValidOrderRecord = false;
        String regex = "^\\d+;\\d+;\\d+;\\d+;(true|false);(true|false);\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2};(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|null)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()){
            isValidOrderRecord = true;
        }
        return isValidOrderRecord;
    }

    public void saveDishesToStorage(List<Dishes> inputList){
        if(this.isWriteable(this.dishesFile)){
            ArrayList<String> writeList = new ArrayList<>();
            for(Dishes dish : inputList){
                writeList.add(dish.getId() + ";" + dish.getTitle() + ";" + dish.getImage() + ";" + dish.getPrice() + ";" + dish.getPreparationTime());
            }
            fileWrite(this.dishesFile, writeList);
        }
    }

    public void saveTablesToStorage(List<Tables> inputList){
        if(this.isWriteable(this.tablesFile)) {
            ArrayList<String> writeList = new ArrayList<>();
            for (Tables table : inputList) {
                writeList.add(table.getId() + ";" + table.getCapacity());
            }
            fileWrite(this.tablesFile, writeList);
        }
    }

    public void saveOrdersToStorage(List<Orders> inputList){
        if(this.isWriteable(this.ordersFile)){
            ArrayList<String> writeList = new ArrayList<>();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            for(Orders order : inputList){
                String orderedDateTime = order.getOrderedTime().format(dateTimeFormatter);
                String fulfilmentTime = order.getFulfilmentTime() == null ? "null" : order.getFulfilmentTime().format(dateTimeFormatter);
                writeList.add(order.getId() + ";" + order.getDish().getId() + ";" + order.getTable().getId() + ";" + order.getCount() + ";" + order.getIsDelivered() + ";" + order.getIsPaid() + ";" + orderedDateTime + ";" + fulfilmentTime);
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

    private void fileClear(File file){
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            outputWriter.print("");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public List<String> fileRead(File file){
        ArrayList<String> StringList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                StringList.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return StringList;
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
            if(!file.exists()) {
                file.createNewFile();
            }else{
                this.fileClear(file);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public File getDishesFile(){return this.dishesFile;}
    public File getTablesFile(){return this.tablesFile;}
    public File getOrdersFile(){return this.ordersFile;}
}
