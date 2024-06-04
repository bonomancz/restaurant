package cz.bonoman.restaurant;

import java.time.LocalDateTime;

public class Order {
    private int id, count;
    private boolean isDelivered, isPaid;
    private Table table;
    private Dish dish;
    private LocalDateTime orderedTime, fulfilmentTime;

    public Order(LocalDateTime orderedTime, LocalDateTime fulfilmentTime, Table table, Dish dish, int count, boolean isDelivered, boolean isPaid, int id){
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
    public Dish getDish(){return this.dish;}
    public Table getTable(){return this.table;}
    public int getTableId(){return this.table.getId();}
    public int getCount(){return this.count;}
    public boolean getIsDelivered(){return this.isDelivered;}
    public boolean getIsPaid(){return this.isPaid;}
    public LocalDateTime getOrderedTime(){return this.orderedTime;}
    public LocalDateTime getFulfilmentTime(){return this.fulfilmentTime;}
    public void setCount(int input){this.count = input;}
    public void setDelivered(boolean input){this.isDelivered = input;}
    public void setPaid(boolean input){this.isPaid = input;}
}
