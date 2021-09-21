package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HumanBeingRaw implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick; //Поле может быть null
    private Float impactSpeed; //Значение поля должно быть больше -882, Поле может быть null
    private String soundtrackName; //Поле не может быть null
    private float minutesOfWaiting;
    private Mood mood; //Поле может быть null
    private Car car; //Поле может быть null

    public HumanBeingRaw() {}

    public HumanBeingRaw(String name,
                      Coordinates coordinates,
                      Boolean realHero,
                      Boolean hasToothpick,
                      Float impactSpeed,
                      String soundtrackName,
                      float minutesOfWaiting,
                      Mood mood,
                      Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.mood = mood;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Car getCar() {
        return car;
    }

    public Float getImpactSpeed() {
        return impactSpeed;
    }

    public float getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public Mood getMood() {
        return mood;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        HumanBeingRaw HumanBeingRaw = (HumanBeingRaw) o;
        if (this.hashCode() != HumanBeingRaw.hashCode()) return false;
        return (this.getName().equals(HumanBeingRaw.getName()) && this.getCoordinates().equals(HumanBeingRaw.getCoordinates())
                && this.getCreationDate().equals(HumanBeingRaw.getCreationDate()) && this.getHasToothpick().equals(HumanBeingRaw.getHasToothpick())
                && this.getRealHero().equals(HumanBeingRaw.getRealHero()) && this.getSoundtrackName().equals(HumanBeingRaw.getSoundtrackName())
                && this.getImpactSpeed().equals(HumanBeingRaw.getImpactSpeed()) && this.getMinutesOfWaiting() == HumanBeingRaw.getMinutesOfWaiting()
                && this.getCar().equals(HumanBeingRaw.getCar()) && this.getMood().equals(HumanBeingRaw.getMood()));
    }

    @Override
    public int hashCode(){
        int ans = 0;
        for (int i = 0; i < this.getName().length(); i++){
            ans = (ans + (int)this.getName().charAt(i)) % 2000000000;
        }
        ans = (ans + this.getSoundtrackName().length()) % 2000000000;
        return ans;
    }

    public String toString() {
        String human = "Name: " + name + "\n";
        human += "Coordinates: " + "(" + coordinates.getX() + "; " + coordinates.getY() + ")\n";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        human += "Creation Date: " + creationDate.format(formatter) + "\n";
        human += "Real hero: " + realHero + "\n";
        human += "Has toothpick: " + hasToothpick + "\n";
        human += "Impact speed: " + impactSpeed + "\n";
        human += "Soundtrack name: " + soundtrackName + "\n";
        human += "Minutes of waiting: " + minutesOfWaiting + "\n";
        human += "Mood: " + (mood != null? mood.toString() : "") + "\n";
        human += "Car: " + car.getName() + "\n";
        return human;
    }
}
