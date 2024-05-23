/*
################################################################################################
# RESTAURANT ORDERING SYSTEM
# It describes system classes for tables, dishes, orders, meal, drinks, etc.
# It controls orders status.
#
# Author: Jan Novotny, <16.5.2024>
# mail: bonoman@volny.cz
################################################################################################
 */

import cz.bonoman.restaurant.RestaurantManager;

public class Main {

    private static final RestaurantManager manager = new RestaurantManager();;

    public static void main(String[] args){
        try {
            System.out.println("RESTAURANT ORDERING system starting.");
            initSystem();
            System.out.println(manager.printOrders(manager.getUnfinishedOrders()));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    private static void initSystem(){
        try {
            if (manager.isDataStorageAvailable()) {
                System.out.println("Data storage available.\nReading storage data.");
                manager.loadStorageData();
            } else {
                System.out.println("Data storage not available.\nPreparing storage data.");
                manager.prepareDataStorage();
                manager.initTables(20);
                manager.initDishes();
                manager.initOrders();
            }
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

}