package server;

import java.util.ArrayList;

public class Turma
	{
		private String numTurma;
		private ArrayList<String> alunos;

		public Turma(String turma)
		{
			this.numTurma = turma;
			alunos = new ArrayList<String>();
		}

		public String getNumTurma()
		{
			return numTurma;
		}

		public void setNumTurma(String numTurma)
		{
			this.numTurma = numTurma;
		}

		public ArrayList<String> getAlunos()
		{
			return alunos;
		}

		public void addAluno(String aluno)
		{
			alunos.add(aluno);
		}
		public void removeAluno(String aluno)
		{
			alunos.remove(aluno);
		}

	}
