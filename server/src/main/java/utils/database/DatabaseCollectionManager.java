package utils.database;

import data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import data.User;
import utils.ServerHelper;
import java.util.logging.Level;

public class DatabaseCollectionManager {

    //sql pre-statements
    private final String SELECT_ALL_HUMAN_BEINGS = "SELECT * FROM " + DatabaseManager.HUMAN_BEINGS_TABLE;
    private final String SELECT_COORDINATES_BY_HUMAN_ID = "SELECT * FROM " + DatabaseManager.COORDINATE_TABLE
            + " WHERE " + DatabaseManager.COORDINATE_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String SELECT_CAR_BY_HUMAN_ID = "SELECT * FROM " + DatabaseManager.CAR_TABLE
            + " WHERE " + DatabaseManager.CAR_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String INSERT_HUMAN = "INSERT INTO " + DatabaseManager.HUMAN_BEINGS_TABLE
            + "(" + DatabaseManager.HUMAN_BEINGS_TABLE_CREATOR_ID_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_NAME_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_CREATION_DATE_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_REAL_HERO_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HAS_TOOTHPICK_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_IMPACT_SPEED_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_SOUNDTRACK_NAME_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_MINUTES_OF_WAITING_COLUMN + ", "
            + DatabaseManager.HUMAN_BEINGS_TABLE_MOOD_COLUMN + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_CAR = "INSERT INTO " + DatabaseManager.CAR_TABLE
            + " (" + DatabaseManager.CAR_TABLE_HUMAN_ID_COLUMN + ", "
            + DatabaseManager.CAR_TABLE_NAME_COLUMN + ") VALUES (?, ?)";
    private final String INSERT_COORDINATE = "INSERT INTO " + DatabaseManager.COORDINATE_TABLE
            + " (" + DatabaseManager.COORDINATE_TABLE_HUMAN_ID_COLUMN + ", "
            + DatabaseManager.COORDINATE_TABLE_X_COLUMN + ", "
            + DatabaseManager.COORDINATE_TABLE_Y_COLUMN + ") VALUES(?, ?, ?)";
    private final String UPDATE_HUMAN_NAME_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_NAME_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_COORDINATE_X_BY_ID = "UPDATE " + DatabaseManager.COORDINATE_TABLE
            + " SET " + DatabaseManager.COORDINATE_TABLE_X_COLUMN + " = ? WHERE "
            + DatabaseManager.COORDINATE_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_COORDINATE_Y_BY_ID = "UPDATE " + DatabaseManager.COORDINATE_TABLE
            + " SET " + DatabaseManager.COORDINATE_TABLE_Y_COLUMN + " = ? WHERE "
            + DatabaseManager.COORDINATE_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_REAL_HERO_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_REAL_HERO_COLUMN+ " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_HAS_TOOTHPICK_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_HAS_TOOTHPICK_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_IMPACT_SPEED_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_IMPACT_SPEED_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_SOUNDTRACK_NAME_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_SOUNDTRACK_NAME_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_MINUTES_OF_WAITING_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_MINUTES_OF_WAITING_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_MOOD_BY_ID = "UPDATE " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " SET " + DatabaseManager.HUMAN_BEINGS_TABLE_MOOD_COLUMN + " = ? WHERE "
            + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String UPDATE_CAR_NAME_BY_ID = "UPDATE " + DatabaseManager.CAR_TABLE
            + " SET " + DatabaseManager.CAR_TABLE_NAME_COLUMN + " = ? WHERE "
            + DatabaseManager.CAR_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String DELETE_HUMAN_BY_ID = "DELETE FROM " + DatabaseManager.HUMAN_BEINGS_TABLE
            + " WHERE " + DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String DELETE_CAR_BY_ID = "DELETE FROM " + DatabaseManager.CAR_TABLE
            + " WHERE " + DatabaseManager.CAR_TABLE_HUMAN_ID_COLUMN + " = ?";
    private final String DELETE_COORDINATE_BY_ID = "DELETE FROM " + DatabaseManager.COORDINATE_TABLE
            + " WHERE " + DatabaseManager.COORDINATE_TABLE_HUMAN_ID_COLUMN + " = ?";


