package cz.bonoman.restaurant;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {
    private final String dishesTxtFile, tablesTxtFile, ordersTxtFile;
    private final File dishesFile, tablesFile, ordersFile;

    public DataHandler(){
        this.dishesTxtFile = "./appData/dishes.txt";
        this.ordersTxtFile = "./appData/orders.txt";
        this.tablesTxtFile = "./appData/tables.txt";
        this.dishesFile = new File(dishesTxtFile);
        this.ordersFile = new File(ordersTxtFile);
        this.tablesFile = new File(tablesTxtFile);
    }

    public ArrayList<Dishes> loadDishesFromStorage() throws RestaurantException{
        ArrayList<Dishes> dishes = new ArrayList<>();
        try {
            if (this.isReadable(this.dishesFile)) {
                for (String record : this.fileRead(this.dishesFile)) {
                    try {
                        if (isValidDishRecord(record)) {
                            String[] splPart = record.split(";");
                            int id = Integer.parseInt(splPart[0].trim());
                            String title = splPart[1].trim();
                            String image = splPart[2].trim();
                            int price = Integer.parseInt(splPart[3].trim());
                            int preparationTime = Integer.parseInt(splPart[4].trim());
                            dishes.add(new Dishes(title, image, preparationTime, price, id));
                        }
                    }catch (RestaurantException e){
                        dishes.clear();
                        throw new RestaurantException("loadDishesFromStorage(): " + e.getMessage());
                    }
                }
            }
        }catch (IOException e){
            throw new RestaurantException("loadDishesFromStorage(): " + e.getMessage());
        }
        return dishes;
    }

    public ArrayList<Tables> loadTablesFromStorage() throws RestaurantException{
        ArrayList<Tables> tables = new ArrayList<>();
        try {
            if (this.isReadable(this.tablesFile)) {
                for (String record : this.fileRead(this.tablesFile)) {
                    try {
                        if (isValidTableRecord(record)) {
                            String[] splPart = record.split(";");
                            int id = Integer.parseInt(splPart[0].trim());
                            int capacity = Integer.parseInt(splPart[1].trim());
                            tables.add(new Tables(id, capacity));
                        }
                    }catch(RestaurantException e){
                        tables.clear();
                        throw new RestaurantException("loadTablesFromStorage(): " + e.getMessage());
                    }
                }
            }
        }catch(IOException e){
            throw new RestaurantException("loadTablesFromStorage(): " + e.getMessage());
        }
        return tables;
    }

    public ArrayList<Orders> loadOrdersFromStorage() throws RestaurantException{
        ArrayList<Orders> orders = new ArrayList<>();
        try {
            if (this.isReadable(this.ordersFile)) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss");
                for (String record : this.fileRead(this.ordersFile)) {
                    try{
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
                        }
                    }catch(RestaurantException e){
                        System.err.println("loadOrdersFromStorage(): " + e.getMessage());
                    }
                }
            }
        }catch(IOException e){
            throw new RestaurantException("loadOrdersFromStorage(): " + e.getMessage());
        }
        return orders;
    }

    private boolean isValidTableRecord(String input) throws RestaurantException{
        boolean isValidTableRecord = true;
        String regex = "^\\d+;\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()){
            isValidTableRecord = false;
            throw new RestaurantException("Storage: Ignoring invalid TABLE record: " + input);
        }
        return isValidTableRecord;
    }

    private boolean isValidDishRecord(String input) throws RestaurantException{
        boolean isValidDishRecord = true;
        String regex = "^\\d+;[^;]+;[^;]+;\\d+;\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()){
            isValidDishRecord = false;
            throw new RestaurantException("Storage: Ignoring invalid DISH record: " + input);
        }
        return isValidDishRecord;
    }

    private boolean isValidOrderRecord(String input) throws RestaurantException{
        boolean isValidOrderRecord = true;
        String regex = "^\\d+;\\d+;\\d+;\\d+;(true|false);(true|false);\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2};(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|null)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()){
            isValidOrderRecord = false;
            throw new RestaurantException("Storage: Ignoring invalid ORDER record: " + input);
        }
        return isValidOrderRecord;
    }

    public void saveDishesToStorage(List<Dishes> inputList) throws RestaurantException{
        try {
            if (this.isWriteable(this.dishesFile)) {
                ArrayList<String> writeList = new ArrayList<>();
                for (Dishes dish : inputList) {
                    writeList.add(dish.getId() + ";" + dish.getTitle() + ";" + dish.getImage() + ";" + dish.getPrice() + ";" + dish.getPreparationTime());
                }
                fileWrite(this.dishesFile, writeList);
            }
        }catch(IOException e){
            throw new RestaurantException("saveDishesToStorage(): " + e.getMessage());
        }
    }

    public void saveTablesToStorage(List<Tables> inputList) throws RestaurantException{
        try{
            if(this.isWriteable(this.tablesFile)) {
                ArrayList<String> writeList = new ArrayList<>();
                for (Tables table : inputList) {
                    writeList.add(table.getId() + ";" + table.getCapacity());
                }
                fileWrite(this.tablesFile, writeList);
            }
        }catch(IOException e){
            throw new RestaurantException("saveDishesToStorage(): " + e.getMessage());
        }
    }

    public void saveOrdersToStorage(List<Orders> inputList) throws RestaurantException{
        try {
            if (this.isWriteable(this.ordersFile)) {
                ArrayList<String> writeList = new ArrayList<>();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                for (Orders order : inputList) {
                    String orderedDateTime = order.getOrderedTime().format(dateTimeFormatter);
                    String fulfilmentTime = order.getFulfilmentTime() == null ? "null" : order.getFulfilmentTime().format(dateTimeFormatter);
                    writeList.add(order.getId() + ";" + order.getDish().getId() + ";" + order.getTable().getId() + ";" + order.getCount() + ";" + order.getIsDelivered() + ";" + order.getIsPaid() + ";" + orderedDateTime + ";" + fulfilmentTime);
                }
                fileWrite(this.ordersFile, writeList);
            }
        }catch (IOException e){
            throw new RestaurantException("saveOrdersToStorage(): " + e.getMessage());
        }
    }

    public boolean isDataStorageAvailable() throws StorageDataException {
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
        }catch(IOException e){
            isDataStorageAvailable = false;
            throw new StorageDataException("isDataStorageAvailable(): " + e.getMessage());
        }
        return isDataStorageAvailable;
    }

    private boolean isWriteable(File inputFile) throws IOException {
        boolean isWriteable = true;
        if (!inputFile.canWrite()) {
            isWriteable = false;
            throw new IOException("isWriteable(): file " + inputFile + " is not writeable.");
        }
        return isWriteable;
    }

    private boolean isReadable(File inputFile) throws IOException{
        boolean isReadable = true;
        if (!inputFile.canRead()) {
            isReadable = false;
            throw new IOException("isReadable(): file " + inputFile + " is not readable.");
        }
        return isReadable;
    }

    public void fileWrite(File file, List<String> inputLines) throws IOException{
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            for (String line : inputLines){
                outputWriter.println(line);
            }
        }catch(IOException e){
            throw new IOException("fileWrite(): " + e.getMessage());
        }
    }

    private void fileClear(File file) throws IOException{
        try(PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            outputWriter.print("");
        }catch(IOException e){
            throw new IOException("fileClear(): " + e.getMessage());
        }
    }

    public List<String> fileRead(File file) throws IOException{
        ArrayList<String> StringList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                StringList.add(line);
            }
        }catch(IOException e){
            throw new IOException("fileRead(): " + e.getMessage());
        }
        return StringList;
    }

    public void prepareDataFiles() throws IOException{
        try {
            this.fileCreate(dishesFile);
            this.fileCreate(tablesFile);
            this.fileCreate(ordersFile);
        }catch(IOException e){
            throw new IOException("prepareDataFiles(): " + e.getMessage());
        }
    }

    private void fileCreate(File file) throws IOException{
        try {
            if(!file.exists()) {
                file.createNewFile();
            }else{
                this.fileClear(file);
            }
        }catch(IOException e){
            throw new IOException("fileCreate(): " + e.getMessage());
        }
    }

    public File getDishesFile(){return this.dishesFile;}
    public File getTablesFile(){return this.tablesFile;}
    public File getOrdersFile(){return this.ordersFile;}
}
