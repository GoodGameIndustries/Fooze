import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class UI extends JFrame implements ActionListener{

	public FoozeKryoServer fks;
	JLabel status = new JLabel("Status: Server Online");
	JLabel world1;
	JLabel world2;
	JLabel world3;
	JLabel world4;
	JLabel world5;
	JButton close = new JButton("Close");
	Timer timer;
	public UI(FoozeKryoServer fks){
		super();
		this.fks=fks;
		
		world1 = new JLabel("World1: "+fks.players1.size()+" players");
		world2 = new JLabel("World2: "+fks.players2.size()+" players");
		world3 = new JLabel("World3: "+fks.players3.size()+" players");
		world4 = new JLabel("World4: "+fks.players4.size()+" players");
		world5 = new JLabel("World5: "+fks.players5.size()+" players");
		
		setSize(175,150);
		setTitle("Fooze Kryo Server");
		setLayout(null);
		
		status.setBounds(0, 0, 200, 15);
		add(status);
		
		world1.setBounds(0, 17, 200, 15);
		add(world1);
		
		world2.setBounds(0, 32, 200, 15);
		add(world2);
		
		world3.setBounds(0, 47, 200, 15);
		add(world3);
		
		world4.setBounds(0, 62, 200, 15);
		add(world4);
		
		world5.setBounds(0, 77, 200, 15);
		add(world5);
		
		close.setBounds(0,92,100,15);
		close.addActionListener(this);
		add(close);
		this.setVisible(true);
		
		timer=new Timer();
		timer.schedule(new Exit(), 1800000);
	}
	
	public void repaint(){
		super.repaint();
		world1.setText("World1: "+fks.players1.size()+" players");
		world2.setText("World2: "+fks.players2.size()+" players");
		world3.setText("World3: "+fks.players3.size()+" players");
		world4.setText("World4: "+fks.players4.size()+" players");
		world5.setText("World5: "+fks.players5.size()+" players");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
		
	}
	
	class Exit extends TimerTask{

		@Override
		public void run() {
			System.exit(0);
			
		}
		
	}
	
	
}
