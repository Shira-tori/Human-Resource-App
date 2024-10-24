import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.util.LinkedList;

public class Main extends JFrame{
	private int[] SCREEN = {1000, 700};
	private int counter = 1;
	private String[] departments = {"Human Resources (HR)", "Finance", "Sales", "Marketing", "Information Technology (IT)"};
	private String[] ranks = {"Manager", "Supervisor", "Rank and File"};
	private LinkedList<Employee> employees = new LinkedList<Employee>();
	private EmployeeTableModel employeeTabModel = new EmployeeTableModel();
	private JTable employeeTable = new JTable(employeeTabModel);
	private JScrollPane scrollPane = new JScrollPane(employeeTable);
	private TablePanel tablePanel = new TablePanel(SCREEN);
	private ComputePanel computePanel = new ComputePanel(SCREEN);
	private ButtonPanel buttonPanel = new ButtonPanel(SCREEN);

	private void run(){
		setLayout(new BorderLayout());

		add(this.tablePanel);
		this.tablePanel.add(scrollPane);
		this.tablePanel.add(buttonPanel, BorderLayout.SOUTH);
		JButton addButton = new JButton("ADD");
		JButton deleteButton = new JButton("DELETE");
		addButton.addActionListener(e -> addButtonClicked());
		deleteButton.addActionListener(e -> deleteButtonClicked());
		this.buttonPanel.add(addButton);
		this.buttonPanel.add(deleteButton);

		add(this.computePanel, BorderLayout.EAST);
		this.computePanel.add(new JLabel("Employee Name: "));

		setSize(new Dimension(SCREEN[0], SCREEN[1]));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void addButtonClicked(){
        JTextField nameField = new JTextField();
        JComboBox<String> departmentField = new JComboBox<String>(this.departments);
        JComboBox<String> rankField = new JComboBox<String>(this.ranks);
		departmentField.setSelectedItem(null);
		rankField.setSelectedItem(null);
        Object[] message = {
                "Name:", nameField,
                "Department:", departmentField,
                "Rank:", rankField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String department = (String)departmentField.getSelectedItem();
            String rank = (String)rankField.getSelectedItem();
            int id = this.counter;
			this.counter++;
            Employee newEmployee = new Employee(Integer.toString(id), name, department, rank);
            this.employees.add(newEmployee);
			this.employeeTabModel.addRow(new Object[] {Integer.toString(id), name, department, rank});
			
        }
	}

	private void deleteButtonClicked(){
		int selectedRow = employeeTable.getSelectedRow();
		employeeTabModel.removeRow(selectedRow);
		this.employees.remove(selectedRow);
	}

	public static void main(String[] args){
		Main main = new Main();
		main.run();
	}
}

class TablePanel extends JPanel{
	public TablePanel(int[] SCREEN){
		int width = SCREEN[0] - (SCREEN[0]/3);
		int height = SCREEN[1];
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(228,229,241));
	}
}

class ComputePanel extends JPanel{
	public ComputePanel(int[] SCREEN){
		int width = (SCREEN[0]/3);
		int height = SCREEN[1];
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(72,75,106));
	}
}

class EmployeeTableModel extends DefaultTableModel{
	public EmployeeTableModel(){
		addColumn("ID");
		addColumn("NAME");
		addColumn("DEPARTMENT");
		addColumn("RANK");
	}

	public boolean isCellEditable(int row, int column){
		return false;
	}
}

class ButtonPanel extends JPanel{
	public ButtonPanel(int[] SCREEN){
		int width = SCREEN[0] - (SCREEN[0]/3);
		int height = 40;
		setPreferredSize(new Dimension(width, height));
		setLayout(new FlowLayout(FlowLayout.TRAILING));
	}
}

class Employee{
	public String id;
	public String name;
	public String department;
	public String rank;

	public Employee(String id, String name, String department, String rank){
		this.id = id;
		this.name = name;
		this.department = department;
		this.rank = rank;
	}
}
