package cz.uhk.fim.workshop.exeption;

public final class EventNotFoundException extends Throwable {

    public EventNotFoundException(String message){
        super(message);
    }

    public EventNotFoundException(){
        super();
    }

}
