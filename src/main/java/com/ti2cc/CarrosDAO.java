package com.ti2cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarrosDAO extends DAO{
	public CarrosDAO() {
		super();
		conectar();
		
	}
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Carros carros) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO Carros (codigo, placa, marca, modelo, ano, preco)" + 
			"VALUES ("+ carros.getCodigo()+ ",'"+ carros.getPlaca() + "','" + carros.getMarca() + "','" +
					carros.getModelo() + "','" + carros.getAno() + "'," + carros.getPreco() +");";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		}catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Carros get(int codigo) {
		Carros carro = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Carros WHERE codigo = " + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				carro = new Carros(rs.getInt("codigo"), rs.getString("placa"), rs.getString("marca"), rs.getString("modelo"), rs.getString("ano"), rs.getDouble("preco"));
			}
			st.close();
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	public List<Carros> get(){
		return get("");
	}
	
	public List<Carros> getOrderByCodigo(){
		return get("codigo");
	}
	
	public List<Carros> getOrderByPlaca(){
		return get("placa");
	}
	
	public List<Carros> getOrderByMarca(){
		return get("marca");
	}
	
	public List<Carros> getOrderByModelo(){
		return get("modelo");
	}
	
	public List<Carros> getOrderByAno(){
		return get("ano");
	}
	
	public List<Carros> getOrderByPreco(){
		return get("preco");
	}
	
	private List<Carros> get(String orderBy){
		List<Carros> carro = new ArrayList<Carros>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Carros" + ((orderBy.trim().length() == 0)? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Carros u = new Carros(rs.getInt("codigo"), rs.getString("placa"), rs.getString("marca"), rs.getString("modelo"), rs.getString("ano"), rs.getDouble("preco"));
				carro.add(u);
			}
			st.close();
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	public boolean update(Carros carro) {
		boolean status = false;
		
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE Carros SET placa = '" + carro.getPlaca() + "', marca= '" + carro.getMarca() + "', modelo= '" + carro.getModelo() + "', ano= '" +
			carro.getAno() + "', preco= '" + carro.getPreco() + "'" + "WHERE codigo = " + carro.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM Carros WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
			
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	
 }
