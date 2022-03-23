import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersData {

    private final String COLLAPSE = "Collapse";
    private final String EXPAND = "Expand";

    private JPanel usersPanel;

    private JButton button;
    private JTextField surField;
    private JPanel surnamePan;
    private JTextField firstField;
    private JTextField middleField;
    private JPanel btnPan;
    private JTextField fioField;
    private JPanel singleLinePan;

    public UsersData() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(e.getSource() instanceof JButton)) {
                    return;
                }
                if (button.getText().equals(COLLAPSE)) {
                    String surname = surField.getText().trim();
                    String firstname = firstField.getText().trim();
                    String middleName = middleField.getText().trim();
                    outputSingleLine(surname, firstname, middleName);
                } else {
                    String fioFieldText = fioField.getText().trim();
                    outputUserPanel(fioFieldText);
                }
            }
        });
    }

    private void outputUserPanel(String fioFieldText) {
        String[] text = fioFieldText.split(" ");

        if (!checkArray(text)) {
            showMessage();
            return;
        }
        surField.setText(text[0]);
        firstField.setText(text[1]);
        if (text.length == 3) {
            middleField.setText(text[2]);
        }
        button.setText(COLLAPSE);
        singleLinePan.setVisible(false);
        surnamePan.setVisible(true);
        fioField.setText("");
    }

    private boolean checkArray(String[] text) {
        return text.length >= 2
                && text.length <= 3
                && !checkDataValidity(text[0], text[1]);
    }

    private void outputSingleLine(String surname, String firstname, String midlname) {
        if (checkDataValidity(surname, firstname)) {
            showMessage();
            return;
        }
        fioField.setText(surname + " "
                + firstname + " "
                + midlname);
        button.setText(EXPAND);
        singleLinePan.setVisible(true);
        surnamePan.setVisible(false);
        surField.setText("");
        firstField.setText("");
        middleField.setText("");
    }

    private void showMessage() {
        JOptionPane.showMessageDialog(usersPanel,
                "Я тоже не понимаю, что происходит!!!",
                "Проверка данных",
                JOptionPane.PLAIN_MESSAGE);
    }

    private boolean checkDataValidity(String surname, String firstname) {
        return (surname.isEmpty() || firstname.isEmpty());
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }
}
