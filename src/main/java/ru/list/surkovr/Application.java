package ru.list.surkovr;

import ru.list.surkovr.graphics.ApplicationGui;
import ru.list.surkovr.graphics.Client;

public class Application {

    public static void main(String[] args) throws Exception {

        Client client = new Client();
        ApplicationGui applicationGui = new ApplicationGui(client);
//        IO.Options options = new IO.Options();
//        options.forceNew = true;
//
//        final OkHttpClient client = new OkHttpClient();
//        options.webSocketFactory = client;
//        options.callFactory = client;
    }
}