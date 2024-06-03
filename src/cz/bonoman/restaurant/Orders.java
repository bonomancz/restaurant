package cz.bonoman.restaurant;

import java.time.LocalDateTime;

public class Orders {
    private int id, count;
    private boolean isDelivered, isPaid;
    private Tables table;
    private Dishes dish;
    private LocalDateTime orderedTime, fulfilmentTime;

    public Orders(LocalDateTime orderedTime, LocalDateTime fulfilmentTime, Tables table, Dishes dish, int count, boolean isDelivered, boolean isPaid, int id){
        this.id = id;
        this.count = count;
        this.isDelivered = isDelivered;
        this.isPaid = isPaid;
        this.table = table;
        this.dish = dish;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
    }

    public int getId(){return this.id;}
    public Dishes getDish(){return this.dish;}
    public Tables getTable(){return this.table;}
    public int getTableId(){return this.table.getId();}
    public int getCount(){return this.count;}
    public boolean getIsDelivered(){return this.isDelivered;}
    public boolean getIsPaid(){return this.isPaid;}
    public LocalDateTime getOrderedTime(){return this.orderedTime;}
    public LocalDateTime getFulfilmentTime(){return this.fulfilmentTime;}
    public void setCount(int input){this.count = input;}
}
