package Demo;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

public class Client1Msg
{
	public static int i=0;
	public static int j=0;
	public static void main(String args[]) throws Exception
	{
		final DataOutputStream dataout;
		final String nameClient;
		InetAddress ip = null;
		int count=0;
		Font font = new Font("Helvetica", Font.PLAIN, 18);
		Color c = new Color(83, 189, 42);
		
		try
		{
			
			try
			{
				//Get Inet Address of Client
				ip = InetAddress.getLocalHost();
				System.out.println("\n\nLocal Host :- "+ip);
			}
			catch(Exception e)
			{
				System.out.println("Error:- "+e);
			}
			
			//Create Socket to get Connected to the Server
			Socket sock = new Socket("192.168.43.198",2004);		//3456 userdefined Port no
			
			//Create input & output Streams for Communicating with server
			InputStream input = sock.getInputStream();
			OutputStream output = sock.getOutputStream();
			
			DataInputStream datain = new DataInputStream(input);
			dataout = new DataOutputStream(output);
			
			System.out.println("\n\nClient Started....");
			
			Scanner S = new Scanner(System.in);
			
			System.out.println("\n***Chatting Application****\n");
			System.out.print("\nEnter Your Name: ");
			 nameClient = S.nextLine();
			dataout.writeUTF(nameClient);
			
			
			String IdClient =  nameClient;
			dataout.writeUTF(IdClient);
			
			System.out.print("\nEnter Your Friend's Name: ");
			String FrClient = S.nextLine();
			dataout.writeUTF(FrClient);
			
			String nameServer = datain.readUTF();
			System.out.println("\nNote:- 'Bye' to end Chatting\n");
			System.out.println("\n***Chatting Application Begins****\n");
			
			
			 i=10;
			JLabel lbl2[] = new JLabel[1000];
			 j=0;
			JFrame f = new JFrame("Just_Chat");
			JPanel p = new JPanel();
			f.setFont(font);
			p.setLayout(null);
			f.setSize(460, 750);
			 f.setLocation(20, 50);
			f.setVisible(true);
			f.setLayout(null);
			ImageIcon img = new ImageIcon("./icon.jpeg");
			f.setIconImage(img.getImage());
			final JTextField jtf = new JTextField(10);
			jtf.setText(nameClient);
			f.add(jtf);
			  jtf.setBounds(5, 655, 310, 40);
			JButton btn = new JButton("Send");	

			f.add(btn);
			btn.setBounds(320, 655, 123, 40);
			btn.addActionListener( new ActionListener(){
				public void actionPerformed( ActionEvent e)
				{
					
					try
					{
						String msgClient =  nameClient +" : "+jtf.getText() ;
						dataout.writeUTF(msgClient);
						lbl2[j] = new JLabel();
						lbl2[j].setText(msgClient);
					    lbl2[j].setFont(font);
					    lbl2[j].setBackground(new Color(83, 189, 42));
						p.add(lbl2[j]);
						p.revalidate();
						f.revalidate();
						
						lbl2[j].setBounds(250,20+i,300,25);
						jtf.setText("");
						j++;
						i+=30;
						
					} 
					catch (IOException e1) 
					{
						
						e1.printStackTrace();
					}
				}
				
			});
			
		int vsb=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int hsb=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
		JScrollPane jsp1=new JScrollPane(p,vsb,hsb);
	
		//JScrollPane jsp1=new JScrollPane(p,vsb,hsb);
			f.add(jsp1);
			
			jsp1.setBounds(0,0,440,650);
			
			 
			
			while(true)
			{
				String msgServer = datain.readUTF();
				System.out.println(msgServer);
				lbl2[j] = new JLabel();
				lbl2[j].setText(msgServer);
				lbl2[j].setFont(font);
				lbl2[j].setBackground(c);
				p.add(lbl2[j]);
				lbl2[j].setBounds(10,20+i,300,25);
				j++;
				i+=30;
				int height = (int)p.getPreferredSize().getHeight();
                 jsp1.getVerticalScrollBar().setValue(height);
				f.revalidate();
				
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error:- "+e);
		}
	}
}