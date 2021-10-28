package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable
	{
		private Socket mainSocket;
		private BufferedReader input;
		private PrintWriter output;
		private String turma;

		public Connection(Socket client)
		{
			this.mainSocket = client;
		}

		@Override
		public void run()
		{
			try
			{
				this.input = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
				this.output = new PrintWriter(mainSocket.getOutputStream(), true);
				char clientType = input.readLine().toUpperCase().charAt(0);
				System.out.printf("-- CLIENTE: %s --\n", (clientType == 'A' ? "ALUNO" : "PROFESSOR"));

				if (clientType == 'A')
				{
					clientAluno();
				}
				else
				{
					clientProf();
				}
			}
			catch (Exception ex)
			{
				System.out.println("-- ERRO: " + ex.getMessage() + " --");
			}
		}

		public void clientProf() throws IOException
		{
			String turma = input.readLine();
			String isEnd = "false";
			ArrayList<String> alunos = new ArrayList<String>();

			if (Server.turmasAbertas.containsKey(turma))
			{
				Turma turmaAtual = Server.turmasAbertas.get(turma);
				alunos = turmaAtual.getAlunos();
				isEnd = "true";
			}

			String retorno = Server.toggleTurma(turma);
			output.println(retorno);
			output.println(isEnd);
			if (Boolean.parseBoolean(isEnd))
			{
				output.println(alunos.size());
				for (String str : alunos)
				{
					output.println(str);
				}
			}
		}

		public void clientAluno() throws IOException
		{
			String mtr = input.readLine();
			turma = input.readLine();
			output.println(Server.registraChamadaAluno(turma, mtr));
		}

	}
