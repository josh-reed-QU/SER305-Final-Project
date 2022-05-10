import javax.swing.*;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class App extends JFrame{

    public App() {

        super("Deals App");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        HomeWindow homeWindow = new HomeWindow();
        this.add(homeWindow);
        this.pack();
        this.setVisible(true);
    }
    public static void main(String[] args) {
       // System.out.println("making new app!");
        new App();
    }
}
