package ma.ac.ensias.model.entity.enums;

public enum MovieType {
    FILM("film"),
    SERIES("series"),
    ANIME("anime"),
    CARTOON("cartoon");

    private String name;
    MovieType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
