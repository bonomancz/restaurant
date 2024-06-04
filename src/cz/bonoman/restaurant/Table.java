package cz.bonoman.restaurant;

public class Table {
    private int id, capacity;

    public Table(int capacity, int id){
        this.capacity = capacity;
        this.id = id;
    }

    public int getId(){return this.id;}
    public int getCapacity(){return this.capacity;}
    public void setCapacity(int input){this.capacity = input;}
}
