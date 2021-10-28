package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
	{
		private static Scanner scan;
		private Socket clientSocket = null;
		private BufferedReader input;
		private PrintWriter output;

		public static void main(String[] args)
		{
			char clientType;
			Client client = null;
			try
			{
				scan = new Scanner(System.in);
				while (true)
				{
					System.out.print("-- ESCOLHA O TIPO DO CLIENTE (ALUNO OU PROFESSOR): ");
					clientType = scan.nextLine().toUpperCase().charAt(0);
					if (clientType == 'A' || clientType == 'P')
						break;
				}

				client = new Client();
				client.startClient(clientType);
			}
			catch (Exception ex)
			{
				System.out.println("-- MENSAGEM DE ERRO: " + ex.getMessage() + " --");
			}
			finally
			{
				client.closeSocket();
				scan.close();
				System.out.println("-- CLIENTE FINALIZADO --");
			}
		}

		public void startClient(char clientType) throws UnknownHostException, IOException
		{

			clientSocket = new Socket("127.0.0.1", 5566);
			System.out.printf("-- CLIENTE INICIADO COMO: %s\n", (clientType == 'A' ? "ALUNO" : "PROFESSOR") + " --");
			this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.output = new PrintWriter(clientSocket.getOutputStream(), true);
			output.println(clientType);
			if (clientType == 'A')
			{
				clientAluno();
			}
			else
			{
				clientProf();
			}
		}

		public void clientProf() throws IOException
		{
			System.out.print("-- DIGITE O NUMERO DA TURMA QUE DESEJA ABRIR/FECHAR A CHAMADA: ");
			output.println(scan.nextLine());
			System.out.println(input.readLine());
			boolean isEnd = Boolean.parseBoolean(input.readLine());
			if (isEnd)
			{
				System.out.println("-- LISTA DE ALUNOS QUE RESPONDERAM A CHAMADA --");
				int size = Integer.parseInt(input.readLine());
				for (int i = 0; i < size; i++)
				{
					System.out.println(input.readLine());
				}
			}
		}

		public void clientAluno() throws IOException
		{
			System.out.print("-- DIGITE SEU NÚMERO DE MATRICULA: ");
			output.println(scan.nextLine());
			System.out.print("-- DIGITE O NÚMERO DA SUA TURMA: ");
			output.println(scan.nextLine());

			System.out.println(input.readLine());
		}

		public void closeSocket()
		{
			if (clientSocket != null && !clientSocket.isClosed())
			{
				try
				{
					clientSocket.close();
				}
				catch (IOException ex)
				{
					System.out.println("-- ERRO AO FECHAR SOCKET: " + ex.getMessage() + " --");
				}
			}
		}
	}
