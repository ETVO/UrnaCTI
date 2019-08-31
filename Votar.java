import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Votar extends JFrame implements ActionListener{

    public Votar() {
        super("Votar");
        
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(420, 90);
    	setSize(550, 550);
    	setLayout(null);
    	getContentPane().setBackground(new Color(240, 240, 240));

        show();
    }

    public void actionPerformed(ActionEvent e){
    } 
}