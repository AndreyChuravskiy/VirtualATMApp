import atm.ATM;
import io.IOService;

public class Main {

    public static void main(String[] args) {
        IOService ioService = new IOService();
        ATM atm = new ATM(ioService);
        atm.run();
    }

}