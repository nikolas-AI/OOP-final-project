package txst.myApp;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TaskDialog extends JDialog {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private TaskFormData taskFormData;

    public TaskDialog(Frame owner, String dialogTitle, Task existingTask) {
        super(owner, dialogTitle, true);
        titleField = new JTextField(28);
        descriptionArea = new JTextArea(6, 28);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        if (existingTask != null) {
            titleField.setText(existingTask.getTitle());
            descriptionArea.setText(existingTask.getDescription());
        }

        setLayout(new BorderLayout(8, 8));
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public static Optional<TaskFormData> showForCreate(Frame owner) {
        TaskDialog dialog = new TaskDialog(owner, "Add Task", null);
        dialog.setVisible(true);
        return Optional.ofNullable(dialog.taskFormData);
    }

    public static Optional<TaskFormData> showForEdit(Frame owner, Task task) {
        TaskDialog dialog = new TaskDialog(owner, "Edit Task", task);
        dialog.setVisible(true);
        return Optional.ofNullable(dialog.taskFormData);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 4, 8);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(new JLabel("Title"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        formPanel.add(titleField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        formPanel.add(new JLabel("Description"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        formPanel.add(new JScrollPane(descriptionArea), constraints);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(event -> saveTaskData());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private void saveTaskData() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task title cannot be blank.");
            return;
        }

        taskFormData = new TaskFormData(title, description);
        dispose();
    }

    public record TaskFormData(String title, String description) {
    }
}
