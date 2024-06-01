package cz.bonoman.restaurant;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private List<Orders> ordersList;
    private List<Dishes> dishesList;
    private List<Tables> tablesList;
    private final DataHandler dataHandler;

    public RestaurantManager(){
        ordersList = new ArrayList<>();
        dishesList = new ArrayList<>();
        tablesList = new ArrayList<>();
        dataHandler = new DataHandler();
    }

    public boolean isDataStorageAvailable() throws StorageDataException {
        boolean isDataStorageAvailable = true;
        if(!dataHandler.isDataStorageAvailable()){
            isDataStorageAvailable = false;
        }
        return isDataStorageAvailable;
    }

    public void flushMemoryStructures(){
        this.ordersList.clear();
        this.tablesList.clear();
        this.dishesList.clear();
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
            if (this.dataHandler.isDataStorageAvailable()) {
                try {
                    ArrayList<Dishes> loadDishesList = new ArrayList<>(this.dataHandler.loadDishesFromStorage());
                    if (!loadDishesList.isEmpty()) {
                        this.dishesList = loadDishesList;
                        isDishesLoaded = true;
                    }
                    ArrayList<Tables> loadTablesList = new ArrayList<>(this.dataHandler.loadTablesFromStorage());
                    if (!loadTablesList.isEmpty()) {
                        this.tablesList = loadTablesList;
                        isTablesLoaded = true;
                    }
                    ArrayList<Orders> loadOrdersList = new ArrayList<>(this.dataHandler.loadOrdersFromStorage());
                    if (!loadOrdersList.isEmpty()) {
                        this.ordersList = loadOrdersList;
                        isOrdersLoaded = true;
                    }
                }catch (RestaurantException e){
                    System.err.println(e.getMessage());
                }
            }
            if (isOrdersLoaded && isDishesLoaded && isTablesLoaded) {
                System.out.println("Storage data loaded successfully.");
            } else {
                this.flushMemoryStructures();
                this.prepareDataStorage();
                this.initDishes();
                this.initTables(20);
                this.initOrders();
                throw new RestaurantException("Storage: Data not loaded completely. System will continue with empty data files.");
            }
        }catch(StorageDataException e){
            throw new RestaurantException("loadStorageData(): " + e.getMessage());
        }
    }

    public void initDishes() throws RestaurantException{
        this.dishListAdd(new Dishes("Minerální voda", "mineralni_voda_01", 5, 40, this.getNextDishId()));
        this.dishListAdd(new Dishes("Pomerančový džus 0,2l", "pomerancovy_dzus_01", 5, 40, this.getNextDishId()));
        this.dishListAdd(new Dishes("Kofola 0,5l", "kofola_01", 5, 40, this.getNextDishId()));
        this.dishListAdd(new Dishes("Pilsner Urquell 0,5l", "pilsner_urquell_01", 8, 50, this.getNextDishId()));
        this.dishListAdd(new Dishes("Irsai Oliver 0,2l", "irsai_oliver_01", 5, 80, this.getNextDishId()));
        this.dishListAdd(new Dishes("Kuřecí řízek obalovaný 150 g", "kureci_rizek_obalovany_01", 15, 240, this.getNextDishId()));
        this.dishListAdd(new Dishes("Hranolky 150 g", "hranolky_01", 8, 45, this.getNextDishId()));
        this.dishListAdd(new Dishes("Boloňské špagety", "bolonske_spagety_01", 20, 195, this.getNextDishId()));
        this.dishListAdd(new Dishes("Pstruh na víně 200 g", "pstruh_na_vine_01", 20, 260, this.getNextDishId()));
        this.dishListAdd(new Dishes("Srnčí guláš", "srnci_gulas_01", 15, 195, this.getNextDishId()));
        this.dishListAdd(new Dishes("Těstoviny Aglio Olio Pepperoncino", "testoviny_aglio_01", 15, 180, this.getNextDishId()));
        this.dishListAdd(new Dishes("Domácí hamburger 350g", "domaci_hamburger_01", 25, 290, this.getNextDishId()));
    }

    public void initTables(int tablesCount) throws RestaurantException{
        for(int i = 0; i < tablesCount; i++){
            this.tableListAdd(new Tables(4, getNextTableId()));
        }
    }

    public void initOrders() throws RestaurantException{
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,38,17), null, this.getTablesList().get(8), this.getDishesList().get(1), 4, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,40,6), null, this.getTablesList().get(15), this.getDishesList().get(5), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,40,6), null, this.getTablesList().get(15), this.getDishesList().get(6), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,40,6), LocalDateTime.of(2024,5,18,15,45,26), this.getTablesList().get(15), this.getDishesList().get(2), 2, true, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,50,56), null, this.getTablesList().get(2), this.getDishesList().get(9), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,50,54), null, this.getTablesList().get(2), this.getDishesList().get(8), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,50,58), LocalDateTime.of(2024, 5, 25,16, 5,12), this.getTablesList().get(2), this.getDishesList().get(3), 1, true, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,51,0), null, this.getTablesList().get(2), this.getDishesList().get(6), 1, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,25,15,51,39), LocalDateTime.of(2024, 5, 25,15, 59,6), this.getTablesList().get(2), this.getDishesList().get(4), 1, true, false, this.getNextOrderId()));
    }

    public void dishListAdd(Dishes input) throws RestaurantException{
        this.dishesList.add(input);
        this.dataHandler.saveDishesToStorage(new ArrayList<>(this.getDishesList()));
    }

    public void tableListAdd(Tables input) throws RestaurantException{
        this.tablesList.add(input);
        this.dataHandler.saveTablesToStorage(new ArrayList<>(this.getTablesList()));
    }

    public void orderListAdd(Orders input) throws RestaurantException{
        this.ordersList.add(input);
        this.dataHandler.saveOrdersToStorage(new ArrayList<>(this.getOrdersList()));
    }

    public ArrayList<Orders> getUnfinishedOrders(){
        ArrayList<Orders> unfinishedOrders = new ArrayList<>();
        for(Orders order : this.getOrdersList()){
            if(!order.getIsDelivered()){
                unfinishedOrders.add(order);
            }
            //System.out.println(order.getIsDelivered());
        }
        return unfinishedOrders;
    }

    public String printOrders(ArrayList<Orders> inputList){
        StringBuilder orders  = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        int counter = 1;
        for(Orders order : inputList){
            String getFulfilmentTime = order.getFulfilmentTime() == null ? "nevydáno" : order.getFulfilmentTime().format(dateTimeFormatter);
            orders.append(counter).append(". ");
            orders.append(order.getDish().getTitle());
            orders.append(" (").append(order.getDish().getPrice());
            orders.append(" Kč):\t").append(order.getOrderedTime().format(dateTimeFormatter));
            orders.append("-").append(getFulfilmentTime);
            orders.append("\n");
            counter++;
        }
        return orders.toString();
    }

    public void restoreStorageData(){}
    public void backupStorageData(){}

    public int getNextTableId(){
        int nextId, id;
        nextId = 0;
        if(!this.tablesList.isEmpty()) {
            for (Tables table : this.tablesList) {
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
        if(!this.dishesList.isEmpty()) {
            for (Dishes dish : this.dishesList) {
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
        if(!this.ordersList.isEmpty()) {
            for (Orders order : this.ordersList) {
                id = order.getId();
                if (id > nextId) {
                    nextId = id;
                }
            }
            nextId += 1;
        }
        return nextId;
    }

    public ArrayList<Tables> getTablesList(){return new ArrayList<>(this.tablesList);}
    public ArrayList<Dishes> getDishesList(){return new ArrayList<>(this.dishesList);}
    public ArrayList<Orders> getOrdersList(){return new ArrayList<>(this.ordersList);}
}
