package dao;

import javax.persistence.EntityManagerFactory;

import basicas.Pessoa;

public class DAOPessoa extends DAOGenerico<Pessoa>{

	public DAOPessoa(EntityManagerFactory emf) {
		super(emf);
	}

}
