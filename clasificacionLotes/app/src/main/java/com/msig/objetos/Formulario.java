package com.msig.objetos;

/**
 * Created by wmazariegos on 28/08/2016.
 */
public class Formulario {
    private String title;
    private String categoryId;
    private String description;
    private int imagen;

    public Formulario() {
        super();
    }

    public Formulario(String title, String categoryId, String description, int imagen) {
        super();
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.imagen = imagen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
