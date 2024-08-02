package demos.misc;

import java.util.ArrayList;
import java.util.List;

public class NotifyMe {
    public static void main(String[] args) {
        StocksObservable stocksObservable = new IphoneObservable();

        NotificationObserver observer = new EmailNotificationObserver(stocksObservable,
                "bbsahmad@gmail.com");

        stocksObservable.add(observer);

        stocksObservable.setStock(10);

    }
}

interface StocksObservable{
    void add(NotificationObserver observer);
    void remove(NotificationObserver observer);
    void notifyObservers();
    void setStock(int stock);
    int getStock();
}

class IphoneObservable implements StocksObservable{
    List<NotificationObserver> observers;
    int stock;

    public IphoneObservable() {
        observers = new ArrayList<NotificationObserver>();
        stock = 0;
        System.out.println("created iphone observable");
    }

    @Override
    public void add(NotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void remove(NotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }

    @Override
    public void setStock(int stock) {
        if(this.stock == 0){
            System.out.println("stocks available now, updating observers");
            notifyObservers();
        }
        this.stock = stock;
    }

    @Override
    public int getStock() {
        return stock;
    }
}

interface NotificationObserver{
    void update();
}

class EmailNotificationObserver implements NotificationObserver{
    StocksObservable stocksObservable;
    String email;

    public EmailNotificationObserver(StocksObservable stocksObservable, String email) {
        this.stocksObservable = stocksObservable;
        this.email = email;
        System.out.println("created email notification observer");
    }

    @Override
    public void update() {
        sendMail();
    }

    private void sendMail(){
        System.out.println("sending mail to " + email);
    }
}


