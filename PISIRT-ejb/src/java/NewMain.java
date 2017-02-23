/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user4
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import org.json.simple.JSONObject;

public class NewMain {

	public static void main(String[] args) {

            //new Thread(new SimpleServer()).start();
		new Thread(new SimpleClient()).start(); //dÃ©marrer le thread responsable de l'envoie de la requete au mobile

	}



	static class SimpleClient implements Runnable {

		@Override
		public void run() {

			Socket socket = null; //initialiser un nouveau objet socket
			try {

		      Thread.sleep(3000);// il faut que le thread attend 3 secondes pour que le mobile reste en Ã©coute 
                                                  //avant qu'on envoie la requete
				
               		socket = new Socket("172.16.96.16", 6050);//prÃ©ciser l'adresse ip et le port du mobile
				
			    PrintWriter outWriter = new PrintWriter(socket.getOutputStream(),true);
                            JSONObject j=new JSONObject();
                            
                            j.put("msg","bonjour ");
                            j.put("num","+21627872299");
  // crÃ©er l'objet json contenant le message Ã  envoyer de la forme {"msg":"message Ã  envoyer","num":"numÃ©ro destinataire"}                          
                            String c=j.toString();
			    
			    outWriter.println(c);// envoyer la requete
			   

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        static class SimpleServer implements Runnable {

        @Override
        public void run() {

            ServerSocket serverSocket = null;
            while (true) {

                try {
                    serverSocket = new ServerSocket(6050);

                    Socket clientSocket = serverSocket.accept();

                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    System.out.println("Client said :"+inputReader.readLine());
    //  a=inputReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        serverSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
	}
        
}
