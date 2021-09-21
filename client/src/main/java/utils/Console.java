package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Working environment for command line reading
 */

public class Console {
    /**
     * Variable to identify the need for further work
     */
    private boolean work;
    /**
     * Reader for line reading support {@link BufferedReader#readLine()}
     */
    private BufferedReader br;
    /**
     * Class providing getting correct information from the user {@link AskManager}
     */
    private AskManager askManager;

    private ClientHelper clientHelper;

    private StringWorking stringWorking = null;

    /**
     * Constructor - new working environment creating
     *
     * @see Console#interactiveMode()
     */
    public Console(BufferedReader br, AskManager askManager, ClientHelper clientHelper) {
        this.br = br;
        this.askManager = askManager;
        this.clientHelper = clientHelper;
        this.work = true;
    }

    /**
     * Setting {@link StringWorking} editor for commands
     *
     * @param stringWorking
     */
    public void setStringWorking(StringWorking stringWorking) {
        this.stringWorking = stringWorking;
    }

    /**
     * Setting working state
     *
     * @param work needful state
     */
    public void setWork(boolean work) {
        this.work = work;
    }

    /**
     * Checking the working state
     *
     * @return working state
     */
    public boolean isWork() {
        return work;
    }

    /**
     * Work in the interactive mode
     */
    public void interactiveMode() {
        while (work) {
            try {
                String command = br.readLine().trim();
                clientHelper.handle(command);
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
            }
        }
    }

    /**
     * Work with a script
     *
     * @param file path to file passed by chooseCommand method
     * @see AskManager#addScriptMode(BufferedReader)
     */
    public void scriptMode(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bf2 = new BufferedInputStream(fis);
            BufferedReader r2 = new BufferedReader(new InputStreamReader(bf2, StandardCharsets.UTF_8));


            System.out.println("Взаимодействие с файлом-скриптом");

//            this.setWork(true);
            String line = r2.readLine().trim();
            while (line != null && this.work) {
                askManager.addScriptMode(r2);
                clientHelper.handle(line);
                line = r2.readLine();
            }
            setWork(true);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка ввода");
        }
    }
}