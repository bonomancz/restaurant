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

import cz.bonoman.restaurant.Manager;

public class Main {
    private static final Manager manager = new Manager();

    public static void main(String[] args) {
        initSystem();
    }

    private static void initSystem(){
        manager.initDataStorage();
        manager.initTables(20);
        manager.initDishes();
        manager.initOrders();
    }
}