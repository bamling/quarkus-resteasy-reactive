package org.acme;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.acme.Animal.Cat;
import org.acme.Animal.Dog;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Dog.class, name = "dog"),
    @JsonSubTypes.Type(value = Cat.class, name = "cat")
})
public class Animal {

    @JsonTypeName("dog")
    public static class Dog extends Animal {

        private double barkVolume;

        public double getBarkVolume() {
            return barkVolume;
        }

        public void setBarkVolume(final double barkVolume) {
            this.barkVolume = barkVolume;
        }
    }

    @JsonTypeName("cat")
    public static class Cat extends Animal {

        private boolean likesCream;
        private int lives;

        public boolean isLikesCream() {
            return likesCream;
        }

        public void setLikesCream(final boolean likesCream) {
            this.likesCream = likesCream;
        }

        public int getLives() {
            return lives;
        }

        public void setLives(final int lives) {
            this.lives = lives;
        }
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}