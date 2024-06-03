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

import java.util.ArrayList;
import cz.bonoman.restaurant.RestaurantManager;
import cz.bonoman.restaurant.RestaurantException;
import cz.bonoman.restaurant.StorageDataException;

public class Main {

    private static final RestaurantManager manager = new RestaurantManager();;

    public static void main(String[] args){
        try {
            System.out.println("\nRESTAURAČNÍ OBJEDNÁVKOVÝ SYSTÉM");
            initSystem();
            manager.sortOrders("orderedTime");
            System.out.println(manager.printOrders(new ArrayList<>(manager.getOrdersByTable(15)), "forTable"));
            System.out.println(manager.printSpendingOnTable(15));
            System.out.println(manager.printOrders(new ArrayList<>(manager.getUnfinishedOrders()), "unfinished"));
            System.out.println(manager.printAverageFulfilmentTime());
            System.out.println(manager.printTodaysDishes());
        }catch (RestaurantException e){
            System.err.println("main(): " + e.getMessage());
        }
    }

    private static void initSystem() throws RestaurantException{
        try{
            if(manager.isDataStorageAvailable()) {
                System.out.println("Datové úložiště je dostupné.\nProbíhá načítání dat.");
                manager.loadStorageData();
            }
        }catch(StorageDataException e){
            System.err.println("Datové úložiště není dostupné.\nPřipravují se data.");
            try{
                manager.prepareDataStorage();
                manager.initTables(20);
                manager.initDishes();
                manager.initOrders();
            }catch(StorageDataException dse){
                throw new RestaurantException(dse.getMessage());
            }
            throw new RestaurantException(e.getMessage());
        }
    }
}