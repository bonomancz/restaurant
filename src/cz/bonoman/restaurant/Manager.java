package cz.bonoman.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String dishesDataFile;
    private String ordersDataFile;
    private List<Orders> ordersList = new ArrayList<>();
    private List<Dishes> dishesList = new ArrayList<>();
    private List<Tables> tablesList = new ArrayList<>();

    public Manager(){}

    public void initDataStorage(){
        this.dishesDataFile = "./appData/dishes.txt";
        this. ordersDataFile = "./appData/orders.txt";
    }

    public void initDishes(){

    }

    public void initTables(int tablesCount){
        for(int i = 0; i < tablesCount; i++){
            this.tableListAdd(new Tables(4, getNextTableId()));
        }
    }

    public void initOrders(){

    }

    public void tableListAdd(Tables input){
        this.tablesList.add(input);
    }

    public void restoreStorageData(){}
    public void backupStorageData(){}

    public int getNextTableId(){
        int retVal, id;
        retVal = 0;
        for(Tables table : this.tablesList){
            id = table.getId();
            if(id > retVal){
                retVal = id;
            }
        }
        retVal += 1;
        return retVal;
    }

    public int getNextDishId(){
        int retVal, id;
        retVal = 0;
        for(Dishes dish : this.dishesList){
            id = dish.getId();
            if(id > retVal){
                retVal = id;
            }
        }
        retVal += 1;
        return retVal;
    }

    public int getNextOrderId(){
        int retVal, id;
        retVal = 0;
        for(Orders order : this.ordersList){
            id = order.getId();
            if(id > retVal){
                retVal = id;
            }
        }
        retVal += 1;
        return retVal;
    }
}
