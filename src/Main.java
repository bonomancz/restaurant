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
    private static final RestaurantManager manager = new RestaurantManager();

    public static void main(String[] args){
        System.out.println("RESTAURANT ORDERING system starting.");
        manager.initSystem();
    }
}