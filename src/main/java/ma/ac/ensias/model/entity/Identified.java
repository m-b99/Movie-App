package ma.ac.ensias.model.entity;

public interface Identified<T> {
    T getId();
    void setId(T id);
}
