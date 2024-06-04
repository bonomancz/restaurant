package cz.bonoman.restaurant;

public class Dish {
    private String title, image;
    private int id, price, preparationTime;

    public Dish(String title, String image, int preparationTime, int price, int id) throws RestaurantException{
        this.setPreparationTime(preparationTime);
        this.title = title;
        this.image = image;
        this.price = price;
        this.id = id;
    }

    public int getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getImage(){return this.image;}
    public int getPrice(){return this.price;}
    public int getPreparationTime(){return this.preparationTime;}
    public void setPrice(int input){this.price = input;}
    public void setTitle(String input){this.title = input;}
    public void setImage(String input){this.image = input;}
    public void setPreparationTime(int input) throws RestaurantException {
        if(input <= 0) {
            throw new RestaurantException("Dish(): Nutno opravit. Čas přípravy <= 0 min.");
        }
        this.preparationTime = input;
    }
}
