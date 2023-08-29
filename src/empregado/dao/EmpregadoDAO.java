package empregado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import empregado.conexao.Conexao;
import empregado.entidade.Departamento;
import empregado.entidade.Empregado;

public class EmpregadoDAO {
	// definição das variáveis para manipular os dados do banco
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private Conexao conexao;

	public EmpregadoDAO() {
		conexao = new Conexao();
	}

	// método para pesquisar um empregado pelo ID
	public Empregado pesquisar(int id) {
		Empregado empregado = null;
		sql = "select e.nome, e.salario, d.nome as departamento from java_empregado e\r\n" +
				"join java_departamento d\r\n" +
				"on e.id_departamento = d.id where e.id = ?";
		
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				String nome =  rs.getString("nome");
				double salario = rs.getDouble("salario");
				String nomeDep = rs.getString("departamento");
				Departamento departamento = new Departamento(0, nomeDep);
				empregado = new Empregado(id, nome, salario, departamento);
			}
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("erro ao pesquisar empregado\n" + e);
		}		
		return empregado;
	}

	// método para inserir um empregado na base de dados
	public void inserir(Empregado empregado) {
		sql = "insert into java_empregado values(?, ?, ?, ?)";
		
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, empregado.getId());
			ps.setString(2, empregado.getNome());
			ps.setDouble(3, empregado.getSalario());
			ps.setInt(4, empregado.getDepartamento().getId());
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("erro ao pesquisar empregado\n" + e);
		}		
		
	}

	public void remover(int id) {
		String sql = "delete from java_empregado where id = ?";

		try (Connection connection = conexao.conectar()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			System.out.println("erro ao remover empregado\n" + e);
		}
	}


	public List<Empregado> listar() {
		List<Empregado> lista = new ArrayList<>();
		sql = "select e.nome, e.salario, d.nome as departamento from java_empregado e\r\n" +
				"	join java_departamento d\r\n" +
				"	on e.id_departamento = d.id";

		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
				String nome =  rs.getString("nome");
				double salario = rs.getDouble("salario");
				String nomeDep = rs.getString("departamento");
				Departamento departamento = new Departamento(0, nomeDep);
				Empregado empregado = new Empregado(0, nome, salario, departamento);
				lista.add(empregado);
			}
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("erro ao listar empregado\n" + e);
		}
		return lista;
	}

	public void atualizar(Empregado empregado){
		sql= "update java_empregado set nome= ?, salario= ?, where id= ?";
		try(Connection connection = conexao.conectar()) {
			ps = connection.prepareStatement(sql);
			ps.setString(1, empregado.getNome());
			ps.setDouble(2, empregado.getSalario());
			ps.setInt(3, empregado.getId());
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("erro ao pesquisar empregado\n" + e);
		}
	}

}
