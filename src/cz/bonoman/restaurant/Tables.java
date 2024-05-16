package cz.bonoman.restaurant;

public class Tables {
    private int id, capacity;

    public Tables(int capacity, int id){
        this.capacity = capacity;
        this.id = id;
    }

    public int getId(){return this.id;}
}
