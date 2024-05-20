package cz.bonoman.restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private final List<Orders> ordersList;
    private final List<Dishes> dishesList;
    private final List<Tables> tablesList;
    private final DataHandler dataHandler;

    public RestaurantManager(){
        ordersList = new ArrayList<>();
        dishesList = new ArrayList<>();
        tablesList = new ArrayList<>();
        dataHandler = new DataHandler();
    }

    public void initSystem(){
        if(this.isDataStorageAvailable()){
            System.out.println("Data storage available.\nReading data from files.");
            this.loadStorageData();
        }else {
            System.out.println("Data storage not available.\nGenerating data and storage.");
            this.prepareDataStorage();
            this.initTables(20);
            this.initDishes();
            this.initOrders();
        }
    }

    public boolean isDataStorageAvailable(){
        boolean isDataStorageAvailable = true;
        if(!dataHandler.isDataStorageAvailable()){
            isDataStorageAvailable = false;
        }
        return isDataStorageAvailable;
    }

    public void prepareDataStorage(){
        this.dataHandler.prepareDataFiles();
    }

    public void loadStorageData(){

    }

    public void initDishes(){
        this.dishListAdd(new Dishes("Boloňské špagety", "bolonske_spagety_01", 20, 195, this.getNextDishId()));
        this.dishListAdd(new Dishes("Minerální voda", "mineralni_voda_01", 0, 40, this.getNextDishId()));
        this.dishListAdd(new Dishes("Kuřecí řízek obalovaný 150 g", "kureci_rizek_obalovany_01", 15, 240, this.getNextDishId()));
        this.dishListAdd(new Dishes("Hranolky 150 g", "hranolky_01", 8, 45, this.getNextDishId()));
        this.dishListAdd(new Dishes("Pstruh na víně 200 g", "pstruh_na_vine_01", 20, 260, this.getNextDishId()));
        this.dishListAdd(new Dishes("Kofola 0,5l", "kofola_01", 0, 40, this.getNextDishId()));
        dataHandler.saveDishesToStorage(new ArrayList<>(this.getDishesList()));
    }

    public void initTables(int tablesCount){
        for(int i = 0; i < tablesCount; i++){
            this.tableListAdd(new Tables(4, getNextTableId()));
        }
        dataHandler.saveTablesToStorage(new ArrayList<>(this.getTablesList()));
    }

    public void initOrders(){
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,18,15,38,17), null, this.getTablesList().get(8), this.getDishesList().get(1), 4, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,18,15,40,6), null, this.getTablesList().get(15), this.getDishesList().get(2), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,18,15,40,6), null, this.getTablesList().get(15), this.getDishesList().get(3), 2, false, false, this.getNextOrderId()));
        this.orderListAdd(new Orders(LocalDateTime.of(2024,5,18,15,40,6), LocalDateTime.of(2024,5,18,15,45,26), this.getTablesList().get(15), this.getDishesList().get(5), 2, true, false, this.getNextOrderId()));
        dataHandler.saveOrdersToStorage(new ArrayList<>(this.getOrdersList()));
    }

    public void dishListAdd(Dishes input){this.dishesList.add(input);}
    public void tableListAdd(Tables input) {this.tablesList.add(input);}
    public void orderListAdd(Orders input){this.ordersList.add(input);}

    public void restoreStorageData(){}
    public void backupStorageData(){}

    public int getNextTableId(){
        int nextId, id;
        nextId = 0;
        for(Tables table : this.tablesList){
            id = table.getId();
            if(id > nextId){
                nextId = id;
            }
        }
        nextId += 1;
        return nextId;
    }

    public int getNextDishId(){
        int nextId, id;
        nextId = 0;
        for(Dishes dish : this.dishesList){
            id = dish.getId();
            if(id > nextId){
                nextId = id;
            }
        }
        nextId += 1;
        return nextId;
    }

    public int getNextOrderId(){
        int nextId, id;
        nextId = 0;
        for(Orders order : this.ordersList){
            id = order.getId();
            if(id > nextId){
                nextId = id;
            }
        }
        nextId += 1;
        return nextId;
    }

    public ArrayList<Tables> getTablesList(){return new ArrayList<>(this.tablesList);}
    public ArrayList<Dishes> getDishesList(){return new ArrayList<>(this.dishesList);}
    public ArrayList<Orders> getOrdersList(){return new ArrayList<>(this.ordersList);}
}
