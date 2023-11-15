import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistrationForm extends JFrame {

    private JTextField nameField, mobileField, dobField, addressField;
    private JComboBox<String> genderComboBox;
    private JCheckBox termsCheckBox;

    public UserRegistrationForm() {
     
        setTitle("User Registration Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
        JLabel nameLabel = new JLabel("Name:");
        JLabel mobileLabel = new JLabel("Mobile:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel dobLabel = new JLabel("Date of Birth:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel termsLabel = new JLabel("Terms and Conditions:");

        nameField = new JTextField(20);
        mobileField = new JTextField(20);
        dobField = new JTextField(20);
        addressField = new JTextField(20);

        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);

        termsCheckBox = new JCheckBox();

        JButton submitButton = new JButton("Submit");

    
        setLayout(new GridLayout(7, 2));

      
        add(nameLabel);
        add(nameField);
        add(mobileLabel);
        add(mobileField);
        add(genderLabel);
        add(genderComboBox);
        add(dobLabel);
        add(dobField);
        add(addressLabel);
        add(addressField);
        add(termsLabel);
        add(termsCheckBox);
        add(new JLabel()); // Empty label for spacing
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the submit button click
                if (termsCheckBox.isSelected()) {
                    saveUserData();
                } else {
                    JOptionPane.showMessageDialog(UserRegistrationForm.this,
                            "Please accept the terms and conditions.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void saveUserData() {
        // Get user input
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String dob = dobField.getText();
        String address = addressField.getText();

       
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");

        
            String url = "jdbc:mysql://localhost:3306/your_database";
            String username = "admin";
            String password = "40544090";
            Connection connection = DriverManager.getConnection(url, username, password);

        
            String sql = "INSERT INTO users (name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, mobile);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);

            preparedStatement.executeUpdate();

     
            preparedStatement.close();
            connection.close();

            
            JOptionPane.showMessageDialog(this, "User data saved successfully!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(this, "Error saving user data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserRegistrationForm form = new UserRegistrationForm();
            form.setVisible(true);
        });
    }
}
