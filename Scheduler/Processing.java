import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Processing extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JButton stop;//,retry;
    private JLabel fit,info;
    private Container c;
    private double fitness=0;
    DecimalFormat percentage = new DecimalFormat("#.00%");
    
    public Processing()
    {
       super("Processing...");
       c = getContentPane();
       generateMenu();
       this.setJMenuBar(menuBar);      
       
       stop = new JButton("Stop");
       stop.setBounds(170,190,80,34);
       stop.setBackground(new Color(255,204,204));
       stop.setForeground(new Color(0,0,153));
       stop.setEnabled(true);
       stop.setFont(new Font("sansserif",0,12));
       stop.setVisible(true);
       
       fit = new JLabel(percentage.format(fitness));
       fit.setBounds(325,40,80,30);
       fit.setFont(new Font("sansserif",0,12));
       fit.setVisible(true);
       
       info = new JLabel("Uncompleted");
       info.setBounds(155,70,150,100);
       info.setFont(new Font("sansserif",0,16));
       info.setVisible(true);
       
       c.setLayout(null);
       c.setBackground(new Color(204,204,255));
       
       //c.add(retry);
       c.add(stop);
       c.add(fit);
       c.add(info);
       
       //retry.addActionListener(this);
       stop.addActionListener(this);
       
       setSize(400,300);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setVisible(true);
    }
    
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
                "The process will automatically start."+
                "\nThe percentage represents the perfectness of the result"+
                " (100%=most perfect)." +
                "\nIt takes longer time to get better results." +
                "\nClick \"Stop\" to ouput current available result."+                
                "\nResults can be found in \"Result\" folder under prgram director."
                );
            }
        });
        
        help.add(about);
        help.add(usertips);
        menuBar.add(help);
    }
    
    public void change(int fit){
        this.fitness=fit;
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==stop){
            Initiate.flag = false;
            stopInfo();
        }
    }      
          
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLUE);
        g.drawRect(100,90,200,20); //(x,y,width,length)
        int length = (int)Math.floor(fitness*200);
        g.fillRect(100,90,length,20);
    }   
    
    public void reload(double fitness){
        if (fitness!=this.fitness){
            this.fitness = fitness;
            repaint();
            fit.setText(percentage.format(fitness));
        }
    }
    
    public void stopInfo(){
        info.setText("Completed");
    }
    
    public static void main (String []args){
        new Processing();
    }
}
