/**
*Text genereted by Simple GUI Extension for BlueJ
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
   
public class mainGUI extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JButton button2; //"confirm"
    private JButton button3; //"Choose Student File"
    private JButton button4; //"Choose Teacher File"
    private JButton button5; //"Choose Course File"
    private JLabel label2; //"IB Time Table Scheduler"
    private JLabel label3; //"Welcom"
    private JLabel label4; //"Plase Select a file to input"
    private JLabel label5; //"This program will help you generate a student schedule based"
    private JLabel label6; //"on student course selection and teacher availability."
    private JTextField textfield1; //"student filename"
    private JTextField textfield2; //"teacher filename"
    private JTextField textfield3; //"course filename"
    private String stuPath, teaPath, cosPath;
    private boolean confirmed = false;
    private File directory;
    
    public void run(){
        Thread thisThread = Thread.currentThread();
        generateGUI();
        while(!confirmed){
             try {
                thisThread.sleep(1000);
            } catch (InterruptedException e){
            }
        }
    }
    
    //Method actionPerformed for Button to proceed
    public void confirm (ActionEvent evt) {        
        try{
            if(stuPath==null||teaPath==null||cosPath==null){
                JOptionPane.showMessageDialog(null, 
                "Error! Please select files.", 
                "Error", JOptionPane.ERROR_MESSAGE);  
            }
            else{
                this.dispose();;
                Initiate.stuPath = stuPath;
                Initiate.teaPath = teaPath;
                Initiate.corPath = cosPath;
                confirmed = true;
                JOptionPane.showMessageDialog(null, 
                "Wait for longer time to get better results.\n"+
                "For information, see Help menu in the next window.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, 
            "Error! Please select correct files.", "Error", JOptionPane.ERROR_MESSAGE);          
        }         
    }

    //Method actionPerformed for SelectFile button
    private void fileSelect1 (ActionEvent evt)
    {
          try{
              JFileChooser fc = new JFileChooser(directory);
              FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","csv");
              fc.setFileFilter(filter);
              fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
              fc.showDialog(new JLabel(),"Select Student file");
              File input = fc.getSelectedFile();
              textfield1.setText(input.getName());
              stuPath=input.getAbsolutePath();
              directory = input.getParentFile();
         }
         catch(Exception e){
             System.out.println("File Not Found.");
         }
    }
    
    private void fileSelect2 (ActionEvent evt)
    {
         try{
              JFileChooser fc = new JFileChooser(directory);
              FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","csv");
              fc.setFileFilter(filter);
              fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
              fc.showDialog(new JLabel(),"Select Teacher file");
              File input = fc.getSelectedFile();
              textfield2.setText(input.getName());
              teaPath=input.getAbsolutePath();
              directory = input.getParentFile();
         }
         catch(Exception e){
             System.out.println("File Not Found.");
         }
     
    }
    
    private void fileSelect3 (ActionEvent evt)
    {
          try{
              JFileChooser fc = new JFileChooser(directory);
              FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","csv");
              fc.setFileFilter(filter);
              fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
              fc.showDialog(new JLabel(),"Select Course file");
              File input = fc.getSelectedFile();
              textfield3.setText(input.getName());
              cosPath=input.getAbsolutePath();
              directory = input.getParentFile();
         }
         catch(Exception e){
             System.out.println("File Not Found.");
         }
    }
    
    
    //method for generate menu
    public void generateMenu(){
        menuBar = new JMenuBar();
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About   ");
        JMenuItem usertips = new JMenuItem("User Tips   ");
        
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(null,
                "This is created by David."
                );
            }
        });
        
        usertips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(null,                
                "Upload corresponding files according to the format."+
                "\nClick \"Confirm\"."+
                "\nThe algorithm will run aotomatically."
                );
            }
        });
        
        help.add(about);
        help.add(usertips);
        menuBar.add(help);
    }
    
    //Constructor 
    public void generateGUI(){
        this.setTitle("mainGUI");
        this.setSize(500,400);
        generateMenu();
        this.setJMenuBar(menuBar);       
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500,400));
        contentPane.setBackground(new Color(204,204,255));

        button2 = new JButton();
        button2.setBounds(200,350,86,30);
        button2.setBackground(new Color(255,204,204));
        button2.setForeground(new Color(0,0,153));
        button2.setEnabled(true);
        button2.setFont(new Font("sansserif",0,15));
        button2.setText("confirm");
        button2.setVisible(true);

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                confirm(evt);
            }
        });


        button3 = new JButton();
        button3.setBounds(15,218,96+50,34);
        button3.setBackground(new Color(255,204,204));
        button3.setForeground(new Color(0,0,153));
        button3.setEnabled(true);
        button3.setFont(new Font("sansserif",0,12));
        button3.setText("Choose Student File");
        button3.setVisible(true);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSelect1(evt);
            }
        });
        
        button4 = new JButton();
        button4.setBounds(15,258,96+50,34);
        button4.setBackground(new Color(255,204,204));
        button4.setForeground(new Color(0,0,153));
        button4.setEnabled(true);
        button4.setFont(new Font("sansserif",0,12));
        button4.setText("Choose Teacher File");
        button4.setVisible(true);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSelect2(evt);
            }
        });
        
        button5 = new JButton();
        button5.setBounds(15,298,96+50,34);
        button5.setBackground(new Color(255,204,204));
        button5.setForeground(new Color(0,0,153));
        button5.setEnabled(true);
        button5.setFont(new Font("sansserif",0,12));
        button5.setText("Choose Course File");
        button5.setVisible(true);
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSelect3(evt);
            }
        });

        label2 = new JLabel();
        label2.setBounds(83,42,335,39);
        label2.setBackground(new Color(204,204,255));
        label2.setForeground(new Color(0,0,153));
        label2.setEnabled(true);
        label2.setFont(new Font("SansSerif",0,25));
        label2.setText("IB Class Group Generator");
        label2.setVisible(true);

        label3 = new JLabel();
        label3.setBounds(169,20,200,32);
        label3.setBackground(new Color(214,217,223));
        label3.setForeground(new Color(0,153,204));
        label3.setEnabled(true);
        label3.setFont(new Font("SansSerif",0,15));
        label3.setText("Welcome! By David Li");
        label3.setVisible(true);

        label4 = new JLabel();
        label4.setBounds(150+30,160,361,39);
        label4.setBackground(new Color(214,217,223));
        label4.setForeground(new Color(0,51,153));
        label4.setEnabled(true);
        label4.setFont(new Font("SansSerif",0,15));
        label4.setText("Please select files.");
        label4.setVisible(true);

        label5 = new JLabel();
        label5.setBounds(61,84,371,16);
        label5.setBackground(new Color(214,217,223));
        label5.setForeground(new Color(0,0,0));
        label5.setEnabled(true);
        label5.setFont(new Font("sansserif",0,12));
        label5.setText("This program will help you generate six class groups based");
        label5.setVisible(true);

        label6 = new JLabel();
        label6.setBounds(96,99,323,33);
        label6.setBackground(new Color(214,217,223));
        label6.setForeground(new Color(0,0,0));
        label6.setEnabled(true);
        label6.setFont(new Font("sansserif",0,12));
        label6.setText("on student course selection and teacher availability.");
        label6.setVisible(true);

        textfield1 = new JTextField();
        textfield1.setBounds(115+50,220,276,29);
        textfield1.setBackground(new Color(255,255,255));
        textfield1.setForeground(new Color(0,0,0));
        textfield1.setEnabled(true);
        textfield1.setFont(new Font("sansserif",0,12));
        textfield1.setText("student filename");
        textfield1.setVisible(true);
        textfield1.setEditable(false);
        
        textfield2 = new JTextField();
        textfield2.setBounds(115+50,260,276,29);
        textfield2.setBackground(new Color(255,255,255));
        textfield2.setForeground(new Color(0,0,0));
        textfield2.setEnabled(true);
        textfield2.setFont(new Font("sansserif",0,12));
        textfield2.setText("teacher filename");
        textfield2.setVisible(true);
        textfield2.setEditable(false);   
        
        textfield3 = new JTextField();
        textfield3.setBounds(115+50,300,276,29);
        textfield3.setBackground(new Color(255,255,255));
        textfield3.setForeground(new Color(0,0,0));
        textfield3.setEnabled(true);
        textfield3.setFont(new Font("sansserif",0,12));
        textfield3.setText("course filename");
        textfield3.setVisible(true);
        textfield3.setEditable(false);

        //add components
        contentPane.add(button2);
        contentPane.add(button3);
        contentPane.add(button4);
        contentPane.add(button5);
        contentPane.add(label2);
        contentPane.add(label3);
        contentPane.add(label4);
        contentPane.add(label5);
        contentPane.add(label6);
        contentPane.add(textfield1);
        contentPane.add(textfield2);
        contentPane.add(textfield3);

        //add panel
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();;
        this.setVisible(true);
    }
}