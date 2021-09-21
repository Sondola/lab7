package utils;

import exceptions.EmptyLoginException;
import exceptions.EmptyPasswordException;

import java.io.BufferedReader;
import java.io.IOException;

public class AskAuthorizationManager {
    private BufferedReader bf;

    public AskAuthorizationManager(BufferedReader bufferedReader){
        this.bf = bufferedReader;
    }

    public String askLogin(){
        String login;
        while(true){
            try{
                System.out.println("Введите логин: ");
                login = bf.readLine().trim().replaceAll("\uFFFD", "");
                if(login.isEmpty()) throw new EmptyLoginException("Логин не может быть пустым");
                break;
            }
            catch (IOException e){
                System.out.println("Ошибка ввода");
            }
            catch (EmptyLoginException e){
                System.out.println(e.getMessage());
            }
        }
        return login;
    }

    public String askPassword(){
        String password;
        while(true){
            try{
                System.out.println("Введите пароль: ");
                password = bf.readLine().trim().replaceAll("\uFFFD", "");
                if(password.isEmpty()) throw new EmptyPasswordException("Логин не может быть пустым");
                break;
            }
            catch (IOException e){
                System.out.println("Ошибка ввода");
            }
            catch (EmptyPasswordException e){
                System.out.println(e.getMessage());
            }
        }
        return password;
    }

    public boolean askQuestion(String question){
        String answer;
        while(true){
            try{
                System.out.println(question);
                System.out.println("1 - да; 2 - нет");
                answer = bf.readLine().trim().replaceAll("\uFFFD", "");
                if(answer.equals("1")) return true;
                if(answer.equals("2")) return false;
            }
            catch (IOException e){
                System.out.println("Ошибка ввода");
            }
        }
    }
}
