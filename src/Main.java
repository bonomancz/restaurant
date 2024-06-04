/*
################################################################################################
# RESTAURANT ORDERING SYSTEM
# It describes system classes for tables, dishes, orders
# It controls orders status.
#
# Author: Jan Novotny, <16.5.2024>
# mail: bonoman@volny.cz
################################################################################################
 */

import java.time.LocalDateTime;
import java.util.ArrayList;

import cz.bonoman.restaurant.Order;
import cz.bonoman.restaurant.RestaurantManager;
import cz.bonoman.restaurant.RestaurantException;
import cz.bonoman.restaurant.StorageDataException;

public class Main {

    private static final RestaurantManager manager = new RestaurantManager();;

    public static void main(String[] args){
        try {
            System.out.println("\nRESTAURAČNÍ OBJEDNÁVKOVÝ SYSTÉM");
            // 1. načtení stavu evidence z disku
            initSystem();
            manager.orderListAdd(new Order(LocalDateTime.of(2024,5,25,15,45,10), LocalDateTime.of(2024,5,25,15,48,6), manager.getTablesList().get(15), manager.getDishesList().get(12), 1, true, false, manager.getNextOrderId()));
            manager.sortOrders("orderedTime");
            System.out.println(manager.printOrders(new ArrayList<>(manager.getOrdersByTable(15)), "forTable"));
            // 3. cena konzumace pro stůl
            System.out.println(manager.printSpendingOnTable(15));
            // 4. informace pro management
            System.out.println(manager.printOrders(new ArrayList<>(manager.getUnfinishedOrders()), "unfinished"));
            System.out.println(manager.printAverageFulfilmentTime());
            System.out.println(manager.printTodaysDishes());
            // 5. backup dat na disk
            manager.backupDataToStorage();
        }catch (RestaurantException e){
            System.err.println("main(): " + e.getMessage());
        }
    }

    private static void initSystem() throws RestaurantException{
        try{
            // 6. load dat při opětovném spuštění
            if(manager.isDataStorageAvailable()) {
                System.out.println("Datové úložiště je dostupné.\nProbíhá načítání dat.");
                manager.loadStorageData();
            }
        }catch(StorageDataException e){
            System.err.println("Datové úložiště není dostupné.\nGenerují se nová data.");
            try{
                manager.prepareDataStorage();
                manager.initTables(20);
                // 2. testovací data
                manager.initDishes();
                manager.initOrders();
            }catch(StorageDataException dse){
                throw new RestaurantException(dse.getMessage());
            }
            throw new RestaurantException("initSystem(): " + e.getMessage());
        }
    }
}