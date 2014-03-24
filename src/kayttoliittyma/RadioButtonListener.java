package kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener implements ActionListener{
    
    private UI ui;
    
    public RadioButtonListener(UI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ui.vaihdaLista(ae.getActionCommand());
    }
}
