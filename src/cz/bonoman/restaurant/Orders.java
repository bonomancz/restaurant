package cz.bonoman.restaurant;

import java.time.LocalDateTime;

public class Orders {
    private int id, count;
    private Tables table;
    private Dishes dish;
    private LocalDateTime orderedTime, fulfilmentTime;

    public Orders(LocalDateTime orderedTime, LocalDateTime fulfilmentTime, Tables table, Dishes dish, int count, int id){
        this.id = id;
        this.count = count;
        this.table = table;
        this.dish = dish;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
    }

    public int getId(){return this.id;}
}
