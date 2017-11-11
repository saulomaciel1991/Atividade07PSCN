package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import basicas.Pessoa;
import dao.DAOFactory;
import dao.DAOPessoa;

@WebServlet("adicionarPessoa")
public class AdicionarPessoa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdicionarPessoa() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String idade = request.getParameter("idade");
		String peso = request.getParameter("peso");
		String altura = request.getParameter("altura");
		PrintWriter out = null;
		out = response.getWriter();

		Pessoa p = new Pessoa();
		p.setNome(nome);
		p.setSobrenome(sobrenome);
		p.setIdade(Integer.parseInt(idade));
		p.setPeso(Double.parseDouble(peso));
		p.setAltura(Double.parseDouble(altura));
		DAOPessoa dao = new DAOPessoa(DAOFactory.getInstance());
		
		try {
			dao.inserir(p);
			out.println("Registro salvo com sucesso!");
		} catch (Exception e) {
			out.println("Falha a salvar!!");
		}
	
	}
}
