package Demo;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import javax.swing.JScrollPane;

class ServerMsg2
{
	public static void main(String args[])
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
			DriverManager.getConnection("jdbc:mysql://localhost:3306/justchat","root","Samarth123@");
			PreparedStatement pst = con.prepareStatement("insert into justchat(client1,client2)values(?,?)");
			
			//Create Server Socket
			ServerSocket SS1 = new ServerSocket(2004);
		
			
			Socket s1 = SS1.accept();
			Socket s2 = SS1.accept();
			System.out.println("\n\nServer Started....");
			System.out.println("\n\nClient Connected to Server....");
			
			//Input Streams for Input and Output Operation 
			InputStream input1 = s1.getInputStream();
			OutputStream output1 = s1.getOutputStream();
			
			DataInputStream datain1 = new DataInputStream(input1);
			DataOutputStream dataout1 = new DataOutputStream(output1);
			
			InputStream input2 = s2.getInputStream();
			OutputStream output2 = s2.getOutputStream();
			
			DataInputStream datain2 = new DataInputStream(input2);
			DataOutputStream dataout2 = new DataOutputStream(output2);
			
			Scanner S = new Scanner(System.in);
			
			System.out.println("\n******Chatting Application*************\n");
			
			String Name1 = datain1.readUTF();
			String Id1me = datain1.readUTF();
			String Id1Fr = datain1.readUTF();
			
			String Name2 = datain2.readUTF();
			String Id2me = datain2.readUTF();
			String Id2Fr = datain2.readUTF();
			
			System.out.println("\nNote:- 'Bye' to end Chatting\n");
			System.out.println("\n******Chatting Application Begins*************\n");
			
			String msgClient1="";
			String msgClient2="";
			String Bye = "bye";
			
			if((Id1me.equalsIgnoreCase(Id2Fr)) && (Id2me.equalsIgnoreCase(Id1Fr)))
			{	
				dataout1.writeUTF(Name2);
				dataout2.writeUTF(Name1);
				
				while(!(msgClient1.equalsIgnoreCase(Bye)) && !(msgClient2.equalsIgnoreCase(Bye)))
				{
					msgClient1 =(datain1.readUTF());
					
					if(msgClient1.contains(Name1) )
					{
						dataout2.writeUTF(msgClient1);
					}
					
					msgClient2 =(datain2.readUTF());
					if(msgClient2.contains(Name2))
					{
						dataout1.writeUTF(msgClient2);
					}
					pst.setString(1, msgClient1);
					pst.setString(2, msgClient2);
					pst.executeUpdate();
					System.out.println("Client1: "+msgClient1);
					System.out.println("Client2: "+msgClient2);
					
					
					
				}
				
				
			}	
				
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error:- "+e);
		}
	}
}