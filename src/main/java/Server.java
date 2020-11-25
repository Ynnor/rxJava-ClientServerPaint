import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 10000;
        ServerSocket ss;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started and listening on port: " + port);
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        Observable<Socket> clients = Observable.empty();

        while(true) {
            try {
                Socket s = ss.accept();
                System.out.println("New connection by " + s.getInetAddress().getHostAddress());
                Observable<Socket> o = Observable.just(s);
                clients.subscribe(o);
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }
}