    private DatabaseManager databaseManager;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseManager databaseManager, DatabaseUserManager databaseUserManager){
        this.databaseManager = databaseManager;
        this.databaseUserManager = databaseUserManager;
    }

    public LinkedHashSet<HumanBeing> getCollection() throws SQLException {
        LinkedHashSet<HumanBeing> collection = new LinkedHashSet<HumanBeing>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.getPreparedStatement(SELECT_ALL_HUMAN_BEINGS, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                collection.add(createHuman(resultSet));
            }
            return collection;
        }
        catch (SQLException e){
            throw new SQLException("Ошибка во время загрузки коллекции");
        }
        finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    private HumanBeing createHuman(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DatabaseManager.HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN);
        String name = resultSet.getString(DatabaseManager.HUMAN_BEINGS_TABLE_NAME_COLUMN);
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseManager.HUMAN_BEINGS_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        Boolean realHero = resultSet.getBoolean(DatabaseManager.HUMAN_BEINGS_TABLE_REAL_HERO_COLUMN);
        Boolean hasToothpick = resultSet.getBoolean(DatabaseManager.HUMAN_BEINGS_TABLE_HAS_TOOTHPICK_COLUMN);
        Float impactSpeed = resultSet.getFloat(DatabaseManager.HUMAN_BEINGS_TABLE_IMPACT_SPEED_COLUMN);
        String soundtrackName = resultSet.getString(DatabaseManager.HUMAN_BEINGS_TABLE_SOUNDTRACK_NAME_COLUMN);
        float minutesOfWaiting = resultSet.getFloat(DatabaseManager.HUMAN_BEINGS_TABLE_MINUTES_OF_WAITING_COLUMN);
        Mood mood = Mood.valueOf(resultSet.getString(DatabaseManager.HUMAN_BEINGS_TABLE_MOOD_COLUMN));
        Car car = getCarByHumanId(id);
        Coordinates coordinates = getCoordinatesByHumanId(id);
        User creator = databaseUserManager.getUserById(resultSet.getInt(DatabaseManager.HUMAN_BEINGS_TABLE_CREATOR_ID_COLUMN));
        return new HumanBeing(id, name, creationDate, coordinates, realHero, hasToothpick, impactSpeed, soundtrackName, minutesOfWaiting, mood, car, creator);
    }

    private Coordinates getCoordinatesByHumanId(Integer HumanId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.getPreparedStatement(SELECT_COORDINATES_BY_HUMAN_ID, false);
            preparedStatement.setInt(1, HumanId);
            //System.out.println("getCoordinatesByHumanId: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                coordinates = new Coordinates(resultSet.getInt(DatabaseManager.COORDINATE_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseManager.COORDINATE_TABLE_Y_COLUMN));
                return coordinates;
            }
            else throw new SQLException();
        }
        catch (SQLException e){
            //e.printStackTrace();
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при выполнении запроса SELECT_COORDINATES_BY_HUMAN_ID");
            throw new SQLException("Ошибка при выполнении запроса SELECT_COORDINATES_BY_HUMAN_ID");
        }
        finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    private Car getCarByHumanId(Integer HumanId) throws SQLException {
        Car car;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = databaseManager.getPreparedStatement(SELECT_CAR_BY_HUMAN_ID, false);
            preparedStatement.setInt(1, HumanId);
            //System.out.println("getCarrByHumanId" + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                car = new Car(resultSet.getString(DatabaseManager.CAR_TABLE_NAME_COLUMN));
                return car;
            }
            else throw new SQLException();
        }
        catch (SQLException e){
            //e.printStackTrace();
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при выполнении запроса SELECT_CAR_BY_HUMAN_ID");
            throw new SQLException("Ошибка при выполнении запроса SELECT_CAR_BY_HUMAN_ID");
        }
        finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public HumanBeing insertHuman(HumanBeingRaw HumanBeingRaw, User user) throws SQLException {
        PreparedStatement preparedStatementInsertHuman = null;
        PreparedStatement preparedStatementInsertCoordinate = null;
        PreparedStatement preparedStatementInsertCar = null;
        try{
            databaseManager.toNonAutoCommit();
            databaseManager.addSavePoint();
            preparedStatementInsertHuman = databaseManager.getPreparedStatement(INSERT_HUMAN, true);
            preparedStatementInsertHuman.setInt(1, databaseUserManager.getUserIdByUsernameAndPassword(user));
            preparedStatementInsertHuman.setString(2, HumanBeingRaw.getName());
            preparedStatementInsertHuman.setTimestamp(3, Timestamp.valueOf(HumanBeingRaw.getCreationDate()));
            preparedStatementInsertHuman.setBoolean(4, HumanBeingRaw.getRealHero());
            preparedStatementInsertHuman.setBoolean(5, HumanBeingRaw.getHasToothpick());
            preparedStatementInsertHuman.setFloat(6, HumanBeingRaw.getImpactSpeed());
            preparedStatementInsertHuman.setString(7, HumanBeingRaw.getSoundtrackName());
            preparedStatementInsertHuman.setFloat(8, HumanBeingRaw.getMinutesOfWaiting());
            preparedStatementInsertHuman.setString(9, HumanBeingRaw.getMood().toString());
            //System.out.println("InsertHuman: " + preparedStatementInsertHuman);
            if(preparedStatementInsertHuman.executeUpdate() == 0) throw new SQLException();
            Integer HumanId;
            ResultSet generatedHumanKeys = preparedStatementInsertHuman.getGeneratedKeys();
            if(generatedHumanKeys.next()){
                HumanId = generatedHumanKeys.getInt(1);
            }
            else throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос INSERT_HUMAN");

            preparedStatementInsertCoordinate = databaseManager.getPreparedStatement(INSERT_COORDINATE, false);
            preparedStatementInsertCoordinate.setInt(1, HumanId);
            preparedStatementInsertCoordinate.setInt(2, HumanBeingRaw.getCoordinates().getX());
            preparedStatementInsertCoordinate.setFloat(3, HumanBeingRaw.getCoordinates().getY());
            //System.out.println("InsertCoordinate: " + preparedStatementInsertCoordinate);
            if(preparedStatementInsertCoordinate.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос INSERT_COORDINATE");

            preparedStatementInsertCar = databaseManager.getPreparedStatement(INSERT_CAR, false);
            preparedStatementInsertCar.setInt(1, HumanId);
            preparedStatementInsertCar.setString(2, HumanBeingRaw.getCar().getName());
            //System.out.println("InsertCar: " + preparedStatementInsertCar);
            if(preparedStatementInsertCar.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос INSERT_CAR");

            databaseManager.commit();

            return new HumanBeing(HumanId, HumanBeingRaw.getName(),HumanBeingRaw.getCreationDate(),
                    HumanBeingRaw.getCoordinates(), HumanBeingRaw.getRealHero(), HumanBeingRaw.getHasToothpick(),
                    HumanBeingRaw.getImpactSpeed(), HumanBeingRaw.getSoundtrackName(), HumanBeingRaw.getMinutesOfWaiting(),
                    HumanBeingRaw.getMood(), HumanBeingRaw.getCar(), user);
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при добавления человека в БД");
            databaseManager.rollback();
            throw new SQLException("Ошибка при добавления человека в БД");
        }
        finally {
            databaseManager.closePreparedStatement(preparedStatementInsertHuman);
            databaseManager.closePreparedStatement(preparedStatementInsertCoordinate);
            databaseManager.closePreparedStatement(preparedStatementInsertCar);
            databaseManager.toAutoCommit();
        }

    }

    public void updateHumanById(Integer HumanId, HumanBeingRaw HumanBeingRaw) throws SQLException {
        PreparedStatement preparedStatementUpdateNameById = null;
        PreparedStatement preparedStatementUpdateCoordinateXById = null;
        PreparedStatement preparedStatementUpdateCoordinateYById = null;
        PreparedStatement preparedStatementUpdateRealHeroById = null;
        PreparedStatement preparedStatementUpdateHasToothpickById = null;
        PreparedStatement preparedStatementUpdateImpactSpeedById = null;
        PreparedStatement preparedStatementUpdateSoundtrackNameById = null;
        PreparedStatement preparedStatementUpdateMinutesOfWaitingById = null;
        PreparedStatement preparedStatementUpdateMoodById = null;
        PreparedStatement preparedStatementUpdateCarNameById = null;
        try{
            databaseManager.toNonAutoCommit();
            databaseManager.addSavePoint();

            preparedStatementUpdateNameById = databaseManager.getPreparedStatement(UPDATE_HUMAN_NAME_BY_ID, false);
            preparedStatementUpdateCoordinateXById = databaseManager.getPreparedStatement(UPDATE_COORDINATE_X_BY_ID, false);
            preparedStatementUpdateCoordinateYById = databaseManager.getPreparedStatement(UPDATE_COORDINATE_Y_BY_ID, false);
            //System.out.println("UpdateCoordinateYById: " + preparedStatementUpdateCoordinateYById);
            preparedStatementUpdateRealHeroById = databaseManager.getPreparedStatement(UPDATE_HUMAN_REAL_HERO_BY_ID, false);

            preparedStatementUpdateHasToothpickById = databaseManager.getPreparedStatement(UPDATE_HUMAN_HAS_TOOTHPICK_BY_ID, false);
            preparedStatementUpdateImpactSpeedById = databaseManager.getPreparedStatement(UPDATE_HUMAN_IMPACT_SPEED_BY_ID, false);
            preparedStatementUpdateSoundtrackNameById = databaseManager.getPreparedStatement(UPDATE_HUMAN_SOUNDTRACK_NAME_BY_ID, false);
            preparedStatementUpdateMinutesOfWaitingById = databaseManager.getPreparedStatement(UPDATE_HUMAN_MINUTES_OF_WAITING_BY_ID, false);
            preparedStatementUpdateMoodById = databaseManager.getPreparedStatement(UPDATE_HUMAN_MOOD_BY_ID, false);
            preparedStatementUpdateCarNameById = databaseManager.getPreparedStatement(UPDATE_CAR_NAME_BY_ID, false);

            if(HumanBeingRaw.getName() != null){
                preparedStatementUpdateNameById.setString(1, HumanBeingRaw.getName());
                preparedStatementUpdateNameById.setInt(2, HumanId);
                //System.out.println("UpdateNameById: " + preparedStatementUpdateNameById);
                if(preparedStatementUpdateNameById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_NAME_BY_ID");
            }
            if(HumanBeingRaw.getCoordinates() != null){
                if (HumanBeingRaw.getCoordinates().getX() != null) {
                    preparedStatementUpdateCoordinateXById.setInt(1, HumanBeingRaw.getCoordinates().getX());
                    preparedStatementUpdateCoordinateXById.setInt(2, HumanId);
                    //System.out.println("UpdateCoordinateXById: " + preparedStatementUpdateCoordinateXById);
                    if (preparedStatementUpdateCoordinateXById.executeUpdate() == 0) throw new SQLException();
                    ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_COORDINATE_X_BY_ID");
                }
                preparedStatementUpdateCoordinateYById.setDouble(1, HumanBeingRaw.getCoordinates().getY());
                preparedStatementUpdateCoordinateYById.setInt(2, HumanId);
                //System.out.println("UpdateCoordinateYById: " + preparedStatementUpdateCoordinateYById);
                if (preparedStatementUpdateCoordinateYById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_COORDINATE_Y_BY_ID");
            }
            if(HumanBeingRaw.getRealHero() != null){
                preparedStatementUpdateRealHeroById.setBoolean(1, HumanBeingRaw.getRealHero());
                preparedStatementUpdateRealHeroById.setInt(2,HumanId);
                //System.out.println("UpdateHealthById: " + preparedStatementUpdateHealthById);
                if(preparedStatementUpdateRealHeroById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_HEALTH_BY_ID");
            }
            if(HumanBeingRaw.getHasToothpick() != null){
                preparedStatementUpdateHasToothpickById.setBoolean(1, HumanBeingRaw.getHasToothpick());
                preparedStatementUpdateHasToothpickById.setInt(2, HumanId);
                //System.out.println("UpdateAchievementsById: " + preparedStatementUpdateAchievementsById);
                if(preparedStatementUpdateHasToothpickById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_ACHIEVEMENTS_BY_ID");
            }
            if(HumanBeingRaw.getImpactSpeed() != null){
                preparedStatementUpdateImpactSpeedById.setString(1, HumanBeingRaw.getImpactSpeed().toString());
                preparedStatementUpdateImpactSpeedById.setInt(2, HumanId);
                //System.out.println("UpdateWeaponTypeById: " + preparedStatementUpdateWeaponTypeById);
                if(preparedStatementUpdateImpactSpeedById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_WEAPON_TYPE_BY_ID");
            }
            if(HumanBeingRaw.getSoundtrackName() != null){
                preparedStatementUpdateSoundtrackNameById.setString(1, HumanBeingRaw.getSoundtrackName().toString());
                preparedStatementUpdateSoundtrackNameById.setInt(2, HumanId);
                //System.out.println("UpdateMeleeWeaponById: " + preparedStatementUpdateMeleeWeaponById);
                if(preparedStatementUpdateSoundtrackNameById.executeUpdate() == 0) throw new SQLException();
                ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_MELEE_WEAPON_BY_ID");
            }

            preparedStatementUpdateMinutesOfWaitingById.setFloat(1, HumanBeingRaw.getMinutesOfWaiting());
            preparedStatementUpdateMinutesOfWaitingById.setInt(2, HumanId);
            //System.out.println("UpdateMeleeWeaponById: " + preparedStatementUpdateMeleeWeaponById);
            if(preparedStatementUpdateMinutesOfWaitingById.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_MELEE_WEAPON_BY_ID");

            preparedStatementUpdateMoodById.setString(1, HumanBeingRaw.getMood().toString());
            preparedStatementUpdateMoodById.setInt(2, HumanId);
            //System.out.println("UpdateMeleeWeaponById: " + preparedStatementUpdateMeleeWeaponById);
            if(preparedStatementUpdateMoodById.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_HUMAN_MELEE_WEAPON_BY_ID");

            if(HumanBeingRaw.getCar() != null){
                if(HumanBeingRaw.getCar().getName() != null) {
                    preparedStatementUpdateCarNameById.setString(1, HumanBeingRaw.getCar().getName());
                    preparedStatementUpdateCarNameById.setInt(2, HumanId);
                    //System.out.println("UpdateCarNameById: " + preparedStatementUpdateCarNameById);
                    if (preparedStatementUpdateCarNameById.executeUpdate() == 0) throw new SQLException();
                    ServerHelper.logger.log(Level.INFO, "Выполнен запрос UPDATE_CAR_NAME_BY_ID");
                }
            }

            databaseManager.commit();
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при обновлении объекта");
            databaseManager.rollback();
            throw new SQLException("Ошибка при добавления космического корабля в БД");
        }
        finally{
            databaseManager.closePreparedStatement(preparedStatementUpdateNameById);
            databaseManager.closePreparedStatement(preparedStatementUpdateCoordinateXById);
            databaseManager.closePreparedStatement(preparedStatementUpdateCoordinateYById);
            databaseManager.closePreparedStatement(preparedStatementUpdateRealHeroById);
            databaseManager.closePreparedStatement(preparedStatementUpdateHasToothpickById);
            databaseManager.closePreparedStatement(preparedStatementUpdateImpactSpeedById);
            databaseManager.closePreparedStatement(preparedStatementUpdateSoundtrackNameById);
            databaseManager.closePreparedStatement(preparedStatementUpdateMinutesOfWaitingById);
            databaseManager.closePreparedStatement(preparedStatementUpdateMoodById);
            databaseManager.closePreparedStatement(preparedStatementUpdateCarNameById);
            databaseManager.toAutoCommit();
        }
    }

    public void deleteHumanById(Integer HumanId) throws SQLException {
        PreparedStatement preparedStatementDeleteHumanById = null;
        PreparedStatement preparedStatementDeleteCarById = null;
        PreparedStatement preparedStatementDeleteCoordinateById = null;
        try{
            databaseManager.toNonAutoCommit();
            databaseManager.addSavePoint();

            preparedStatementDeleteHumanById = databaseManager.getPreparedStatement(DELETE_HUMAN_BY_ID, false);
            preparedStatementDeleteCarById = databaseManager.getPreparedStatement(DELETE_CAR_BY_ID, false);
            preparedStatementDeleteCoordinateById = databaseManager.getPreparedStatement(DELETE_COORDINATE_BY_ID, false);

            preparedStatementDeleteCarById.setInt(1, HumanId);
            //System.out.println("DeleteCarById: " + preparedStatementDeleteCarById);
            if(preparedStatementDeleteCarById.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос DELETE_CAR_BY_ID");

            preparedStatementDeleteCoordinateById.setInt(1, HumanId);
            //System.out.println("DeleteCoordinateById: " + preparedStatementDeleteCoordinateById);
            if(preparedStatementDeleteCoordinateById.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос DELETE_COORDINATE_BY_ID");

            preparedStatementDeleteHumanById.setInt(1, HumanId);
            //System.out.println("DeleteHumanById: " + preparedStatementDeleteHumanById);
            if(preparedStatementDeleteHumanById.executeUpdate() == 0) throw new SQLException();
            ServerHelper.logger.log(Level.INFO, "Выполнен запрос DELETE_HUMAN_BY_ID");

            databaseManager.commit();

        }
        catch (SQLException e){
            //e.printStackTrace();
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при удалении объекта");
            databaseManager.rollback();
            throw new SQLException("Ошибка при удалении человека из БД");
        }
        finally{
            databaseManager.closePreparedStatement(preparedStatementDeleteHumanById);
            databaseManager.closePreparedStatement(preparedStatementDeleteCarById);
            databaseManager.closePreparedStatement(preparedStatementDeleteCoordinateById);
            databaseManager.toAutoCommit();
        }
    }

    public void clearCollection() throws SQLException {
        LinkedHashSet<HumanBeing> HumanBeings = getCollection();
        for(HumanBeing sp: HumanBeings){
            deleteHumanById(sp.getId());
        }
    }

}
