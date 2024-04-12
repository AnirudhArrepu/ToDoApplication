import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class TODO extends JFrame {
    TODO(Vector<String> preTasks) {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextArea inTask = new JTextArea(1, 30);
        JButton addTask = new JButton("+");

        topPanel.add(inTask);
        topPanel.add(addTask);
        contentPane.add(topPanel, BorderLayout.NORTH);
        setContentPane(contentPane);
        setVisible(true);
        for (int i = 0; i < preTasks.size(); ++i) {
            createTask(preTasks.get(i), contentPane, preTasks);
        }
        addTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = inTask.getText();
                createTask(task, contentPane, preTasks);
            }
        });
    }

    public static void main(String[] args) {
        Vector<String> preTasks = new Vector<>();
        loadTasks(preTasks);
        SwingUtilities.invokeLater(() -> new TODO(preTasks));
    }

    public static void loadTasks(Vector<String> preTasks) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("tasks.csv"));
            while ((line = br.readLine()) != null) {
                preTasks.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void addTask(String task) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("tasks.csv", true));
            bw.write(task);
            bw.newLine();
            bw.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void removetask(String task, Vector<String> preTasks){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("tasks.csv"));
            for(int i=0; i<preTasks.size(); i++){
                if(preTasks.get(i).equals(task)){
                    continue;
                }
                bw.write(preTasks.get(i));
            }
            bw.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void createTask(String task, JPanel contentPane, Vector<String> preTasks) {
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox taskbox = new JCheckBox(task, false);

        taskPanel.add(taskbox);

        contentPane.add(taskPanel);
        contentPane.revalidate();
        contentPane.repaint();

        taskbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskbox.isSelected()) {
                    contentPane.remove(taskPanel);
                    contentPane.revalidate();
                    contentPane.repaint();
                    removetask(taskbox.getText(), preTasks);
                    addTask(task);
                }
            }
        });
    }

}
