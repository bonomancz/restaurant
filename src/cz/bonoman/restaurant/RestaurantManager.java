package cz.bonoman.restaurant;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {
    private List<Order> orderList;
    private List<Dish> dishList;
    private List<Table> tablesList;
    private final DataHandler dataHandler;

    public RestaurantManager(){
        orderList = new ArrayList<>();
        dishList = new ArrayList<>();
        tablesList = new ArrayList<>();
        dataHandler = new DataHandler();
    }

    public boolean isDataStorageAvailable() throws StorageDataException{
        boolean isDataStorageAvailable = false;
        if (this.dataHandler.isDataStorageAvailable()) {
            isDataStorageAvailable = true;
        }
        return isDataStorageAvailable;
    }

    public void flushMemoryStructures(){
        this.orderList.clear();
        this.tablesList.clear();
        this.dishList.clear();
    }

    public void prepareDataStorage() throws StorageDataException{
        try {
            this.dataHandler.prepareDataFiles();
        }catch(IOException e){
            throw new StorageDataException("prepareDataStorage() " + e.getMessage());
        }
    }

    public void loadStorageData() throws RestaurantException {
        boolean isOrdersLoaded = false, isDishesLoaded = false, isTablesLoaded = false;
        try {
            if (this.isDataStorageAvailable()) {
                try {
                    ArrayList<Dish> loadDishList = new ArrayList<>(this.dataHandler.loadDishesFromStorage());
                    if (!loadDishList.isEmpty()) {this.dishList = loadDishList;isDishesLoaded = true;}
                    ArrayList<Table> loadTableList = new ArrayList<>(this.dataHandler.loadTablesFromStorage());
                    if (!loadTableList.isEmpty()) {this.tablesList = loadTableList;isTablesLoaded = true;}
                    ArrayList<Order> loadOrderList = new ArrayList<>(this.dataHandler.loadOrdersFromStorage());
                    if (!loadOrderList.isEmpty()) {this.orderList = loadOrderList;isOrdersLoaded = true;}
                }catch (RestaurantException e){System.err.println(e.getMessage());}
            }
            if (isOrdersLoaded && isDishesLoaded && isTablesLoaded) { // content load from existing storage
                System.out.println("Data v pořádku načtena.");
            } else { // unavailable storage, content generated automatically
                this.flushMemoryStructures();this.prepareDataStorage();this.initDishes();this.initTables(20);this.initOrders();
                throw new RestaurantException("Storage: Data nebyla kompletně načtena. Systém bude pokračovat s prázdnými daty.");
            }
        }catch(StorageDataException e){throw new RestaurantException("loadStorageData(): " + e.getMessage());}
    }

    public void initDishes() throws RestaurantException{
        try {
            this.dishListAdd(new Dish("Minerální voda", "mineralni_voda_01", 5, 40, this.getNextDishId()));
            this.dishListAdd(new Dish("Pomerančový džus 0,2l", "pomerancovy_dzus_01", 5, 40, this.getNextDishId()));
            this.dishListAdd(new Dish("Kofola 0,5l", "kofola_01", 5, 40, this.getNextDishId()));
            this.dishListAdd(new Dish("Pilsner Urquell 0,5l", "pilsner_urquell_01", 8, 50, this.getNextDishId()));
            this.dishListAdd(new Dish("Irsai Oliver 0,2l", "irsai_oliver_01", 5, 80, this.getNextDishId()));
            this.dishListAdd(new Dish("Kuřecí řízek obalovaný 150 g", "kureci_rizek_obalovany_01", 15, 240, this.getNextDishId()));
            this.dishListAdd(new Dish("Hranolky 150 g", "hranolky_01", 8, 45, this.getNextDishId()));
            this.dishListAdd(new Dish("Boloňské špagety", "bolonske_spagety_01", 20, 195, this.getNextDishId()));
            this.dishListAdd(new Dish("Pstruh na víně 200 g", "pstruh_na_vine_01", 20, 260, this.getNextDishId()));
            this.dishListAdd(new Dish("Srnčí guláš", "srnci_gulas_01", 15, 195, this.getNextDishId()));
            this.dishListAdd(new Dish("Těstoviny Aglio Olio Pepperoncino", "testoviny_aglio_01", 15, 180, this.getNextDishId()));
            this.dishListAdd(new Dish("Domácí hamburger 350g", "domaci_hamburger_01", 25, 290, this.getNextDishId()));
            this.dishListAdd(new Dish("Procesecco 0,7l", "prosecco_01", 5, 340, this.getNextDishId()));
        }catch(RestaurantException e){
            throw new RestaurantException("initDishes(): " + e.getMessage());
        }

    }

    public void initTables(int tablesCount) throws RestaurantException{
        for(int i = 0; i < tablesCount; i++){
            this.tableListAdd(new Table(4, getNextTableId()));
        }
    }

    public void initOrders() throws RestaurantException{
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,38,17), null, this.getTablesList().get(8), this.getDishesList().get(1), 4, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,37,10), null, this.getTablesList().get(15), this.getDishesList().get(5), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,39,5), null, this.getTablesList().get(15), this.getDishesList().get(6), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,34,15), LocalDateTime.of(2024,5,25,15,45,26), this.getTablesList().get(15), this.getDishesList().get(2), 2, true, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,50,56), null, this.getTablesList().get(2), this.getDishesList().get(9), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,50,54), null, this.getTablesList().get(2), this.getDishesList().get(8), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,50,58), LocalDateTime.of(2024, 5, 25,16, 5,12), this.getTablesList().get(2), this.getDishesList().get(3), 1, true, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,51,0), null, this.getTablesList().get(2), this.getDishesList().get(6), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,51,39), LocalDateTime.of(2024, 5, 25,15, 59,6), this.getTablesList().get(2), this.getDishesList().get(4), 1, true, false, this.getNextOrderId()));
    }

    public void dishListAdd(Dish input) throws RestaurantException{
            this.dishList.add(input);
            this.dataHandler.saveDishesToStorage(new ArrayList<>(this.getDishesList()));
    }

    public void tableListAdd(Table input) throws RestaurantException{
        this.tablesList.add(input);
        this.dataHandler.saveTablesToStorage(new ArrayList<>(this.getTablesList()));
    }

    public void orderListAdd(Order input) throws RestaurantException{
        this.orderList.add(input);
        this.dataHandler.saveOrdersToStorage(new ArrayList<>(this.getOrdersList()));
    }

    public ArrayList<Order> getUnfinishedOrders(){
        ArrayList<Order> unfinishedOrders = new ArrayList<>();
        for(Order order : this.getOrdersList()){
            if(!order.getIsDelivered()){
                unfinishedOrders.add(order);
            }
        }
        return unfinishedOrders;
    }

    public int getSpendingOnTable(int tableNumber){
        int spending = 0, subSum = 0;
        for(Order order : this.getOrdersList()){
            if(order.getTableId() == tableNumber){
                int price = order.getDish().getPrice();
                int count = order.getCount();
                if(price > 0 && count > 0) {
                    subSum = price * count;
                }
                spending += subSum;
            }
        }
        return spending;
    }

    public String printSpendingOnTable(int tableNumber){
        StringBuilder avgFfTime = new StringBuilder();
        avgFfTime.append("\n** Celková cena konzumace pro stůl č. " + tableNumber + " **\n******\n");
        avgFfTime.append(this.getSpendingOnTable(tableNumber)).append(" Kč").append("\n");
        avgFfTime.append("******");
        return avgFfTime.toString();
    }

    public ArrayList<Order> getOrdersByTable(int tableNumber){
        ArrayList<Order> tableOrders = new ArrayList<>();
        HashMap<Integer, Integer> dishesIds = new HashMap<Integer, Integer>();
        for(Order order : this.getOrdersList()){
            if(order.getTable().getId() == tableNumber){
                int dishId = order.getDish().getId();
                if(!dishesIds.containsKey(dishId)) {
                    tableOrders.add(order);
                    dishesIds.put(dishId, 1);
                }else{
                    dishesIds.put(dishId, dishesIds.get(dishId) + 1);
                    for(Order tmpOrder : tableOrders){
                        if(tmpOrder.getDish().getId() == dishId){
                            tmpOrder.setCount(dishesIds.get(dishId));
                        }
                    }
                }
            }
        }
        return tableOrders;
    }

    public String printOrders(ArrayList<Order> inputList, String header){
        StringBuilder orders  = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        int counter = 1, tableId = 0;
        orders.append("\n******\n");
        for(Order order : inputList){
            tableId = order.getTableId();
            String getCountStr = order.getCount() > 1 ? String.valueOf(order.getCount()) + "x " : "";
            String getCompletePriceStr = order.getCount() > 1 ? String.valueOf(order.getDish().getPrice() * order.getCount()) : String.valueOf(order.getDish().getPrice());
            String getFulfilmentTime = order.getFulfilmentTime() == null ? "" : order.getFulfilmentTime().format(dateTimeFormatter);
            String getPaidStatus = order.getIsPaid() ? " zaplaceno" : "";
            orders.append(counter).append(". ").append(order.getDish().getTitle()).append(" ").append(getCountStr).append("(").append(getCompletePriceStr).append(" Kč):\t").append(order.getOrderedTime().format(dateTimeFormatter)).append("-");
            orders.append(getFulfilmentTime).append("\t").append(getPaidStatus).append("\n");
            counter++;
        }
        orders.append("******");
        orders.insert(0, "\n** " + prepareHeaderString(tableId, header) + " **");
        return orders.toString();
    }

    private String prepareHeaderString(int tableId, String header){
        String headerStr = "";
        String tableIdString = String.valueOf(tableId);
        if(tableId < 10){tableIdString = " " + tableIdString;}
        switch(header){
            case "forTable":    headerStr = "Objednávky pro stůl č. " + tableIdString + " "; break;
            case "unfinished":  headerStr = "Nedokončené objednávky"; break;
            default:            headerStr = "Všechny objednávky"; break;
        }
        return headerStr;
    }

    public void sortOrders(String sortBy){
        switch(sortBy) {
            case "orderedTime": this.orderList.sort(Comparator.comparing(Order::getOrderedTime));
                                break;
            case "id":          this.orderList.sort(Comparator.comparing(Order::getId));
                                break;
            case "tableId":     this.orderList.sort(Comparator.comparing(Order::getTableId));
                                break;
            default:            this.orderList.sort(Comparator.comparing(Order::getId));
                                break;
        }
    }

    private ArrayList<Dish> getTodaysOrdersDishes(){
        ArrayList<Dish> todaysOrdersDishes = new ArrayList<>();
        ArrayList<Integer> tmpDishes = new ArrayList<>();
        for(Order order : this.getOrdersList()){
            if (!tmpDishes.contains(order.getDish().getId())) {
                tmpDishes.add(order.getDish().getId());
                todaysOrdersDishes.add(order.getDish());
            }
        }
        return todaysOrdersDishes;
    }

    public String printTodaysDishes(){
        StringBuilder todaysDishes = new StringBuilder();
        todaysDishes.append("\n** Dnes objednaná jídla **\n******\n");
        int counter = 1;
        for(Dish dish : this.getTodaysOrdersDishes()){
            todaysDishes.append(counter).append(". ").append(dish.getTitle()).append("\n");
            counter++;
        }
        todaysDishes.append("******");
        return todaysDishes.toString();
    }

    public int getAverageFulfilmentTime(){
        int fulfilmentTime = 0, counter = 0;
        long minutes = 0;
        for(Order order : this.getOrdersList()){
            if(order.getFulfilmentTime() != null){
                LocalDateTime start = order.getOrderedTime();
                LocalDateTime stop = order.getFulfilmentTime();
                minutes += ChronoUnit.MINUTES.between(start, stop);
                counter++;
            }
        }
        if(counter > 0 && minutes > 0) {
            fulfilmentTime = (int)Math.ceil((float) minutes / counter);
        }
        return fulfilmentTime;
    }

    public String printAverageFulfilmentTime(){
        StringBuilder avgFfTime = new StringBuilder();
        avgFfTime.append("\n** Průměrný čas vydávání objednávek **\n******\n");
        avgFfTime.append(this.getAverageFulfilmentTime()).append(" min.").append("\n");
        avgFfTime.append("******");
        return avgFfTime.toString();
    }


    public void restoreDataFromStorage() throws RestaurantException{
        System.out.println("Probíhá načítání dat z úložiště...");
        this.loadStorageData();
    }

    public void backupDataToStorage() throws RestaurantException{
        System.out.println("Probíhá ukládání dat do úložiště...");
        this.dataHandler.saveDishesToStorage(new ArrayList<>(this.getDishesList()));
        this.dataHandler.saveTablesToStorage(new ArrayList<>(this.getTablesList()));
        this.dataHandler.saveOrdersToStorage(new ArrayList<>(this.getOrdersList()));
    }

    public int getNextTableId(){
        int nextId, id;
        nextId = 0;
        if(!this.tablesList.isEmpty()) {
            for (Table table : this.tablesList) {
                id = table.getId();
                if (id > nextId) {
                    nextId = id;
                }
            }
            nextId += 1;
        }
        return nextId;
    }

    public int getNextDishId(){
        int nextId, id;
        nextId = 0;
        if(!this.dishList.isEmpty()) {
            for (Dish dish : this.dishList) {
                id = dish.getId();
                if (id > nextId) {
                    nextId = id;
                }
            }
            nextId += 1;
        }
        return nextId;
    }

    public int getNextOrderId(){
        int nextId, id;
        nextId = 0;
        if(!this.orderList.isEmpty()) {
            for (Order order : this.orderList) {
                id = order.getId();
                if (id > nextId) {
                    nextId = id;
                }
            }
            nextId += 1;
        }
        return nextId;
    }

    public ArrayList<Table> getTablesList(){return new ArrayList<>(this.tablesList);}
    public ArrayList<Dish> getDishesList(){return new ArrayList<>(this.dishList);}
    public ArrayList<Order> getOrdersList(){return new ArrayList<>(this.orderList);}
}
