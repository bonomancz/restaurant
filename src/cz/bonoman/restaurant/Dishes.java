package cz.bonoman.restaurant;

public class Dishes {
    private String title, image;
    private int id, price, preparationTime;

    public Dishes(String title, String image, int preparationTime, int price, int id){
        this.title = title;
        this.image = image;
        this.preparationTime = preparationTime;
        this.price = price;
        this.id = id;
    }

    public int getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getImage(){return this.image;}
    public int getPrice(){return this.price;}
    public int getPreparationTime(){return this.preparationTime;}
}