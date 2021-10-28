package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Server
	{
		ServerSocket listener;
		public static Map<String, Turma> turmasAbertas;
		private static DateTimeFormatter dtf;
		public static final int PORT = 5566;

		public static void main(String[] args)
		{
			try
			{
				dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
				turmasAbertas = new HashMap<String, Turma>();
				Server server = new Server();
				server.startServer();
			}
			catch (Exception e)
			{
				System.out.println("-- MENSAGEM DE ERRO:" + e.getMessage() + " --");
			}
		}

		public static String registraChamadaAluno(String turma, String mtr)
		{
			String turmaRegistrada = "0";
			Turma turmaAtual;
			if (turmasAbertas.containsKey(turma))
			{
				turmaAtual = turmasAbertas.get(turma);
				turmaRegistrada = turma;
				if (!turmaAtual.getAlunos().contains(mtr))
					turmaAtual.addAluno(mtr);
				else
					return "-- NÃO É POSSIVEL REALIZAR A PRESENÇA NOVAMENTE PARA A MESMA MATRICULA NA MESMA TURMA --";
			}
			return (String.format("-- PRESENÇA %s REGISTRADA EM: %s NA TURMA: %s \n", mtr, dtf.format(LocalDateTime.now()), turmaRegistrada));
		}

		public static String toggleTurma(String turma)
		{
			String str;
			if(!turmasAbertas.containsKey(turma)) 
			{
				turmasAbertas.put(turma, new Turma(turma));
				str = String.format("-- CHAMADA DA TURMA %s ABERTA EM: %s --", turma, dtf.format(LocalDateTime.now()));
			}
			else 
			{
				turmasAbertas.remove(turma);
				str = String.format("-- CHAMADA DA TURMA %s FECHADA EM: %s --", turma, dtf.format(LocalDateTime.now()));
			}
			return str;
		}

		public void startServer() throws IOException
		{
			listener = new ServerSocket(PORT);
			System.out.println("-- SERVIDOR INICIADO --");
			connectionLoop();
		}

		public void connectionLoop() throws IOException
		{
			while (true)
			{
				Socket client = listener.accept();
				System.out.println("-- CLIENTE ACEITO: " + client.getRemoteSocketAddress() + " --");
				new Thread(new Connection(client)).start();
			}
		}

	}