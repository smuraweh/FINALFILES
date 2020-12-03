import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Frame extends JFrame implements MenuListener, ActionListener{

    private JMenuBar menuBar = new JMenuBar();
    private JMenu about = new JMenu("About");
    private JMenu file = new JMenu("File");
    private JMenuItem load = new JMenuItem("Load a Roster");
    private JMenuItem add_att = new JMenuItem("Add Attendance");
    private JMenuItem save = new JMenuItem("Save");
    private JMenuItem plot = new JMenuItem("Plot Data");
    private JPanel pane = new JPanel();
    private Student[] studentArray; // declare a student array
    private JScrollPane scroll;
    private Load loadObject = new Load(); // declare an object of Load class to call Load operations
    //private Date dateObject = new Date();
    private Save saveObject = new Save();
    private Plot plotObject;
    public javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    public String date;
    public Node[] classAttend = null;
    private JFrame secondFrame;
    ArrayList<String> newList;
    ArrayList<Double> newTIME = new ArrayList<>();
    public JDialog tempPane2;

    JFrame window;
    JTable table;

    Frame() {
        window = new JFrame("CSE360 Final Project");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //table.getModel().addTableModelListener(this);

        table = createTable();
        scroll = new JScrollPane(table);

        pane.setLayout(new BorderLayout());
        pane.add(scroll, BorderLayout.CENTER);
//
//        pane.setLayout(new BorderLayout());
//        pane.add(scroll, BorderLayout.CENTER);

        about.addMenuListener(this);
        load.addActionListener(this);
        add_att.addActionListener(this);
        save.addActionListener(this);
        plot.addActionListener(this);

        menuBar.add(file);
        menuBar.add(about);

        file.add(load);
        file.add(add_att);
        file.add(save);
        file.add(plot);

        window.add(pane);
        window.setJMenuBar(menuBar);
        window.setSize(500, 400);
        window.setVisible(true);
    }

    public JTable createTable() {
        String[][] fileContents = new String[0][6];
        String[] column = {"ID", "First Name", "Last Name", "Program", "Level", "ASUrite"};
        TableModel tblMod = new DefaultTableModel(column, 0);
        table = new JTable(tblMod);
        table.setBounds(30, 40, 200, 300);
        return table;
    }

    public void refresh(Student[] array){
        String[][] fileContents;
        String[] column = {"ID", "First Name", "Last Name", "Program", "Level", "ASUrite"};
        if(array != null) {
            fileContents = new String[array.length][6];

            for (int i = 0; i < array.length; i++) {
                fileContents[i][0] = array[i].getID();
                fileContents[i][1] = array[i].getFirst();
                fileContents[i][2] = array[i].getLast();
                fileContents[i][3] = array[i].getProg();
                fileContents[i][4] = array[i].getLevel();
                fileContents[i][5] = array[i].getASUrite();
            }
        }
        else
            fileContents = new String[0][6];
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setDataVector(fileContents, column);
        model.fireTableDataChanged();
    }

    public void addAtt(Node[] attendance) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        if (attendance.length != 0) {
            String date = attendance[0].attendanceDate;
            model.addColumn(date);
            int columnCount = model.getColumnCount();
            //System.out.println(columnCount);
            //System.out.println(model.getRowCount());
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                //System.out.println(attendance.length);
                for (int n = 0; n < attendance.length; n++) {
                    //System.out.println(n);
                    if (model.getValueAt(i, 5) == attendance[n].ASUrite) {
                        model.setValueAt(attendance[n].attendanceAmount, i, columnCount - 1);
                        //System.out.println(model.getValueAt(i, columnCount - 1));
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        new Frame();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == load){ // complete
            Student[] sArray = loadObject.loadRoster();
            refresh(sArray);
            studentArray = sArray;
        }
        else if(e.getSource() == add_att) { // in-progress
            addAttendance();
        }
        else if(e.getSource() == save) { // complete
            String name = loadObject.fileName;
            saveObject.writeFile(name, table);
        }
        else if(e.getSource() == plot) {
            plotObject = new Plot(table);
            plotObject.display();
        }
    }


    public void menuSelected (MenuEvent e) {
        if(e.getSource() == about)
            JOptionPane.showMessageDialog(null, "Project Contributors:\nSamia Muraweh,\nZack Sanchez,\nJacob Sumner");
    }

    public void menuDeselected (MenuEvent e) {

    }

    public void menuCanceled (MenuEvent e) {

    }

    public void addAttendance() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                secondFrame = new JFrame("Select Date");
                secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel pane = new JPanel();

                jLabel1 = new javax.swing.JLabel();
                jComboBox1 = new javax.swing.JComboBox<>();
                jComboBox2 = new javax.swing.JComboBox<>();
                jButton1 = new javax.swing.JButton();

                pane.add(jLabel1);
                pane.add(jComboBox1);
                pane.add(jComboBox2);
                pane.add(jButton1);

                jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
                jLabel1.setText("Please select date.");

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

                jButton1.setText("Done");
                jButton1.setToolTipText("");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton1ActionPerformed(evt);
                    }
                });

                secondFrame.add(pane);
                secondFrame.setSize(400, 150);
                secondFrame.setVisible(true);
            }
        });
    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String month = (String)jComboBox1.getSelectedItem();
        String day = (String)jComboBox2.getSelectedItem();

        date = (month + " " + day);
        secondFrame.dispose();
        guiPrompt();
    }

    public Node[] sort(ArrayList<String> users, ArrayList<Double> times){
        int size = users.size();
        Node[] array = new Node[size];
        for(int n = 0; n < size; n++){
            Node newItem = new Node();
            newItem.attendanceDate = date;
            newItem.attendanceAmount = times.get(n);
            newItem.ASUrite = users.get(n);
            array[n] = newItem;
        }

        return array;
    }

    public void guiPrompt()
    {

        ArrayList<Double> TIME = new ArrayList<>();
        ArrayList<String> ASUR = new ArrayList<>();
        //Node[] attendanceList = null;

        JFileChooser fc = new JFileChooser();
        JButton open = new JButton();
        fc.setCurrentDirectory(new File("Desktop"));
        fc.setDialogTitle("Select CSV FILE");
        //	fc.setFileSelectionMode("JFileChooser.") possibly make only csv files selectable

        //fc.showOpenDialog(open);
        tempPane2 = new JDialog();
        tempPane2.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        int value = fc.showOpenDialog(tempPane2);
        if (value == JFileChooser.APPROVE_OPTION)
        {
            String path = fc.getSelectedFile().getAbsolutePath();
            String line = "";

            newTIME = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));

                while((line = reader.readLine()) != null) {
                    String[] stuff = line.split(",");
                    ASUR.add(stuff[0]);

                    double dnum = Double.parseDouble(stuff[1]);
                    TIME.add(dnum);
                }

                newList = removeDuplicates(ASUR);

                int size =  newList.size();
                int size2 =  ASUR.size();
                int k = 0;
                double g = 0;
                int i = 0;
                int q= 0;

                Node[] attendanceList = new Node[size];

                while(i < size)
                {
                    q=0;
                    k = 0;
                    g = 0;
                    while ( k < size2)
                    {
                        boolean isEqual = ASUR.get(k).equals(newList.get(i));

                        if( q == 0)
                        {
                            g = g + TIME.get(k);

                            newTIME.add(g);
                            //	 System.out.println(k);
                            k++;
                            //	 System.out.println(g);
                            q++;
                            if(i >0)
                            {
                                g = g - TIME.get(0);
                                newTIME.set(i, g);
                            }

                        }

                        else if(isEqual)
                        {
                            g = g + TIME.get(k);
                            newTIME.set(i, g);
                            k++;

                        }

                        else
                        {
                            k++;
                        }

                    }
                    i++;
                }

				for(int n = 0; n < size; n++){
					Node newItem = new Node();
					newItem.attendanceDate = date;
					newItem.attendanceAmount = newTIME.get(n);
					newItem.ASUrite = newList.get(n);
					attendanceList[n] = newItem;

					System.out.println("Data successfully added.");
				}

				addAtt(attendanceList);
            }catch(FileNotFoundException a) {
                System.out.println("Error.");
            }catch(IOException a) {
                System.out.println("Error.");
            }
        }
        else if (value == JFileChooser.CANCEL_OPTION)
        {
            System.out.println("Selection cancelled.");
        }
    }

}
