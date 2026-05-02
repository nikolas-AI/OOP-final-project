package txst.myApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TodoFrame extends JFrame {
    private static final String[] TABLE_COLUMNS = {"ID", "Title", "Description", "Status"};
    private static final String ALL_TASKS_FILTER = "All Tasks";
    private static final String COMPLETED_TASKS_FILTER = "Completed Tasks";

    private final TaskService taskService;
    private final DefaultTableModel tableModel;
    private final JTable taskTable;
    private final JComboBox<String> filterComboBox;

    public TodoFrame(TaskService taskService) {
        this.taskService = taskService;
        tableModel = createTableModel();
        taskTable = createTaskTable();
        filterComboBox = createFilterComboBox();

        setTitle("Todo List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(new JScrollPane(taskTable), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);
        setSize(760, 480);
        setLocationRelativeTo(null);

        refreshTaskTable();
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(TABLE_COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable createTaskTable() {
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(380);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        return table;
    }

    private JComboBox<String> createFilterComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[] {ALL_TASKS_FILTER, COMPLETED_TASKS_FILTER});
        comboBox.addActionListener(event -> refreshTaskTable());
        return comboBox;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(8, 8));
        JLabel titleLabel = new JLabel("Todo List");
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(new JLabel("View"));
        filterPanel.add(filterComboBox);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(filterPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addButton = new JButton("Add");
        addButton.setEnabled(false);

        JButton editButton = new JButton("Edit");
        editButton.setEnabled(false);

        JButton completeButton = new JButton("Complete");
        completeButton.addActionListener(this::handleCompleteTask);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::handleDeleteTask);

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(completeButton);
        actionPanel.add(deleteButton);
        return actionPanel;
    }

    private void refreshTaskTable() {
        tableModel.setRowCount(0);
        List<Task> tasks = getVisibleTasks();
        for (Task task : tasks) {
            tableModel.addRow(new Object[] {
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.isCompleted() ? "Completed" : "Pending"
            });
        }
    }

    private List<Task> getVisibleTasks() {
        if (COMPLETED_TASKS_FILTER.equals(filterComboBox.getSelectedItem())) {
            return taskService.getCompletedTasks();
        }
        return taskService.getAllTasks();
    }

    private void handleCompleteTask(ActionEvent event) {
        Optional<Integer> selectedTaskId = getSelectedTaskId();
        if (selectedTaskId.isEmpty()) {
            showMessage("Select a task to complete.");
            return;
        }

        try {
            boolean completed = taskService.markTaskCompleted(selectedTaskId.get());
            if (!completed) {
                showMessage("The selected task could not be found.");
            }
            refreshTaskTable();
        } catch (IOException exception) {
            showError("Could not save completed task.", exception);
        }
    }

    private void handleDeleteTask(ActionEvent event) {
        Optional<Integer> selectedTaskId = getSelectedTaskId();
        if (selectedTaskId.isEmpty()) {
            showMessage("Select a task to delete.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Delete the selected task?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean deleted = taskService.deleteTask(selectedTaskId.get());
            if (!deleted) {
                showMessage("The selected task could not be found.");
            }
            refreshTaskTable();
        } catch (IOException exception) {
            showError("Could not save deleted task.", exception);
        }
    }

    private Optional<Integer> getSelectedTaskId() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow < 0) {
            return Optional.empty();
        }

        Object idValue = taskTable.getValueAt(selectedRow, 0);
        if (idValue instanceof Integer id) {
            return Optional.of(id);
        }
        return Optional.empty();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showError(String message, IOException exception) {
        JOptionPane.showMessageDialog(this, message + "\n" + exception.getMessage(), "Storage Error", JOptionPane.ERROR_MESSAGE);
    }
}
