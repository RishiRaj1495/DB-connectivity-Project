import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DisplayQueryResults extends JFrame {
    private JTextField filterField;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    // Database credentials (update as needed)
    private static final String URL = "jdbc:mysql://localhost:3306/books";
    private static final String USER = "root";
    private static final String PASSWORD = "**********";  // Replace with your MySQL password

    public DisplayQueryResults() {
        setTitle("Display Query Results");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel filterLabel = new JLabel("Enter filter text:");
        filterLabel.setBounds(10, 10, 150, 25);
        add(filterLabel);

        filterField = new JTextField();
        filterField.setBounds(160, 10, 200, 25);
        add(filterField);

        JButton buttonA = new JButton("Query A: All Authors");
        buttonA.setBounds(10, 40, 200, 25);
        add(buttonA);

        JButton buttonB = new JButton("Query B: Authors with Books");
        buttonB.setBounds(220, 40, 200, 25);
        add(buttonB);

        JButton buttonC = new JButton("Query C: Filter Java Books");
        buttonC.setBounds(430, 40, 200, 25);
        add(buttonC);

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(10, 80, 660, 380);
        add(scrollPane);

        buttonA.addActionListener(this::handleButtonA);
        buttonB.addActionListener(this::handleButtonB);
        buttonC.addActionListener(this::handleButtonC);
    }

    private void executeQuery(String query, String[] columns) {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(columns);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Object[] row = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = resultSet.getObject(columns[i]);
                }
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void handleButtonA(ActionEvent e) {
        String query = "SELECT authorID, firstName, lastName FROM authors";
        String[] columns = {"authorID", "firstName", "lastName"};
        executeQuery(query, columns);
    }

    private void handleButtonB(ActionEvent e) {
        String query = "SELECT firstName, lastName, title, editionNumber " +
                       "FROM authors INNER JOIN authorISBN ON authors.authorID = authorISBN.authorID " +
                       "INNER JOIN titles ON authorISBN.isbn = titles.isbn";
        String[] columns = {"firstName", "lastName", "title", "editionNumber"};
        executeQuery(query, columns);
    }

    private void handleButtonC(ActionEvent e) {
        String filter = filterField.getText().trim();
        String baseQuery = "SELECT firstName, lastName, title, editionNumber " +
                           "FROM authors INNER JOIN authorISBN ON authors.authorID = authorISBN.authorID " +
                           "INNER JOIN titles ON authorISBN.isbn = titles.isbn";
        String query = baseQuery + " WHERE title LIKE '%" + (filter.isEmpty() ? "Java" : filter) + "%'";
        String[] columns = {"firstName", "lastName", "title", "editionNumber"};
        executeQuery(query, columns);
    }

    public static void main(String[] args) {
        DisplayQueryResults app = new DisplayQueryResults();
        app.setVisible(true);
    }
}

