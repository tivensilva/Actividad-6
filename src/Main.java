import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class directorioAPP extends JFrame {

    private JTextField nameField;
    private JTextField numberField;
    private JTextArea contactList;

    public directorioAPP() {
        setTitle("Libreta de direcciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Nombre");
        nameField = new JTextField();
        JLabel numberLabel = new JLabel("ID");
        numberField = new JTextField();

        JButton addButton = new JButton("Añadir");
        JButton showButton = new JButton("Libreta");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String number = numberField.getText();
                agregarContacto(name, number);
                nameField.setText("");
                numberField.setText("");
            }
        });

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarContacto();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String number = numberField.getText();
                actualizarContacto(name);
                nameField.setText("");
                numberField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                eliminarContacto(name);
                nameField.setText("");
                numberField.setText("");
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(numberLabel);
        inputPanel.add(numberField);
        inputPanel.add(addButton);
        inputPanel.add(showButton);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1, 2));
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);

        contactList = new JTextArea();
        contactList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(contactList);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(actionPanel, BorderLayout.SOUTH);
    }

    private void agregarContacto(String name, String number) {
        try {
            File file = new File("LibretaAmigos.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name + "!" + number);
            bw.newLine();
            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(this, "¡Contacto agregado correctamente!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error mientras se agregaba el contacto.");
        }
    }

    private void mostrarContacto() {
        try {
            File file = new File("LibretaAmigos.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No se encontraron contactos.");
                return;
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            fr.close();
            contactList.setText(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error mientras se leían los contactos.");
        }
    }

    private void actualizarContacto(String name) {
        try {
            File file = new File("LibretaAmigos.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No se encontraron contactos");
                return;
            }
            String newName = JOptionPane.showInputDialog(this, "Entra el nuevo nombre de contacto: ");
            if (newName == null || newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID incorrecta.");
                return;
            }
            String newNumber = JOptionPane.showInputDialog(this, "Entra el nuevo número ID: ");
            if (newNumber == null || newNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID incorrecta.");
                return;
            }
            File tempFile = new File("temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            BufferedWriter bw = new BufferedWriter(fw);
            String line;
            boolean updated = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("!");
                String contactName = parts[0];
                String number = parts[1];
                if (contactName.equals(name) || number.equals(name)) {
                    line = newName + "!" + newNumber;
                    updated = true;
                }
                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();
            fr.close();
            fw.close();
            file.delete();
            tempFile.renameTo(file);
            if (updated) {
                JOptionPane.showMessageDialog(this, "¡Contacto actualizado correctamente!");
            } else {
                JOptionPane.showMessageDialog(this, "Contacto no encontrado");
            }
            mostrarContacto(); // Actualizar la lista de contactos mostrados después de la actualización.
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error mientras se actualizaba el contacto.");
        }
    }








    private void eliminarContacto(String name) {
        try {
            File file = new File("LibretaAmigos.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No se encontraron contactos.");
                return;
            }
            File tempFile = new File("temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            BufferedWriter bw = new BufferedWriter(fw);
            String line;
            boolean deleted = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("!");
                String contactName = parts[0];
                if (contactName.equals(name)) {
                    deleted = true;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();
            fr.close();
            fw.close();
            file.delete();
            tempFile.renameTo(file);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "¡Contacto eliminado correctamente!");
            } else {
                JOptionPane.showMessageDialog(this, "Contacto no encontrado.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error mientras se eliminaba el contacto.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                directorioAPP app = new directorioAPP();
                app.setVisible(true);
            }
        });
    }
}
