import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JSlider;

import com.fazecast.jSerialComm.*; 
public class Main {

	public static void main(String[] args) {
		
		SerialPort ports[] = SerialPort.getCommPorts();
		
		SerialPort port = ports[4];
		if(port.openPort())
		{
			System.out.println("Successfully opened port!!");
		}
		else
		{
			System.out.println("tre fails");
			return;
		}
		
		port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING, 0, 0);
		OutputStream out = port.getOutputStream();
		for(int j = 0; j < 10; j++)
		{
			try{
				out.write(lightUp(4,10));
			
			}
			catch(IOException e){System.out.println("Somethings weird");}
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		System.out.println("done");
		port.closePort();
	}
	public static int lightUp(int green, int blue)
	{
		if(green == 2)
		{
			if(blue == 7)
			{
				return 1;
			}
			if(blue == 8)
			{
				return 2;
			}
			if(blue == 9)
			{
				return 3;
			}
			if(blue == 10)
			{
				return 4;
			}
			if(blue == 11)
			{
				return 5;
			}
		}
		if(green == 3)
		{
			if(blue == 7)
			{
				return 6;
			}
			if(blue == 8)
			{
				return 7;
			}
			if(blue == 9)
			{
				return 8;
			}
			if(blue == 10)
			{
				return 9;
			}
			if(blue == 11)
			{
				return 10;
			}
		}
		if(green == 4)
		{
			if(blue == 7)
			{
				return 11;
			}
			if(blue == 8)
			{
				return 12;
			}
			if(blue == 9)
			{
				return 13;
			}
			if(blue == 10)
			{
				return 14;
			}
			if(blue == 11)
			{
				return 15;
			}
		}
		if(green == 5)
		{
			if(blue == 7)
			{
				return 16;
			}
			if(blue == 8)
			{
				return 17;
			}
			if(blue == 9)
			{
				return 18;
			}
			if(blue == 10)
			{
				return 19;
			}
			if(blue == 11)
			{
				return 20;
			}
		}
		if(green == 6)
		{
			if(blue == 7)
			{
				return 21;
			}
			if(blue == 8)
			{
				return 22;
			}
			if(blue == 9)
			{
				return 23;
			}
			if(blue == 10)
			{
				return 24;
			}
		}
		return 25; // this should never happen
	}

}

