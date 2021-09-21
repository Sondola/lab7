package utils.database;

import utils.ResponseBuilder;
import utils.ServerHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

    //Table names
    public static final String USERS_TABLE = "users";
    public static final String CAR_TABLE = "car";
    public static final String COORDINATE_TABLE = "coordinates";
    public static final String HUMAN_BEINGS_TABLE = "human_beings";

    //USERS_TABLE columns
    public static final String USERS_TABLE_ID_COLUMN = "id";
    public static final String USERS_TABLE_USERNAME_COLUMN = "username";
    public static final String USERS_TABLE_PASSWORD_COLUMN = "password";

    //CAR_TABLE columns
    public static final String CAR_TABLE_HUMAN_ID_COLUMN = "human_id";
    public static final String CAR_TABLE_NAME_COLUMN = "car_name";

    //COORDINATE_TABLE columns
    public static final String COORDINATE_TABLE_HUMAN_ID_COLUMN = "human_id";
    public static final String COORDINATE_TABLE_X_COLUMN = "x";
    public static final String COORDINATE_TABLE_Y_COLUMN = "y";

    //HUMAN_BEINGS_TABLE columns
    public static final String HUMAN_BEINGS_TABLE_HUMAN_ID_COLUMN = "human_id";
    public static final String HUMAN_BEINGS_TABLE_CREATOR_ID_COLUMN = "creator_id";
    public static final String HUMAN_BEINGS_TABLE_NAME_COLUMN = "name";
    public static final String HUMAN_BEINGS_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String HUMAN_BEINGS_TABLE_REAL_HERO_COLUMN = "real_hero";
    public static final String HUMAN_BEINGS_TABLE_HAS_TOOTHPICK_COLUMN = "has_toothpick";
    public static final String HUMAN_BEINGS_TABLE_IMPACT_SPEED_COLUMN = "impact_speed";
    public static final String HUMAN_BEINGS_TABLE_SOUNDTRACK_NAME_COLUMN = "soundtrack_name";
    public static final String HUMAN_BEINGS_TABLE_MINUTES_OF_WAITING_COLUMN = "minutes_of_waiting";
    public static final String HUMAN_BEINGS_TABLE_MOOD_COLUMN = "mood";

    private final String address;
    private final String user;
    private final String password;
    private final String DRIVER = "org.postgresql.Driver";
    private Connection connection;

    public DatabaseManager(String address, String user, String password){
        this.address = address;
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    private void connectToDatabase(){
        ResponseBuilder.append("Попытка подключения к БД");
        ServerHelper.logger.log(Level.INFO, "Попытка подключения к БД");
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(address, user, password);
            ResponseBuilder.append("Подключились к БД");
            ServerHelper.logger.log(Level.INFO, "Подключились к БД");
        } catch (ClassNotFoundException e) {
            ResponseBuilder.appendError("Драйвер для данной БД не найден");
            ServerHelper.logger.log(Level.SEVERE, "Драйвер для данной БД не найден");
        }
        catch (SQLException e){
            ResponseBuilder.appendError("Ошибка при подключении к БД");
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при подключении к БД");
        }

    }

    public void closeConnection(){
        if(connection == null) return;
        try {
            connection.close();
        }
        catch (SQLException e){
            ResponseBuilder.appendError("Ошибка при закрытии БД");
            ServerHelper.logger.log(Level.SEVERE, "Ошибка при закрытии БД");
        }
    }

    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generationMode) throws SQLException {
        if(connection == null) throw new SQLException("Ошибка при формировании запроса");
        int key = generationMode ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS;
        return connection.prepareStatement(sqlStatement, key);
    }

    public void closePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement == null) return;
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            ServerHelper.logger.log(Level.SEVERE, "Ошибка во время закрытия запроса " + preparedStatement);
        }
    }

    public void toNonAutoCommit(){
        try {
            if (connection == null) throw new SQLException("Ошибка во время изменения режима сохранения");
            connection.setAutoCommit(false);
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void toAutoCommit(){
        try {
            if (connection == null) throw new SQLException("Ошибка во время изменения режима сохранения");
            connection.setAutoCommit(true);
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void commit(){
        try {
            if (connection == null) throw new SQLException("Ошибка во время сохранения состояния БД");
            connection.commit();
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void rollback(){
        try {
            if (connection == null) throw new SQLException("Ошибка во время отката состояния БД");
            connection.rollback();
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void addSavePoint(){
        try {
            if (connection == null) throw new SQLException("Ошибка во время добавления точки сохранения");
            connection.setSavepoint();
        }
        catch (SQLException e){
            ServerHelper.logger.log(Level.SEVERE, e.getMessage());
        }

    }


}
