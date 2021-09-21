package utils;

import data.*;

import exceptions.PermissionDeniedException;
import utils.database.DatabaseCollectionManager;
import utils.ServerHelper;
import comparators.CarComparator;
import comparators.SoundtrackNameComparator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.logging.Level;

public class CollectionManager {
    private String string = "";
    private LinkedHashSet<HumanBeing> humanBeings;
    private LocalDateTime lastSave;
    private LocalDateTime lastInit;
    private DatabaseCollectionManager databaseCollectionManager;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;
        lastInit = LocalDateTime.now();
        loadCollection();
    }

    public void loadCollection() {
        try {
            humanBeings = databaseCollectionManager.getCollection();
            ResponseBuilder.append("Коллекция загружена");
            ServerHelper.logger.log(Level.INFO, "Коллекция загружена");
        } catch (SQLException throwables) {
            humanBeings = new LinkedHashSet<>();
            ResponseBuilder.appendError("Коллекция не может быть загружена");
            ServerHelper.logger.log(Level.SEVERE, "Коллекция не может быть загружена");

        }
    }

    public LinkedHashSet<HumanBeing> getCollection() {
        return this.humanBeings;
    }

    public void setCollection(LinkedHashSet<HumanBeing> humans) {
        this.humanBeings = humans;
    }

    public int collectionSize() {
        return humanBeings.size();
    }

    public LocalDateTime getLastSave(){
        return this.lastSave;
    }

    public LocalDateTime getLastInit(){
        return this.lastInit;
    }

    public Class<? extends LinkedHashSet> collectionType() {
        return humanBeings.getClass();
    }

    /*public int getNewId() {
        int id = 1;
        for (HumanBeing human : humanBeings) {
            if (human.getId() > id) id = human.getId() + 1;
        }
        return id;
    }*/

    public HumanBeing getById(int id) {
        for (HumanBeing human : humanBeings) {
            if (human.getId() == id) return human;
        }
        return null;
    }

    public float getMaxImpactSpeed() {
        float max = -883;
        for (HumanBeing human : humanBeings) {
            if (human.getImpactSpeed() > max) max = human.getImpactSpeed();
        }
        return max;
    }

    public float getMinImpactSpeed() {
        float min = Float.MAX_VALUE;
        for (HumanBeing human : humanBeings) {
            if (human.getImpactSpeed() < min) min = human.getImpactSpeed();
        }
        return min;
    }

    public void add(HumanBeing human) {
        humanBeings.add(human);
    }

    public void clearCollection() {
        humanBeings.clear();
    }

    public String showCollection() {
        string = "";
        if (humanBeings.size() != 0)
            for (HumanBeing human : humanBeings) {
                string = new StringBuilder().append(string).append(human.toString()).append("\n").toString();

            }
        else string = "Collection is empty!";
        return string;
    }

    public String showCollection(LinkedHashSet<HumanBeing> humanBeings2) {
        string = "";
        if (humanBeings2.size() != 0)
            for (HumanBeing human : humanBeings2) {
                string += human.toString();
            }
        else string = "Collection is empty!";
        return string;
    }

    public void setNewHuman(int id, HumanBeing human) {
        //for (HumanBeing humanBeing : humanBeings) {
            //if (humanBeing.getId() == id) humanBeing = human;
            //break;
        //}
        humanBeings.stream().filter(humanBeing -> humanBeing.getId() == id).map(humanBeing -> human);
    }

    public void removeById(int id) {
        humanBeings.removeIf(humanBeing -> humanBeing.getId() == id);
    }

    public void removeGreater(HumanBeing human) {
        //for (HumanBeing humanBeing : humanBeings) {
            //if (Float.compare(humanBeing.getImpactSpeed(), human.getImpactSpeed()) == 1)
                //humanBeings.remove(humanBeing);
        //}
        humanBeings.removeIf(humanBeing -> Float.compare(humanBeing.getImpactSpeed(), human.getImpactSpeed()) == 1);
    }

    public void removeByCar(Car car, User user) throws PermissionDeniedException{
        CarComparator carComparator = new CarComparator();
        String count = "";
        for (HumanBeing humanBeing : humanBeings) {
            if (user.equals(humanBeing.getCreator())) {
                if (carComparator.compare(humanBeing.getCar(), car) == 0)
                    humanBeings.remove(humanBeing);
            } else count += humanBeing.getName() + "\n";
            if (!count.equals(""))
                throw new PermissionDeniedException("Недостаточно прав для удаления объекта(ов): " + count);
        }
        //humanBeings.removeIf(humanBeing -> carComparator.compare(humanBeing.getCar(), car) == 0);
    }

    public HumanBeing maxByCreationDate() {
        HumanBeing human = null;
        for (HumanBeing humanBeing : humanBeings) {
            if (human == null) {
                human = humanBeing;
                continue;
            }
            if (humanBeing.getCreationDate().compareTo(human.getCreationDate()) != -1)
                human = humanBeing;
        }
        return human;
    }

    public LinkedHashSet<HumanBeing> filterGreaterThanSoundtrackName(String soundtrackName) {
        LinkedHashSet<HumanBeing> humans = new LinkedHashSet<>();
        SoundtrackNameComparator soundtrackNameComparator = new SoundtrackNameComparator();
        for (HumanBeing human : humanBeings) {
            if (soundtrackNameComparator.compare(human.getSoundtrackName(), soundtrackName) == 1)
                humans.add(human);
        }
        return humans;
    }

    /*public void saveCollection() throws XMLStreamException, XMLStreamException {
        OutputStreamWriter fos = null;
        try {
            fos = new OutputStreamWriter(new FileOutputStream(myFile));
        } catch (FileNotFoundException e) {
            ServerMain.logger.info("Файл для записи не найден");
            ResponseBuilder.appendError("Файл для записи не найден");
        }
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = new IndentingXMLStreamWriter(output.createXMLStreamWriter(fos));

        writer.writeStartDocument("1.0");
        writer.writeStartElement("humanBeing");

        for (HumanBeing human : humanBeings) {
            human.toXML(writer);
        }

        writer.writeEndElement();
        // Закрываем XML-документ
        writer.writeEndDocument();
        writer.flush();
    }

    public String getString() {
        return string;
    }*/
}
