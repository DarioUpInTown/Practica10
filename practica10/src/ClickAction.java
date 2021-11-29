
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class ClickAction extends AbstractAction{
    
    private JButton button;

    public ClickAction(JButton button) {
        this.button = button;
    }

    public void actionPerformed(ActionEvent e) {
        button.doClick();
    }
}

