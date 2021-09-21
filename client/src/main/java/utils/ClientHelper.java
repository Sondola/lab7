package utils;

import data.User;
import exceptions.IncorrectCommandException;
import objects.Request;
import objects.Response;
import objects.ResponseAnswer;

public class ClientHelper {
    private ClientReceiver receiver;
    private ClientSender sender;
    private StringWorking stringWorking;
    private Authorization authorization;
    private User user;

    public ClientHelper(ClientReceiver receiver, ClientSender sender, StringWorking stringWorking, Authorization authorization){
        this.receiver = receiver;
        this.sender = sender;
        this.stringWorking = stringWorking;
        this.authorization = authorization;
    }

    public void login(){
        while(true){
            Request request = authorization.makeRequest();
            if(request != null){
                if(sender.send(request)){
                    Response response = receiver.getResponse();
                    if(response != null && response.getResponseAnswer().equals(ResponseAnswer.OK)){
                        System.out.println("Вы авторизованы. Можете приступать к работе.");
                        this.user = request.getUser();
                        break;
                    }
                    else{
                        System.out.println("Вы не авторизованы. Попробуйте еще.");
                    }
                }
            }
        }
    }

    public void handle(String command){
        try {
            Request request = stringWorking.makeRequest(command, user);
            if(request != null){
                if(sender.send(request)){
                    //System.out.println(request.toString());
                    Response response = receiver.getResponse();
                    if(response != null){
                        System.out.print(response.getResponseBody());
                    }
                }
            }

        } catch (IncorrectCommandException e) {
            System.out.println(e.getMessage());
        }
    }
}
