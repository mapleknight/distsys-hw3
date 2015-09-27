import java.math.BigDecimal;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class store_server {
	public static void main(String args[]) throws IOException{
		if(args.length != 1){
			System.out.println("Usage : java Store_server port");
		}
		int port = Integer.parseInt(args[0]);
		ServerSocket serversocket = new ServerSocket(port);
		System.out.println("Start Server on port :" + port);
		try{
			while(true){
				Socket clientSocket = serversocket.accept();
				System.out.println("\nAccept connection from client");
				process(clientSocket);
			}
		}catch(IOException e){
			System.out.println("Error Connection");
		}
		
		//System.exit(0);
	}
	
	public static void process(Socket clientSocket)throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		out.println("Welcome to the store system");
		String UserInput;
		String value = "";
		String getfirst = "Hava not set any value.";
		String setting = "The value has been set";
		String result = "";
		int flag = 1;
		if((UserInput = in.readLine()) == null){
			System.out.println("Error reading message");
			in.close();
			out.close();
			clientSocket.close();
		}
		System.out.println("message " + UserInput);
		String[] params = UserInput.split(" ");
		if(params.length > 2) {
			out.println("you must input as 'set <value>' or 'get'");
			flag = 0;
		}
		else if(params[0].equalsIgnoreCase("set")){
			result = setting;
			value = params[1];
			flag = 2;
		}
		else if(params[0].equalsIgnoreCase("get") && params.length == 1){
			result = value;
			flag = 2;
		}
		if(flag !=0 ){
			try{
				out.println(result);
			}catch(Exception e){
				out.println("we can only set a value or get that value.");
				out.close();
				in.close();
				clientSocket.close();
				continue;
			}
		}
	}
}

