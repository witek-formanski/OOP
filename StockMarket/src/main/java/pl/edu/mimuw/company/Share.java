package pl.edu.mimuw.company;

public class Share {
    private String name;
    private Company company;

    public Share(String name, int price) {
        this.name = name;
        company = new Company(price);
    }
}
