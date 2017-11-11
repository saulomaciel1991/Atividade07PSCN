package dao;

import java.util.List;

public interface IDAOGenerico<E> {
	public void inserir(E e);
	public void excluir(E e);
	public void alterar(E e);
	public E buscarPorID(Long id);
	public List<E> listar();
}

