package empregado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import empregado.conexao.Conexao;
import empregado.entidade.Departamento;

public class DepartamentoDAO {
	
	// definição das variáveis para manipular os dados do banco
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private Conexao conexao;
	
	public DepartamentoDAO() {
		conexao = new Conexao();
	}
	
	// método para inserir os dados de um departamento no banco
	public void inserir(Departamento departamento) {
		sql = "insert into java_departamento(id, nome) values(?, ?)";
		
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, departamento.getId());
			ps.setString(2, departamento.getNome());
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);			
		}
	}

	// método para pesquisar um departamento
	public boolean pesquisar(Departamento departamento) {
		boolean aux = false;
		sql = "select * from java_departamento where id = ? or nome = ?";
		
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, departamento.getId());
			ps.setString(2, departamento.getNome());
			rs = ps.executeQuery();
			if(rs.next()) {
				aux = true;
			}
			ps.close();
			rs.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);			
		}
		
		return aux;
	}

	// método para listar todos os departamentos
	public List<Departamento> listar() {
		List<Departamento> lista = new ArrayList<>();
		sql = "select * from java_departamento order by id";
		
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				lista.add(new Departamento(rs.getInt("id"), rs.getString("nome")));
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar departamento\n" + e);
		}		
		return lista;
	}
}
